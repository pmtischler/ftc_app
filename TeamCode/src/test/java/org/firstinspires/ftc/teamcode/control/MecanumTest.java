package org.firstinspires.ftc.teamcode.control;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests correctness of Mecanum calculations.
 */
public class MecanumTest {
    // The comparison threshold.
    private static final double diffThresh = 0.001;

    /**
     * Asserts that the angles are equal.
     */
    private void assertAnglesEqual(double expected, double actual,
                                   double diffThresh) {
        if (expected < 0) {
            expected += (Math.PI * 2);
        }
        if (actual < 0) {
            actual += (Math.PI * 2);
        }
        assertEquals(expected, actual, diffThresh);
    }

    /**
     * Asserts that the input controls yields the expected motion.
     */
    private void assertMotion(double leftStickX, double leftStickY,
                              double rightStickX, double rightStickY,
                              double vD, double thetaD, double vTheta) {
        Mecanum.Motion motion = Mecanum.joystickToMotion(
                leftStickX, leftStickY,
                rightStickX, rightStickY);
        assertEquals(vD, motion.vD, diffThresh);
        assertAnglesEqual(thetaD, motion.thetaD, diffThresh);
        assertEquals(vTheta, motion.vTheta, diffThresh);
    }

    @Test
    // Test Mecanum for direct strafing.
    public void testMecanumMotionStrafing() throws Exception {
        // Forward.
        assertMotion(0, -1,
                     0, 0,
                     1, 0, 0);
        // Right.
        assertMotion(1, 0,
                     0, 0,
                     1, -Math.PI / 2, 0);
        // Back.
        assertMotion(0, 1,
                     0, 0,
                     1, Math.PI, 0);
        // Left.
        assertMotion(-1, 0,
                     0, 0,
                     1, Math.PI / 2, 0);

        // Front right.
        assertMotion(1, -1,
                     0, 0,
                     1, -Math.PI / 4, 0);
    }

    @Test
    // Test Mecanum for turning.
    public void testMecanumMotionTurning() throws Exception {
        // Left.
        assertMotion(0, 0,
                     -1, 0,
                     0, Math.PI, 1);
        // Right.
        assertMotion(0, 0,
                     1, 0,
                     0, Math.PI, -1);
    }

    /**
     * Asserts that the input motion yields the expected wheel poweers.
     */
    private void assertWheels(double vD, double thetaD, double vTheta,
                              double frontLeft, double frontRight,
                              double backLeft, double backRight) {
        Mecanum.Wheels wheels = Mecanum.motionToWheels(
                new Mecanum.Motion(vD, thetaD, vTheta));
        assertEquals(frontLeft, wheels.frontLeft, diffThresh);
        assertEquals(frontRight, wheels.frontRight, diffThresh);
        assertEquals(backLeft, wheels.backLeft, diffThresh);
        assertEquals(backRight, wheels.backRight, diffThresh);
    }

    @Test
    // Test Mecanum for direct strafing.
    public void testMecanumWheelStrafing() throws Exception {
        // Forward.
        assertWheels(1, 0, 0,
                     0.7071, 0.7071,
                     0.7071, 0.7071);
        // Right.
        assertWheels(1, -Math.PI / 2, 0,
                     0.7071, -0.7071,
                     -0.7071, 0.7071);
        // Back.
        assertWheels(1, Math.PI, 0,
                     -0.7071, -0.7071,
                     -0.7071, -0.7071);
        // Left.
        assertWheels(1, Math.PI / 2, 0,
                     -0.7071, 0.7071,
                     0.7071, -0.7071);

        // Front right.
        assertWheels(1, -Math.PI / 4, 0,
                     1, 0,
                     0, 1);
    }

    @Test
    // Test Mecanum for turning.
    public void testMecanumWheelTurning() throws Exception {
        // Right.
        assertWheels(0, 0, -1,
                     1, -1,
                     1, -1);
        // Left.
        assertWheels(0, 0, 1,
                     -1, 1,
                     -1, 1);
    }

    @Test
    // Test Mecanum for moving and turning to clamp motors.
    public void testMecanumWheelClamping() throws Exception {
        // Forward and full right.
        assertWheels(1, 0, -1,
                     1, -0.1716,
                     1, -0.1716);
    }
}
