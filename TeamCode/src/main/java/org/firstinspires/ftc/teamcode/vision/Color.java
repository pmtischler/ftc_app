package org.firstinspires.ftc.teamcode.vision;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * Color handling code.
 */
public class Color {
    /**
     * Converts an RGB pixel to HSV.
     * Convenience wrapper of Mat version.
     */
    public static Mat rgbToHsv(int red, int green, int blue) {
        Mat rgb = new Mat(1, 1, CvType.CV_8UC3);
        byte[] pixel = new byte[3];
        pixel[0] = toUnsignedByte(red);
        pixel[1] = toUnsignedByte(green);
        pixel[2] = toUnsignedByte(blue);
        rgb.put(0, 0, pixel);
        return rgbToHsv(rgb);
    }

    /**
     * Converts an RGB matrix to HSV.
     */
    public static Mat rgbToHsv(Mat rgb) {
        Mat hsv = new Mat();
        Imgproc.cvtColor(rgb, hsv, Imgproc.COLOR_RGB2HSV);
        return hsv;
    }

    /**
     * Determines whether the given RGB color is yellow.
     * Convenience wrapper of HSV version.
     */
    public static boolean rgbIsYellow(int red, int green, int blue) {
        return hsvIsYellow(rgbToHsv(red, green, blue));
    }

    /**
     * Determines whether the given HSV color is yellow.
     */
    public static boolean hsvIsYellow(Mat hsv) {
        byte[] pixel = new byte[3];
        hsv.get(0, 0, pixel);
        int hue = toUnsignedInt(pixel[0]);
        int saturation = toUnsignedInt(pixel[1]);
        int value = toUnsignedInt(pixel[2]);
        // Not too white, not too black, in the range of yellow hue.
        return saturation > 25 && value > 127 && hue > 50/2 && hue < 70/2;
    }

    // Utility not available in FTC's version of Java.
    private static byte toUnsignedByte(int value) {
        return (byte)(value & (-1L >>> 32));
    }

    // Utility not available in FTC's version of Java.
    private static int toUnsignedInt(byte value) {
        return ((int) value) & 0xff;
    }
}
