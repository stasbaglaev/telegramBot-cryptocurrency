package telegrambot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegrambot.bot.TelegramBot;
import telegrambot.command.Command;
import telegrambot.command.IconEmoji;
import telegrambot.command.ParsedCommand;
import telegrambot.entity.Crypt;
import telegrambot.message.Subscription;
import telegrambot.message.Unsubscription;

import java.util.ArrayList;
import java.util.List;

public class UnsubscriptionInformationCryptsHandler extends AbstractHandler{
    private static final String btcName = Crypt.BTC.getName();
    private static final String ethName = Crypt.ETH.getName();
    private static final String bnbName = Crypt.BNB.getName();
    private static final String uniName = Crypt.UNI.getName();
    private static final String dotName = Crypt.DOT.getName();
    private static final String solName = Crypt.SOL.getName();

    public UnsubscriptionInformationCryptsHandler(TelegramBot telegramBot) {
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
                .setText(IconEmoji.HOURGLASS.get() + "Список криптовалют формируется, пожалуйста, подождите");
    }

    private static SendMessage sendInlineKeyBoardListCrypts(String chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();

        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(btcName).setCallbackData(Unsubscription.determineStatus(Unsubscription.BTC,btcName,chatId)));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(ethName).setCallbackData(Unsubscription.determineStatus(Unsubscription.ETH,ethName,chatId)));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(bnbName).setCallbackData(Unsubscription.determineStatus(Unsubscription.BNB,bnbName,chatId)));
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(uniName).setCallbackData(Unsubscription.determineStatus(Unsubscription.UNI,uniName,chatId)));
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(dotName).setCallbackData(Unsubscription.determineStatus(Unsubscription.DOT,dotName,chatId)));
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(solName).setCallbackData(Unsubscription.determineStatus(Unsubscription.SOL,solName,chatId)));


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId)
                .setText(IconEmoji.BOARD.get() + "Выбери криптовалюту, чтобы отписаться от информации о ее стоимости")
                .setReplyMarkup(inlineKeyboardMarkup);
    }

}
