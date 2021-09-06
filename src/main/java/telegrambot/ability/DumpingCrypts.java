package telegrambot.ability;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import telegrambot.bot.TelegramBot;
import telegrambot.service.database.ConnectionSql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DumpingCrypts implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(DumpingCrypts.class);
    private static final int SENDER_SLEEP_TIME = 10000;
    private static final String END_LINE = "\n";
    TelegramBot telegramBot;
    String chatId;
    String nameCrypt;
    int percent;

    public DumpingCrypts(TelegramBot telegramBot, String chatId, String nameCrypt, int percent) {
        this.telegramBot = telegramBot;
        this.chatId = chatId;
        this.percent = percent;
        this.nameCrypt = nameCrypt;
        LOGGER.debug("CREATE... ");
    }

    @Override
    public void run() {
        LOGGER.info("RUN... ");
        LOGGER.debug(getCurrentPrice(nameCrypt));
        int currentValue = Integer.parseInt(getCurrentPrice(nameCrypt).get(3));
        LOGGER.debug("CurrentValue " + currentValue);
        final int initialValue = (currentValue - ((currentValue * percent) / 100));
        LOGGER.debug("InitialValue " + initialValue);
        Thread run = new Thread(() -> {
            while (true) {
                if (initialValue <= (Integer.parseInt(getCurrentPrice(nameCrypt).get(3)))) {
                    LOGGER.debug("We are waiting until we reach the required value.");
                } else {
                    LOGGER.debug("Reached the required value!");
                    List<String> priceCrypt = getCurrentPrice(nameCrypt);
                    String textMessage = "Сработало событие на понижение стоимости криптовалюты *" + END_LINE +
                            nameCrypt + "*" + END_LINE +
                            "USD: " + priceCrypt.get(1) + END_LINE +
                            "EUR: " + priceCrypt.get(2) + END_LINE +
                            "RUB: " + priceCrypt.get(3) + END_LINE;
                    telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText(textMessage).enableMarkdown(true));
                    LOGGER.info("FINISH... ");
                    break;
                }

            }
        });
        run.start();
    }

    private static ResultSet select(String nameCrypt) {
        ResultSet resultSet;
        String sqlQuery = "SELECT name, USD, EUR, RUB FROM crypts where name=? order by date DESC limit 1";
        try {
            PreparedStatement preparedStatement = ConnectionSql.getConnection().prepareStatement(sqlQuery);
            preparedStatement.setString(1, nameCrypt);
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            LOGGER.warn("I can not complete the request: " + sqlQuery);
        }
        return null;
    }

    public List<String> getCurrentPrice(String nameCrypt) {
        ResultSet resultSet = select(nameCrypt);
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
}
