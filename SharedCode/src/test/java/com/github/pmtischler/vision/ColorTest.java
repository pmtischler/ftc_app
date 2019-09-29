package com.github.pmtischler.vision;

import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests correctness of Color.
 */
public class ColorTest {

    @Before
    public void setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public void assertRgbToHsv(int red, int green, int blue,
                               int hue, int saturation, int value) {
        Mat hsv = Color.rgbToHsv(red, green,blue);
        byte[] pixel = new byte[3];
        hsv.get(0, 0, pixel);
        assertEquals(pixel[0], (byte)hue);
        assertEquals(pixel[1], (byte)saturation);
        assertEquals(pixel[2], (byte)value);
    }

    @Test
    public void testRgbToHsv() throws Exception {
        // Red.
        assertRgbToHsv(255,   0,   0,
                         0, 255, 255);
        // Green.
        assertRgbToHsv(  0, 255,   0,
                       120, 255, 255);
        // Blue.
        assertRgbToHsv(  0,   0, 255,
                       240, 255, 255);
        // White.
        assertRgbToHsv(255, 255, 255,
                         0,   0, 255);
        // Black.
        assertRgbToHsv(  0,   0,   0,
                         0,   0,   0);
        // Yellow.
        assertRgbToHsv(255, 255,   0,
                        60, 255, 255);
    }

    public boolean hsvIsYellow(int hue, int saturation, int value) {
        byte[] pixel = new byte[3];

        Mat hsv = new Mat(1, 1, CvType.CV_8UC3);
        pixel[0] = (byte)hue;
        pixel[1] = (byte)saturation;
        pixel[2] = (byte)value;
        hsv.put(0, 0, pixel);

        return Color.hsvIsYellow(hsv);
    }

    public void testHsvIsYellow() throws Exception {
        // Red.
        assertFalse(hsvIsYellow(0, 255, 255));
        // Green.
        assertFalse(hsvIsYellow(120, 255, 255));
        // Blue.
        assertFalse(hsvIsYellow(240, 255, 255));
        // White.
        assertFalse(hsvIsYellow(0, 0, 255));
        // Black.
        assertFalse(hsvIsYellow(0, 0, 0));
        // Yellow.
        assertTrue(hsvIsYellow(60, 255, 255));
    }
}
