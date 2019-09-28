package com.github.pmtischler.opmode;

import com.github.pmtischler.control.Mecanum;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Robot code that is almost always needed.
 */
public class BaseRobot extends OpMode {
    /**
     * Initializes the robot.
     */
    public void init() {
        initializeDriveMotors(
                "dfl", "dfr",
                "dbl", "dbr");
    }

    /**
     * Initializes the drive motors.
     */
    protected void initializeDriveMotors(
            String frontLeft, String frontRight,
            String backLeft, String backRight) {
        dfl = hardwareMap.dcMotor.get(frontLeft);
        dfr = hardwareMap.dcMotor.get(frontRight);
        dbl = hardwareMap.dcMotor.get(backLeft);
        dbr = hardwareMap.dcMotor.get(backRight);
    }

    /**
     * Main loop function.
     */
    public void loop() {}

    /**
     * Tank drive control from gamepad to drive motors.
     */
    protected void gamepadTankDrive() {
        dfl.setPower(gamepad1.left_stick_y);
        dbl.setPower(gamepad1.left_stick_y);
        dfr.setPower(gamepad1.right_stick_y);
        dbr.setPower(gamepad1.right_stick_y);
    }

    /**
     * Mecanum drive control from gamepad to drive motors.
     */
    protected void gamepadMecanumDrive() {
      Mecanum.Motion motion = Mecanum.joystickToMotion(
              gamepad1.left_stick_x, gamepad1.left_stick_y,
              gamepad1.right_stick_x, gamepad1.right_stick_y);

      Mecanum.Wheels wheels = Mecanum.motionToWheels(motion);
      dfl.setPower(wheels.frontLeft);
      dfr.setPower(wheels.frontRight);
      dbl.setPower(wheels.backLeft);
      dbr.setPower(wheels.backRight);
    }

    /**
     * Stops the robot.
     */
    public void stop() {
        // Stop all drive motors.
        dfl.setPower(0);
        dfr.setPower(0);
        dbl.setPower(0);
        dbr.setPower(0);
    }

    // The drive motors (front, back; left, right).
    private DcMotor dfl;
    private DcMotor dfr;
    private DcMotor dbl;
    private DcMotor dbr;
}
