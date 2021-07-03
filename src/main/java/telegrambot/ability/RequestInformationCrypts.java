package telegrambot.ability;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.database.ConnectionSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RequestInformationCrypts {
    private static final Logger LOGGER = LogManager.getLogger(RequestInformationCrypts.class);


    public static void main(String[] args) {
        //getPriceCrypt();
//        getPriceBtc();
//        getPriceEth();
//        getPriceBnb();
//        getPriceUni();
//        getPriceDot();
//        getPriceSol();
//        System.out.println(priceCryptBtc);
//        System.out.println(priceCryptEth);
//        System.out.println(priceCryptBnb);
//        System.out.println(priceCryptUni);
//        System.out.println(priceCryptDot);
//        System.out.println(priceCryptSol);
    }

//    public RequestInformationCrypts() {
//        getPriceCrypt();
//    }

//    private static void getPriceCrypt() {
//        try (ResultSet resultSet = select()) {
//            try {
//                while (true) {
//                    assert resultSet != null;
//                    if (!resultSet.next()) break;
//                    for (int i = 0; i <= 6; i++) {
//                        String name = resultSet.getString("name");
//                        String valueUSD = resultSet.getString("USD");
//                        String valueEUR = resultSet.getString("EUR");
//                        String valueRUB = resultSet.getString("RUB");
//                        switch (name) {
//                            case "BTC":
//                                priceCryptBtc.add(name);
//                                priceCryptBtc.add(valueUSD);
//                                priceCryptBtc.add(valueEUR);
//                                priceCryptBtc.add(valueRUB);
//                                break;
//                            case "ETH":
//                                priceCryptEth.add(name);
//                                priceCryptEth.add(valueUSD);
//                                priceCryptEth.add(valueEUR);
//                                priceCryptEth.add(valueRUB);
//                                break;
//                            case "BNB":
//                                priceCryptBnb.add(name);
//                                priceCryptBnb.add(valueUSD);
//                                priceCryptBnb.add(valueEUR);
//                                priceCryptBnb.add(valueRUB);
//                                break;
//                            case "UNI":
//                                priceCryptUni.add(name);
//                                priceCryptUni.add(valueUSD);
//                                priceCryptUni.add(valueEUR);
//                                priceCryptUni.add(valueRUB);
//                                break;
//                            case "DOT":
//                                priceCryptDot.add(name);
//                                priceCryptDot.add(valueUSD);
//                                priceCryptDot.add(valueEUR);
//                                priceCryptDot.add(valueRUB);
//                                break;
//                            case "SOL":
//                                priceCryptSol.add(name);
//                                priceCryptSol.add(valueUSD);
//                                priceCryptSol.add(valueEUR);
//                                priceCryptSol.add(valueRUB);
//                                break;
//                        }
//                    }
//                }
//            } catch (SQLException e) {
//                LOGGER.warn("Error! ");
//            }
//        } catch (SQLException e) {
//            LOGGER.warn("Error! The request is being generated, but there may be an error in the structure of the database table");
//        }
//    }

//    private static void getPriceCrypt() {
//        //getPriceBtc();
//        getPriceEth();
//        getPriceBnb();
//        getPriceUni();
//        getPriceDot();
//        getPriceSol();
//    }



    public List<String> getPriceBtc() {
        ResultSet resultSet = select("BTC");
        List<String> priceCryptBtc = new LinkedList<>();
        while (true) {
            assert resultSet != null;
            try {
                if (!resultSet.next()) break;
                priceCryptBtc.add(resultSet.getString("name"));
                priceCryptBtc.add(resultSet.getString("USD"));
                priceCryptBtc.add(resultSet.getString("EUR"));
                priceCryptBtc.add(resultSet.getString("RUB"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return priceCryptBtc;
    }

    public List<String> getPriceEth() {
        ResultSet resultSet = select("ETH");
        List<String> priceCryptEth = new LinkedList<>();
        while (true) {
            assert resultSet != null;
            try {
                if (!resultSet.next()) break;
                priceCryptEth.add(resultSet.getString("name"));
                priceCryptEth.add(resultSet.getString("USD"));
                priceCryptEth.add(resultSet.getString("EUR"));
                priceCryptEth.add(resultSet.getString("RUB"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return priceCryptEth;
    }

    public List<String> getPriceBnb() {
        ResultSet resultSet = select("BNB");
        List<String> priceCryptBnb = new LinkedList<>();
        while (true) {
            assert resultSet != null;
            try {
                if (!resultSet.next()) break;
                priceCryptBnb.add(resultSet.getString("name"));
                priceCryptBnb.add(resultSet.getString("USD"));
                priceCryptBnb.add(resultSet.getString("EUR"));
                priceCryptBnb.add(resultSet.getString("RUB"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return priceCryptBnb;
    }

    public List<String> getPriceUni() {
        ResultSet resultSet = select("UNI");
        List<String> priceCryptUni = new LinkedList<>();
        while (true) {
            assert resultSet != null;
            try {
                if (!resultSet.next()) break;
                priceCryptUni.add(resultSet.getString("name"));
                priceCryptUni.add(resultSet.getString("USD"));
                priceCryptUni.add(resultSet.getString("EUR"));
                priceCryptUni.add(resultSet.getString("RUB"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return priceCryptUni;
    }

    public List<String> getPriceDot() {
        ResultSet resultSet = select("DOT");
        List<String> priceCryptDot = new LinkedList<>();
        while (true) {
            assert resultSet != null;
            try {
                if (!resultSet.next()) break;
                priceCryptDot.add(resultSet.getString("name"));
                priceCryptDot.add(resultSet.getString("USD"));
                priceCryptDot.add(resultSet.getString("EUR"));
                priceCryptDot.add(resultSet.getString("RUB"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return priceCryptDot;
    }

    public List<String> getPriceSol() {
        ResultSet resultSet = select("SOL");
        List<String> priceCryptSol = new LinkedList<>();
        while (true) {
            assert resultSet != null;
            try {
                if (!resultSet.next()) break;
                priceCryptSol.add(resultSet.getString("name"));
                priceCryptSol.add(resultSet.getString("USD"));
                priceCryptSol.add(resultSet.getString("EUR"));
                priceCryptSol.add(resultSet.getString("RUB"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return priceCryptSol;
    }


    private static ResultSet select(String nameCrypt) {
        String sqlQuery = "SELECT name, date, USD, EUR, RUB FROM crypts where name=? order by date DESC limit 1";
        try {
            PreparedStatement preparedStatement = ConnectionSQL.getConnection().prepareStatement(sqlQuery);
            preparedStatement.setString(1, nameCrypt);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            LOGGER.warn("I can not complete the request: " + sqlQuery);
        }
        return null;
    }
}