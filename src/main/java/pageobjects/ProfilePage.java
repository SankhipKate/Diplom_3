package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.EnvConfig;

import java.time.Duration;

public class ProfilePage {
    private WebDriver driver;

    // Локатор для кнопки "Конструктор"
    private By constructorButton = By.xpath("//p[contains(text(),'Конструктор')]");
    // Локатор для кнопки "Выйти"
    private By logoutButton = By.xpath("//button[contains(@class, 'Account_button__14Yp3')]");
    // Локатор для кнопки логотипа Stellar Burgers
    private By logoButton = By.xpath("//div[contains(@class, 'AppHeader_header__logo__')]");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Переход в раздел 'Конструктор'")
    public void clickConstructorButton() {
        driver.findElement(constructorButton).click();
    }

    @Step("Переход по логотипу Stellar Burgers")
    public void clickLogo() {
        driver.findElement(logoButton).click();
    }

    @Step("Выход из аккаунта")
    public void clickLogoutButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.IMPLICIT_WAIT));
        WebElement logoutBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(logoutButton));
        logoutBtn.click();
    }
}
