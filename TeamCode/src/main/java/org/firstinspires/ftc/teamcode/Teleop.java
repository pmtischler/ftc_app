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
                hardwareMap.dcMotor.get("driveFrontLeft"),
                hardwareMap.dcMotor.get("driveFrontRight"),
                hardwareMap.dcMotor.get("driveBackLeft"),
                hardwareMap.dcMotor.get("driveBackRight"));
    }

    /**
     * Main loop function.
     * Called repeatedly during the match after pressing "Play".
     */
    public void loop() {
        // Drive using Mecanum controls for gamepad 1.
        mecanum.setDriveFromGamepad(gamepad1);
    }

    /**
     * Stops the robot.
     * Called once at the end of the match when time runs out.
     */
    public void stop() {}

    // Controls driving of the robot.
    private Mecanum.Drive mecanum;
}
