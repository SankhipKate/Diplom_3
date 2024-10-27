package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.support.PageFactory;
import utils.TestConstants;

public class LoginPage {
    private WebDriver driver;

    // Локаторы для элементов страницы логина
    private By emailField = By.xpath("//input[@type='text' and @name='name']");
    private By passwordField = By.xpath("//input[@type='password']");
    private By loginButton = By.xpath("//button[contains(text(),'Войти')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @Step("Ввод email")
    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    @Step("Ввод пароля")
    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Нажатие на кнопку входа")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Открытие страницы логина")
    public void openLoginPage() {
        driver.get(TestConstants.LOGIN_PAGE_URL);
    }

    // Метод для выполнения логина
    public void login(String email, String password) {
        enterEmail(email); // Ввод email
        enterPassword(password);  // Ввод пароля
        clickLoginButton();  // Клик по кнопке "Войти"
    }
}
