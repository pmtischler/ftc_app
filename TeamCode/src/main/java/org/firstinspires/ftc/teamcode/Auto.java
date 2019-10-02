package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.control.Mecanum;
import org.opencv.core.Core;

@Autonomous(name="TeamCode.Auto", group="TeamCode")
public class Auto extends OpMode {

    /**
     * Initializes the robot.
     * Called once before the match when the "Init" button is pressed.
     */
    public void init() {
        // Setup OpenCV library for Computer Vision.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Setup motors for Mecanum driving.
        mecanum = new Mecanum.Drive(
                hardwareMap.dcMotor.get("dfl"),
                hardwareMap.dcMotor.get("dfr"),
                hardwareMap.dcMotor.get("dbl"),
                hardwareMap.dcMotor.get("dbr"));

        // Initialize the Skystone detector.
        skystoneLeft = hardwareMap.colorSensor.get("sl");
        skystoneCenter = hardwareMap.colorSensor.get("sc");
        skystoneRight = hardwareMap.colorSensor.get("sr");
        detector = new Detector(
                skystoneLeft, skystoneCenter, skystoneRight);
    }

    /**
     * Main loop function.
     * Called repeatedly during the match after pressing "Play".
     */
    public void loop() {
        // Log the color sensor readings.
        telemetry.addLine("Skystone.Color.Left")
            .addData("red", skystoneLeft.red())
            .addData("green", skystoneLeft.green())
            .addData("blue", skystoneLeft.blue());
        telemetry.addLine("Skystone.Color.Center")
            .addData("red", skystoneCenter.red())
            .addData("green", skystoneCenter.green())
            .addData("blue", skystoneCenter.blue());
        telemetry.addLine("Skystone.Color.Right")
            .addData("red", skystoneRight.red())
            .addData("green", skystoneRight.green())
            .addData("blue", skystoneRight.blue());

        // Log whether the detector sees stone or skystone.
        telemetry.addLine("Detector")
            .addData("detectsStone", detector.detectsStone())
            .addData("detectsSkystone", detector.detectsSkystone());
    }

    /**
     * Stops the robot.
     * Called once at the end of the match when time runs out.
     */
    public void stop() {}

    // Controls driving of the robot.
    private Mecanum.Drive mecanum;
    // The Skystone color sensors.
    private ColorSensor skystoneLeft;
    private ColorSensor skystoneCenter;
    private ColorSensor skystoneRight;
    // Detects stones and skystone objects.
    private Detector detector;
}
