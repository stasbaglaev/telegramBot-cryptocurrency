package telegrambot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegrambot.ability.PriceCrypts;
import telegrambot.bot.TelegramBot;
import telegrambot.command.Command;
import telegrambot.command.IconEmoji;
import telegrambot.command.ParsedCommand;
import telegrambot.entity.Crypt;

import java.util.ArrayList;
import java.util.List;

public class PriceCryptsHandler extends AbstractHandler {
    private static final String END_LINE = "\n";
    private static final PriceCrypts priceCrypts = new PriceCrypts();
    private static final String bitcoinName = Crypt.Bitcoin.getName();
    private static final String ethereumName = Crypt.Ethereum.getName();
    private static final String litecoinName = Crypt.Litecoin.getName();

    public PriceCryptsHandler(TelegramBot telegramBot) {
        super(telegramBot);
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        Command command = parsedCommand.getCommand();

        if (command == Command.PRICE) {
            telegramBot.sendQueue.add(sendMessageStatusCommand(chatId));
            telegramBot.sendQueue.add(sendInlineKeyBoardListCrypts(chatId));
        }
        return "";
    }

    private static SendMessage sendMessageStatusCommand(String chatId) {
        return new SendMessage().setChatId(chatId)
                .setText("Список криптовалют формируется, пожалуйста, подождите");
    }

    private static SendMessage sendInlineKeyBoardListCrypts(String chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        String bitcoin = "Bitcoin" + END_LINE +
                "USD: " + priceCrypts.getPriceCryptUsd(bitcoinName) + END_LINE +
                "EUR: " + priceCrypts.getPriceCryptEur(bitcoinName) + END_LINE +
                "RUB: " + priceCrypts.getPriceCryptRub(bitcoinName) + END_LINE;
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(bitcoinName + IconEmoji.GRAPH.get()).setCallbackData(bitcoin));

        String ethereum = "Ethereum" + END_LINE +
                "USD: " + priceCrypts.getPriceCryptUsd(ethereumName) + END_LINE +
                "EUR: " + priceCrypts.getPriceCryptEur(ethereumName) + END_LINE +
                "RUB: " + priceCrypts.getPriceCryptRub(ethereumName) + END_LINE;
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(ethereumName + IconEmoji.MONEY.get()).setCallbackData(ethereum));

        String litecoin = "Litecoin" + END_LINE +
                "USD: " + priceCrypts.getPriceCryptUsd(litecoinName) + END_LINE +
                "EUR: " + priceCrypts.getPriceCryptEur(litecoinName) + END_LINE +
                "RUB: " + priceCrypts.getPriceCryptRub(litecoinName) + END_LINE;
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(litecoinName).setCallbackData(litecoin));

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId)
                .setText("Выбери криптовалюту, чтобы узнать ее стоимость")
                .setReplyMarkup(inlineKeyboardMarkup);
    }
}
