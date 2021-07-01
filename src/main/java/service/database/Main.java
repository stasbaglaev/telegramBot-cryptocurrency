package service.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import telegrambot.handler.SystemHandler;

import java.sql.*;
import java.text.SimpleDateFormat;

public class Main {
    private static Connection connection;
    private static Statement statement;
    private static final Logger LOGGER = LogManager.getLogger(SystemHandler.class);

    public static void main(String[] args) {
        SimpleDateFormat formater = new SimpleDateFormat("yy.MM.dd HH:mm");
        java.util.Date date = new java.util.Date();
        connectSQL();
        add("ETC", formater.format(date),3050);
    }

    public static void connectSQL(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_db","wwroot","1qaz@WSX");
            statement = connection.createStatement();

        } catch (SQLException e) {
            LOGGER.warn("Error connection on DB 'my_db' ");
        }
    }

    private static String add (String nameCrypt, String date, Integer priceCrypt){
        String sqlQuery = "INSERT INTO crypts(name, date, currency) VALUES (?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, nameCrypt);
            preparedStatement.setString(2,date);
            preparedStatement.setString(3,priceCrypt.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            LOGGER.warn("Error while using the command 'INSERT INTO' on DB 'my_db' ");
            return "Информацию не удалось добавить!";
        }
        return "Информация добавлена!";
    }
}
