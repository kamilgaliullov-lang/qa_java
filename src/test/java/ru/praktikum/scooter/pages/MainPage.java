package ru.praktikum.scooter.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {

    private final WebDriver driver;

    // URL главной страницы
    private static final String PAGE_URL = "https://qa-scooter.praktikum-services.ru/";

    // Кнопка «Заказать» вверху страницы (в хедере)
    private final By orderButtonTop = By.xpath(".//div[contains(@class, 'Header_Nav')]//button[text()='Заказать']");

    // Кнопка «Заказать» внизу страницы
    private final By orderButtonBottom = By.xpath(".//div[contains(@class, 'Home_FinishButton')]//button[text()='Заказать']");

    // Заголовок раздела «Вопросы о важном»
    private final By faqSectionTitle = By.xpath(".//div[text()='Вопросы о важном']");

    // Кнопка принятия куки
    private final By cookieAcceptButton = By.id("rcc-confirm-button");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Открыть главную страницу
    public void open() {
        driver.get(PAGE_URL);
    }

    // Принять куки, если отображаются
    public void acceptCookiesIfDisplayed() {
        try {
            WebElement cookieButton = driver.findElement(cookieAcceptButton);
            if (cookieButton.isDisplayed()) {
                cookieButton.click();
            }
        } catch (Exception e) {
            // Куки-баннер не найден
        }
    }

    // Локатор вопроса FAQ по индексу
    private By getFaqQuestionLocator(int index) {
        return By.id("accordion__heading-" + index);
    }

    // Локатор ответа FAQ по индексу
    private By getFaqAnswerLocator(int index) {
        return By.id("accordion__panel-" + index);
    }

    // Прокрутить к разделу FAQ и кликнуть на вопрос по индексу
    public void clickFaqQuestion(int index) {
        WebElement faqTitle = driver.findElement(faqSectionTitle);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", faqTitle);

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(getFaqQuestionLocator(index)));
        driver.findElement(getFaqQuestionLocator(index)).click();
    }

    // Получить текст ответа FAQ по индексу
    public String getFaqAnswerText(int index) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(getFaqAnswerLocator(index)));
        return driver.findElement(getFaqAnswerLocator(index)).getText();
    }

    // Нажать кнопку «Заказать» вверху
    public void clickOrderButtonTop() {
        driver.findElement(orderButtonTop).click();
    }

    // Нажать кнопку «Заказать» внизу
    public void clickOrderButtonBottom() {
        WebElement button = driver.findElement(orderButtonBottom);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", button);
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(button));
        button.click();
    }
}
