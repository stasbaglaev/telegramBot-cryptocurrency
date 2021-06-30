package telegrambot.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegrambot.bot.TelegramBot;
import telegrambot.command.Command;
import telegrambot.command.ParsedCommand;
import telegrambot.command.ParserCommand;
import telegrambot.entity.Crypt;
import telegrambot.handler.AbstractHandler;
import telegrambot.handler.DefaultHandler;
import telegrambot.handler.PriceCryptsHandler;
import telegrambot.handler.SystemHandler;


public class MessageRecipientService implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(MessageRecipientService.class);
    private static final int WAIT_FOR_NEW_MESSAGE_DELAY = 1000;
    private final TelegramBot telegramBot;
    private final ParserCommand parserCommand;

    public MessageRecipientService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
        parserCommand = new ParserCommand(telegramBot.getBotName());
    }

    @Override
    public void run() {
        LOGGER.info("[STARTED] MessageRecipientService.  Bot class: " + telegramBot.getBotName());
        while (true) {
            for (Object object = telegramBot.receiveQueue.poll(); object != null; object = telegramBot.receiveQueue.poll()) {
                LOGGER.info("New object for analyze in queue " + object);
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
                LOGGER.info("Message.getText " + text);
                if (text.equals(Command.PRICE.getName())){

                }
                parsedCommand = parserCommand.getParsedCommand(text);
                LOGGER.info("ParsedCommand " + parsedCommand.getCommand());
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
            Long chatId = callbackQuery.getMessage().getChat().getId();
            SendMessage messageOut = new SendMessage();
            messageOut.setChatId(chatId);
            messageOut.setText(callbackQuery.getData()).setChatId(chatId);
            telegramBot.sendQueue.add(messageOut);
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
            case PRICE:
                PriceCryptsHandler priceCryptsHandler = new PriceCryptsHandler(telegramBot);
                LOGGER.info("Handler for command[" + command + "] is: " + priceCryptsHandler);
                return priceCryptsHandler;
            default:
                LOGGER.info("Handler for command[" + command + "] not set. Return DefaultHandler");
                return new DefaultHandler(telegramBot);
        }
    }
}
