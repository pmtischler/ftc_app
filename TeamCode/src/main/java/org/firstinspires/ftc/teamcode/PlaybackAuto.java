package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import org.firstinspires.ftc.teamcode.base.BlackBox;

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
            bufferedInputStream = new BufferedInputStream(inputStream, 1 << 20);
            player = new BlackBox.Player(bufferedInputStream, hardwareMap);
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
            bufferedInputStream.close();
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
    // The buffered input stream.
    private BufferedInputStream bufferedInputStream;
    // The hardware player.
    private BlackBox.Player player;
    // Start time of recording.
    private double startTime;
}
