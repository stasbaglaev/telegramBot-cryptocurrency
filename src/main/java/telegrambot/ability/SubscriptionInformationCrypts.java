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
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = null;
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
        PreparedStatement preparedStatement = null;
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


//    public List<String> getPriceBtc() {
//        ResultSet resultSet = select("BTC");
//        List<String> priceCryptBtc = new LinkedList<>();
//        while (true) {
//            assert resultSet != null;
//            try {
//                if (!resultSet.next()) break;
//                priceCryptBtc.add(resultSet.getString("name"));
//                priceCryptBtc.add(resultSet.getString("USD"));
//                priceCryptBtc.add(resultSet.getString("EUR"));
//                priceCryptBtc.add(resultSet.getString("RUB"));
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return priceCryptBtc;
//    }
//
//    public List<String> getPriceEth() {
//        ResultSet resultSet = select("ETH");
//        List<String> priceCryptEth = new LinkedList<>();
//        while (true) {
//            assert resultSet != null;
//            try {
//                if (!resultSet.next()) break;
//                priceCryptEth.add(resultSet.getString("name"));
//                priceCryptEth.add(resultSet.getString("USD"));
//                priceCryptEth.add(resultSet.getString("EUR"));
//                priceCryptEth.add(resultSet.getString("RUB"));
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return priceCryptEth;
//    }
//
//    public List<String> getPriceBnb() {
//        ResultSet resultSet = select("BNB");
//        List<String> priceCryptBnb = new LinkedList<>();
//        while (true) {
//            assert resultSet != null;
//            try {
//                if (!resultSet.next()) break;
//                priceCryptBnb.add(resultSet.getString("name"));
//                priceCryptBnb.add(resultSet.getString("USD"));
//                priceCryptBnb.add(resultSet.getString("EUR"));
//                priceCryptBnb.add(resultSet.getString("RUB"));
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return priceCryptBnb;
//    }
//
//    public List<String> getPriceUni() {
//        ResultSet resultSet = select("UNI");
//        List<String> priceCryptUni = new LinkedList<>();
//        while (true) {
//            assert resultSet != null;
//            try {
//                if (!resultSet.next()) break;
//                priceCryptUni.add(resultSet.getString("name"));
//                priceCryptUni.add(resultSet.getString("USD"));
//                priceCryptUni.add(resultSet.getString("EUR"));
//                priceCryptUni.add(resultSet.getString("RUB"));
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return priceCryptUni;
//    }
//
//    public List<String> getPriceDot() {
//        ResultSet resultSet = select("DOT");
//        List<String> priceCryptDot = new LinkedList<>();
//        while (true) {
//            assert resultSet != null;
//            try {
//                if (!resultSet.next()) break;
//                priceCryptDot.add(resultSet.getString("name"));
//                priceCryptDot.add(resultSet.getString("USD"));
//                priceCryptDot.add(resultSet.getString("EUR"));
//                priceCryptDot.add(resultSet.getString("RUB"));
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return priceCryptDot;
//    }
//
//    public List<String> getPriceSol() {
//        ResultSet resultSet = select("SOL");
//        List<String> priceCryptSol = new LinkedList<>();
//        while (true) {
//            assert resultSet != null;
//            try {
//                if (!resultSet.next()) break;
//                priceCryptSol.add(resultSet.getString("name"));
//                priceCryptSol.add(resultSet.getString("USD"));
//                priceCryptSol.add(resultSet.getString("EUR"));
//                priceCryptSol.add(resultSet.getString("RUB"));
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return priceCryptSol;
//    }


//    private static ResultSet select(String nameCrypt) {
//        String sqlQuery = "SELECT name, date, USD, EUR, RUB FROM crypts where name=? order by date DESC limit 1";
//        try {
//            PreparedStatement preparedStatement = ConnectionSQL.getConnection().prepareStatement(sqlQuery);
//            preparedStatement.setString(1, nameCrypt);
//            return preparedStatement.executeQuery();
//        } catch (SQLException e) {
//            LOGGER.warn("I can not complete the request: " + sqlQuery);
//        }
//        return null;
//    }
}
