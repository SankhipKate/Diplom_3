package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import pageobjects.LoginPage;
import utils.EnvConfig;
import utils.TestConstants;
import utils.UserApi;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import static org.junit.Assert.assertEquals;

//Тесты для страницы логина
public class LoginPageTest {

    @ClassRule
    // Используем ClassRule для настройки WebDriver
    public static DriverRule driverRule = new DriverRule();
    // Получаем WebDriver через DriverRule
    private WebDriver driver = driverRule.getDriver();

    //Создание тестового пользователя через API перед каждым тестом.
    @Before
    public void setUp() {
        UserApi.createTestUser();
    }

    //Удаление тестового пользователя после выполнения тестов.
    @After
    public void tearDown() {
        UserApi.deleteUser(UserApi.token);
    }

    //Тест на успешную авторизацию
    //Проверяет переход на главную страницу после ввода правильных данных.
    @Test
    public void testSuccessfulLogin() {
        // Создаем страницу логина
        LoginPage loginPage = new LoginPage(driver);
        // Открываем страницу логина
        loginPage.openLoginPage();
        // Выполняем логин
        loginPage.login(UserApi.email, UserApi.password);
        // Добавляем явное ожидание перехода на главную страницу
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.IMPLICIT_WAIT));
        wait.until(ExpectedConditions.urlToBe(TestConstants.STELLAR_BURGERS_URL));
        // Проверяем, что перешли на главную страницу
        assertEquals(TestConstants.STELLAR_BURGERS_URL, driver.getCurrentUrl());
    }

    //Тест на отсутствие логина  при неправильном пароле.
    //Проверяет, что пользователь остается на странице логина при вводе неверного пароля.
    @Test
    public void testLoginWithInvalidPassword() {
        // Создаем страницу логина
        LoginPage loginPage = new LoginPage(driver);
        // Открываем страницу логина
        driver.get(TestConstants.LOGIN_PAGE_URL);
        // Вводим корректный email и неправильный пароль
        loginPage.enterEmail(UserApi.email);
        loginPage.enterPassword(TestConstants.WRONG_PASSWORD);
        loginPage.clickLoginButton();
        // Проверяем, что остались на странице логина
        assertEquals(TestConstants.LOGIN_PAGE_URL, driver.getCurrentUrl());
    }
}
