package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import pageobjects.LoginPage;
import pageobjects.MainPage;
import pageobjects.ProfilePage;
import utils.EnvConfig;
import utils.TestConstants;
import utils.UserApi;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class AccountNavigationTest {

    @ClassRule
    public static DriverRule driverRule = new DriverRule();
    private WebDriver driver = driverRule.getDriver();

    @Before
    public void setUp() {
        // Создание тестового пользователя через API
        UserApi.createTestUser();
        // Переход на страницу логина и выполнение авторизации
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        mainPage.open();
        mainPage.clickLoginButton();
        loginPage.login(UserApi.email, UserApi.password);

        // Ожидание перехода на главную страницу после логина
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.IMPLICIT_WAIT));
        wait.until(ExpectedConditions.urlToBe(TestConstants.STELLAR_BURGERS_URL));
    }

    @After
    public void tearDown() {
        // Удаление тестового пользователя через API
        UserApi.deleteUser(UserApi.token);
    }

    // Проверка перехода на основную страницу по кнопке «Конструктор» из личного кабинета
    @Test
    public void navigateToConstructorFromProfile() {
        ProfilePage profilePage = new ProfilePage(driver);

        // Переход в личный кабинет
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccountButton();

        // Клик по кнопке «Конструктор»
        profilePage.clickConstructorButton();

        // Проверка, что произошёл переход на главную страницу
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.IMPLICIT_WAIT));
        wait.until(ExpectedConditions.urlToBe(TestConstants.STELLAR_BURGERS_URL));
        assertEquals(TestConstants.STELLAR_BURGERS_URL, driver.getCurrentUrl());
    }

    // Проверка перехода на главную страницу по клику на логотип Stellar Burgers из личного кабинета
    @Test
    public void navigateToMainPageFromProfileByLogo() {
        ProfilePage profilePage = new ProfilePage(driver);

        // Переход в личный кабинет
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccountButton();

        // Клик по логотипу Stellar Burgers
        profilePage.clickLogo();

        // Проверка, что произошёл переход на главную страницу
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.IMPLICIT_WAIT));
        wait.until(ExpectedConditions.urlToBe(TestConstants.STELLAR_BURGERS_URL));
        assertEquals(TestConstants.STELLAR_BURGERS_URL, driver.getCurrentUrl());
    }

    // Тест на выход из аккаунта через личный кабинет
    @Test
    public void testLogoutFromProfile() {
        ProfilePage profilePage = new ProfilePage(driver);
        MainPage mainPage = new MainPage(driver);

        // Переход в личный кабинет
        mainPage.clickPersonalAccountButton();

        // Нажатие на кнопку "Выйти"
        profilePage.clickLogoutButton();

        // Ожидание перехода на страницу логина
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.IMPLICIT_WAIT));
        wait.until(ExpectedConditions.urlToBe(TestConstants.LOGIN_PAGE_URL));

        // Проверка, что текущий URL соответствует странице логина
        assertEquals(TestConstants.LOGIN_PAGE_URL, driver.getCurrentUrl());
    }
}
