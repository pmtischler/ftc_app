package org.firstinspires.ftc.teamcode;

import android.content.Context;
import com.github.pmtischler.base.BlackBox;
import com.github.pmtischler.base.Color;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.io.FileOutputStream;

/**
 * Recorded teleop mode.
 * This mode records the hardware which can later be played back in autonomous.
 */
public class RecordedTeleop extends Teleop {
    @TeleOp(name="TeamCode.Teleop.Recorded.1", group="TeamCode")
    public static class RecordedTeleop1 extends RecordedTeleop {
        @Override public void init() {
            filename = "RecordedTeleop1";
            super.init();
        }
    }

    @TeleOp(name="TeamCode.Teleop.Recorded.2", group="TeamCode")
    public static class RecordedTeleop2 extends RecordedTeleop {
        @Override public void init() {
            filename = "RecordedTeleop2";
            super.init();
        }
    }

    @TeleOp(name="TeamCode.Teleop.Recorded.3", group="TeamCode")
    public static class RecordedTeleop3 extends RecordedTeleop {
        @Override public void init() {
            filename = "RecordedTeleop3";
            super.init();
        }
    }

    @TeleOp(name="TeamCode.Teleop.Recorded.4", group="TeamCode")
    public static class RecordedTeleop4 extends RecordedTeleop {
        @Override public void init() {
            filename = "RecordedTeleop4";
            super.init();
        }
    }

    /**
     * Extends teleop initialization to start a recorder.
     */
    public void init() {
        super.init();

        startTime = -1;
        try {
            outputStream = hardwareMap.appContext.openFileOutput(
                    filename, Context.MODE_PRIVATE);
            recorder = new BlackBox.Recorder(hardwareMap, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
            telemetry.addLine(e.toString());
            telemetry.update();
            requestOpModeStop();
        }
    }

    /**
     * Extends teleop control to record hardware after loop.
     */
    public void loop() {
        super.loop();

        if (startTime == -1) {
            startTime = time;
        }
        double elapsed = time - startTime;
        telemetry.addData("Recording File", filename);
        telemetry.addData("Elapsed", elapsed);

        try {
            recorder.recordAllDevices(elapsed);
        } catch (Exception e) {
            e.printStackTrace();
            telemetry.addLine(e.toString());
            telemetry.update();
            requestOpModeStop();
        }
    }

    /**
     * Closes the file to flush recorded data.
     */
    public void stop() {
        super.stop();

        try {
            recorder = null;
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            telemetry.addLine(e.toString());
            telemetry.update();
        }
    }

    // The filename base to write to.
    protected String filename;
    // The output file stream.
    private FileOutputStream outputStream;
    // The hardware recorder.
    private BlackBox.Recorder recorder;
    // Start time of recording.
    private double startTime;
}
