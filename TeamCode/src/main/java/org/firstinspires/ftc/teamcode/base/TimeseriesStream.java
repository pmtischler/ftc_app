package org.firstinspires.ftc.teamcode.base;

import java.io.EOFException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Timeseries streaming.
 * Writes and reads timeseries streams.
 */
public class TimeseriesStream {
    /**
     * Data point in a time series.
     * A variable has a Datapoint's value until the next instance in the stream.
     */
    public static class DataPoint implements java.io.Serializable {
        // Name of the variable.
        public final String varname;
        // Time of the data point (seconds, inclusive).
        public final double timestamp;
        // Value of the variable at the timestamp.
        public final double value;

        /**
         * Creates a DataPoint.
         * @param varname The name of the variable.
         * @param timestamp The time of the data point.
         * @param value The value of the variable.
         */
        public DataPoint(String varname, double timestamp, double value) {
            this.varname = varname;
            this.timestamp = timestamp;
            this.value = value;
        }
    }

    /**
     * Timeseries writer.
     */
    public static class Writer {
        /**
         * Creates the Writer.
         * @param outputStream The output stream to write to.
         */
        public Writer(OutputStream outputStream) throws Exception {
            this.outputStream = new ObjectOutputStream(outputStream);
            lastTimestamp = 0;
        }

        /**
         * Writes a DataPoint to the output stream.
         * Calls to this function must be done with non-decreasing timestamps.
         */
        public void write(DataPoint point) throws Exception {
            if (point.timestamp < lastTimestamp) {
                throw new IllegalArgumentException("Timestamp decreased.");
            }
            outputStream.writeObject(point);
            lastTimestamp = point.timestamp;
        }

        // The object output stream.
        private ObjectOutputStream outputStream;
        // The last timestamp seen.
        private double lastTimestamp;
    }

    /**
     * Timeseries reader.
     */
    public static class Reader {
        /**
         * Creates the Reader.
         * @param inputStream The input stream to read from.
         */
        public Reader(InputStream inputStream) throws Exception {
            this.inputStream = new ObjectInputStream(inputStream);
            nextPoint = null;
            endOfStream = false;
        }

        /**
         * Reads a DataPoint from the input stream.
         * @return DataPoint if available, null otherwise.
         */
        public DataPoint read() throws Exception {
            // Return next data point if stored.
            if (nextPoint != null) {
                DataPoint ret = nextPoint;
                nextPoint = null;
                return ret;
            }
            try {
                return (DataPoint)inputStream.readObject();
            } catch (EOFException e) {
                endOfStream = true;
                return null;
            }
        }

        /**
         * Reads all DataPoint up to specific time.
         * @param time The timestamp to read up to (seconds, inclusive).
         * @return The DataPoint read.
         */
        public List<DataPoint> readUntil(double time) throws Exception {
            ArrayList<DataPoint> points = new ArrayList<DataPoint>();
            // Read from stream until past time, then store as next.
            while (true) {
                DataPoint p = read();  // Returns next if stored.
                if (p == null) {
                    // No more points.
                    return points;
                }
                if (p.timestamp <= time) {
                    points.add(p);
                } else {
                    // Point past read time, store for future.
                    nextPoint = p;
                    return points;
                }
            }
        }

        // Returns whether the time series read stream is complete.
        public boolean readDone() {
            return nextPoint == null && endOfStream;
        }

        // The object input stream.
        private ObjectInputStream inputStream;
        // The next point to be returned.
        private DataPoint nextPoint;
        // Whether the read is done.
        private boolean endOfStream;
    }
}
