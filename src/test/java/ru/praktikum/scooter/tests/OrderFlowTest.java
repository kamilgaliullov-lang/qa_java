package ru.praktikum.scooter.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import ru.praktikum.scooter.pages.MainPage;
import ru.praktikum.scooter.pages.OrderPage;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

// Тест позитивного сценария заказа самоката
public class OrderFlowTest {

    private WebDriver driver;
    private MainPage mainPage;
    private OrderPage orderPage;

    @BeforeEach
    public void setUp() {
        String browser = System.getProperty("browser", "chrome");
        if ("firefox".equalsIgnoreCase(browser)) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver(new FirefoxOptions());
        } else {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(new ChromeOptions());
        }
        mainPage = new MainPage(driver);
        orderPage = new OrderPage(driver);
        mainPage.open();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    // Параметры: точка входа (top/bottom), имя, фамилия, адрес, станция метро, телефон,
    //            дата, срок аренды, цвет, комментарий
    static Stream<Object[]> orderData() {
        return Stream.of(
                // Набор данных 1, кнопка «Заказать» вверху
                new Object[]{
                        "top",
                        "Махмутгарай", "Булатов", "ул. Ленина, д. 1", "Черкизовская", "+79991234567",
                        "31.03.2026", "сутки", "чёрный жемчуг", "Позвоните за час"
                },
                // Набор данных 2, кнопка «Заказать» внизу
                new Object[]{
                        "bottom",
                        "Камалия", "Мазитова", "Проспект Мира, д. 10", "Лубянка", "+79997654321",
                        "01.04.2026", "двое суток", "серая безысходность", "Домофон не работает"
                }
        );
    }

    @ParameterizedTest
    @MethodSource("orderData")
    public void checkOrderFlow(String entryPoint, String firstName, String lastName,
                               String address, String metroStation, String phone,
                               String date, String rentPeriod, String color, String comment) {
        // Нажать кнопку «Заказать», верхнюю или нижнюю
        if ("top".equals(entryPoint)) {
            mainPage.clickOrderButtonTop();
        } else {
            mainPage.clickOrderButtonBottom();
        }

        // Заполнить форму «Для кого самокат»
        orderPage.fillPersonalInfo(firstName, lastName, address, metroStation, phone);
        orderPage.clickNextButton();

        // Заполнить форму «Про аренду»
        orderPage.fillRentInfo(date, rentPeriod, color, comment);

        // Нажать «Заказать» и подтвердить
        orderPage.clickOrderButton();
        orderPage.confirmOrder();

        // Проверить, что появилось всплывающее окно с сообщением об успешном создании заказа
        assertTrue(orderPage.isOrderSuccessModalDisplayed(),
                "Модальное окно успешного заказа не появилось");
    }
}
