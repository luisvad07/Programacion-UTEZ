package InicioSesion;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "usuario")
    private WebElement username;

    @FindBy(name = "password")
    private WebElement userPasswd;

    @FindBy(xpath = "//*[@id=\"entrarSistema\"]")
    private WebElement btnLogin;

    public void login(String user, String password) {
        username.sendKeys(user);
        userPasswd.sendKeys(password);
        btnLogin.click();
    }
}
