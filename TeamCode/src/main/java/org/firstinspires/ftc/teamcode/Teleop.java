package org.firstinspires.ftc.teamcode;

import com.github.pmtischler.opmode.BaseRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Teleop mode.
 */
@TeleOp(name="TeamCode.Teleop", group="TeamCode")
public class Teleop extends BaseRobot {

    /**
     * Initializes the robot.
     * Called once before the match when the "Init" button is pressed.
     */
    public void init() {
        super.init();

        // TODO: Initialize other motors and servos.
    }

    /**
     * Main loop function.
     * Called repeatedly during the match after pressing "Play".
     */
    public void loop() {
        super.loop();

        mecanumDriveFromGamepad();

        // TODO: Add rest of robot teleop control.
    }

    /**
     * Stops the robot.
     * Called once at the end of the match when time runs out.
     */
    public void stop() {
        super.stop();

        // TODO: Add any other stop behavior.
    }
}
