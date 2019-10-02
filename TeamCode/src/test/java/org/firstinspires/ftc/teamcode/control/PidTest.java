package org.firstinspires.ftc.teamcode.control;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests correctness of Pid.
 */
public class PidTest {

    // The threshold for comparing PID outputs.
    private static final double pidThresh = 0.001;

    @Test
    // Test a P-only PID.
    public void testPOnly() throws Exception {
        Pid pid = new Pid(2, 1, 0, 0, 0, -1000, 1000);
        for (int i = 0; i < 2; i++) {
            assertEquals(6, pid.update(3, 0, 1), pidThresh);
        }
    }

    @Test
    // Test a PI-only PID.
    public void testPiOnly() throws Exception {
        Pid pid = new Pid(2, 2, 0, -100, 100, -1000, 1000);
        assertEquals(9, pid.update(3, 0, 1), pidThresh);
        assertEquals(24, pid.update(7, 0, 1), pidThresh);
    }

    @Test
    // Test a PD-only PID.
    public void testPdOnly() throws Exception {
        Pid pid = new Pid(2, 1, 5, 0, 0, -1000, 1000);
        assertEquals(36, pid.update(3, 0, 1), pidThresh);
        assertEquals(6, pid.update(3, 0, 1), pidThresh);
        assertEquals(-6, pid.update(2, 0, 1), pidThresh);
    }

    @Test
    // Tests integral clamping.
    public void testIntegralClamp() throws Exception {
        Pid pid = new Pid(1, 1, 1, -100, 100, -1000, 1000);
        assertEquals(120, pid.update(40, 0, 1), pidThresh);
        assertEquals(120, pid.update(40, 0, 1), pidThresh);
        assertEquals(140, pid.update(40, 0, 1), pidThresh);
        assertEquals(140, pid.update(40, 0, 1), pidThresh);
    }

    @Test
    // Tests correct delta time.
    public void testDeltaTime() throws Exception {
        Pid pid = new Pid(1, 1, 1, -100, 100, -1000, 1000);
        assertEquals(10.5, pid.update(3, 0, 0.5), pidThresh);
        assertEquals(6, pid.update(3, 0, 0.5), pidThresh);
    }

    @Test
    // Tests full PID.
    public void testPid() throws Exception {
        Pid pid = new Pid(2, 4, 5, -100, 100, -1000, 1000);
        assertEquals(44.5, pid.update(9, 7, 0.5), pidThresh);
        assertEquals(30.5, pid.update(15, 11, 1), pidThresh);
    }

    @Test
    // Tests output clamping.
    public void testOutputClamping() throws Exception {
        Pid pid = new Pid(1, 1, 0, 0, 0, -1, 1);
        assertEquals(1, pid.update(3, 0, 0.5), pidThresh);
        assertEquals(-1, pid.update(-3, 0, 0.5), pidThresh);
    }
}
