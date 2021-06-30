package telegrambot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegrambot.bot.TelegramBot;
import telegrambot.command.Command;
import telegrambot.command.ParsedCommand;
import telegrambot.entity.Crypt;
import telegrambot.entity.Currency;
import telegrambot.service.PriceCrypts;

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
            telegramBot.sendQueue.add(sendInlineKeyBoardListCrypts(chatId));
        }
        return "";
    }

    private static SendMessage sendInlineKeyBoardListCrypts(String chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();

        String bitcoin = "Bitcoin" + END_LINE +
                "USD: " + priceCrypts.getPriceCrypt(bitcoinName, Currency.USD.getName()) + END_LINE +
                "EUR: " + priceCrypts.getPriceCrypt(bitcoinName, Currency.EUR.getName()) + END_LINE +
                "RUB: " + priceCrypts.getPriceCrypt(bitcoinName, Currency.RUB.getName()) + END_LINE;
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(bitcoinName).setCallbackData(bitcoin));

        String ethereum = "Ethereum" + END_LINE +
                "USD: " + priceCrypts.getPriceCrypt(ethereumName, Currency.USD.getName()) + END_LINE +
                "EUR: " + priceCrypts.getPriceCrypt(ethereumName, Currency.EUR.getName()) + END_LINE +
                "RUB: " + priceCrypts.getPriceCrypt(ethereumName, Currency.RUB.getName()) + END_LINE;
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(ethereumName).setCallbackData(ethereum));

        String litecoin = "Litecoin" + END_LINE +
                "USD: " + priceCrypts.getPriceCrypt(litecoinName, Currency.USD.getName()) + END_LINE +
                "EUR: " + priceCrypts.getPriceCrypt(litecoinName, Currency.EUR.getName()) + END_LINE +
                "RUB: " + priceCrypts.getPriceCrypt(litecoinName, Currency.RUB.getName()) + END_LINE;
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(litecoinName).setCallbackData(litecoin));
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText("Выбери криптовалюту, чтобы узнать ее стоимость").setReplyMarkup(inlineKeyboardMarkup);
    }
}
