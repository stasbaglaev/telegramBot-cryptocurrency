package telegrambot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegrambot.bot.TelegramBot;
import telegrambot.command.ParsedCommand;

public class DefaultHandler extends AbstractHandler {

    public DefaultHandler(TelegramBot telegramBot) {
        super(telegramBot);
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        telegramBot.sendQueue.add(defaultMessage(chatId));
        return "";
    }

    private SendMessage defaultMessage(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Команда не распознана, для начала используй команду /start");
        return sendMessage;
    }
}
