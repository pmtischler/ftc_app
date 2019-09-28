package com.github.pmtischler.opmode;

import com.github.pmtischler.control.Mecanum;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Robot code that is almost always needed.
 */
public class BaseRobot extends OpMode {
    /**
     * Initializes the robot.
     */
    public void init() {
        initDriveMotors(
                "dfl", "dfr",
                "dbl", "dbr");
        initSkystoneSensors(
                "sl", "sc", "sr");
    }

    /**
     * Initializes the drive motors.
     */
    protected void initDriveMotors(
            String frontLeft, String frontRight,
            String backLeft, String backRight) {
        telemetry.addData("Drive.frontLeft", frontLeft);
        telemetry.addData("Drive.frontRight", frontRight);
        telemetry.addData("Drive.backLeft", backLeft);
        telemetry.addData("Drive.backRight", backRight);
        dfl = hardwareMap.dcMotor.get(frontLeft);
        dfr = hardwareMap.dcMotor.get(frontRight);
        dbl = hardwareMap.dcMotor.get(backLeft);
        dbr = hardwareMap.dcMotor.get(backRight);

        DcMotor[] motors = {dfl, dfr, dbl, dbr};
        for (DcMotor motor : motors) {
            // Ensure current position interpreted as 0.
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            // Re-enable direct power drive.
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    /**
     * Initializes the skystone color sensors.
     */
    protected void initSkystoneSensors(
            String left, String center, String right) {
        telemetry.addData("Skystone.left", left);
        telemetry.addData("Skystone.center", center);
        telemetry.addData("Skystone.right", right);
        sl = hardwareMap.colorSensor.get(left);
        sc = hardwareMap.colorSensor.get(center);
        sr = hardwareMap.colorSensor.get(right);
    }

    /**
     * Main loop function.
     */
    public void loop() {
        telemetry.addData(
                "Drive.frontLeft.Position", dfl.getCurrentPosition());
        telemetry.addData(
                "Drive.frontRight.Position", dfl.getCurrentPosition());
        telemetry.addData(
                "Drive.backLeft.Position", dbl.getCurrentPosition());
        telemetry.addData(
                "Drive.backRight.Position", dbr.getCurrentPosition());

        telemetry.addData("Skystone.left.red", sl.red());
        telemetry.addData("Skystone.left.green", sl.green());
        telemetry.addData("Skystone.left.blue", sl.blue());
        telemetry.addData("Skystone.center.red", sc.red());
        telemetry.addData("Skystone.center.green", sc.green());
        telemetry.addData("Skystone.center.blue", sc.blue());
        telemetry.addData("Skystone.right.red", sr.red());
        telemetry.addData("Skystone.right.green", sr.green());
        telemetry.addData("Skystone.right.blue", sr.blue());
    }

    /**
     * Tank drive control from gamepad to drive motors.
     */
    protected void tankDriveFromGamepad() {
        double left = gamepad1.left_stick_y;
        double right = gamepad1.right_stick_y;
        telemetry.addData("Tank.left", left);
        telemetry.addData("Tank.right", right);
        dfl.setPower(left);
        dbl.setPower(left);
        dfr.setPower(right);
        dbr.setPower(right);
    }

    /**
     * Mecanum drive control from gamepad to drive motors.
     */
    protected void mecanumDriveFromGamepad() {
        Mecanum.Motion motion = Mecanum.joystickToMotion(
                gamepad1.left_stick_x, gamepad1.left_stick_y,
                gamepad1.right_stick_x, gamepad1.right_stick_y);
        mecanumDriveFromMotion(motion);
    }

    /**
     * Mecanum drive control from Mecanum motion parameters.
     */
    protected void mecanumDriveFromMotion(Mecanum.Motion motion) {
        telemetry.addData("Mecanum.vD", motion.vD);
        telemetry.addData("Mecanum.thetaD", motion.thetaD);
        telemetry.addData("Mecanum.vTheta", motion.vTheta);
        Mecanum.Wheels wheels = Mecanum.motionToWheels(motion);
        telemetry.addData("Mecanum.frontLeft", wheels.frontLeft);
        telemetry.addData("Mecanum.frontRight", wheels.frontRight);
        telemetry.addData("Mecanum.backLeft", wheels.backLeft);
        telemetry.addData("Mecanum.backRight", wheels.backRight);
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

    // The skystone color sensors.
    private ColorSensor sl;
    private ColorSensor sc;
    private ColorSensor sr;
}
