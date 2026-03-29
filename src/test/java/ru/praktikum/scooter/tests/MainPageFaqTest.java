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

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Тест выпадающего списка в разделе «Вопросы о важном»
public class MainPageFaqTest {

    private WebDriver driver;
    private MainPage mainPage;

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
        mainPage.open();
        mainPage.acceptCookiesIfDisplayed();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    // Параметры: индекс вопроса и ожидаемый текст ответа
    static Stream<Object[]> faqData() {
        return Stream.of(
                new Object[]{0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                new Object[]{1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                new Object[]{2, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                new Object[]{3, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                new Object[]{4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                new Object[]{5, "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                new Object[]{6, "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                new Object[]{7, "Да, обязательно. Всем самокатов! И Москве, и Московской области."}
        );
    }

    @ParameterizedTest
    @MethodSource("faqData")
    public void checkFaqAnswerText(int questionIndex, String expectedAnswer) {
        mainPage.clickFaqQuestion(questionIndex);
        String actualAnswer = mainPage.getFaqAnswerText(questionIndex);
        assertEquals(expectedAnswer, actualAnswer, "Текст ответа FAQ с индексом " + questionIndex + " не совпадает");
    }
}
