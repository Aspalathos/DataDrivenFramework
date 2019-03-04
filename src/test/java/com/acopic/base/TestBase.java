package com.acopic.base;

import org.apache.logging.log4j.core.LoggerContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {
    /*
    WebDriver
    Properties
    Logs
    ExtentReports
    DB
    Excel
    Mail
    ReportNG
    Jenkins
     */


    public static WebDriver driver;
    public static Properties config = new Properties();
    public static Properties OR = new Properties();
    public static FileInputStream fis;
    public static LoggerContext log = LoggerContext.getContext();

    @BeforeSuite
    public void setUp() throws IOException {

        if(driver == null) {
            fis = new FileInputStream("src/test/resources/properties/Config.properties");
            config.load(fis);
            fis = new FileInputStream("src/test/resources/properties/OR.properties");
            OR.load(fis);
        }

        String browser = config.getProperty("browser");
        if(browser.equals("firefox")) {
            //System.setProperty("webdriver.gecko.driver", "gecko.exe");
            driver = new FirefoxDriver();
        } else if(browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", "src/test/resources/executables/chromedriver_2");
            driver = new ChromeDriver();
        } else if(browser.equals("ie")) {
            driver = new InternetExplorerDriver();
        }

        driver.get(config.getProperty("testsiteurl"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
    }

    @AfterSuite
    public void tearDown() {
        if(driver!=null) {
            driver.quit();
        }
    }
}
