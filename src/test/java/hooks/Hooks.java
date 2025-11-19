package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.ConfigReader;
import utils.DriverManager;


public class Hooks {


    @Before
    public void setUp() {
        DriverManager.initializeDriver();
        DriverManager.getDriver().manage().window().maximize();
        DriverManager.getDriver().get(ConfigReader.get("baseUrl"));
    }


    @After
    public void tearDown(Scenario scenario) {
        DriverManager.quitDriver();
    }
}
