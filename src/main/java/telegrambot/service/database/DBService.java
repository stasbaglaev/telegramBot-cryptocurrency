package telegrambot.service.database;

import com.crypto.cryptocompare.api.CryptoCompareApi;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class DBService {
    private static final Logger LOGGER = LogManager.getLogger(DBService.class);
    private static final String BITCOIN = "Bitcoin";
    private static final String ETHEREUM = "Ethereum";
    private static final String BINANCE_COIN = "Binance Coin";
    private static final String UNISWAP = "Uniswap";
    private static final String POLKADOT = "Polkadot";
    private static final String SOLANA = "Solana";
    private static final List<String> actualCrypt = Arrays.asList(BITCOIN, ETHEREUM, BINANCE_COIN, UNISWAP, POLKADOT, SOLANA);
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yy.MM.dd HH:mm");


    public static void main(String[] args) {
        Thread run = new Thread(() -> {
            while (true) {
                java.util.Date date = new java.util.Date();
                ConnectionSql.getConnection();
                JsonObject response = getCryptsInfo();
                LOGGER.info(response);

                insertIntoDB("BTC", FORMAT.format(date), response.get("BTC").getAsJsonObject().get("USD"), response.get("BTC").getAsJsonObject().get("EUR"), response.get("BTC").getAsJsonObject().get("RUB"));
                insertIntoDB("ETH", FORMAT.format(date), response.get("ETH").getAsJsonObject().get("USD"), response.get("ETH").getAsJsonObject().get("EUR"), response.get("ETH").getAsJsonObject().get("RUB"));
                insertIntoDB("BNB", FORMAT.format(date), response.get("BNB").getAsJsonObject().get("USD"), response.get("BNB").getAsJsonObject().get("EUR"), response.get("BNB").getAsJsonObject().get("RUB"));
                insertIntoDB("UNI", FORMAT.format(date), response.get("UNI").getAsJsonObject().get("USD"), response.get("UNI").getAsJsonObject().get("EUR"), response.get("UNI").getAsJsonObject().get("RUB"));
                insertIntoDB("DOT", FORMAT.format(date), response.get("DOT").getAsJsonObject().get("USD"), response.get("DOT").getAsJsonObject().get("EUR"), response.get("DOT").getAsJsonObject().get("RUB"));
                insertIntoDB("SOL", FORMAT.format(date), response.get("SOL").getAsJsonObject().get("USD"), response.get("SOL").getAsJsonObject().get("EUR"), response.get("SOL").getAsJsonObject().get("RUB"));
                LOGGER.info("INSERT INTO прошел успешно!");

                getCryptsInfo();
                try {
                    Thread.sleep(6000); //1000 - 1 сек
                } catch (InterruptedException e) {
                    LOGGER.error("Error!");
                }
            }
        });
        run.start();
    }

    private static JsonObject getCryptsInfo(){
        CryptoCompareApi cryptoCompareApi = new CryptoCompareApi();
        JsonObject response = cryptoCompareApi.priceMulti(
                "BTC,ETH,BNB,UNI,DOT,SOL",
                "USD,EUR,RUB",
                new LinkedHashMap<String, Object>() {{
                    put("extraParams", "TestProject");
                }});
        return response;
    }

    private static void insertIntoDB(String nameCrypt, String date, JsonElement valueUSD, JsonElement valueEUR, JsonElement valueRUB) {
        String sqlQuery = "INSERT INTO crypts(name, date, USD, EUR, RUB) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = ConnectionSql.getConnection().prepareStatement(sqlQuery);
            preparedStatement.setString(1, nameCrypt);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, valueUSD.toString());
            preparedStatement.setString(4, valueEUR.toString());
            preparedStatement.setString(5, valueRUB.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.warn("Error while using the command 'INSERT INTO' on DB 'my_db' ");
        }
    }
}