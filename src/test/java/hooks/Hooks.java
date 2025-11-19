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

        String videoName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_");
        videoManager.startRecording(videoName);
    }


    @After
    public void tearDown(Scenario scenario) {

        File videoFile = videoManager.stopRecording();

        if (scenario.isFailed()) {

            try {
                byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
                        .getScreenshotAs(OutputType.BYTES);

                Allure.addAttachment("Failed Screenshot", new ByteArrayInputStream(screenshot));

                scenario.attach(screenshot, "image/png", "Failed Screenshot");
            } catch (Exception e) {
                System.err.println("Screenshot capture failed: " + e.getMessage());
            }

            if (videoFile != null) {
                try {
                    Allure.addAttachment("Test Execution Video", "video/avi",
                            Files.newInputStream(videoFile.toPath()), "avi");
                } catch (IOException e) {
                    System.out.println("Could not attach video to report");
                }
            }
        } else {
            videoManager.deleteRecordedFile();
        }

        DriverManager.quitDriver();
    }
}
