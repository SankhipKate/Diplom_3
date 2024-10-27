package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.EnvConfig;
import utils.TestConstants;
import java.time.Duration;

public class MainPage {

    private WebDriver driver;

    // Локаторы
    //кнопки
    private final By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By personalAccountButton = By.xpath("//a[@href='/account']/p[text()='Личный Кабинет']");
    // Локатор для кнопки "Конструктор"
    private By constructorButton = By.xpath("//p[contains(text(),'Конструктор')]");
    // Локатор для логотипа Stellar Burgers
    private By logo = By.xpath("//div[contains(@class,'AppHeader_header__logo')]");

    //разделы конструктора
    private final By bunsSection = By.xpath("//span[text()='Булки']");
    private final By saucesSection = By.xpath("//span[text()='Соусы']");
    private final By fillingsSection = By.xpath("//span[text()='Начинки']");
    //заголовки разделов конструктора
    private final By saucesHeader = By.xpath("//h2[text()='Соусы']");
    private final By fillingsHeader = By.xpath("//h2[text()='Начинки']");
    private final By bunsHeader = By.xpath("//h2[text()='Булки']");

    // Конструктор
    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Методы для действий с элементами
    @Step("Открытие главной страницы")
    public void open() {
        driver.get(TestConstants.STELLAR_BURGERS_URL);  // URL главной страницы из TestConstants
    }

    @Step("Клик на кнопку 'Войти в аккаунт'")
    public void clickLoginButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.IMPLICIT_WAIT));
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginBtn.click();
    }

    @Step("Клик на кнопку 'Личный Кабинет'")
    public void clickPersonalAccountButton() {
        driver.findElement(personalAccountButton).click();
    }

    @Step("Клик по кнопке 'Конструктор'")
    public void clickConstructorButton() {
        driver.findElement(constructorButton).click();
    }

    @Step("Клик по логотипу Stellar Burgers")
    public void clickLogo() {
        driver.findElement(logo).click();
    }

    // Методы для взаимодействия с разделами конструктора
    @Step("Клик на раздел 'Булки'")
    public void clickBunsSection() {
        driver.findElement(bunsSection).click();
    }

    @Step("Клик на раздел 'Соусы'")
    public void clickSaucesSection() {
        driver.findElement(saucesSection).click();
    }

    @Step("Клик на раздел 'Начинки'")
    public void clickFillingsSection() {
        driver.findElement(fillingsSection).click();
    }

    @Step("Проверка, что заголовок 'Булки' видим")
    public boolean isBunsHeaderDisplayed() {
        return driver.findElement(bunsHeader).isDisplayed();
    }

    @Step("Проверка, что заголовок 'Соусы' видим")
    public boolean isSaucesHeaderDisplayed() {
        return driver.findElement(saucesHeader).isDisplayed();
    }

    @Step("Проверка, что заголовок 'Начинки' видим")
    public boolean isFillingsHeaderDisplayed() {
        return driver.findElement(fillingsHeader).isDisplayed();
    }
}
