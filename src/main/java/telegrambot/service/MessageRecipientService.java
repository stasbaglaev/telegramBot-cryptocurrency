package telegrambot.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegrambot.ability.LineChartCrypts;
import telegrambot.ability.SubscriptionInformationCrypts;
import telegrambot.bot.TelegramBot;
import telegrambot.command.Command;
import telegrambot.command.ParsedCommand;
import telegrambot.command.ParserCommand;
import telegrambot.entity.Crypt;
import telegrambot.handler.*;
import telegrambot.message.LineChart;
import telegrambot.message.Subscription;
import telegrambot.message.Unsubscription;


public class MessageRecipientService implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(MessageRecipientService.class);
    private static final int WAIT_FOR_NEW_MESSAGE_DELAY = 1000;
    private final TelegramBot telegramBot;
    private final ParserCommand parserCommand;
    private static final SubscriptionInformationCrypts subscriptionInformationCrypts = new SubscriptionInformationCrypts();
    private static final LineChartCrypts lineChartCrypts = new LineChartCrypts();
    private static final String btcName = Crypt.BTC.getName();
    private static final String ethName = Crypt.ETH.getName();
    private static final String bnbName = Crypt.BNB.getName();
    private static final String uniName = Crypt.UNI.getName();
    private static final String dotName = Crypt.DOT.getName();
    private static final String solName = Crypt.SOL.getName();

    public MessageRecipientService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
        parserCommand = new ParserCommand(telegramBot.getBotName());
    }

    @Override
    public void run() {
        LOGGER.info("[STARTED] MessageRecipientService.  Bot class: " + telegramBot.getBotName());
        while (true) {
            for (Object object = telegramBot.receiveQueue.poll(); object != null; object = telegramBot.receiveQueue.poll()) {
                //LOGGER.info("New object for analyze in queue " + object);
                analyze(object);
            }
            try {
                Thread.sleep(WAIT_FOR_NEW_MESSAGE_DELAY);
            } catch (InterruptedException e) {
                LOGGER.error("Catch interrupt. Exit", e);
                return;
            }
        }
    }

    private void analyze(Object object) {
        if (object instanceof Update) {
            Update update = (Update) object;
            LOGGER.debug("Update recipient: " + update);
            analyzeForUpdateType(update);
        } else LOGGER.warn("Cant operate type of object: " + object.toString());
    }

    private void analyzeForUpdateType(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();

            ParsedCommand parsedCommand = new ParsedCommand(Command.NONE, "");
            if (message.hasText()) {
                String text = message.getText();
                parsedCommand = parserCommand.getParsedCommand(text);
                LOGGER.debug("ParsedCommand " + parsedCommand.getCommand());
            }

            AbstractHandler handlerForCommand = getHandlerForCommand(parsedCommand.getCommand());
            String operationResult = handlerForCommand.operate(chatId.toString(), parsedCommand, update);

            if (!"".equals(operationResult)) {
                SendMessage messageOut = new SendMessage();
                messageOut.setChatId(chatId);
                messageOut.setText(operationResult);
                telegramBot.sendQueue.add(messageOut);
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            checkSubscription(callbackQuery);
            checkUnsubscription(callbackQuery);
            checkBuildLineChart(callbackQuery);
            Long chatId = callbackQuery.getMessage().getChat().getId();
            SendMessage messageOut = new SendMessage();
            messageOut.setChatId(chatId);
            messageOut.setText(callbackQuery.getData()).setChatId(chatId);
            telegramBot.sendQueue.add(messageOut);
        }
    }

    private static void checkSubscription(CallbackQuery callbackQuery) {
        String chatId = Long.toString(callbackQuery.getMessage().getChatId());
        if (callbackQuery.getData().equals(Subscription.BTC.getNotActiveMessage())) {
            subscriptionInformationCrypts.updateDB(btcName, chatId);
        } else if (callbackQuery.getData().equals(Subscription.ETH.getNotActiveMessage())) {
            subscriptionInformationCrypts.updateDB(ethName, chatId);
        } else if (callbackQuery.getData().equals(Subscription.BNB.getNotActiveMessage())) {
            subscriptionInformationCrypts.updateDB(bnbName, chatId);
        } else if (callbackQuery.getData().equals(Subscription.UNI.getNotActiveMessage())) {
            subscriptionInformationCrypts.updateDB(uniName, chatId);
        } else if (callbackQuery.getData().equals(Subscription.DOT.getNotActiveMessage())) {
            subscriptionInformationCrypts.updateDB(dotName, chatId);
        } else if (callbackQuery.getData().equals(Subscription.SOL.getNotActiveMessage())) {
            subscriptionInformationCrypts.updateDB(solName, chatId);
        }
    }

    private static void checkUnsubscription(CallbackQuery callbackQuery) {
        String chatId = Long.toString(callbackQuery.getMessage().getChatId());
        if (callbackQuery.getData().equals(Unsubscription.BTC.getActiveMessage())) {
            subscriptionInformationCrypts.deleteDB(btcName, chatId);
        } else if (callbackQuery.getData().equals(Unsubscription.ETH.getActiveMessage())) {
            subscriptionInformationCrypts.deleteDB(ethName, chatId);
        } else if (callbackQuery.getData().equals(Unsubscription.BNB.getActiveMessage())) {
            subscriptionInformationCrypts.deleteDB(bnbName, chatId);
        } else if (callbackQuery.getData().equals(Unsubscription.UNI.getActiveMessage())) {
            subscriptionInformationCrypts.deleteDB(uniName, chatId);
        } else if (callbackQuery.getData().equals(Unsubscription.DOT.getActiveMessage())) {
            subscriptionInformationCrypts.deleteDB(dotName, chatId);
        } else if (callbackQuery.getData().equals(Unsubscription.SOL.getActiveMessage())) {
            subscriptionInformationCrypts.deleteDB(solName, chatId);
        }
    }

    private static void checkBuildLineChart(CallbackQuery callbackQuery) {
        String chatId = Long.toString(callbackQuery.getMessage().getChatId());
        if (callbackQuery.getData().equals(LineChart.BTC.getBuildLineChartMessage())) {
            //lineChartCrypts.getLineChartCrypts(btcName);
        }

    }


    private AbstractHandler getHandlerForCommand(Command command) {
        if (command == null) {
            LOGGER.warn("Null command accepted!");
            return new DefaultHandler(telegramBot);
        }
        switch (command) {
            case START:
            case HELP:
                SystemHandler systemHandler = new SystemHandler(telegramBot);
                LOGGER.info("Handler for command[" + command + "] is: " + systemHandler + " Return SystemHandler");
                return systemHandler;
            case REQUEST:
                RequestInformationCryptsHandler requestInformationCryptsHandler = new RequestInformationCryptsHandler(telegramBot);
                LOGGER.info("Handler for command[" + command + "] is: " + requestInformationCryptsHandler);
                return requestInformationCryptsHandler;
            case SUBSCRIBE:
                SubscriptionInformationCryptsHandler subscriptionInformationCryptsHandler = new SubscriptionInformationCryptsHandler(telegramBot);
                LOGGER.info("Handler for command[" + command + "] is: " + subscriptionInformationCryptsHandler);
                return subscriptionInformationCryptsHandler;
            case UNSUBSCRIBE:
                UnsubscriptionInformationCryptsHandler unsubscriptionInformationCryptsHandler = new UnsubscriptionInformationCryptsHandler(telegramBot);
                LOGGER.info("Handler for command[" + command + "] is: " + unsubscriptionInformationCryptsHandler);
                return unsubscriptionInformationCryptsHandler;
            case GRAPH:
                LineChartCryptsHandler lineChartCryptsHandler = new LineChartCryptsHandler(telegramBot);
                LOGGER.info("Handler for command[" + command + "] is: " + lineChartCryptsHandler);
                return lineChartCryptsHandler;
            case DUMPING:
            case DUMPINGBTC:
            case DUMPINGETH:
            case DUMPINGBNB:
            case DUMPINGUNI:
            case DUMPINGDOT:
            case DUMPINGSOL:
                DumpingCryptsHandler dumpingCryptsHandler = new DumpingCryptsHandler(telegramBot);
                LOGGER.info("Handler for command[" + command + "] is: " + dumpingCryptsHandler);
                return dumpingCryptsHandler;

            default:
                LOGGER.info("Handler for command[" + command + "] not set. Return DefaultHandler");
                return new DefaultHandler(telegramBot);
        }
    }
}
