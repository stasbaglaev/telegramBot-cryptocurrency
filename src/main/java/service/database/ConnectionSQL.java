package service.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import telegrambot.handler.SystemHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionSQL {
    private static Connection connection;
    private static Statement statement;
    private static final Logger LOGGER = LogManager.getLogger(SystemHandler.class);

    public void connectSQL() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_db", "wwroot", "1qaz@WSX");
            statement = connection.createStatement();

        } catch (SQLException e) {
            LOGGER.warn("Error connection on DB 'my_db' ");
        }
    }
}
