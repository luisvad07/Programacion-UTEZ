import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import java.net.URL;

public class TestNotas {
    static AndroidDriver<MobileElement> driver;

    @Test
    public static void testNotas() throws Exception {

        DesiredCapabilities cap = new DesiredCapabilities();

        cap.setCapability("deviceName", "ZTE 8010");

        cap.setCapability("udid", "GTY7O8P9");

        cap.setCapability("platformName", "Android");

        cap.setCapability("platformVersion", "11");

        cap.setCapability("appPackage", "com.google.android.keep");

        cap.setCapability("appActivity", "com.google.android.apps.keep.ui.activities.BrowseActivity");

        URL url = new URL("http://127.0.0.1:4723/wd/hub");

        driver = new AndroidDriver<MobileElement>(url, cap);

        System.out.println("La aplicacion ha sido lanzada correctamente!");

        Thread.sleep(5000);


        MobileElement start = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.Button");

        start.click();


        Thread.sleep(5000);


        MobileElement add = driver.findElementById("com.google.android.keep:id/new_note_button");

        add.click();

        MobileElement title = driver.findElementById("com.google.android.keep:id/editable_title");

        Thread.sleep(3000);

        title.setValue("Mi Examen Unidad 3");

        MobileElement content = driver.findElementById("com.google.android.keep:id/edit_note_text");

        Thread.sleep(5000);

        content.setValue("Hola a todos, mi examen está muy fácil. Pasaré con AU");

        MobileElement ready = driver.findElementByXPath("//android.widget.ImageButton[@content-desc=\"Navegar hacia arriba\"]");

        ready.click();

    }
}
