package org.firstinspires.ftc.teamcode.control;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Gantry for raising and extending the Gripper.
 */
public class Gantry {
    /**
     * @param xMotor The motor which extends away from robot.
     * @param zMotor The motor which extends upwards.
     */
    public Gantry(DcMotor xMotor, DcMotor zMotor) {
        this.xMotor = xMotor;
        this.zMotor = zMotor;

        // When not actively moving, break to prevent motion.
        xMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        zMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Sets the X axis speed.
     * @param speed The speed to raise at [-1, 1].
     */
    public void setXSpeed(double speed) {
        this.xMotor.setPower(speed);
    }

    /**
     * Sets the Z axis speed.
     * @param speed The speed to raise at [-1, 1].
     */
    public void setZSpeed(double speed) {
        this.zMotor.setPower(speed);
    }

    private DcMotor xMotor;
    private DcMotor zMotor;
}
