package telegrambot.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegrambot.bot.TelegramBot;
import telegrambot.command.ParsedCommand;
import telegrambot.entity.Crypt;
import telegrambot.service.database.ConnectionSql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class DistributionHandler extends AbstractHandler implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(DistributionHandler.class);
    private static final String END_LINE = "\n";
    private static final int SENDER_SLEEP_TIME = 10000;

    public DistributionHandler(TelegramBot telegramBot) {
        super(telegramBot);
    }

    private void create() {
        List<String> priceCryptBtc = getPriceBtc(Objects.requireNonNull(select(Crypt.BTC.getName())));
        List<String> priceCryptEth = getPriceEth(Objects.requireNonNull(select(Crypt.ETH.getName())));
        List<String> priceCryptBnb = getPriceBnb(Objects.requireNonNull(select(Crypt.BNB.getName())));
        List<String> priceCryptUni = getPriceUni(Objects.requireNonNull(select(Crypt.UNI.getName())));
        List<String> priceCryptDot = getPriceDot(Objects.requireNonNull(select(Crypt.DOT.getName())));
        List<String> priceCryptSol = getPriceSol(Objects.requireNonNull(select(Crypt.SOL.getName())));

        for (String chatId : getIdChats()) {
            getSubscription(chatId);
            for (String subscription : getSubscription(chatId)) {
                switch (subscription) {
                    case "BTC":
                        LOGGER.info("priceCryptBtc: " + priceCryptBtc);
                        String bitcoin = "*BTC*" + END_LINE +
                                "USD: " + priceCryptBtc.get(1) + END_LINE +
                                "EUR: " + priceCryptBtc.get(2) + END_LINE +
                                "RUB: " + priceCryptBtc.get(3) + END_LINE;
                        telegramBot.sendSubscriptionQueue.add(new SendMessage().setChatId(chatId).setText(bitcoin).enableMarkdown(true));
                        break;
                    case "ETH":
                        LOGGER.info("priceCryptEth: " + priceCryptEth);
                        String ethereum = "*ETH*" + END_LINE +
                                "USD: " + priceCryptEth.get(1) + END_LINE +
                                "EUR: " + priceCryptEth.get(2) + END_LINE +
                                "RUB: " + priceCryptEth.get(3) + END_LINE;
                        telegramBot.sendSubscriptionQueue.add(new SendMessage().setChatId(chatId).setText(ethereum).enableMarkdown(true));
                        break;
                    case "BNB":
                        LOGGER.info("priceCryptBnb: " + priceCryptBnb);
                        String binance = "*BNB*" + END_LINE +
                                "USD: " + priceCryptBnb.get(1) + END_LINE +
                                "EUR: " + priceCryptBnb.get(2) + END_LINE +
                                "RUB: " + priceCryptBnb.get(3) + END_LINE;
                        telegramBot.sendSubscriptionQueue.add(new SendMessage().setChatId(chatId).setText(binance).enableMarkdown(true));
                        break;
                    case "UNI":
                        LOGGER.info("priceCryptUni: " + priceCryptUni);
                        String uniswap = "*UNI*" + END_LINE +
                                "USD: " + priceCryptUni.get(1) + END_LINE +
                                "EUR: " + priceCryptUni.get(2) + END_LINE +
                                "RUB: " + priceCryptUni.get(3) + END_LINE;
                        telegramBot.sendSubscriptionQueue.add(new SendMessage().setChatId(chatId).setText(uniswap).enableMarkdown(true));
                        break;
                    case "DOT":
                        LOGGER.debug("PriceCryptDot: " + priceCryptDot);
                        String polkadot = "*DOT*" + END_LINE +
                                "USD: " + priceCryptDot.get(1) + END_LINE +
                                "EUR: " + priceCryptDot.get(2) + END_LINE +
                                "RUB: " + priceCryptDot.get(3) + END_LINE;
                        telegramBot.sendSubscriptionQueue.add(new SendMessage().setChatId(chatId).setText(polkadot).enableMarkdown(true));
                        break;
                    case "SOL":
                        LOGGER.debug("PriceCryptSol: " + priceCryptSol);
                        String solana = "*SOL*" + END_LINE +
                                "USD: " + priceCryptSol.get(1) + END_LINE +
                                "EUR: " + priceCryptSol.get(2) + END_LINE +
                                "RUB: " + priceCryptSol.get(3) + END_LINE;
                        telegramBot.sendSubscriptionQueue.add(new SendMessage().setChatId(chatId).setText(solana).enableMarkdown(true));
                        break;
                }
            }
        }
        LOGGER.debug("Сообщения успешно отправлены!");
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        return null;
    }

    private static List<String> getIdChats() {
        List<String> listChatId = new LinkedList<>();
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement;
            String sqlQuery = "SELECT DISTINCT chatID FROM subscription";
            preparedStatement = ConnectionSql.getConnection().prepareStatement(sqlQuery);
            resultSet = preparedStatement.executeQuery();
            while (true) {
                assert resultSet != null;
                try {
                    if (!resultSet.next()) break;
                    listChatId.add(resultSet.getString("chatID"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listChatId;
    }

    public static List<String> getSubscription(String chatId) {
        List<String> listSubscription = new LinkedList<>();
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
                    listSubscription.add(resultSet.getString("crypt"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listSubscription;
    }

    private static ResultSet select(String nameCrypt) {
        String sqlQuery = "SELECT name, date, USD, EUR, RUB FROM crypts where name=? order by date DESC limit 1";
        try {
            PreparedStatement preparedStatement = ConnectionSql.getConnection().prepareStatement(sqlQuery);
            preparedStatement.setString(1, nameCrypt);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            LOGGER.warn("I can not complete the request: " + sqlQuery);
        }
        return null;
    }

    private static List<String> getPriceBtc(ResultSet resultSet) {
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

    private static List<String> getPriceEth(ResultSet resultSet) {
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

    private static List<String> getPriceBnb(ResultSet resultSet) {
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

    private static List<String> getPriceUni(ResultSet resultSet) {
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

    private static List<String> getPriceDot(ResultSet resultSet) {
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

    private static List<String> getPriceSol(ResultSet resultSet) {
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

    @Override
    public void run() {
        LOGGER.info("[STARTED] SubscriptionService.  Bot class: " + telegramBot.getBotName());
        Thread run = new Thread(() -> {
            while (true) {
                create();
                try {
                    Thread.sleep(SENDER_SLEEP_TIME);
                } catch (InterruptedException e) {
                    LOGGER.error("Error!");
                }
            }
        });
        run.start();
    }
}