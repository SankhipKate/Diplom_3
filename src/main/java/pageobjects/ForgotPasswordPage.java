package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.TestConstants;

public class ForgotPasswordPage {
    private WebDriver driver;

    // Локатор для поля Email
    private By emailField = By.name("email");
    // Локатор для кнопки восстановления пароля
    private By recoverButton = By.xpath("//button[contains(@class, 'button_button_type_primary')]");
    // Локатор для ссылки на вход в аккаунт
    private By loginLink = By.xpath("//a[text()='Войти']");

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открытие страницы восстановления пароля")
    public void open() {
        driver.get(TestConstants.FORGOT_PASSWORD_PAGE_URL);  // URL страницы добавляем в TestConstants
    }

    @Step("Ввод email для восстановления пароля")
    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    @Step("Нажатие на кнопку восстановления пароля")
    public void clickRecoverButton() {
        driver.findElement(recoverButton).click();
    }

    @Step("Переход на страницу логина через ссылку 'Войти'")
    public void clickLoginLink() {
        driver.findElement(loginLink).click();
    }
}
