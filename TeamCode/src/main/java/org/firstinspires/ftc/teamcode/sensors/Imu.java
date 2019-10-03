package org.firstinspires.ftc.teamcode.sensors;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.util.ReadWriteFile;
import java.io.File;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

/**
 * Inertial Measurement Unit (IMU) utility code.
 */
public class Imu {
    public Imu(BNO055IMU device, Telemetry telemetry) {
        this.device = device;

        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
        params.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        params.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        params.loggingEnabled = false;
        if (getCalibrationFile().exists()) {
            params.calibrationDataFile = CALIBRATION_FILENAME;
            telemetry.addLine("BNO055IMU Calibration Found");
        }
        device.initialize(params);
    }

    /**
     * Writes the calibration data to a file.
     * Calibration data can be used to initialize the device next time.
     */
    public void writeCalibrationData() {
        getCalibrationFile().delete();

        ReadWriteFile.writeFile(
                getCalibrationFile(), device.readCalibrationData().serialize());

        if (getCalibrationFile().exists()) {
            telemetry.addLine("BNO055IMU Calibration Written");
        }
    }

    /**
     * Gets the calibration file for the IMU.
     */
    private File getCalibrationFile() {
        return AppUtil.getInstance().getSettingsFile(CALIBRATION_FILENAME);
    }

    // The underlying IMU device.
    public BNO055IMU device;
    // Telemetry for logging state.
    private Telemetry telemetry;
    // Filename to read/write the calibration data.
    private String CALIBRATION_FILENAME = "BNO055IMUCalibration.json";
}
