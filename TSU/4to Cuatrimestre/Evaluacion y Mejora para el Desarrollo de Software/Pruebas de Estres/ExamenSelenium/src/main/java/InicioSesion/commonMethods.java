package InicioSesion;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;

public class commonMethods {
    public static void takeScreenshot1(WebDriver webDriver, String testCaseName) {
        File srcfile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = "test-output/ExecutionResults";
        try {
            FileHandler.createDir(new File(screenshotPath));
            FileHandler.copy(srcfile, new File(screenshotPath + File.separator + testCaseName + ".PNG"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void takeScreenshot2(WebDriver webDriver, String testCaseName) {
        File srcfile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = "test-output/ExecutionResults";
        try {
            FileHandler.createDir(new File(screenshotPath));
            FileHandler.copy(srcfile, new File(screenshotPath + File.separator + testCaseName + ".PNG"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void takeScreenshot3(WebDriver webDriver, String testCaseName) {
        File srcfile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = "test-output/ExecutionResults";
        try {
            FileHandler.createDir(new File(screenshotPath));
            FileHandler.copy(srcfile, new File(screenshotPath + File.separator + testCaseName + ".PNG"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
