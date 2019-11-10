package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.teamcode.state.ImuDeadReckon;

@Autonomous(name="TeamCode.Auto", group="TeamCode")
public class Auto extends Hardware {

    /**
     * Initializes the robot.
     * Called once before the match when the "Init" button is pressed.
     */
    public void init() {
        super.init();
        if (imu != null) {
            reckon = new ImuDeadReckon(imu);
        }
    }

    /**
     * Main loop function.
     * Called repeatedly during the match after pressing "Play".
     */
    public void loop() {
        super.loop();

        // Log the color sensor readings.
        if (skystoneLeft != null) {
            telemetry.addLine("Skystone.Color.Left")
                .addData("red", skystoneLeft.red())
                .addData("green", skystoneLeft.green())
                .addData("blue", skystoneLeft.blue());
        }
        if (skystoneCenter != null) {
            telemetry.addLine("Skystone.Color.Center")
                .addData("red", skystoneCenter.red())
                .addData("green", skystoneCenter.green())
                .addData("blue", skystoneCenter.blue());
        }
        if (skystoneRight != null) {
            telemetry.addLine("Skystone.Color.Right")
                .addData("red", skystoneRight.red())
                .addData("green", skystoneRight.green())
                .addData("blue", skystoneRight.blue());
        }

        // Log whether the detector sees stone or skystone.
        if (detector != null) {
            telemetry.addLine("Detector")
                .addData("detectsStone", detector.detectsStone())
                .addData("detectsSkystone", detector.detectsSkystone());
        }

        // Log the IMU state.
        if (imu != null) {
            Orientation angles = imu.device.getAngularOrientation(
                    AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            Acceleration acceleration = imu.device.getLinearAcceleration();
            telemetry.addLine("BNO055IMU")
                .addData("status", imu.device.getSystemStatus().toShortString())
                .addData("calib", imu.device.getCalibrationStatus().toString())
                .addData("heading", angles.firstAngle)
                .addData("roll", angles.secondAngle)
                .addData("pitch", angles.thirdAngle)
                .addData("acc", acceleration.toString());
        }

        // Log the dead reckon state.
        if (reckon != null) {
            reckon.loop();
            telemetry.addLine("ImuDeadReckon")
                .addData("pos", reckon.position.toString())
                .addData("vel", reckon.velocity.toString())
                .addData("acc", reckon.acceleration.toString());
        }
    }

    /**
     * Stops the robot.
     * Called once at the end of the match when time runs out.
     */
    public void stop() {
        super.stop();
    }

    // Dead reckons based on IMU.
    private ImuDeadReckon reckon;
}
