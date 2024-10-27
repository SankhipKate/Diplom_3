package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.rules.ExternalResource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utils.EnvConfig;
import java.time.Duration;

public class DriverRule extends ExternalResource {

    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    // Инициализация драйвера перед каждым тестом
    @Override
    protected void before()  {
        initDriver();
    }

    // Корректное завершение работы драйвера после каждого теста
    @Override
    protected void after() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void initDriver() {
        // Логирование браузера, который будет использоваться
        String browser = System.getProperty("browser", "chrome");  // По умолчанию chrome
        System.out.println("Запуск тестов в браузере: " + browser);

        if ("yandex".equalsIgnoreCase(browser)) {
            startYandex();
        } else {
            startChrome();
        }
    }

    private void startChrome() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        // Указываем путь к Chrome
        options.setBinary("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
        // Аргумент для совместимости с новыми версиями
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(EnvConfig.IMPLICIT_WAIT));
    }

    private void startYandex() {
        // Настройка ChromeDriver для Яндекс.Браузера
        WebDriverManager.chromedriver().driverVersion(System.getProperty("driver.version")).setup();

        // Параметры ChromeOptions с указанием пути к бинарному файлу Яндекс.Браузера
        var options = new ChromeOptions();
        options.setBinary(System.getProperty("webdriver.yandex.bin"));
        // Аргумент для совместимости с новыми версиями
//        options.addArguments("--remote-allow-origins=*");
        // Запуск ChromeDriver с переданными опциями
        driver = new ChromeDriver(options);

        // Открытие браузера в полноэкранном режиме и установка таймаутов
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(EnvConfig.IMPLICIT_WAIT));
    }
}
