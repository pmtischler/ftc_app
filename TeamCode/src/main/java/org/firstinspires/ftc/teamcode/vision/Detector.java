package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.hardware.ColorSensor;
import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Detector of Skystones using color sensors.
 */
public class Detector {
    /**
     * Constructs the detector.
     * @param left The left color sensor.
     * @param center The center color sensor.
     * @param right The right color sensor.
     */
    public Detector(
            ColorSensor left, ColorSensor center, ColorSensor right) {
        this.left = left;
        this.center = center;
        this.right = right;
    }

    /**
     * @return Whether the detector sees a normal stone.
     */
    public boolean detectsStone() {
        return isYellow(left) && isYellow(center) && isYellow(right);
    }

    /**
     * @return Whether the detector sees a Skystone.
     */
    public boolean detectsSkystone() {
        return !isYellow(left) && !isYellow(center) && !isYellow(right);
    }

    /**
     * @param sensor The color sensor to evaluate.
     * @return Whether the sensor is yellow.
     */
    private boolean isYellow(ColorSensor sensor) {
        return Color.rgbIsYellowAfterNormalization(
                sensor.red(), sensor.green(), sensor.blue());
    }

    private ColorSensor left;
    private ColorSensor center;
    private ColorSensor right;
}
