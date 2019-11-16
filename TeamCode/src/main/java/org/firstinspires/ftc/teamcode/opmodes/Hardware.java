package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
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

        // Setup the Skystone detector.
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
    }

    /**
     * Stops the robot.
     * Called once at the end of the match when time runs out.
     */
    public void stop() {
    }

    // Controls driving of the robot.
    protected Mecanum.Drive mecanum;
    // Controls the gripper for stones.
    protected Gripper gripper;
    // The Skystone color sensors.
    protected ColorSensor skystoneLeft;
    protected ColorSensor skystoneCenter;
    protected ColorSensor skystoneRight;
    // Detects stones and skystone objects.
    protected Detector detector;
}
