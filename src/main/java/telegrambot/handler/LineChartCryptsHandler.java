package telegrambot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegrambot.ability.LineChartCrypts;
import telegrambot.bot.TelegramBot;
import telegrambot.command.Command;
import telegrambot.command.ParsedCommand;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LineChartCryptsHandler extends AbstractHandler{
    private static final LineChartCrypts lineChartCrypts = new LineChartCrypts();
    private static final SendPhoto sendPhotoRequest = new SendPhoto();

    public LineChartCryptsHandler(TelegramBot telegramBot) {
        super(telegramBot);
    }
    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        Command command = parsedCommand.getCommand();

        if (command == Command.GRAPH) {
            lineChartCrypts.getLineChartCrypts();
            telegramBot.sendQueue.add(sendLineChartCrypts(chatId));
        }
        return "";
    }


    private static SendPhoto sendLineChartCrypts(String chatId) {
        sendPhotoRequest.setChatId(chatId);
        sendPhotoRequest.setPhoto(new File(lineChartCrypts.getFileName()));
//        try {
//            Files.delete(Paths.get(lineChartCrypts.getFileName()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return sendPhotoRequest;
    }
}

