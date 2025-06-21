package testCases;

import DriverSetup.SetupDriver;
import InicioSesion.LoginPage;
import InicioSesion.commonMethods;
import VariablesGlobales.VariablesGlobales;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TC_02 {

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
        WebElement btnR = driver.findElement(By.xpath("//*[@id=\"navbar\"]/ul/li[4]/a"));
        btnR.click();

        WebElement Vender = driver.findElement(By.xpath("//*[@id=\"ventaProductosBtn\"]"));
        Vender.click();
        Thread.sleep(5000);
        Select cliente = new Select(driver.findElement(By.name("select2-clienteVenta-container")));
        cliente.selectByVisibleText("Bahena Castillo LuisEduardo_4A-DSM");
        Thread.sleep(500);
        Select product = new Select(driver.findElement(By.name("select2-productoVenta-container")));
        product.selectByVisibleText("LuisBahena_ChivasGuadalajara");
        Thread.sleep(500);
        WebElement btn = driver.findElement(By.id("btnAgregaVenta"));
        btn.click();
        WebElement btnV = driver.findElement(By.xpath("//*[@id=\"tablaVentasTempLoad\"]/table/caption/span"));
        btnV.click();
        WebElement btnS = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div[4]/div[2]/button"));
        btnS.click();
        WebElement btnG = driver.findElement(By.xpath("//*[@id=\"ventasHechasBtn\"]"));
        btnG.click();

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
