package service.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.database.coingecko.CoinGeckoApiClient;
import service.database.coingecko.constant.Currency;
import service.database.coingecko.domain.Coins.CoinMarkets;
import service.database.coingecko.impl.CoinGeckoApiClientImpl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class RunService {
    private static final Logger LOGGER = LogManager.getLogger(RunService.class);
    private static final LinkedHashMap<String, Long> cryptsPriceUsdMap = new LinkedHashMap<>();
    private static final LinkedHashMap<String, Long> cryptsPriceEurMap = new LinkedHashMap<>();
    private static final LinkedHashMap<String, Long> cryptsPriceRubMap = new LinkedHashMap<>();
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
                CoinGeckoApiClient client = new CoinGeckoApiClientImpl();
                java.util.Date date = new java.util.Date();
                generateMaps(client);
                ConnectionSql.getConnection();
                insertIntoDB("BTC", FORMAT.format(date), cryptsPriceUsdMap.get(BITCOIN), cryptsPriceEurMap.get(BITCOIN), cryptsPriceRubMap.get(BITCOIN));
                insertIntoDB("ETH", FORMAT.format(date), cryptsPriceUsdMap.get(ETHEREUM), cryptsPriceEurMap.get(ETHEREUM), cryptsPriceRubMap.get(ETHEREUM));
                insertIntoDB("BNB", FORMAT.format(date), cryptsPriceUsdMap.get(BINANCE_COIN), cryptsPriceEurMap.get(BINANCE_COIN), cryptsPriceRubMap.get(BINANCE_COIN));
                insertIntoDB("UNI", FORMAT.format(date), cryptsPriceUsdMap.get(UNISWAP), cryptsPriceEurMap.get(UNISWAP), cryptsPriceRubMap.get(UNISWAP));
                insertIntoDB("DOT", FORMAT.format(date), cryptsPriceUsdMap.get(POLKADOT), cryptsPriceEurMap.get(POLKADOT), cryptsPriceRubMap.get(POLKADOT));
                insertIntoDB("SOL", FORMAT.format(date), cryptsPriceUsdMap.get(SOLANA), cryptsPriceEurMap.get(SOLANA), cryptsPriceRubMap.get(SOLANA));
                LOGGER.info("INSERT INTO прошел успешно!");
                try {
                    Thread.sleep(3000); //1000 - 1 сек
                } catch (InterruptedException e) {
                    LOGGER.error("Error!");
                }
            }
        });
        run.start();
    }

    private static void getPriceCrypts(List<CoinMarkets> coinMarketsCurrency, LinkedHashMap<String, Long> cryptsPriceMap) {
        for (CoinMarkets element : coinMarketsCurrency) {
            for (String s : RunService.actualCrypt) {
                if (s.equals(element.getName())) {
                    String nameCrypt = element.getName();
                    Long priceCrypt = element.getCurrentPrice();
                    cryptsPriceMap.put(nameCrypt, priceCrypt);
                }
            }
        }
    }

    private static void generateMaps(CoinGeckoApiClient client) {
        List<CoinMarkets> coinMarketsCurrencyUsd = client.getCoinMarkets(Currency.USD);
        List<CoinMarkets> coinMarketsCurrencyRub = client.getCoinMarkets(Currency.RUB);
        List<CoinMarkets> coinMarketsCurrencyEur = client.getCoinMarkets(Currency.EUR);
        getPriceCrypts(coinMarketsCurrencyUsd, cryptsPriceUsdMap);
        getPriceCrypts(coinMarketsCurrencyRub, cryptsPriceRubMap);
        getPriceCrypts(coinMarketsCurrencyEur, cryptsPriceEurMap);
    }

    private static void insertIntoDB(String nameCrypt, String date, Long valueUSD, Long valueEUR, Long valueRUB) {
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

