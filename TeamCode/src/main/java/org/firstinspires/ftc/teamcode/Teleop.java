package org.firstinspires.ftc.teamcode;

import com.github.pmtischler.control.Mecanum;
import com.github.pmtischler.vision.Skystone;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.opencv.core.Core;

/**
 * Teleop mode.
 */
@TeleOp(name="TeamCode.Teleop", group="TeamCode")
public class Teleop extends OpMode {

    /**
     * Initializes the robot.
     * Called once before the match when the "Init" button is pressed.
     */
    public void init() {
        // Setup OpenCV library for Computer Vision.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Setup motors for Mecanum driving.
        mecanum = new Mecanum.Drive(
                hardwareMap.dcMotor.get("driveFrontLeft"),
                hardwareMap.dcMotor.get("driveFrontRight"),
                hardwareMap.dcMotor.get("driveBackLeft"),
                hardwareMap.dcMotor.get("driveBackRight"));

        // Initialize the Skystone detector.
        detector = new Skystone.Detector(
                hardwareMap.colorSensor.get("skystoneLeft"),
                hardwareMap.colorSensor.get("skystoneCenter"),
                hardwareMap.colorSensor.get("skystoneRight"));

        // TODO: Initialize other motors and servos.
    }

    /**
     * Main loop function.
     * Called repeatedly during the match after pressing "Play".
     */
    public void loop() {
        // Drive using Mecanum controls for gamepad 1.
        mecanum.setDriveFromGamepad(gamepad1);

        // Log whether the detector sees stone or skystone.
        telemetry.addLine("Skystone.Detector")
            .addData("detectsStone", detector.detectsStone())
            .addData("detectsSkystone", detector.detectsSkystone());

        // TODO: Add rest of robot teleop control.
    }

    /**
     * Stops the robot.
     * Called once at the end of the match when time runs out.
     */
    public void stop() {
        // TODO: Add stop behavior.
    }

    // Controls driving of the robot.
    private Mecanum.Drive mecanum;
    // Detects game objects.
    private Skystone.Detector detector;
}
