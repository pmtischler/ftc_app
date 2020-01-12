package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.base.PeriodicTelemetry;
import org.firstinspires.ftc.teamcode.control.Gantry;
import org.firstinspires.ftc.teamcode.control.Gripper;
import org.firstinspires.ftc.teamcode.control.Mecanum;
import org.firstinspires.ftc.teamcode.vision.Detector;
import org.opencv.core.Core;

/**
 * Base robot class which configures all hardware.
 */
public class Hardware extends OpMode {
    /**
     * Initializes the robot.
     * Called once before the match when the "Init" button is pressed.
     */
    public void init() {
        // Setup OpenCV library for Computer Vision.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Generate telemetry at defined rate.
        periodicTelemetry = new PeriodicTelemetry(telemetry);
        periodicTelemetry.setPeriodSeconds(1.0);

        // Setup motors for Mecanum driving.
        mecanum = new Mecanum.Drive(
                hardwareMap.dcMotor.get("dfl"),
                hardwareMap.dcMotor.get("dfr"),
                hardwareMap.dcMotor.get("dbl"),
                hardwareMap.dcMotor.get("dbr"));
        // Setup gripper for the stone.
        gripper = new Gripper(
                hardwareMap.servo.get("gl"),
                hardwareMap.servo.get("gr"),
                hardwareMap.servo.get("gw"));
        // Setup gantry for moving the gripper.
        gantry = new Gantry(
                hardwareMap.dcMotor.get("gx"),
                hardwareMap.dcMotor.get("gz"));

        // Setup the Skystone detector.
        skystoneLeft = hardwareMap.get(ColorSensor.class, "sl");
        periodicTelemetry.addTelemetrySource(
                new ColorSensorTelemetry("sl", skystoneLeft));
        skystoneCenter = hardwareMap.get(ColorSensor.class, "sc");
        periodicTelemetry.addTelemetrySource(
                new ColorSensorTelemetry("sc", skystoneCenter));
        skystoneRight = hardwareMap.get(ColorSensor.class, "sr");
        periodicTelemetry.addTelemetrySource(
                new ColorSensorTelemetry("sr", skystoneRight));
        detector = new Detector(skystoneLeft, skystoneCenter, skystoneRight);
        periodicTelemetry.addTelemetrySource(new Func<String>() {
            @Override public String value() {
                return String.format(
                        "[detector] stone:%b skystone:%b",
                        detector.detectsStone(), detector.detectsSkystone());
            }
        });

        distanceLeft = hardwareMap.get(DistanceSensor.class, "dl");
        periodicTelemetry.addTelemetrySource(
                new DistanceSensorTelemetry("dl", distanceLeft));
        distanceRight = hardwareMap.get(DistanceSensor.class, "dr");
        periodicTelemetry.addTelemetrySource(
                new DistanceSensorTelemetry("dr", distanceRight));

        // Initialize to retracted position.
        gripper.close();
        gripper.rotateToLeftRight();
        gantry.setXSpeed(0);
        gantry.setZSpeed(0);
    }

    /**
     * Main loop function.
     * Called repeatedly during the match after pressing "Play".
     */
    public void loop() {
        periodicTelemetry.loop(time);
    }

    /**
     * Stops the robot.
     * Called once at the end of the match when time runs out.
     */
    public void stop() {
    }

    /**
     * Generates telemetry lines for a color sensor.
     */
    private static class ColorSensorTelemetry implements Func<String> {
        ColorSensorTelemetry(String name, ColorSensor sensor) {
            this.name = name;
            this.sensor = sensor;
        }

        @Override public String value() {
            return String.format(
                    "[%s] r:%d g:%d b:%d",
                    name, sensor.red(), sensor.green(), sensor.blue());
        }

        private String name;
        private ColorSensor sensor;
    }

    /**
     * Generates telemetry lines for a distance sensor.
     */
    private static class DistanceSensorTelemetry implements Func<String> {
        DistanceSensorTelemetry(String name, DistanceSensor sensor) {
            this.name = name;
            this.sensor = sensor;
        }

        @Override public String value() {
            return String.format(
                    "[%s] cm:%f",
                    name, sensor.getDistance(DistanceUnit.CM));
        }

        private String name;
        private DistanceSensor sensor;
    }

    // Periodic telemetry generation at user-controlled rate.
    PeriodicTelemetry periodicTelemetry;
    // Controls driving of the robot.
    protected Mecanum.Drive mecanum;
    // Controls the gripper for stones.
    protected Gripper gripper;
    // Gantry which moves the gripper.
    protected Gantry gantry;
    // The Skystone color sensors.
    protected ColorSensor skystoneLeft;
    protected ColorSensor skystoneCenter;
    protected ColorSensor skystoneRight;
    // Detects stones and skystone objects.
    protected Detector detector;
    // Distance sensors.
    protected DistanceSensor distanceLeft;
    protected DistanceSensor distanceRight;
}
