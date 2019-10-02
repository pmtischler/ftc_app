package org.firstinspires.ftc.teamcode.control;

import org.firstinspires.ftc.teamcode.base.Vector2d;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


/**
 * Tests correctness of FeedbackLinearizer.
 */
public class FeedbackLinearizerTest {
    // The comparison threshold.
    private static final double diffThresh = 0.00001;
    // The FeedbackLinearizer to use for testing.
    private FeedbackLinearizer linearizer;

    @Before
    public void setUp() {
        linearizer = new FeedbackLinearizer(2, 3, 5);
    }

    @Test
    // Test getWheelVelocitiesForRobotVelocity().
    public void testGetWheelVelocitiesForRobotVelocity() throws Exception {
        Vector2d w = linearizer.getWheelVelocitiesForRobotVelocity(
                new Vector2d(7, 45));
        assertEquals(-3.25, w.getX(), diffThresh);
        assertEquals(10.25, w.getY(), diffThresh);
    }

    @Test
    // Test feedbackLinearize().
    public void testFeedbackLinearize() throws Exception {
        Vector2d p = linearizer.feedbackLinearize(new Vector2d(7, 9));
        assertEquals(7, p.getX(), diffThresh);
        assertEquals(1.8, p.getY(), diffThresh);
    }

    @Test
    // Test convertToWheelVelocities().
    public void testConvertToWheelVelocities() throws Exception {
       Vector2d w = linearizer.convertToWheelVelocities(7, 9);
       assertEquals(-3.25, w.getX(), diffThresh);
       assertEquals(10.25, w.getY(), diffThresh);
    }
}
