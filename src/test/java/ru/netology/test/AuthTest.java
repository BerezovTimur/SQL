package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;

public class AuthTest {

    @AfterAll
    public static void cleanTables() throws SQLException {
        DataHelper.cleanData();
    }

    @Test
    void shouldEnterIfCorrectData() throws SQLException {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validAuth(authInfo);
        String verificationCode = DataHelper.getVerificationCodeForVasya();
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldNotEnterIfNotCorrectData() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getInvalidLogin();
        loginPage.invalidAuth(authInfo);
    }

    @Test
    void shouldNotEnterIfInvalidPassword() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getInvalidPassword();
        loginPage.invalidAuth(authInfo);
    }

    @Test
    void shouldNotEnterIfInvalidCode() throws SQLException {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validAuth(authInfo);
        val verificationCode = DataHelper.getInvalidVerificationCode();
        verificationPage.invalidVerify(verificationCode);
    }

    @Test
    void shouldBlockWhenThreeInvalidPasswords() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getInvalidPassword();
        loginPage.invalidAuth(authInfo);
        val invalidPassword = DataHelper.invalidPassword();
        loginPage.sendInvalidPassword(invalidPassword);
        loginPage.sendInvalidPasswordThirdTime(invalidPassword);
    }
}
