import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver(); // Создание драйвера перед каждым тестом
    }

    @Test
    public void testPageTitle() {
        driver.get("https://qa-mesto.praktikum-services.ru/signin");
        String title = driver.getTitle(); // Получение заголовка страницы
        assertEquals("React App", title); // Проверка заголовка страницы
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Закрытие браузера
        }
    }
}
