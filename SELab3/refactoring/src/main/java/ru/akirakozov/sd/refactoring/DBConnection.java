package ru.akirakozov.sd.refactoring;

import java.sql.*;
import java.util.Properties;

public class DBConnection {
    public DBConnection() {

    }

    public ResultSet  executeQuery(String sql) throws SQLException {
        try (Connection c = getConnection()) {
            Statement stmt = c.createStatement();
            ResultSet rs = null;
            if (sql.contains("SELECT")) {
                rs = stmt.executeQuery(sql);
            } else {
                stmt.executeUpdate(sql);
                stmt.close();
            }
            return rs;
        }
    }

    private Connection getConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "barter");
        props.setProperty("password", "barter");
        return DriverManager.getConnection("jdbc:postgresql:barter", props);
    }
}
