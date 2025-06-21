package testCases;

import DriverSetup.SetupDriver;
import InicioSesion.LoginPage;
import InicioSesion.commonMethods;
import VariablesGlobales.VariablesGlobales;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TC_01_Login { //Ejercicio 1
    WebDriver driver = SetupDriver.SetupChromeDriver();
    LoginPage login = new LoginPage(driver);

    @BeforeTest
    public void startWebDriver(){
        driver.get(VariablesGlobales.HOME_PAGE);
    }

    @Test
    public void TC_01() throws InterruptedException{
        Thread.sleep(3000);
        login.login(VariablesGlobales.USER_ADMIN, VariablesGlobales.USER_PASSWD);
        Thread.sleep(5000);
        WebElement btnR = driver.findElement(By.xpath("//*[@id=\"navbar\"]/ul/li[3]/a"));
        btnR.click();

        WebElement nombre= driver.findElement(By.name("nombre"));
        nombre.sendKeys("Luis");
        Thread.sleep(500);
        WebElement apellido = driver.findElement(By.name("apellido"));
        apellido.sendKeys("Valladolid");
        Thread.sleep(500);
        WebElement usuario= driver.findElement(By.name("usuario"));
        usuario.sendKeys("LuisVad");
        Thread.sleep(500);
        WebElement password= driver.findElement(By.name("password"));
        password.sendKeys("fucker123");
        Thread.sleep(500);
        WebElement btn = driver.findElement(By.xpath("//*[@id=\"registro\"]"));
        btn.click();
    }

    @AfterTest
    public void closeWebDriver() throws InterruptedException {
        Thread.sleep(3000);
        commonMethods.takeScreenshot(driver, "TC_01_Login");
        Thread.sleep(5000);
        WebElement btn = driver.findElement(By.xpath("//*[@id=\"navbar\"]/ul/li[6]/ul/li/a"));
        btn.click();
        driver.close();
    }
}
