package telegrambot.handler;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegrambot.bot.TelegramBot;
import telegrambot.command.ParsedCommand;

public abstract class AbstractHandler {
    TelegramBot telegramBot;

    AbstractHandler(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public abstract String operate(String chatId, ParsedCommand parsedCommand, Update update);
}
