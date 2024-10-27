package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;
import utils.TestConstants;

public class RegistrationPage {
    private WebDriver driver;

    // Локаторы для элементов страницы регистрации
    private By nameField = By.xpath("//input[@name='name']");
    private By emailField = By.xpath("//label[text()='Email']/following-sibling::input");
    private By passwordField = By.xpath("//input[@type='password']");
    private By registerButton = By.xpath("//button[contains(text(),'Зарегистрироваться')]");
    // Локатор для ссылки перехода на страницу входа
    private By loginLink = By.xpath("//a[text()='Войти']");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открытие страницы регистрации")
    public void open() {
        driver.get(TestConstants.REGISTER_PAGE_URL);  // URL страницы регистрации из TestConstants
    }

    @Step("Ввод имени")
    public void enterName(String name) {
        driver.findElement(nameField).sendKeys(name);
    }

    @Step("Ввод email")
    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    @Step("Ввод пароля")
    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Нажатие на кнопку регистрации")
    public void clickRegisterButton() {
        driver.findElement(registerButton).click();
    }

    @Step("Переход на страницу логина через ссылку 'Войти'")
    public void clickLoginLink() {
        driver.findElement(loginLink).click();
    }
}
