package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.control.Gripper;
import org.firstinspires.ftc.teamcode.control.Mecanum;

/**
 * OpMode for calibrating sensors.
 */
@TeleOp(name="TeamCode.Calibration", group="TeamCode")
public class Calibration extends Teleop {
    /**
     * Initializes calibration.
     */
    public void init() {
        super.init();
    }

    /**
     * Logs calibration status.
     */
    public void loop() {
        super.loop();
        telemetry.addLine("BNO055IMU")
            .addData("status", imu.device.getSystemStatus().toShortString())
            .addData("calib", imu.device.getCalibrationStatus().toString());
    }

    /**
     * Writes calibration.
     */
    public void stop() {
        super.stop();
        imu.writeCalibrationData();
    }
}
