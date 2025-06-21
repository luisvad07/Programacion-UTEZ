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

import java.io.File;

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
        nombre.sendKeys("LuisEduardo_4A-DSM");
        Thread.sleep(500);
        WebElement apellidos = driver.findElement(By.name("apellidos"));
        apellidos.sendKeys("Bahena Castillo");
        Thread.sleep(500);
        WebElement direccion= driver.findElement(By.name("direccion"));
        direccion.sendKeys("St. Marry, NewCastle");
        Thread.sleep(500);
        WebElement email = driver.findElement(By.name("email"));
        email.sendKeys("20213tn002@utez.edu.mx");
        Thread.sleep(500);
        WebElement number = driver.findElement(By.name("telefono"));
        number.sendKeys("7771234567");
        Thread.sleep(500);
        WebElement rfc = driver.findElement(By.name("rfc"));
        rfc.sendKeys("BACO123456");
        Thread.sleep(500);
        WebElement btn = driver.findElement(By.xpath("//*[@id=\"btnAgregarCliente\"]"));
        btn.click();

        Thread.sleep(300);
        commonMethods.takeScreenshot1(driver, "TC_01_Login");
        Thread.sleep(10000);

        WebElement btnE = driver.findElement(By.xpath("//*[@id=\"navbar\"]/ul/li[2]/a"));
        btnE.click();
        WebElement btnF = driver.findElement(By.xpath("//*[@id=\"navbar\"]/ul/li[2]/ul/li[1]/a"));
        btnF.click();
        WebElement categoria= driver.findElement(By.name("categoria"));
        categoria.sendKeys("LuisEduardo_BAHENACASTILLO_4A-DSM");
        Thread.sleep(500);
        WebElement btnS = driver.findElement(By.xpath("//*[@id=\"btnAgregaCategoria\"]"));
        btnS.click();

        Thread.sleep(300);
        commonMethods.takeScreenshot2(driver, "TC_02_Login");
        Thread.sleep(5000);

        WebElement btnT = driver.findElement(By.xpath("//*[@id=\"navbar\"]/ul/li[2]/a"));
        btnT.click();
        WebElement btnY = driver.findElement(By.xpath("//*[@id=\"navbar\"]/ul/li[2]/ul/li[2]/a"));
        btnY.click();

        Thread.sleep(5000);
        File file = new File("C:\\Chivas.jpg");
        String path = file.getAbsolutePath();
        driver.get("http://desarrollosenweb.com/examenUnidad3/vistas/articulos.php");
        driver.findElement(By.id("imagen")).sendKeys(path);
        Select categorias = new Select(driver.findElement(By.name("categoriaSelect")));
        categorias.selectByVisibleText("LuisEduardo_BAHENACASTILLO_4A-DSM");
        Thread.sleep(500);
        WebElement nombrea= driver.findElement(By.name("nombre"));
        nombrea.sendKeys("LuisBahena_ChivasGuadalajara");
        Thread.sleep(500);
        WebElement descripcion = driver.findElement(By.name("descripcion"));
        descripcion.sendKeys("El mejor club del mundo");
        Thread.sleep(500);
        WebElement cantidad = driver.findElement(By.name("cantidad"));
        cantidad.sendKeys("10");
        Thread.sleep(500);
        WebElement precio = driver.findElement(By.name("precio"));
        precio.sendKeys("150");
        Thread.sleep(5000);
        WebElement btnl = driver.findElement(By.xpath("//*[@id=\"btnAgregaArticulo\"]"));
        btnl.click();
        Thread.sleep(300);
        commonMethods.takeScreenshot3(driver, "TC_03_Login");
    }


    @AfterTest
    public void closeWebDriver() throws InterruptedException {
        Thread.sleep(5000);
        WebElement btn = driver.findElement(By.xpath("//*[@id=\"navbar\"]/ul/li[5]/a"));
        btn.click();
        WebElement btnClose = driver.findElement(By.xpath("//*[@id=\"navbar\"]/ul/li[5]/ul/li/a"));
        btnClose.click();
        driver.close();
    }
}
