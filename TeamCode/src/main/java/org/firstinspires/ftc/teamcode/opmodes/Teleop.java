package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Teleop mode.
 */
@TeleOp(name="TeamCode.Teleop", group="TeamCode")
public class Teleop extends Hardware {

    /**
     * Initializes the robot.
     * Called once before the match when the "Init" button is pressed.
     */
    public void init() {
        super.init();
    }

    /**
     * Main loop function.
     * Called repeatedly during the match after pressing "Play".
     */
    public void loop() {
        super.loop();

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

        // Extends the gantry.
        //   R1 -> Extend.
        //   L1 -> Retract.
        if (gamepad1.right_bumper) {
            gantry.setXSpeed(0.5);
        } else if (gamepad1.left_bumper) {
            gantry.setXSpeed(-0.5);
        } else {
            gantry.setXSpeed(0);
        }

        // Raises the gantry.
        //   R2 -> Raises.
        //   L2 -> Lowers.
        if (gamepad1.right_trigger > 0.1) {
            gantry.setZSpeed(gamepad1.right_trigger);
        } else if (gamepad1.left_trigger > 0.1) {
            gantry.setZSpeed(-gamepad1.left_trigger);
        } else {
            gantry.setZSpeed(0);
        }
    }

    /**
     * Stops the robot.
     * Called once at the end of the match when time runs out.
     */
    public void stop() {
        super.stop();
    }
}
