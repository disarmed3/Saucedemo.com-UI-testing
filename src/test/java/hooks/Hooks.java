package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.ConfigReader;
import utils.DriverManager;
import utils.VideoManager;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class Hooks {

    VideoManager videoManager = new VideoManager();

    @Before
    public void setUp(Scenario scenario) {
        DriverManager.initializeDriver();
        DriverManager.getDriver().manage().window().maximize();
        DriverManager.getDriver().get(ConfigReader.get("baseUrl"));

        // Video Recording Logic
        // We read the property passed from Maven command line (defaulting to NONE)
        String mode = System.getProperty("video.mode", "NONE");

        // Start recording only if mode is NOT 'NONE'
        if (!"NONE".equalsIgnoreCase(mode)) {
            // Sanitize the scenario name to be safe for file systems (remove spaces/special chars)
            String videoName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_");
            videoManager.startRecording(videoName);
        }
    }


    @After
    public void tearDown(Scenario scenario) {
        // 1. STOP RECORDING
        // Only try to stop if we are NOT in NONE mode
        String mode = System.getProperty("video.mode", "NONE").toUpperCase();
        File videoFile = null;

        if (!"NONE".equals(mode)) {
            videoFile = videoManager.stopRecording();
        }

        // 2. Determine if we should attach
        boolean shouldAttachVideo = false;

        switch (mode) {
            case "ALWAYS":
                shouldAttachVideo = true;
                break;
            case "FAILED_ONLY":
                shouldAttachVideo = scenario.isFailed();
                break;
            case "NONE":
            default:
                shouldAttachVideo = false;
                break;
        }

        // 3. Failure Handling (Screenshots)
        if (scenario.isFailed()) {
            try {
                // Capture Screenshot as Byte Array (Memory Efficient)
                byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
                        .getScreenshotAs(OutputType.BYTES);

                // A. Attach to Allure Report
                Allure.addAttachment("Failed Screenshot", new ByteArrayInputStream(screenshot));

                // B. Attach to Standard Cucumber Report (Backup)
                scenario.attach(screenshot, "image/png", "Failed Screenshot");

            } catch (Exception e) {
                System.err.println("Screenshot capture failed: " + e.getMessage());
            }
        }

        // 4. Video Attachment vs. Cleanup
        if (shouldAttachVideo && videoFile != null) {
            try {
                // Attach video to Allure Report
                Allure.addAttachment("Test Execution Video", "video/avi",
                        Files.newInputStream(videoFile.toPath()), "avi");
            } catch (IOException e) {
                System.err.println("Could not attach video to report: " + e.getMessage());
            }
        } else {
            // Delete the recorded file to save disk space (if it exists)
            videoManager.deleteRecordedFile();
        }

        // 5. Always quit the driver
        DriverManager.quitDriver();
        }

}
