package org.firstinspires.ftc.teamcode.base;

import java.util.ArrayList;
import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Efficiently generate and log periodic telemetry via the FTC framework.
 * Empirically, the FTC telemetry callback method or every-loop telemetry
 * causes the robot to have periodic lag events which can impact things like
 * manual drive control. This class moves control into application layer to
 * rate limit the generation of telemetry to user-defined frequency.
 */
public class PeriodicTelemetry {
    /**
     * Creates the periodic telemetry generator.
     * @param telemetry The FTC framework telemetry to emit to.
     */
    public PeriodicTelemetry(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    /**
     * Loop function which handles periodic generation.
     * Must be called within loop() of the op mode every time.
     * @param time The current time of the opmode class.
     */
    public void loop(double time) {
        if (time - lastTelemetryTime < periodSeconds) {
            // Not time to emit yet.
            return;
        }

        for (Func<String> func : generators) {
            telemetry.addLine(func.value());
        }
        lastTelemetryTime = time;
    }

    /**
     * Sets the period at which telemetry should be generated.
     */
    public void setPeriodSeconds(double periodSeconds) {
        this.periodSeconds = periodSeconds;
    }

    /**
     * Register a functor for generating a telemetry line.
     */
    public void addTelemetrySource(Func<String> func) {
        generators.add(func);
    }

    // Output for telemetry lines.
    private Telemetry telemetry;
    // Frequency to generate telemetry.
    private double periodSeconds = 1;
    // Generators of telemetry lines.
    private ArrayList<Func<String>> generators;
    // Last time telemetry was generated.
    private double lastTelemetryTime = 0;
}
