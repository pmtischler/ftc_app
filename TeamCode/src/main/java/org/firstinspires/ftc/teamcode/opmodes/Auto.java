package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import java.lang.Math;
import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.base.StateMachine;
import org.firstinspires.ftc.teamcode.control.Gripper;
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
            // Drive up to the stones. Stop when at color sensing range.
            .setNext(new SetMotion(new Mecanum.Motion(1, 0, 0)))
            .setNext(new WaitUntilUnderDistance(15))
            .setNext(new SetMotion(new Mecanum.Motion(0, 0, 0)))
            // Move right until skystone detected, fallen off, or timeout.
            .setNext(new SetMotion(new Mecanum.Motion(1, Math.PI * 1.5, 0)))
            .setNext(new WaitUntilDetectSkystone(10.0))
            .setNext(new SetMotion(new Mecanum.Motion(0, 0, 0)))
            // Move the gantry & gripper to grab whatever is in front.
            // Raise the gantry.
            .setNext(new SetGantrySpeed(0.0, 0.5))
            .setNext(new WaitForSeconds(1))
            .setNext(new SetGantrySpeed(0.0, 0.0))
            // Extend the gantry.
            .setNext(new SetGantrySpeed(0.5, 0.0))
            .setNext(new WaitForSeconds(1))
            .setNext(new SetGantrySpeed(0.0, 0.0))
            // Open the gripper and position to pick.
            .setNext(new SetGripperFingers(Gripper.FingerPosition.OPEN))
            .setNext(new SetGripperWrist(Gripper.WristPosition.FRONT_BACK))
            // Lower the gantry.
            .setNext(new SetGantrySpeed(0.0, -0.3))
            .setNext(new WaitForSeconds(1))
            .setNext(new SetGantrySpeed(0.0, 0.0))
            // Grip the stone/skystone.
            .setNext(new SetGripperFingers(Gripper.FingerPosition.CLOSED))
            .setNext(new WaitForSeconds(1))
            // Raise the gantry to raise the stone.
            .setNext(new SetGantrySpeed(0.0, 0.5))
            .setNext(new WaitForSeconds(1))
            .setNext(new SetGantrySpeed(0.0, 0.0))
            // Back away from the stone line to align with drop path.
            .setNext(new SetMotion(new Mecanum.Motion(1, Math.PI, 0)))
            .setNext(new WaitForSeconds(1))
            .setNext(new SetMotion(new Mecanum.Motion(0, 0, 0)))
            // Turn in place to point at stone drop location.
            .setNext(new SetMotion(new Mecanum.Motion(0, 0, 0.5)))
            .setNext(new WaitForSeconds(1))
            .setNext(new SetMotion(new Mecanum.Motion(0, 0, 0)))
            // Drive up to the drop location.
            .setNext(new SetMotion(new Mecanum.Motion(1, 0, 0)))
            .setNext(new WaitUntilUnderDistance(100))
            .setNext(new SetMotion(new Mecanum.Motion(0, 0, 0)))
            // Drop the stone.
            .setNext(new SetGripperFingers(Gripper.FingerPosition.OPEN_LEFT))
            .setNext(new WaitForSeconds(1))
            .setNext(new SetGripperFingers(Gripper.FingerPosition.OPEN))
            .setNext(new WaitForSeconds(1))
            // Back away from the drop.
            .setNext(new SetMotion(new Mecanum.Motion(1, Math.PI, 0)))
            .setNext(new WaitForSeconds(1))
            .setNext(new SetMotion(new Mecanum.Motion(0, 0, 0)))
            // Turn in place to point at stop line under bridge.
            .setNext(new SetMotion(new Mecanum.Motion(0, 0, 0.5)))
            .setNext(new WaitForSeconds(1))
            .setNext(new SetMotion(new Mecanum.Motion(0, 0, 0)))
            // Drive until over the stop line.
            .setNext(new SetMotion(new Mecanum.Motion(1, 0, 0)))
            .setNext(new WaitForSeconds(1));

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

    /**
     * State which actuates the gripper fingers.
     */
    public class SetGripperFingers extends StateMachine.State {
        public SetGripperFingers(Gripper.FingerPosition position) {
            this.position = position;
        }

        public void start() {}

        public StateMachine.State update() {
            gripper.setFingerPosition(position);
            return next;
        }

        public String toString() {
            return "SetGripperFingers";
        }

        private Gripper.FingerPosition position;
    }

    /**
     * State which actuates the gripper wrist.
     */
    public class SetGripperWrist extends StateMachine.State {
        public SetGripperWrist(Gripper.WristPosition position) {
            this.position = position;
        }

        public void start() {}

        public StateMachine.State update() {
            gripper.setWristPosition(position);
            return next;
        }

        public String toString() {
            return "SetGripperWrist";
        }

        private Gripper.WristPosition position;
    }

    /**
     * State which waits until the distance sensors read under a value.
     */
    public class WaitUntilUnderDistance extends StateMachine.State {
        public WaitUntilUnderDistance(double distanceCm) {
            this.distanceCm = distanceCm;
        }

        public void start() {}

        public StateMachine.State update() {
            if (distanceLeft.getDistance(DistanceUnit.CM) < distanceCm &&
                    distanceRight.getDistance(DistanceUnit.CM) < distanceCm) {
                return next;
            }
            return this;
        }

        public String toString() {
            return "WaitUntilUnderDistance";
        }

        private double distanceCm;
    }

    /**
     * State which waits until Skystone detected, fallen off path, or timeout.
     * The timeout prevents driving forever in error case.
     */
    public class WaitUntilDetectSkystone extends StateMachine.State {
        public WaitUntilDetectSkystone(double timeoutSec) {
            this.timeoutSec = timeoutSec;
        }

        public void start() {
            startTime = time;
        }

        public StateMachine.State update() {
            if (time - startTime >= timeoutSec) {
                // Timed out.
                return next;
            }
            if (detector.detectsSkystone()) {
                // Found the skystone.
                return next;
            }
            // Still searching.
            return this;
        }

        public String toString() {
            return "WaitUntilDetectSkystone";
        }

        private double timeoutSec;
        private double startTime;
    }

    // State machine for the auto program.
    private StateMachine machine;
}
