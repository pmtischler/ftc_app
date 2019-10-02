package org.firstinspires.ftc.teamcode;

import com.github.pmtischler.control.Mecanum;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

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
    }

    /**
     * Main loop function.
     * Called repeatedly during the match after pressing "Play".
     */
    public void loop() {
        // Drive using Mecanum controls for gamepad 1.
        mecanum.setDriveFromGamepad(gamepad1);

        // Open / close the gripper.
        //   A     -> Close.
        //   A + B -> Half open.
        //       B -> Open.
        if (gamepad1.a && gamepad1.b) {
            gripper.openLeft();
        } else if (gamepad1.a) {
            gripper.close();
        } else if (gamepad1.b) {
            gripper.open();
        }

        // Rotate the gripper.
        //   Y -> Front to back.
        //   X -> Left to right.
        if (gamepad1.y) {
            gripper.rotateToFrontBack();
        } else if (gamepad1.x) {
            gripper.rotateToLeftRight();
        }
    }

    /**
     * Stops the robot.
     * Called once at the end of the match when time runs out.
     */
    public void stop() {}

    // Controls driving of the robot.
    private Mecanum.Drive mecanum;
    // Controls the gripper for stones.
    private Gripper gripper;
}
