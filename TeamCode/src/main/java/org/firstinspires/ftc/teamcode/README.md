# pmtischler/ftc_app

This is documentation for
[pmtischler/ftc_app](https://github.com/pmtischler/ftc_app), a repository
containing customizations of the FIRST FTC code for an improved development
experience. It is provided as open-source to provide no single team a
competitive advantage.

This site contains a series of tutorials for interacting with the FIRST FTC
code and the customizations. You shouldn't copy-paste code snippets found here,
as that will diminish your learning experience and make it harder to develop
new code built on top of these concepts.

## Getting Started

### Java

The following assumes you know how to program in Java. If you do not, please
complete this free online course in Java programming: [Java Programming
Basics](https://www.udacity.com/course/java-programming-basics--ud282).

### FTC Guides

The following guides will walk you through installing all required software,
and setting up all required hardware.

* [FTC Game & Season
  Info](http://www.firstinspires.org/resource-library/ftc/game-and-season-info).
  This page contains all the rules and regulations regarding robot construction
  and operation.
* [FTC Technology Information &
  Resources](http://www.firstinspires.org/resource-library/ftc/technology-information-and-resources).
  This page contains all software related documentation.
* [FTC Robot Building
  Resources](http://www.firstinspires.org/resource-library/ftc/robot-building-resources).
  This page contains all the hardware related documentation.

By the end of this section you should have a gamepad-controlled robot and you
should be able to deploy programs using Android Studio to the robot phones.

### First Robot Program

Let's create the first Robot program which simply makes a motor run at full
speed.

**Add a Robot Class**. In Android Studio, open the `Project` pane, right click
on `TeamCode/java/org.firstinspires.ftc.teamcode`, and click `New > Java
Class`. Set the name field to `FullPower` and the superclass to
`com.qualcomm.robotcore.eventloop.opmode.OpMode`. This will add the file and
open it in the editor.

**Add Imports**. Under the `package` line, add the following imports. This will
allow you to use the code contained in those files.

```java
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
```

**Register the Robot Class**. The base code uses annotations to register the
class as an OpMode, which will allow it to be selected in the robot's UI. Add
the annotation so that the class declaration looks like this:

```java
@TeleOp(name="FullPower", group="FullPower")
public class FullPower extends OpMode {
    // ...
}
```

**Add Motor Variables**. We need a motor variable in order to refer to it when
we set the power. Add the following member variable to the class.

```java
private DcMotor motor = null;
```

**Initialize the Robot**. The next step is to initialize the robot and set the
robot's motor variable. The `init` function is called when the robot is
started. Add the following function to the class.

```java
public void init() {
    motor = hardwareMap.dcMotor.get("motor");
}
```

**Set the Motor Power**. The next step is to set the motor power. The `loop`
function is called repeatedly until the robot is stopped (e.g. match is over).
Add the following function to the class.

```java
public void loop() {
    motor.setPower(1.0);
}
```

**Configure Robot, Run Program**. The final step is to run the program on the
phone, create a robot configuration with a single motor named `motor`, and
start the `FullPower OpMode`. If you have connected everything and programmed
correctly, you should see the motor spin at full power. The following is the
final code you should have.

```java
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="FullPower", group="FullPower")
public class FullPower extends OpMode {
    private DcMotor motor = null;

    public void init() {
        motor = hardwareMap.dcMotor.get("motor");
    }

    public void loop() {
        motor.setPower(1.0);
    }
}
```

You now have an end-to-end example of programming a robot.

## Tutorials

### Tank Drive

This tutorial adds tank drive manual controls to a 4-wheel robot. That is, the
left joystick will control the left motors and the right joystick will control
the right motors. The robot should have 4 wheels: two on the left, and two on
the right. Two of those motors should be in the front, and two in the back. You
should name the robots the following:

* `leftFront`
* `leftBack`
* `rightFront`
* `rightBack`

**Skeleton**. The following code block contains the standard skeleton of code
that every robot program will be based on. Create a new class under
`TeamCode/java/org.firstinspires.ftc.teamcode` and call it `TankDrive.java`.
Add the following code to the file:

```java
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="TankDrive", group="TankDrive")
public class TankDrive extends OpMode {

    public void init() {
        // Run once when driver hits "INIT".
        // Robot setup code goes here.
    }

    public void start() {
        // Run once when driver hits "PLAY".
        // Robot start-of-match code goes here.
    }

    public void loop() {
        // Code executed continuously until robot end.
    }

    public void stop() {
        // Run once when driver hits "STOP" or time elapses.
        // Robot end-of-match code goes here.
    }
}
```

**Motor Variables**. After creating the robot class with the skeleton, the next
step is to create variables for the 4 motors. These will later be initialized
to the named motors above, and then set to the joystick inputs. Add the
following as class member variables.

```java
private DcMotor leftFrontMotor = null;
private DcMotor leftBackMotor = null;
private DcMotor rightFrontMotor = null;
private DcMotor rightBackMotor = null;
```

**Robot Initialization**. In the robot initialization step, you need to assign
the specific motor values to the variables we created in the last step. We will
also set the initial power to zero, so the robot doesn't start moving on
power-up before we hit the play button. Replace the `init` function with the
following:

```java
public void init() {
    leftFrontMotor = hardwareMap.dcMotor.get("leftFront");
    leftBackMotor = hardwareMap.dcMotor.get("leftBack");
    rightFrontMotor = hardwareMap.dcMotor.get("rightFront");
    rightBackMotor = hardwareMap.dcMotor.get("rightBack");

    leftFrontMotor.setPower(0);
    leftBackMotor.setPower(0);
    rightFrontMotor.setPower(0);
    rightBackMotor.setPower(0);
}
```

**Joystick Controls**. The joystick can be accessed through the `gamepad`
variable. Tank drive can be implemented by setting motor power to joystick
y-axis values. When you push forward on the joystick, the motor power will be
set to 100% and the robot will move forward. When you push backward on the
youstick, the motor power will be set to -100% and the robot will move
backward. With two joystics, each controlling one side of the robot, pushing
forward on one joystick while pushing backward on the other joystick will cause
the robot to turn in place. Replace the `loop` function with the following:

```java
    public void loop() {
        double left = gamepad1.left_stick_y;
        double right = gamepad1.right_stick_y;

        leftFrontMotor.setPower(left);
        leftBackMotor.setPower(left);
        rightFrontMotor.setPower(right);
        rightBackMotor.setPower(right);
    }
```

**End of Match Stop**. At the end of the match you want the robot to stop
moving. If the last call to `loop` at the end of the match had set a non-zero
motor power, then the robot will continue to move in that direction
indefinitely, without you being able to stop it. For this reason, we always
want to set the end-condition, for all motors to have zero power, in the `stop`
function. Replace the `stop` function with the following:

```java
public void stop() {
    leftFrontMotor.setPower(0);
    leftBackMotor.setPower(0);
    rightFrontMotor.setPower(0);
    rightBackMotor.setPower(0);
}
```

**Motor Polarity**. If you were to test the program, you would be able to
control the motors with the gamepad, but the motors turn direction might be
reversed. You can handle this type of problem two ways. First, you could
reverse the wire connections (e.g. connect red header to black socket) from the
motor to the motor controller, which will reverse the input signal to the
motor. This is easier in terms of coding, but harder in terms of wiring.
Second, you could modify the code to invert the control signal, so a 100%
gamepad signal will set a -100% motor signal. The following shows an example of
such:

```java
double left = gamepad1.left_stick_y;
leftFrontMotor.setPower(-left);
```

Congratulations, you should now be able to drive a 4-wheel robot using
tank-drive style controls!

### Mecanum Drive

Mecanum drive allows the robot to move at any angle and rotate in place. This
will make it faster and more effective to maneuver the robot, like lining up to
press a button. Mecanum wheels have lower grip, causing it to be less effective
to go up/down ramps or climb over obstacles.

The roller component of the wheel should create a diagonal between the
front-left and bottom-right, and between the front-right and bottom-left.

See [Simplistic Control of a Mecanum
Drive](http://thinktank.wpi.edu/resources/346/ControllingMecanumDrive.pdf) for
the math behind the following code.

**Import Mecanum Code**. One of the additions to `pmtischler/ftc_app` is a
class to perform the Mecanum calculation. It takes the desired robot speed,
velocity angle, and rotation speed. It returns the speeds of each motor after
clamping. In order to use it, you'll need to import the code.

```java
import org.firstinspires.ftc.teamcode.control.Mecanum;
```

**Add Mode Select**. We want the ability to control the robot with mecanum
wheels or with regular wheels. Add the following member variable which will
select between the two. This will allow you to select the control style by
changing this variable, without having to change your code.

```java
private final boolean shouldMecanumDrive = true;
```

**Add Mecanum Drive**. The mecanum drive is a straightforward implementation of
the formulas above. Update the ``loop`` function with the following code.

```java
public void loop() {
  if (shouldMecanumDrive) {
      // Convert joysticks to desired motion.
      Mecanum.Motion motion = Mecanum.joystickToMotion(
                          gamepad1.left_stick_x, gamepad1.left_stick_y,
                          gamepad1.right_stick_x, gamepad1.right_stick_y);

      // Convert desired motion to wheel powers, with power clamping.
      Mecanum.Wheels wheels = Mecanum.motionToWheels(motion);
      leftFrontMotor.setPower(wheels.frontLeft);
      rightFrontMotor.setPower(wheels.frontRight);
      leftBackMotor.setPower(wheels.backLeft);
      rightBackMotor.setPower(wheels.backRight);
  } else {
      // ... previous loop code.
  }
}
```

Congratulations, you now have the ability to drive with Mecanum wheels!

### State Machine

In this tutorial you will implement a [Finite State
Machine](https://en.wikipedia.org/wiki/Finite-state_machine). A state machine
is made up of a series of states, and a series of transitions between states. A
state can be something like "drive forward", and a transition can be something
like "when a distance sensor reads less than 3 inches then change to the drive
for time state". In this tutorial, we will implement an autonomous mode that
drives forward until the distance sensor reads less than 3 inches, after which
it will drive left for 2 seconds.

**Program Structure**. The first step is to create a skeleton of the final
program. Notice that there are two internal classes representing the states of
the state machine, one for moving forward until a distance threshold is met,
and another which moves left for an amount of time. Each state has 2 functions,
`start` which is called when the state machine first enters the state, and
`update` which updates the robot in the state and returns the next state to be
in. The state machine transitions when the current state's `update` function
returns a different state.

```java
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.base.StateMachine.State;
import org.firstinspires.ftc.teamcode.base.StateMachine;

@Autonomous(name="ForwardThenLeftStateMachine", group="ForwardThenLeftStateMachine")
public class ForwardThenLeftStateMachine extends MecanumDrive {

    /**
     * Moves forward until a distance threshold is met.
     */
    public class ForwardUntilDistance implements StateMachine.State {
        @Override
        public void start() {
        }

        @Override
        public State update() {
        }
    }

    /**
     * Moves left for a specific amount of time.
     */
    public class LeftForTime implements StateMachine.State {
        @Override
        public void start() {
        }

        @Override
        public State update() {
        }
    }

    /**
     * Initializes the state machine.
     */
    public void init() {
    }

    /**
     * Runs the state machine.
     */
    public void loop() {
    }

    // Distance sensor reading forward.
    DistanceSensor distanceSensor;

    // The state machine manager.
    private StateMachine machine;
    // Move forward until distance.
    private ForwardUntilDistance forwardUntilDistance;
    // Move left for time.
    private LeftForTime leftForTime;

}
```

**Setup & Run State Machine**. The next step is to setup and run the state
machine. Setup involves instantiating each state, creating a state machine
manager, and providing it with an initial state. Running involves telling the
state machine manager to update, which will call the current state's `update`
function, and track the returned state for the next update.

```java
public void init() {
    super.init();  // Initialize mecanum drive.

    // Create the states.
    forwardUntilDistance = new ForwardUntilDistance();
    leftForTime = new LeftForTime();
    // Start the state machine with forward state.
    machine = new StateMachine(forwardUntilDistance);
}

public void loop() {
    machine.update();  // Run one update in state machine.
}
```

**Define Drive Until Distance State**. The next step is to define the first
state, driving forward until a distance is met. There is no initialization
needed for the state. Updating the state involves checking the distance sensor.
If we haven't yet reached the desired distance, we command the motors to drive
forward. If we have reached the desired distance, we return the next state:
driving left until for an amount of time.

```java
public class ForwardUntilDistance implements StateMachine.State {
    @Override
    public void start() {
    }

    @Override
    public State update() {
        if (distanceSensor.getDistance(DistanceUnit.INCH) > 3) {
            // Haven't yet reached distance, drive forward.
            setDrive(1, 0, 0);
            return this;
        } else {
            // Reached distance, switch to left for time state.
            setDrive(0, 0, 0);
            return leftForTime;
        }
    }
}
```

**Define Drive For Time State**. The next step is to define the second state,
driving left for an amount of time. To initialize the state, save the time the
state started. To update the state, drive left. The state is done when the
difference between current time and start time is greater than 2 seconds. The
effect will be to drive left for 2 seconds.

```java
public class LeftForTime implements StateMachine.State {
    @Override
    public void start() {
        startTime = time;
    }

    @Override
    public State update() {
        if (time - startTime < 2) {
            // Less than 2 seconds elapsed, drive left.
            setDrive(1, Math.PI, 0);
            return this;
        } else {
            // 2 seconds elapsed, stop and terminate state machine.
            setDrive(0, 0, 0);
            return null;
        }
    }

    private double startTime;
}
```

Congratulations! You can now use a state machine to define multiple robot
states and when to transition between them. You can use this to implement much
more complex state machines for complex autonomous behavior.

### Record & Playback

In this tutorial you will write your first simple autonomous program. This
program will record the hardware state during each call to `loop` while the
robot is in manual mode. When the program is in autonomous mode, it'll play
back that recorded hardware log to mimic the manual operation.

Note this is not a robust autonomous method. It doesn't handle any changes in
robot or environment. If the robot changes weight, changes air drag, etc the
exact motor powers needed to turn will change, so the manual log previously
recorded will not have the same affect. If the environment changes, the robot
will not be able to adapt to avoid obstacles, deal with slippery conditions,
etc. In future more advanced tutorials we'll learn how to make better autonomy
programs.

One of the additions to `pmtischler/ftc_app` is a class to record the robot's
hardware state and play it back. That is, it'll record the motors and servo
states during Teleop mode, and then playback that recorded log during
Autonomous mode. The robot will mimic the actions of the manual driver. This
class uses the name of the hardware devices, so if you change the name of the
devices you will need to re-record.

**Create Recorded Teleop Class.** First we will create a new class called
`RecordedTeleop`. This class will inherit from the Teleop mode you've
developed, like `TankDrive`. This will reuse all the code to drive the robot,
and we'll extend it to record the hardware.

```java
package org.firstinspires.ftc.teamcode;

import android.content.Context;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.io.FileOutputStream;
import org.firstinspires.ftc.teamcode.base.BlackBox;

@Teleop(name="RecordedTeleop", group="RecordedTeleop")
public class RecordedTeleop extends TankDrive {
    // The output file stream.
    private FileOutputStream outputStream;
    // The hardware recorder.
    private BlackBox.Recorder recorder;
}
```

You'll notice we've imported the `BlackBox` code. We've also imported `Context`
and `FileOutputStream` which will be needed to open a file for which to write
the data.

**Open File, Construct Recorder**. We will override the `init` function to
create a file to write data to, and then create a `BlackBox.Recorder` that will
use the file to write hardware data. If this fails, we'll want to print the
error and stop the robot, so we wrap the process in a try-catch.

```java
public void init() {
    super.init();  // TankDrive teleop initialization.

    try {
        // Open a file named "recordedTeleop" in the app's folder.
        outputStream = hardwareMap.appContext.openFileOutput("recordedTeleop",
                                                             Context.MODE_PRIVATE);
        // Setup a hardware recorder.
        recorder = new BlackBox.Recorder(hardwareMap, outputStream);
    } catch (Exception e) {
        e.printStackTrace();
        requestOpModeStop();
    }
}
```

**Careful: File Overwriting**. The file we created will be created each time
the program is run. This means that the second time you run the `OpMode` it'll
overwrite the previously recorded file. You need to make sure you backup this
file after recording so that you don't accidentally lose a good file.

**Record over Time**. Now we want to record the state of the hardware to the
file. The `BlackBox.Recorder` has a `record` function which does exactly that.
We will call it with the current time so the hardware state is written with the
timestamp. Similar to setup, if it fails we wan't to print the error and stop
the robot.

```java
public void loop() {
    super.loop();  // TankDrive teleop control code.

    try {
        // Record the hardware state at the current time.
        recorder.record("leftFront", time);
        recorder.record("rightFront", time);
        recorder.record("leftBack", time);
        recorder.record("rightBack", time);
    } catch (Exception e) {
        e.printStackTrace();
        requestOpModeStop();
    }
}
```

**Close to Flush Contents**. When the Teleop mode is complete we need to close
the file so the data is written. If there is a problem we will print the error,
but as we are already stopping we don't need to request the `OpMode` stop.

```java
public void stop() {
    super.stop();  // TankDrive stop code.

    try {
        // Close the file to write recorded data.
        outputStream.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

You now have a Teleop program which can use your base Teleop code to drive, and
can use the `BlackBox` to record telemetry data. Run your program and test it
out. Check the App's data folder to see that the file was written, and that it
has data (has non-zero file size).

**Create Playback Autonomous Class**. Now it's time to create the autonomous
`OpMode` which will playback the previously recorded file. Create a new class
called `PlaybackAuto`. This time it will inherit from `OpMode` instead of a
Teleop class.

```java
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import java.io.FileInputStream;
import org.firstinspires.ftc.teamcode.base.BlackBox;

@Autonomous(name="PlaybackAuto", group="PlaybackAuto")
public class PlaybackAuto extends OpMode {
    // The input file stream.
    private FileInputStream inputStream;
    // The hardware player.
    private BlackBox.Player player;
}
```

**Open File, Construct Player**. Similar to the recorder, we will now open the
file and construct a player.

```java
public void init() {
    try {
        // Open previously written file full of hardware data.
        inputStream = hardwareMap.appContext.openFileInput("recordedTeleop");
        // Create a player to playback the hardware log.
        player = new BlackBox.Player(inputStream, hardwareMap);
    } catch (Exception e) {
        e.printStackTrace();
        requestOpModeStop();
    }
}
```

**Playback Recorded Data**. Similar to the recorder, we will now call
`playback` on the player. This will determine the state of the robot when it
was recording at this time in the match, and then set the motors and servos to
those values.

```java
public void loop() {
    try {
        // Update the hardware to mimic human during recorded Teleop.
        player.playback(time);
    } catch (Exception e) {
        e.printStackTrace();
        requestOpModeStop();
    }
}
```

**Close the File**. At the end of the mode, close the file. This is necessary
to terminate cleanly, so the robot can run multiple times without error.

```java
public void stop() {
    try {
        inputStream.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

Congratulations! You now have an autonomous mode that can mimic a human driver.
Test out the mode, see if it accurately mimics the driver. Does it do the same
thing every time? How sensitive is it to initial conditions (e.g. initial
heading or position on the field)?
