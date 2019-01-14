package com.github.pmtischler.opmode;

import android.content.Context;
import com.github.pmtischler.base.BlackBox;
import com.github.pmtischler.base.Color;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.io.FileOutputStream;

/**
 * Recorded teleop mode.
 * This mode records the hardware which can later be played back in autonomous.
 * Select the manual control mode by changing the parent class.
 */
public class RecordedTeleop extends RelicRecoveryManual {
    @TeleOp(name="pmt.Recorded.PlaybackAuto", group="pmtischler")
    public static class RecordedTeleopPlaybackAuto extends RecordedTeleop {
        @Override public void init() {
            filename = "PlaybackAuto";
            super.init();
        }
    }

    @TeleOp(name="pmt.Recorded.Red.Center", group="pmtischler")
    public static class RecordedTeleopRedCenter extends RecordedTeleop {
        @Override public void init() {
            filename = getStartPositionName(Color.Ftc.RED,
                                            StartPosition.FIELD_CENTER);
            super.init();
        }
    }

    @TeleOp(name="pmt.Recorded.Red.Corner", group="pmtischler")
    public static class RecordedTeleopRedCorner extends RecordedTeleop {
        @Override public void init() {
            filename = getStartPositionName(Color.Ftc.RED,
                                            StartPosition.FIELD_CORNER);
            super.init();
        }
    }

    @TeleOp(name="pmt.Recorded.Blue.Center", group="pmtischler")
    public static class RecordedTeleopBlueCenter extends RecordedTeleop {
        @Override public void init() {
            filename = getStartPositionName(Color.Ftc.BLUE,
                                            StartPosition.FIELD_CENTER);
            super.init();
        }
    }

    @TeleOp(name="pmt.Recorded.Blue.Corner", group="pmtischler")
    public static class RecordedTeleopBlueCorner extends RecordedTeleop {
        @Override public void init() {
            filename = getStartPositionName(Color.Ftc.BLUE,
                                            StartPosition.FIELD_CORNER);
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
