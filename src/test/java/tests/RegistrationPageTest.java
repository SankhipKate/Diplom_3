package tests;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.RegistrationPage;
import utils.EnvConfig;
import utils.TestConstants;
import utils.UserApi;

import java.time.Duration;

import static org.junit.Assert.assertEquals;


// Тесты для страницы регистрации Stellar Burgers.
// Проверяются успешная регистрация и последующее удаление пользователя через API.
public class RegistrationPageTest {

    // Инициализируем драйвер через DriverRule
    private WebDriver driver;
    private DriverRule driverRule = new DriverRule();

    private String registeredEmail;
    private String registeredPassword;

    // Инициализация драйвера перед каждым тестом
    @Before
    public void setUp() {
        driverRule.before();  // Инициализация WebDriver через DriverRule
        driver = driverRule.getDriver();
    }

    // Очистка после теста: удаление пользователя через API и закрытие драйвера
    @After
    public void tearDown() {
        if (registeredEmail != null && registeredPassword != null) {
            // Получаем токен с помощью email и пароля пользователя
            String token = UserApi.getToken(registeredEmail, registeredPassword);
            // Удаляем пользователя через API
            UserApi.deleteUser(token);
        }

        // Закрываем драйвер после теста
        driverRule.after();
    }

    // Тест на успешную регистрацию пользователя через UI
    @Test
    public void testSuccessfulRegistration() {
        RegistrationPage registrationPage = new RegistrationPage(driver);
        // Переход на страницу регистрации
        driver.get(TestConstants.REGISTER_PAGE_URL);

        // Генерируем уникальный email и пароль для пользователя
        registeredEmail = TestConstants.TEST_EMAIL_PREFIX + System.currentTimeMillis() + TestConstants.TEST_EMAIL_DOMAIN;
        registeredPassword = TestConstants.VALID_PASSWORD_123;

        // Вводим данные для регистрации
        registrationPage.enterName(TestConstants.TEST_USER_NAME);
        registrationPage.enterEmail(registeredEmail);
        registrationPage.enterPassword(registeredPassword);
        registrationPage.clickRegisterButton();

        // Явное ожидание перехода на страницу логина
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.IMPLICIT_WAIT));
        wait.until(ExpectedConditions.urlToBe(TestConstants.LOGIN_PAGE_URL));

        // Проверяем, что успешная регистрация направляет на личный кабинет
        assertEquals(TestConstants.LOGIN_PAGE_URL, driver.getCurrentUrl());
    }

    // Тест на ошибку при регистрации с некорректным паролем (короткий пароль).
    @Test
    public void testRegistrationWithInvalidPassword() {
        RegistrationPage registrationPage = new RegistrationPage(driver);
        // Переход на страницу регистрации
        driver.get(TestConstants.REGISTER_PAGE_URL);
        // Вводим имя пользователя
        registrationPage.enterName(TestConstants.TEST_USER_NAME);
        // Вводим сгенерированный email
        registrationPage.enterEmail(TestConstants.TEST_EMAIL_PREFIX + System.currentTimeMillis() + TestConstants.TEST_EMAIL_DOMAIN);
        // Используем короткий некорректный пароль
        registrationPage.enterPassword(TestConstants.SHORT_INVALID_PASSWORD);
        registrationPage.clickRegisterButton();
        // Проверка, что остались на странице регистрации
        assertEquals(TestConstants.REGISTER_PAGE_URL, driver.getCurrentUrl());
    }
}