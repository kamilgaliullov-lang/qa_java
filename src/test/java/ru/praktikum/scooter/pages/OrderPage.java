package ru.praktikum.scooter.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {

    private final WebDriver driver;

    // Поле «Имя»
    private final By firstNameField = By.xpath(".//input[@placeholder='* Имя']");

    // Поле «Фамилия»
    private final By lastNameField = By.xpath(".//input[@placeholder='* Фамилия']");

    // Поле «Адрес»
    private final By addressField = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");

    // Поле «Станция метро»
    private final By metroStationField = By.xpath(".//input[@placeholder='* Станция метро']");

    // Поле «Телефон»
    private final By phoneField = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");

    // Кнопка «Далее»
    private final By nextButton = By.xpath(".//button[text()='Далее']");

    // Поле «Когда привезти самокат»
    private final By dateField = By.xpath(".//input[@placeholder='* Когда привезти самокат']");

    // Выпадающий список «Срок аренды»
    private final By rentPeriodDropdown = By.className("Dropdown-placeholder");

    // Чекбокс чёрный цвет
    private final By blackColorCheckbox = By.id("black");

    // Чекбокс серый цвет
    private final By greyColorCheckbox = By.id("grey");

    // Поле «Комментарий для курьера»
    private final By commentField = By.xpath(".//input[@placeholder='Комментарий для курьера']");

    // Кнопка «Заказать» на форме
    private final By orderSubmitButton = By.xpath(".//div[contains(@class, 'Order_Buttons')]//button[text()='Заказать']");

    // Кнопка подтверждения «Да»
    private final By confirmButton = By.xpath(".//button[text()='Да']");

    // Модальное окно с текстом об успешном создании заказа
    private final By orderSuccessModal = By.xpath(".//div[contains(@class, 'Order_ModalHeader') and contains(text(), 'Заказ оформлен')]");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    // Заполнить первую часть формы: «Для кого самокат»
    public void fillPersonalInfo(String firstName, String lastName, String address, String metroStation, String phone) {
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(addressField).sendKeys(address);

        // Выбор станции метро: кликаем по полю, вводим текст и выбираем из списка
        driver.findElement(metroStationField).click();
        driver.findElement(metroStationField).sendKeys(metroStation);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Ждём появления выпадающего списка и кликаем по первому подходящему варианту
        By metroOption = By.xpath(".//div[contains(@class, 'select-search')]//div[contains(@class, 'Order_Text') or contains(@class, 'select-search__select')]//button");
        wait.until(ExpectedConditions.elementToBeClickable(metroOption));
        driver.findElement(metroOption).click();

        driver.findElement(phoneField).sendKeys(phone);
    }

    // Нажать кнопку «Далее»
    public void clickNextButton() {
        driver.findElement(nextButton).click();
    }

    // Заполнить вторую часть формы: «Про аренду»
    public void fillRentInfo(String date, String rentPeriod, String color, String comment) {
        driver.findElement(dateField).sendKeys(date);
        // Нажать Enter, чтобы закрыть календарь
        driver.findElement(dateField).sendKeys(Keys.ENTER);

        // Выбор срока аренды
        driver.findElement(rentPeriodDropdown).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        By rentOption = By.xpath(".//div[@class='Dropdown-menu']//div[text()='" + rentPeriod + "']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(rentOption));
        driver.findElement(rentOption).click();

        // Выбор цвета
        if ("чёрный жемчуг".equals(color)) {
            driver.findElement(blackColorCheckbox).click();
        } else if ("серая безысходность".equals(color)) {
            driver.findElement(greyColorCheckbox).click();
        }

        // Комментарий
        if (comment != null && !comment.isEmpty()) {
            driver.findElement(commentField).sendKeys(comment);
        }
    }

    // Нажать кнопку «Заказать» на форме
    public void clickOrderButton() {
        driver.findElement(orderSubmitButton).click();
    }

    // Подтвердить заказ, нажать «Да»
    public void confirmOrder() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(confirmButton));
        driver.findElement(confirmButton).click();
    }

    // Проверить, что всплывающее окно с сообщением об успешном заказе появилось
    public boolean isOrderSuccessModalDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessModal));
        return driver.findElement(orderSuccessModal).isDisplayed();
    }
}
