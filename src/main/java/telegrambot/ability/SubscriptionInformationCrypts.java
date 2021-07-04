package telegrambot.ability;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import telegrambot.service.database.ConnectionSql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class SubscriptionInformationCrypts {
    private static final Logger LOGGER = LogManager.getLogger(SubscriptionInformationCrypts.class);
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yy.MM.dd HH:mm");


    public static List<String> getSubscription(String chatId) {
        List<String> listCrypt = new LinkedList<>();
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement;
            String sqlQuery = "SELECT crypt, chatID, date FROM subscription where chatID=?";
            preparedStatement = ConnectionSql.getConnection().prepareStatement(sqlQuery);
            preparedStatement.setString(1, chatId);
            resultSet = preparedStatement.executeQuery();
            while (true) {
                assert resultSet != null;
                try {
                    if (!resultSet.next()) break;
                    listCrypt.add(resultSet.getString("crypt"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listCrypt;
    }

    public void updateDB(String name, String chatId) {
        List<String> listCryptsSubscription = getSubscription(chatId);
        if (!listCryptsSubscription.contains(name)) {
            insertInto(name, chatId);
        }
    }

    private static void insertInto(String name, String chatId) {
        String sqlQuery = "INSERT INTO subscription(crypt, chatID, date) VALUES (?,?,?)";
        java.util.Date date = new java.util.Date();
        try {
            PreparedStatement preparedStatement = ConnectionSql.getConnection().prepareStatement(sqlQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, chatId);
            preparedStatement.setString(3, FORMAT.format(date));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Failed to add information on subscription to table 'my_db.subscription'");
        }
    }

    public void deleteDB(String name, String chatId) {
        List<String> listCryptsSubscription = getSubscription(chatId);
        if (listCryptsSubscription.contains(name)) {
            delete(name, chatId);
        }
    }

    private static void delete(String name, String chatId) {
        PreparedStatement preparedStatement;
        String sqlQuery = "DELETE FROM subscription WHERE crypt=? AND chatID=?";
        try {
            preparedStatement = ConnectionSql.getConnection().prepareStatement(sqlQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, chatId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Failed to delete information on subscription to table 'my_db.subscription'");
        }
    }

}
