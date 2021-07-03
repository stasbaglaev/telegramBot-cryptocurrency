package service.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import telegrambot.handler.SystemHandler;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Random;

public class MainOld {
    private static final Logger LOGGER = LogManager.getLogger(SystemHandler.class);

    public static void main(String[] args) {
        Thread run = new Thread(() -> {
            while(true){
                try {
                    SimpleDateFormat formater = new SimpleDateFormat("yy.MM.dd HH:mm");
                    java.util.Date date = new java.util.Date();
                    ConnectionSQL.getConnection();
                    add("ETC", formater.format(date), generateValue(50, 90), generateValue(55, 95), generateValue(70, 105));
                    Thread.sleep(10000); //2000000 - 33 мин
                } catch (InterruptedException ex) {
                }
            }
        });
        run.start();
    }

    private static Integer generateValue(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    private static String add(String nameCrypt, String date, Integer valueUSD, Integer valueEUR, Integer valueRUB) {
        String sqlQuery = "INSERT INTO crypts(name, date, currencyUSD, currencyEUR, currencyRUB) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = ConnectionSQL.getConnection().prepareStatement(sqlQuery);
            preparedStatement.setString(1, nameCrypt);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, valueUSD.toString());
            preparedStatement.setString(4, valueEUR.toString());
            preparedStatement.setString(5, valueRUB.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            LOGGER.warn("Error while using the command 'INSERT INTO' on DB 'my_db' ");
            return "Информацию не удалось добавить!";
        }
        return "Информация добавлена!";
    }
}
