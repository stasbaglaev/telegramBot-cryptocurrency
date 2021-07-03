package telegrambot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegrambot.ability.PriceCrypts;
import telegrambot.ability.SubscriptionInformationCrypts;
import telegrambot.bot.TelegramBot;
import telegrambot.command.Command;
import telegrambot.command.IconEmoji;
import telegrambot.command.ParsedCommand;
import telegrambot.entity.Crypt;
import telegrambot.message.Subscription;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionInformationCryptsHandler extends AbstractHandler {
    private static final String END_LINE = "\n";
    private static final String btcName = Crypt.BTC.getName();
    private static final String ethName = Crypt.ETH.getName();
    private static final String bnbName = Crypt.BNB.getName();
    private static final String uniName = Crypt.UNI.getName();
    private static final String dotName = Crypt.DOT.getName();
    private static final String solName = Crypt.SOL.getName();
    private static final SubscriptionInformationCrypts subscriptionInformationCrypts = new SubscriptionInformationCrypts();

    public SubscriptionInformationCryptsHandler(TelegramBot telegramBot) {
        super(telegramBot);
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        Command command = parsedCommand.getCommand();

        if (command == Command.SUBSCRIBE) {
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

        String bitcoin = Subscription.BTC.getTextMessage();
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(btcName).setCallbackData(bitcoin));

        String ethereum = Subscription.ETH.getTextMessage();
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(ethName).setCallbackData(ethereum));

        String binance = Subscription.BNB.getTextMessage();
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(bnbName).setCallbackData(binance));

        String uniswap = Subscription.UNI.getTextMessage();
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(uniName).setCallbackData(uniswap));

        String polkadot = Subscription.DOT.getTextMessage();
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(dotName).setCallbackData(polkadot));

        String solano = Subscription.SOL.getTextMessage();
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(solName).setCallbackData(solano));

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId)
                .setText(IconEmoji.BOARD.get() + "Выбери криптовалюту, чтобы подписаться на информацию о ее стоимости")
                .setReplyMarkup(inlineKeyboardMarkup);
    }
}
