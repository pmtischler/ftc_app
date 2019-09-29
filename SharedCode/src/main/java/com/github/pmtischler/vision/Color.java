package com.github.pmtischler.vision;

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
        byte[] pixel = new byte[3];
        pixel[0] = (byte)red;
        pixel[1] = (byte)green;
        pixel[2] = (byte)blue;
        Mat rgb = new Mat(1, 1, CvType.CV_8UC3);
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
     * Determines whether the given HSV color is yellow.
     */
    public static boolean hsvIsYellow(Mat hsv) {
        byte[] pixel = new byte[3];
        hsv.get(0, 0, pixel);
        byte hue = pixel[0];
        byte saturation = pixel[1];
        byte value = pixel[2];
        // Not too white, not too black, in the range of yellow hue.
        return saturation > 25 && value > 127 && hue > 50 && hue < 70;
    }
}
