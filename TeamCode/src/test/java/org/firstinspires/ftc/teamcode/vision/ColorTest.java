package org.firstinspires.ftc.teamcode.vision;

import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import static org.junit.Assert.assertArrayEquals;
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
        Mat hsv = Color.rgbToHsv(red, green, blue);
        byte[] pixel = new byte[3];
        hsv.get(0, 0, pixel);
        int actual_hue = Byte.toUnsignedInt(pixel[0]);
        int actual_saturation = Byte.toUnsignedInt(pixel[1]);
        int actual_value = Byte.toUnsignedInt(pixel[2]);
        assertEquals(hue / 2, actual_hue);
        assertEquals(saturation, actual_saturation);
        assertEquals(value, actual_value);
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

    @Test
    public void testHsvIsYellow() throws Exception {
        // Red.
        assertFalse(Color.rgbIsYellow(255, 0, 0));
        // Green.
        assertFalse(Color.rgbIsYellow(0, 255, 0));
        // Blue.
        assertFalse(Color.rgbIsYellow(0, 0, 255));
        // White.
        assertFalse(Color.rgbIsYellow(255, 255, 255));
        // Black.
        assertFalse(Color.rgbIsYellow(0, 0, 0));
        // Yellow.
        assertTrue(Color.rgbIsYellow(255, 255, 0));
    }
}
