package com.github.pmtischler.control;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Tank drive.
 */
public class Tank {

    /**
     * Tank style drive control of motors.
     */
    public static class Drive {
        /**
         * Constructs the drive.
         * @param frontLeft The front left drive motor.
         * @param frontRight The front right drive motor.
         * @param backLeft The back left drive motor.
         * @param backRight The back right drive motor.
         */
        public Drive(DcMotor frontLeft, DcMotor frontRight,
                     DcMotor backLeft, DcMotor backRight) {
            this.frontLeft = frontLeft;
            this.frontRight = frontRight;
            this.backLeft = backLeft;
            this.backRight = backRight;
        }

        /**
         * Sets the drive power of the motors.
         * @param leftPower The left side tank power.
         * @param rightPower The right side tank power.
         */
        public void setDrive(double leftPower, double rightPower) {
            this.frontLeft.setPower(leftPower);
            this.backLeft.setPower(leftPower);
            this.frontRight.setPower(rightPower);
            this.backRight.setPower(rightPower);
        }

        /**
         * Sets the drive power of the motors based on gamepad input.
         * @param gamepad The gamepad for drive input.
         */
        public void setDriveFromGamepad(Gamepad gamepad) {
            setDrive(gamepad.left_stick_y, gamepad.right_stick_y);
        }

        private DcMotor frontLeft;
        private DcMotor frontRight;
        private DcMotor backLeft;
        private DcMotor backRight;
    }
}
