package ru.netology.data;

import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHelper {
    private DataHelper() {
    }

    public static void cleanData() throws SQLException {
        QueryRunner runner = new QueryRunner();
        val codes = "DELETE FROM auth_codes";
        val cards = "DELETE FROM cards";
        val users = "DELETE FROM users";

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                )
        ) {
            runner.update(conn, codes);
            runner.update(conn, cards);
            runner.update(conn, users);
        }
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getInvalidLogin() {
        return new AuthInfo("virusPetya", "virusPetya");
    }

    public static AuthInfo getInvalidPassword() {
        return new AuthInfo("vasya", "asdfg");
    }

    public static String invalidPassword() {
        return "asdfg";
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static String getVerificationCodeForVasya() throws SQLException {
        val verificationCode = "SELECT code FROM auth_codes WHERE created = (SELECT MAX(created) FROM auth_codes);";

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
                val countStmt = conn.createStatement();
        ) {
            try (val rs = countStmt.executeQuery(verificationCode)) {
                if (rs.next()) {
                    // выборка значения по индексу столбца (нумерация с 1)
                    val code = rs.getString("code");
                    // TODO: использовать
                    return code;
                }
            }
        }
        return null;
    }

    public static String getInvalidVerificationCode() {
        return "54321";
    }
}
