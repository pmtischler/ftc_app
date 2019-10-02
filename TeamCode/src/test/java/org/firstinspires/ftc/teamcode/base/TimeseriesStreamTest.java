package org.firstinspires.ftc.teamcode.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests correctness of TimeseriesStream.
 */
public class TimeseriesStreamTest {
    // The comparison threshold.
    private static final double diffThresh = 0.00001;
    // The data points written.
    private ArrayList<TimeseriesStream.DataPoint> points;
    // The input stream for data written.
    private InputStream inputStream;
    // The series reader.
    private TimeseriesStream.Reader reader;

    @Before
    public void setUp() throws Exception {
        points = new ArrayList<TimeseriesStream.DataPoint>();
        points.add(new TimeseriesStream.DataPoint("A", 0, 1));
        points.add(new TimeseriesStream.DataPoint("B", 0, 2));
        points.add(new TimeseriesStream.DataPoint("A", 0, 2));
        points.add(new TimeseriesStream.DataPoint("A", 1, 3));
        points.add(new TimeseriesStream.DataPoint("A", 2, 4));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        TimeseriesStream.Writer writer = new TimeseriesStream.Writer(outputStream);
        for (TimeseriesStream.DataPoint p : points) {
            writer.write(p);
        }

        inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        reader = new TimeseriesStream.Reader(inputStream);
    }


    @Test
    // Test read.
    public void testRead() throws Exception {
        for (TimeseriesStream.DataPoint p : points) {
            assertPointsEqual(p, reader.read());
        }
        assertNull(reader.read());
        assertNull(reader.read());
    }

    @Test
    // Test readUntil.
    public void testReadUntil() throws Exception {
        // Read first set.
        assertPointsEqual(points.subList(0, 3), reader.readUntil(0));
        // Read at same timestamp returns nothing.
        assertPointsEqual(points.subList(3, 3), reader.readUntil(0));
        // Next read leverages nextPoint.
        assertPointsEqual(points.subList(3, 5), reader.readUntil(2));
        // Next read returns nothing.
        assertPointsEqual(points.subList(5, 5), reader.readUntil(10));
    }
        
    // Compares two points for equality.
    private void assertPointsEqual(TimeseriesStream.DataPoint expect,
                                   TimeseriesStream.DataPoint actual) {
        assertEquals(expect.varname, actual.varname);
        assertEquals(expect.timestamp, actual.timestamp, diffThresh);
        assertEquals(expect.value, actual.value, diffThresh);
    }

    // Compare two point lists for equality.
    private void assertPointsEqual(List<TimeseriesStream.DataPoint> expect,
                                   List<TimeseriesStream.DataPoint> actual) {
        assertEquals(expect.size(), actual.size());
        for (int i = 0; i < expect.size(); i++) {
            assertPointsEqual(expect.get(i), actual.get(i));
        }
    }
}
