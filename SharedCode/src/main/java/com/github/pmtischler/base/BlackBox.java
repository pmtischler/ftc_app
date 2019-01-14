package com.github.pmtischler.base;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Robot BlackBox recording and playback.
 * Writes hardware state as a time series and plays it back.
 */
public class BlackBox {
    /**
     * Writes hardware state as a timeseries stream.
     */
    public static class Recorder {
        /**
         * Creates the recorder.
         * @param hardware The hardware to record.
         * @param outputStream The output stream to write.
         */
        public Recorder(HardwareMap hardware, OutputStream outputStream) throws Exception {
            this.hardware = hardware;
            writer = new TimeseriesStream.Writer(outputStream);
        }

        /**
         * Records all hardware devices.
         * @param time The time to record hardware at (seconds).
         */
        public void recordAllDevices(double time) throws Exception {
            for (Map.Entry<String, DcMotor> entry : hardware.dcMotor.entrySet()) {
                recordDevice(entry.getKey(), time);
            }
            for (Map.Entry<String, Servo> entry : hardware.servo.entrySet()) {
                recordDevice(entry.getKey(), time);
            }
        }

        /**
         * Records the specific hardware device.
         * @param deviceName The device to record.
         * @param time The time to record hardware at (seconds).
         */
        public void recordDevice(String deviceName, double time) throws Exception {
            HardwareDevice device = hardware.get(deviceName);
            double value;
            if (device instanceof DcMotor) {
                value = ((DcMotor)device).getPower();
            } else if (device instanceof Servo) {
                value = ((Servo)device).getPosition();
            } else {
                return;
            }
            writer.write(new TimeseriesStream.DataPoint(deviceName, time, value));
        }

        // The hardware to record.
        private HardwareMap hardware;
        // The timeseries stream to write.
        private TimeseriesStream.Writer writer;
    }

    /**
     * Reads hardware state from a timeseries stream and applies it.
     */
    public static class Player {
        /**
         * Creates the player.
         * @param inputStream The input stream to read from.
         * @param hardware The hardware to manipulate.
         */
        public Player(InputStream inputStream, HardwareMap hardware) throws Exception {
            this.hardware = hardware;
            reader = new TimeseriesStream.Reader(inputStream);
        }

        /**
         * Playbacks the hardware up to the time.
         * @param time The time to playback up to (seconds).
         * @return True if playback is still running. False if it has completed.
         */
        public boolean playback(double time) throws Exception {
            List<TimeseriesStream.DataPoint> points = reader.readUntil(time);
            for (TimeseriesStream.DataPoint p : points) {
                HardwareDevice device = hardware.get(p.varname);
                if (device != null) {
                    if (device instanceof DcMotor) {
                        ((DcMotor)device).setPower(p.value);
                    }
                    if (device instanceof Servo) {
                        ((Servo)device).setPosition(p.value);
                    }
                }
            }
            return !reader.readDone();
        }

        // The hardware to manipulate.
        private HardwareMap hardware;
        // The timeseries stream to read.
        private TimeseriesStream.Reader reader;
    }
}
