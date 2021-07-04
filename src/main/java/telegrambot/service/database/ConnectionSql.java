package telegrambot.service.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSql {
    private static Connection connection;
    private static final Logger LOGGER = LogManager.getLogger(ConnectionSql.class);
    private static final String DB_URL = "jdbc:mysql://localhost:3306/my_db";
    private static final String DB_USER = "wwroot";
    private static final String DB_PASS = "1qaz@WSX";

    private ConnectionSql() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            } catch (SQLException e) {
                LOGGER.warn("Error connection on DB 'my_db'!");
            }
        }
        return connection;
    }
}
