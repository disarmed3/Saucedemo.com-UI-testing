package utils;

import org.monte.media.Format;
import org.monte.media.FormatKeys;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class VideoManager {

    private ScreenRecorder screenRecorder;
    private String currentFileName;
    private File currentFile;

    public void startRecording(String fileName) {
        this.currentFileName = fileName;

        // Create directory if it doesn't exist
        File file = new File("target/videos/");
        if (!file.exists()) {
            file.mkdirs();
        }

        try {
            // Graphics Configuration
            GraphicsConfiguration gc = GraphicsEnvironment
                    .getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice()
                    .getDefaultConfiguration();

            // Monte Configuration (Standard settings for decent quality/size balance)
            this.screenRecorder = new ScreenRecorder(gc,
                    gc.getBounds(),
                    new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                            CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                            DepthKey, 24, FrameRateKey, Rational.valueOf(15),
                            QualityKey, 1.0f,
                            KeyFrameIntervalKey, 15 * 60),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black",
                            FrameRateKey, Rational.valueOf(30)),
                    null,
                    file); // Output folder

            this.screenRecorder.start();

        } catch (Exception e) {
            System.err.println("Failed to start video recording: " + e.getMessage());
        }
    }

    public File stopRecording() {
        try {
            this.screenRecorder.stop();

            // Monte generates a file with a timestamp. We want to rename it to our test name.
            // The created file is usually the last one in the list created by the recorder.
            if (this.screenRecorder.getCreatedMovieFiles().size() > 0) {
                File videoFile = this.screenRecorder.getCreatedMovieFiles().get(0);

                // Rename logic (Optional, but keeps things clean)
                File newFile = new File("target/videos/" + currentFileName + ".avi");

                // Delete existing if overwrite is needed
                if(newFile.exists()) newFile.delete();

                videoFile.renameTo(newFile);
                this.currentFile = newFile;
                return newFile;
            }
        } catch (IOException e) {
            System.err.println("Failed to stop video recording: " + e.getMessage());
        }
        return null;
    }

    public void deleteRecordedFile() {
        if (currentFile != null && currentFile.exists()) {
            currentFile.delete();
        }
    }

    public File getLastRecordedFile() {
        return currentFile;
    }
}