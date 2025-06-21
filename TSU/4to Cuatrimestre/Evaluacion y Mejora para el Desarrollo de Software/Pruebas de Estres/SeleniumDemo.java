package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumDemo {
    public static void main(String[] args) throws  InterruptedException {
        String exePath="src/driver/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", exePath);
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");
        driver.manage().window().maximize();
        WebElement buscar = driver.findElement(By.name("q"));
        buscar.sendKeys("UTEZ");
        WebElement botonBuscar = driver.findElement(By.name("btnK"));
        botonBuscar.click();

        String titulo = driver.getTitle();
        System.out.println("Titulo de mi pagina: " + titulo);

        String urlActual = driver.getCurrentUrl();
        System.out.println("Mi URL: "+urlActual);

        driver.navigate().back();
        Thread.sleep(2000);
        driver.navigate().forward();
        Thread.sleep(2000);
        driver.navigate().refresh();
    }
}
