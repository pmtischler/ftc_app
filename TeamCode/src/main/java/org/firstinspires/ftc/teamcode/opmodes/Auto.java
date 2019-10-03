package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name="TeamCode.Auto", group="TeamCode")
public class Auto extends Hardware {

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

        // Log the color sensor readings.
        telemetry.addLine("Skystone.Color.Left")
            .addData("red", skystoneLeft.red())
            .addData("green", skystoneLeft.green())
            .addData("blue", skystoneLeft.blue());
        telemetry.addLine("Skystone.Color.Center")
            .addData("red", skystoneCenter.red())
            .addData("green", skystoneCenter.green())
            .addData("blue", skystoneCenter.blue());
        telemetry.addLine("Skystone.Color.Right")
            .addData("red", skystoneRight.red())
            .addData("green", skystoneRight.green())
            .addData("blue", skystoneRight.blue());

        // Log whether the detector sees stone or skystone.
        telemetry.addLine("Detector")
            .addData("detectsStone", detector.detectsStone())
            .addData("detectsSkystone", detector.detectsSkystone());
    }

    /**
     * Stops the robot.
     * Called once at the end of the match when time runs out.
     */
    public void stop() {
        super.stop();
    }
}
