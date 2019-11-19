package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.control.Gantry;
import org.firstinspires.ftc.teamcode.control.Gripper;
import org.firstinspires.ftc.teamcode.control.Mecanum;
import org.firstinspires.ftc.teamcode.vision.Detector;
import org.opencv.core.Core;

/**
 * Base robot class which configures all hardware.
 */
public class Hardware extends OpMode {
    /**
     * Initializes the robot.
     * Called once before the match when the "Init" button is pressed.
     */
    public void init() {
        // Setup OpenCV library for Computer Vision.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Setup motors for Mecanum driving.
        mecanum = new Mecanum.Drive(
                hardwareMap.dcMotor.get("dfl"),
                hardwareMap.dcMotor.get("dfr"),
                hardwareMap.dcMotor.get("dbl"),
                hardwareMap.dcMotor.get("dbr"));
        // Setup gripper for the stone.
        gripper = new Gripper(
                hardwareMap.servo.get("gl"),
                hardwareMap.servo.get("gr"),
                hardwareMap.servo.get("gw"));
        // Setup gantry for moving the gripper.
        gantry = new Gantry(
                hardwareMap.dcMotor.get("gx"),
                hardwareMap.dcMotor.get("gy"));

        // Setup the Skystone detector.
        skystoneLeft = hardwareMap.get(ColorSensor.class, "sl");
        telemetry.addLine("[sl]")
            .addData("r", new Func<Integer>() {
                @Override public Integer value() {
                    return skystoneLeft.red();
                }
            })
            .addData("g", new Func<Integer>() {
                @Override public Integer value() {
                    return skystoneLeft.green();
                }
            })
            .addData("b", new Func<Integer>() {
                @Override public Integer value() {
                    return skystoneLeft.blue();
                }
            });
        skystoneCenter = hardwareMap.get(ColorSensor.class, "sc");
        telemetry.addLine("[sc]")
            .addData("r", new Func<Integer>() {
                @Override public Integer value() {
                    return skystoneCenter.red();
                }
            })
            .addData("g", new Func<Integer>() {
                @Override public Integer value() {
                    return skystoneCenter.green();
                }
            })
            .addData("b", new Func<Integer>() {
                @Override public Integer value() {
                    return skystoneCenter.blue();
                }
            });
        skystoneRight = hardwareMap.get(ColorSensor.class, "sr");
        telemetry.addLine("[sr]")
            .addData("r", new Func<Integer>() {
                @Override public Integer value() {
                    return skystoneRight.red();
                }
            })
            .addData("g", new Func<Integer>() {
                @Override public Integer value() {
                    return skystoneRight.green();
                }
            })
            .addData("b", new Func<Integer>() {
                @Override public Integer value() {
                    return skystoneRight.blue();
                }
            });
        detector = new Detector(skystoneLeft, skystoneCenter, skystoneRight);
        telemetry.addLine("[d]")
            .addData("detectsStone", new Func<Boolean>() {
                @Override public Boolean value() {
                    return detector.detectsStone();
                }
            })
            .addData("detectsSkystone", new Func<Boolean>() {
                @Override public Boolean value() {
                    return detector.detectsSkystone();
                }
            });

        distanceLeft = hardwareMap.get(DistanceSensor.class, "dl");
        telemetry.addLine("[dl]")
            .addData("cm", new Func<Double>() {
                @Override public Double value() {
                    return distanceLeft.getDistance(DistanceUnit.CM);
                }
            });
        distanceRight = hardwareMap.get(DistanceSensor.class, "dr");
        telemetry.addLine("[dr]")
            .addData("cm", new Func<Double>() {
                @Override public Double value() {
                    return distanceRight.getDistance(DistanceUnit.CM);
                }
            });
    }

    /**
     * Main loop function.
     * Called repeatedly during the match after pressing "Play".
     */
    public void loop() {
    }

    /**
     * Stops the robot.
     * Called once at the end of the match when time runs out.
     */
    public void stop() {
    }

    // Controls driving of the robot.
    protected Mecanum.Drive mecanum;
    // Controls the gripper for stones.
    protected Gripper gripper;
    // Gantry which moves the gripper.
    protected Gantry gantry;
    // The Skystone color sensors.
    protected ColorSensor skystoneLeft;
    protected ColorSensor skystoneCenter;
    protected ColorSensor skystoneRight;
    // Detects stones and skystone objects.
    protected Detector detector;
    // Distance sensors.
    protected DistanceSensor distanceLeft;
    protected DistanceSensor distanceRight;
}
