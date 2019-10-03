package org.firstinspires.ftc.teamcode.state;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.NavUtil;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.teamcode.sensors.Imu;

/**
 * Dead reckon based on an Inertial Measurement Unit.
 */
public class ImuDeadReckon {
    public ImuDeadReckon(Imu imu) {
        this.imu = imu;
        acceleration = new Acceleration();
        velocity = new Velocity();
        position = new Position();
    }

    public void loop() {
        Acceleration cur_acceleration = imu.device.getLinearAcceleration();
        Velocity cur_velocity = NavUtil.meanIntegrate(
                cur_acceleration, acceleration);
        Position cur_position = NavUtil.meanIntegrate(
                cur_velocity, velocity);

        acceleration = cur_acceleration;
        velocity = cur_velocity;
        position = cur_position;
    }

    // The IMU device to dead reckon from.
    public Imu imu;
    // The internal state of dead reckon.
    public Acceleration acceleration;
    public Velocity velocity;
    public Position position;
}
