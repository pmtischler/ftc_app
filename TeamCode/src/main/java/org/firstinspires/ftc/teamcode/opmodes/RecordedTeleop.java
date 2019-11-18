package org.firstinspires.ftc.teamcode.opmodes;

import android.content.Context;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.teamcode.base.BlackBox;

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
            bufferedOutputStream = new BufferedOutputStream(
                    outputStream, 1 << 20);
            recorder = new BlackBox.Recorder(hardwareMap, bufferedOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
            telemetry.addLine(e.toString());
            telemetry.update();
            requestOpModeStop();
        }

        telemetry.addData("Elapsed", new Func<Double>() {
            @Override public Double value() {
                if (startTime == -1) {
                    return 0.0;
                } else {
                    return time - startTime;
                }
            }
        });
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
            bufferedOutputStream.close();
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
    // The buffered output stream.
    private BufferedOutputStream bufferedOutputStream;
    // The hardware recorder.
    private BlackBox.Recorder recorder;
    // Start time of recording.
    private double startTime;
}
