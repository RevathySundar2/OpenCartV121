package testBase;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class BaseClass {

    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public Logger logger; // for logging
    public Properties p; // for properties

    public WebDriver getDriver() {
        return driver.get();
    }

    @BeforeClass(groups = {"Sanity", "Regression", "DatadrivenTCs", "Master"})
    @Parameters({"browser", "os"})
    public void setup(String browser, String os) throws IOException {

        FileReader fileReader = new FileReader("./src//test//resources//config.properties"); // for properties file
        p = new Properties();
        p.load(fileReader);

        logger = LogManager.getLogger(this.getClass()); // for logging


        if(p.getProperty("execution_env").equalsIgnoreCase("remote")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            if(os.equalsIgnoreCase("windows")) {
                capabilities.setPlatform(Platform.WIN11);
            }
            else if(os.equalsIgnoreCase("mac")) {
                capabilities.setPlatform(Platform.MAC);
            }
            else if(os.equalsIgnoreCase("linux")) {
                capabilities.setPlatform(Platform.LINUX);
            }
            else
            {
                System.out.println("Unrecognized platform: " + p.getProperty("browser"));
                return;
            }

            switch(browser.toLowerCase()) {
                case "chrome": capabilities.setBrowserName("chrome"); break;
                case "firefox": capabilities.setBrowserName("firefox"); break;
                case "ie": capabilities.setBrowserName("ie"); break;
                case "opera": capabilities.setBrowserName("opera"); break;
                case "safari": capabilities.setBrowserName("safari"); break;
                case "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
                default: System.out.println("Unrecognized browser: " + browser);
            }
//            driver.set(new RemoteWebDriver(new URL("http://172.16.0.11:4444/wd/hub"),capabilities));
              driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities));
        }

        if(p.getProperty("execution_env").equalsIgnoreCase("local")) {

            switch (browser.toLowerCase()) {
                case "chrome":
                    driver.set(new ChromeDriver());
                    break;
                case "firefox":
                    driver.set(new FirefoxDriver());
                    break;
                default:
                    System.out.println("Invalid browser");
                    return;
            }
        }

        // driver = new ChromeDriver();
        driver.get().manage().window().maximize();
        driver.get().manage().deleteAllCookies();
        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get().get(p.getProperty("appURL"));
    }

    @AfterClass (groups = {"Sanity", "Regression", "DatadrivenTCs", "Master"})
    public void tearDown() {
        driver.get().quit();
        driver.remove();
    }

    public String randomString(int len) {
        return RandomStringUtils.randomAlphabetic(len);
    }

    public String randomNumeric(int len) {
        return RandomStringUtils.randomNumeric(len);
    }

    public String randomAlphanumeric(int len) {
        return (RandomStringUtils.randomAlphanumeric(len) + "$");
    }

    // for Screenshots
    public String captureScreen(String tname)
    {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        TakesScreenshot ts = ((TakesScreenshot) driver);
        File src = ts.getScreenshotAs(OutputType.FILE);

        String path = System.getProperty("user.dir") + "/screenshots/" + tname + "_" + timestamp + ".png";
        File dest = new File(path);

        src.renameTo(dest);
        return path;
    }
}
