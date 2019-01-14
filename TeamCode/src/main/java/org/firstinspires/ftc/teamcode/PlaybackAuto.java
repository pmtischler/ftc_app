package org.firstinspires.ftc.teamcode;

import com.github.pmtischler.base.BlackBox;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import java.io.FileInputStream;

/**
 * Playback autonomous mode.
 * This mode playbacks the recorded values previously recorded in teleop.
 */
public class PlaybackAuto extends OpMode {
    @Autonomous(name="TeamCode.Auto.Playback.1", group="TeamCode")
    public static class PlaybackAuto1 extends PlaybackAuto {
        @Override public void init() {
            filename = "RecordedTeleop1";
            super.init();
        }
    }

    @Autonomous(name="TeamCode.Auto.Playback.2", group="TeamCode")
    public static class PlaybackAuto2 extends PlaybackAuto {
        @Override public void init() {
            filename = "RecordedTeleop2";
            super.init();
        }
    }

    @Autonomous(name="TeamCode.Auto.Playback.3", group="TeamCode")
    public static class PlaybackAuto3 extends PlaybackAuto {
        @Override public void init() {
            filename = "RecordedTeleop3";
            super.init();
        }
    }

    @Autonomous(name="TeamCode.Auto.Playback.4", group="TeamCode")
    public static class PlaybackAuto4 extends PlaybackAuto {
        @Override public void init() {
            filename = "RecordedTeleop4";
            super.init();
        }
    }

    /**
     * Creates the playback.
     */
    public void init() {
        startTime = -1;
        try {
            inputStream = hardwareMap.appContext.openFileInput(filename);
            player = new BlackBox.Player(inputStream, hardwareMap);
        } catch (Exception e) {
            e.printStackTrace();
            telemetry.addLine(e.toString());
            telemetry.update();
            requestOpModeStop();
        }
    }

    /**
     * Plays back the recorded hardware at the current time.
     */
    public void loop() {
        if (startTime == -1) {
            startTime = time;
        }
        double elapsed = time - startTime;
        telemetry.addData("Playing File", filename);
        telemetry.addData("Elapsed", elapsed);

        try {
            player.playback(elapsed);
        } catch (Exception e) {
            e.printStackTrace();
            telemetry.addLine(e.toString());
            telemetry.update();
            requestOpModeStop();
        }
    }

    /**
     * Closes the file.
     */
    public void stop() {
        try {
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            telemetry.addLine(e.toString());
            telemetry.update();
        }
    }

    // The filename base to read from.
    protected String filename;
    // The input file stream.
    private FileInputStream inputStream;
    // The hardware player.
    private BlackBox.Player player;
    // Start time of recording.
    private double startTime;
}
