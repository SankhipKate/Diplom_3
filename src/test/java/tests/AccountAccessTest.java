package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import pageobjects.ForgotPasswordPage;
import pageobjects.LoginPage;
import pageobjects.MainPage;
import pageobjects.RegistrationPage;
import utils.EnvConfig;
import utils.TestConstants;
import utils.UserApi;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class AccountAccessTest {

    @ClassRule
    public static DriverRule driverRule = new DriverRule();
    private WebDriver driver = driverRule.getDriver();

    @Before
    public void setUp() {
        // Создание тестового пользователя через API
        UserApi.createTestUser();
    }

    @After
    public void tearDown() {
        // Удаление тестового пользователя через API
        UserApi.deleteUser(UserApi.token);
    }

    // Тест на проверку входа в аккаунт через кнопку "Войти в аккаунт" на главной странице
    @Test
    public void accessAccountThroughLoginButtonOnMainPage() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        // Открываем главную страницу
        mainPage.open();

        // Переход на страницу логина через кнопку "Войти в аккаунт" на главной странице
        mainPage.clickLoginButton();

        // Выполняем логин через UI на странице логина
        loginPage.login(UserApi.email, UserApi.password);

        // Проверка, что произошёл переход на основную страницу
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.IMPLICIT_WAIT));
        wait.until(ExpectedConditions.urlToBe(TestConstants.STELLAR_BURGERS_URL));
        assertEquals(TestConstants.STELLAR_BURGERS_URL, driver.getCurrentUrl());
    }

    // Тест на проверку входа в личный кабинет через кнопку "Личный кабинет" на главной странице
    @Test
    public void accessAccountThroughPersonalAccountButtonOnMainPage() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        // Открываем главную страницу
        mainPage.open();

        // Переход на страницу логина через кнопку "Личный кабинет" на главной странице
        mainPage.clickPersonalAccountButton();

        // Выполняем логин через UI на странице логина
        loginPage.login(UserApi.email, UserApi.password);

        // Проверка, что произошёл переход на основную страницу
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.IMPLICIT_WAIT));
        wait.until(ExpectedConditions.urlToBe(TestConstants.STELLAR_BURGERS_URL));
        assertEquals(TestConstants.STELLAR_BURGERS_URL, driver.getCurrentUrl());
    }

    // Тест на проверку перехода в личный кабинет через кнопку "Личный кабинет" под авторизацией
    @Test
    public void accessAccountFromMainPage() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        // Открываем страницу логина и выполняем авторизацию через UI
        loginPage.openLoginPage();
        loginPage.login(UserApi.email, UserApi.password);

        // Ожидаем перехода на главную страницу после логина
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.IMPLICIT_WAIT));
        wait.until(ExpectedConditions.urlToBe(TestConstants.STELLAR_BURGERS_URL));

        // Переход на страницу профиля через кнопку "Личный кабинет"
        mainPage.clickPersonalAccountButton();

        // Проверка, что произошёл переход в личный кабинет
        wait.until(ExpectedConditions.urlToBe(TestConstants.PERSONAL_ACCOUNT_URL));
        assertEquals(TestConstants.PERSONAL_ACCOUNT_URL, driver.getCurrentUrl());
    }

    //Проверка входа через кнопку "Войти" на странице регистрации.
    @Test
    public void accessAccountFromRegistrationPage() {
        RegistrationPage registrationPage = new RegistrationPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        // Открываем страницу регистрации и переходим на страницу логина
        registrationPage.open();
        registrationPage.clickLoginLink();

        // Выполняем авторизацию через UI
        loginPage.login(UserApi.email, UserApi.password);

        // Проверка, что произошёл переход на основную страницу
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.IMPLICIT_WAIT));
        wait.until(ExpectedConditions.urlToBe(TestConstants.STELLAR_BURGERS_URL));
        assertEquals(TestConstants.STELLAR_BURGERS_URL, driver.getCurrentUrl());
    }

    //Проверка входа через кнопку "Войти" на странице восстановления пароля.
    @Test
    public void accessAccountFromForgotPasswordPage() {
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        // Открываем страницу восстановления пароля и переходим на страницу логина
        forgotPasswordPage.open();
        forgotPasswordPage.clickLoginLink();

        // Выполняем авторизацию через UI
        loginPage.login(UserApi.email, UserApi.password);

        // Проверка, что произошёл переход на основную страницу
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.IMPLICIT_WAIT));
        wait.until(ExpectedConditions.urlToBe(TestConstants.STELLAR_BURGERS_URL));
        assertEquals(TestConstants.STELLAR_BURGERS_URL, driver.getCurrentUrl());
    }
}
