package tests;

import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobjects.MainPage;
import utils.TestConstants;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MainPageTest {
    // Используем ClassRule для настройки WebDriver
    @ClassRule
    public static DriverRule driverRule = new DriverRule();  // Используем ClassRule
    // Получаем WebDriver через DriverRule
    private WebDriver driver = driverRule.getDriver();  // Получаем WebDriver через DriverRule

    //Тест проверяет, что после клика на кнопку "Войти" на главной странице
    //пользователь переходит на страницу входа в аккаунт.
    @Test
    public void testClickLoginButton() {
        MainPage mainPage = new MainPage(driver);
        // Переход на главную страницу
        driver.get(TestConstants.STELLAR_BURGERS_URL);
        // Клик на кнопку "Войти"
        mainPage.clickLoginButton();
        // Проверка, что открылась страница входа
        assertEquals(TestConstants.LOGIN_PAGE_URL, driver.getCurrentUrl());
    }

    //Тест проверяет, что после клика на кнопку "Личный кабинет" на главной странице
    //пользователь попадает на страницу входа в личный кабинет (если не авторизован).
    @Test
    public void testClickPersonalAccountButton() {
        MainPage mainPage = new MainPage(driver);
        // Переход на главную страницу
        driver.get(TestConstants.STELLAR_BURGERS_URL);
        // Клик на кнопку "Личный кабинет"
        mainPage.clickPersonalAccountButton();
        // Проверка, что открылась страница входа в личный кабинет
        assertEquals(TestConstants.LOGIN_PAGE_URL, driver.getCurrentUrl());
    }

    //тесты разделов конструктора
    //тест раздела булки
    @Test
    public void testClickBunsSection() {
        MainPage mainPage = new MainPage(driver);
        // Переход на главную страницу
        driver.get(TestConstants.STELLAR_BURGERS_URL);
        // Клик на раздел "Соусы"
        mainPage.clickSaucesSection();
        // Клик на раздел "Булки"
        mainPage.clickBunsSection();
        // Проверяем, что элемент видим
        assertTrue(mainPage.isBunsHeaderDisplayed());
    }
    //тест раздела соусы
    @Test
    public void testClickSaucesSection() {
        MainPage mainPage = new MainPage(driver);
        // Переход на главную страницу
        driver.get(TestConstants.STELLAR_BURGERS_URL);
        // Клик на раздел "Соусы"
        mainPage.clickSaucesSection();
        // Проверяем, что элемент видим
        assertTrue(mainPage.isSaucesHeaderDisplayed());
    }

    //тест раздела начинки
    @Test
    public void testClickFillingsSection() {
        MainPage mainPage = new MainPage(driver);
        // Переход на главную страницу
        driver.get(TestConstants.STELLAR_BURGERS_URL);
        // Клик на раздел "Начинки"
        mainPage.clickFillingsSection();
        // Проверяем, что элемент видим
        assertTrue(mainPage.isFillingsHeaderDisplayed());
    }
}
