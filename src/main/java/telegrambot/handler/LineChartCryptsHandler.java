package telegrambot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegrambot.ability.LineChartCrypts;
import telegrambot.bot.TelegramBot;
import telegrambot.command.Command;
import telegrambot.command.IconEmoji;
import telegrambot.command.ParsedCommand;

import java.io.File;

public class LineChartCryptsHandler extends AbstractHandler {
    private static final LineChartCrypts lineChartCrypts = new LineChartCrypts();

    public LineChartCryptsHandler(TelegramBot telegramBot) {
        super(telegramBot);
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        Command command = parsedCommand.getCommand();

        if (command == Command.GRAPH) {
            telegramBot.sendQueue.add(sendMessageStatusCommand(chatId));
            lineChartCrypts.getLineChartCrypts();
            if (lineChartCrypts.completedOperation()){
            telegramBot.sendQueue.add(sendLineChartCrypts(chatId));}
        }
        return "";
    }

    private static SendMessage sendMessageStatusCommand(String chatId) {
        return new SendMessage().setChatId(chatId)
                .setText(IconEmoji.GRAPH.get() + " График формируется, пожалуйста, подождите");
    }

    private static SendPhoto sendLineChartCrypts(String chatId) {
        return new SendPhoto().setChatId(chatId)
                .setPhoto(new File(LineChartCrypts.getFILE_NAME()));

    }
}

