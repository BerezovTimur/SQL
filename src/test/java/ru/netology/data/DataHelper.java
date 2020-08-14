package ru.netology.data;

import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHelper {
    String url = "jdbc:mysql://localhost:3306/app";
    String user = "app";
    String password = "pass";

    public DataHelper() {
    }

    public void cleanData() throws SQLException {
        QueryRunner runner = new QueryRunner();
        val codes = "DELETE FROM auth_codes";
        val cards = "DELETE FROM cards";
        val users = "DELETE FROM users";

        try (
                val conn = DriverManager.getConnection(url, user, password);
        ) {
            runner.update(conn, codes);
            runner.update(conn, cards);
            runner.update(conn, users);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Value
    public class AuthInfo {
        String login;
        String password;
    }

    public AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public AuthInfo getInvalidLogin() {
        return new AuthInfo("virusPetya", "virusPetya");
    }

    public AuthInfo getInvalidPassword() {
        return new AuthInfo("vasya", "asdfg");
    }

    public String invalidPassword() {
        return "asdfg";
    }

    @Value
    public class VerificationCode {
        private String code;
    }

    public String getUserId() {
        val getUserId = "SELECT id FROM users WHERE login = 'vasya';";
        try (
                val connect = DriverManager.getConnection(url, user, password
                );
                val createStmt = connect.createStatement();
        ) {
            try (val resultSet = createStmt.executeQuery(getUserId)) {
                if (resultSet.next()) {
                    val userId = resultSet.getString(1);
                    return userId;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public String getVerificationCode() {
        val requestCode = "SELECT code FROM auth_codes WHERE user_id = ? ORDER BY created DESC LIMIT 1;";

        try (
                val connect = DriverManager.getConnection(url, user, password
                );
                val prepareStmt = connect.prepareStatement(requestCode);
        ) {
            prepareStmt.setString(1, getUserId());
            try (val resultSet = prepareStmt.executeQuery()) {
                if (resultSet.next()) {
                    val code = resultSet.getString(1);
                    return code;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public String getInvalidVerificationCode() {
        return "54321";
    }
}
