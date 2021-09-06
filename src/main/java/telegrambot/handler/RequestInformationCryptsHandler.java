package telegrambot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegrambot.ability.RequestInformationCrypts;
import telegrambot.bot.TelegramBot;
import telegrambot.command.Command;
import telegrambot.command.IconEmoji;
import telegrambot.command.ParsedCommand;
import telegrambot.entity.Crypt;

import java.util.ArrayList;
import java.util.List;

public class RequestInformationCryptsHandler extends AbstractHandler {
    private static final String END_LINE = "\n";
    private static final RequestInformationCrypts requestInformationCrypts = new RequestInformationCrypts();
    private static final String BTC_NAME = Crypt.BTC.getName();
    private static final String ETH_NAME = Crypt.ETH.getName();
    private static final String BNB_NAME = Crypt.BNB.getName();
    private static final String UNI_NAME = Crypt.UNI.getName();
    private static final String DOT_NAME = Crypt.DOT.getName();
    private static final String SOL_NAME = Crypt.SOL.getName();

    public RequestInformationCryptsHandler(TelegramBot telegramBot) {
        super(telegramBot);
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        Command command = parsedCommand.getCommand();

        if (command == Command.REQUEST) {
            telegramBot.sendQueue.add(sendMessageStatusCommand(chatId));
            telegramBot.sendQueue.add(sendInlineKeyBoardListCrypts(chatId));
        }
        return "";
    }

    private static SendMessage sendMessageStatusCommand(String chatId) {
        return new SendMessage().setChatId(chatId)
                .setText(IconEmoji.HOURGLASS.get() + "Список криптовалют формируется, пожалуйста, подождите");
    }

    private static SendMessage sendInlineKeyBoardListCrypts(String chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();

        String bitcoin = "BTC" + END_LINE +
                "USD: " + requestInformationCrypts.getPriceBtc().get(1) + END_LINE +
                "EUR: " + requestInformationCrypts.getPriceBtc().get(2) + END_LINE +
                "RUB: " + requestInformationCrypts.getPriceBtc().get(3) + END_LINE;
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(BTC_NAME).setCallbackData(bitcoin));

        String ethereum = "ETH" + END_LINE +
                "USD: " + requestInformationCrypts.getPriceEth().get(1) + END_LINE +
                "EUR: " + requestInformationCrypts.getPriceEth().get(2) + END_LINE +
                "RUB: " + requestInformationCrypts.getPriceEth().get(3) + END_LINE;
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(ETH_NAME).setCallbackData(ethereum));

        String binance = "BNB" + END_LINE +
                "USD: " + requestInformationCrypts.getPriceBnb().get(1) + END_LINE +
                "EUR: " + requestInformationCrypts.getPriceBnb().get(2) + END_LINE +
                "RUB: " + requestInformationCrypts.getPriceBnb().get(3) + END_LINE;
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(BNB_NAME).setCallbackData(binance));

        String uniswap = "UNI" + END_LINE +
                "USD: " + requestInformationCrypts.getPriceUni().get(1) + END_LINE +
                "EUR: " + requestInformationCrypts.getPriceUni().get(2) + END_LINE +
                "RUB: " + requestInformationCrypts.getPriceUni().get(3) + END_LINE;
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(UNI_NAME).setCallbackData(uniswap));

        String polkadot = "DOT" + END_LINE +
                "USD: " + requestInformationCrypts.getPriceDot().get(1) + END_LINE +
                "EUR: " + requestInformationCrypts.getPriceDot().get(2) + END_LINE +
                "RUB: " + requestInformationCrypts.getPriceDot().get(3) + END_LINE;
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(DOT_NAME).setCallbackData(polkadot));

        String solana = "SOL" + END_LINE +
                "USD: " + requestInformationCrypts.getPriceSol().get(1) + END_LINE +
                "EUR: " + requestInformationCrypts.getPriceSol().get(2) + END_LINE +
                "RUB: " + requestInformationCrypts.getPriceSol().get(3) + END_LINE;
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(SOL_NAME).setCallbackData(solana));

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId)
                .setText(IconEmoji.BOARD.get() + "Выбери криптовалюту, чтобы запросить информацию о ее стоимости")
                .setReplyMarkup(inlineKeyboardMarkup);
    }
}
