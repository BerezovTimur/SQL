package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement errorMessage = $("[data-test-id=error-notification]");

    public VerificationPage validAuth(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }

    public void invalidAuth(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        errorMessage.shouldBe(Condition.visible);
    }

    public void sendInvalidPassword(String password) {
        passwordField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        passwordField.doubleClick().sendKeys(Keys.DELETE);
        passwordField.sendKeys(password);
        loginButton.click();
    }

    public void sendInvalidPasswordThirdTime(String password) {
        passwordField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        passwordField.doubleClick().sendKeys(Keys.DELETE);
        passwordField.sendKeys(password);
        loginButton.click();
        loginButton.shouldBe(Condition.disabled);
    }
}
