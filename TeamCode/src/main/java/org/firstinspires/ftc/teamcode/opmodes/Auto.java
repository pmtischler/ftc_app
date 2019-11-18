package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.teamcode.base.StateMachine;
import org.firstinspires.ftc.teamcode.control.Mecanum;

@Autonomous(name="TeamCode.Auto", group="TeamCode")
public class Auto extends Hardware {

    /**
     * Initializes the robot.
     * Called once before the match when the "Init" button is pressed.
     */
    public void init() {
        super.init();

        StateMachine.State initial = new WaitForSeconds(1);
        initial
            .setNext(new SetGantrySpeed(0.0, 0.5))
            .setNext(new WaitForSeconds(1))
            .setNext(new SetGantrySpeed(0.5, 0.0))
            .setNext(new WaitForSeconds(1))
            .setNext(new SetMotion(new Mecanum.Motion(1, 0, 0)))
            .setNext(new WaitForSeconds(5))
            .setNext(new SetMotion(new Mecanum.Motion(0, 0, 0)));

        machine = new StateMachine(initial);
        telemetry.addLine("[StateMachine]")
            .addData("state", new Func<String>() {
                @Override public String value() {
                    StateMachine.State current = machine.currentState();
                    if (current == null) {
                        return "null";
                    }
                    return current.toString();
                }
            });
    }

    /**
     * Main loop function.
     * Called repeatedly during the match after pressing "Play".
     */
    public void loop() {
        super.loop();
        machine.update();
    }

    /**
     * Stops the robot.
     * Called once at the end of the match when time runs out.
     */
    public void stop() {
        super.stop();
    }

    /**
     * State which waits for some period.
     */
    public class WaitForSeconds extends StateMachine.State {
        public WaitForSeconds(double seconds) {
            this.seconds = seconds;
        }

        public void start() {
            this.startTime = time;
        }

        public StateMachine.State update() {
            if (time - startTime < seconds) {
                return this;
            } else {
                return next;
            }
        }

        public String toString() {
            return "WaitForTime";
        }

        // The amount of time to wait.
        private double seconds;
        // Start time of the state.
        private double startTime;
    }

    /**
     * State which sets a specific Motion.
     */
    public class SetMotion extends StateMachine.State {
        public SetMotion(Mecanum.Motion motion) {
            this.motion = motion;
        }

        public void start() {
            mecanum.setDrive(motion);
        }

        public StateMachine.State update() {
            return next;
        }

        public String toString() {
            return "SetMotion";
        }

        private Mecanum.Motion motion;
    }

    /**
     * State which moves the Gantry.
     */
    public class SetGantrySpeed extends StateMachine.State {
        public SetGantrySpeed(double xSpeed, double zSpeed) {
            this.xSpeed = xSpeed;
            this.zSpeed = zSpeed;
        }

        public void start() {}

        public StateMachine.State update() {
            gantry.setXSpeed(xSpeed);
            gantry.setZSpeed(zSpeed);
            return next;
        }

        public String toString() {
            return "SetGantrySpeed";
        }

        private double xSpeed;
        private double zSpeed;
    }

    // State machine for the auto program.
    private StateMachine machine;
}
