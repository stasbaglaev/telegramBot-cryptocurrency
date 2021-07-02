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

public class UnsubscribeInfoCryptsHandler extends AbstractHandler{
    private static final String END_LINE = "\n";
    private static final PriceCrypts priceCrypts = new PriceCrypts(); // тут менять, внизу в коде (в колбэках будет метод который совершает подписки или отказывается от нее)
    private static final String btcName = Crypt.BTC.getName();
    private static final String ethName = Crypt.ETH.getName();
    private static final String bnbName = Crypt.BNB.getName();
    private static final String dogeName = Crypt.DOGE.getName();
    private static final String dotName = Crypt.DOT.getName();
    private static final String adaName = Crypt.ADA.getName();

    public UnsubscribeInfoCryptsHandler(TelegramBot telegramBot) {
        super(telegramBot);
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        Command command = parsedCommand.getCommand();

        if (command == Command.UNSUBSCRIBE) {
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
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        String bitcoin = "BTC";
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(btcName + IconEmoji.MONEY.get()).setCallbackData(bitcoin));

        String ethereum = "ETH";
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(ethName).setCallbackData(ethereum));

        String binance = "BNB";
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(bnbName).setCallbackData(binance));

        String dogecoin = "DOGE";
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(dogeName).setCallbackData(dogecoin));

        String polkadot = "DOT";
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(dotName).setCallbackData(polkadot));

        String cardano = "ADA";
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(adaName).setCallbackData(cardano));


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId)
                .setText("Выбери криптовалюту, чтобы подписаться на информацию о ее стоимости")
                .setReplyMarkup(inlineKeyboardMarkup);
    }

}
