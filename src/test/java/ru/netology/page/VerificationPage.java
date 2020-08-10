package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private SelenideElement error = $("[data-test-id=error-notification]");

    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    public void invalidVerify(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
        error.shouldBe(visible);
    }

    public AccountPage validVerify(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
        return new AccountPage();
    }
}
