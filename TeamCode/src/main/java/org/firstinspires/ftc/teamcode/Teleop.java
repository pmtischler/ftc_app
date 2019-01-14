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
        dfl = hardwareMap.dcMotor.get("dfl");
        dfr = hardwareMap.dcMotor.get("dfr");
        dbl = hardwareMap.dcMotor.get("dbl");
        dbr = hardwareMap.dcMotor.get("dbr");

        // TODO: Initialize other motors and servos.
    }

    /**
     * Main loop function.
     * Called repeatedly during the match after the "Play" button is pressed.
     */
    public void loop() {
        // Tank drive controls.
        dfl.setPower(gamepad1.left_stick_y);
        dbl.setPower(gamepad1.left_stick_y);
        dfr.setPower(gamepad1.right_stick_y);
        dbr.setPower(gamepad1.right_stick_y);

        // TODO: Add rest of robot teleop control.
    }



    /**
     * Stops the robot.
     * Called once at the end of the match when time runs out.
     */
    public void stop() {
        // Stop all drive motors.
        dfl.setPower(0);
        dfr.setPower(0);
        dbl.setPower(0);
        dbr.setPower(0);

        // TODO: Add any other stop behavior.
    }

    // The drive motors (front, back; left, right).
    private DcMotor dfl;
    private DcMotor dfr;
    private DcMotor dbl;
    private DcMotor dbr;
}
