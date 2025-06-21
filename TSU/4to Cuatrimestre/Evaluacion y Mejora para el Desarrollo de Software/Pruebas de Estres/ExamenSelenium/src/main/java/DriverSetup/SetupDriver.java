package DriverSetup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SetupDriver {
    public static WebDriver SetupChromeDriver() {
        String driverChrome="src/driver/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", driverChrome);
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }
}
