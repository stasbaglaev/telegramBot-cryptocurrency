package telegrambot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegrambot.ability.LineChartCrypts;
import telegrambot.ability.PriceCrypts;
import telegrambot.bot.TelegramBot;
import telegrambot.command.Command;
import telegrambot.command.IconEmoji;
import telegrambot.command.ParsedCommand;
import telegrambot.entity.Crypt;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LineChartCryptsHandler extends AbstractHandler {
    private static final LineChartCrypts lineChartCrypts = new LineChartCrypts();
    private static final String END_LINE = "\n";


    private static final PriceCrypts priceCrypts = new PriceCrypts();
    private static final String BTC_NAME = Crypt.BTC.getName();
    private static final String ETH_NAME = Crypt.ETH.getName();
    private static final String BNB_NAME = Crypt.BNB.getName();
    private static final String UNI_NAME = Crypt.UNI.getName();
    private static final String DOT_NAME = Crypt.DOT.getName();
    private static final String SOL_NAME = Crypt.SOL.getName();


    public LineChartCryptsHandler(TelegramBot telegramBot) {
        super(telegramBot);
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        Command command = parsedCommand.getCommand();

        if (command == Command.GRAPH) {
            telegramBot.sendQueue.add(sendMessageStatusCommand(chatId));
            //telegramBot.sendQueue.add(sendInlineKeyBoardListCrypts(chatId));

//            if (lineChartCrypts.completedOperation()){
//            telegramBot.sendQueue.add(sendLineChartCrypts(chatId));
//            }
       }
        return "";
    }

//    private static SendMessage sendMessageStatusCommand(String chatId) {
//        return new SendMessage().setChatId(chatId)
//                .setText(IconEmoji.GRAPH.get() + " График формируется, пожалуйста, подождите");
//    }

    private static SendMessage sendMessageStatusCommand(String chatId) {
        return new SendMessage().setChatId(chatId)
                .setText(IconEmoji.HOURGLASS.get() + "Список криптовалют формируется, пожалуйста, подождите");
    }

    private static SendPhoto sendLineChartCrypts(String chatId) {
        return new SendPhoto().setChatId(chatId)
                .setPhoto(new File(LineChartCrypts.getFILE_NAME()));
    }

//    private static SendMessage sendInlineKeyBoardListCrypts(String chatId) {
//        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
//        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
//
//       // String bitcoin = lineChartCrypts.getLineChartCrypts(Crypt.BTC.getName());
//        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(Crypt.BTC.getName()));
//
//        //String ethereum = lineChartCrypts.getLineChartCrypts(Crypt.ETH.getName());
//        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(Crypt.ETH.getName()));
//
//        //String binance = "BNB";
//        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(Crypt.BNB.getName()));
//
//       // String dogecoin = "DOGE";
//        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(Crypt.DOGE.getName()));
//
//        //String polkadot = "DOT";
//        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(Crypt.DOT.getName()));
//
//        //String cardano = "ADA";
//        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(Crypt.ADA.getName()));
//
//
//        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
//        rowList.add(keyboardButtonsRow1);
//        rowList.add(keyboardButtonsRow2);
//        inlineKeyboardMarkup.setKeyboard(rowList);
//        return new SendMessage().setChatId(chatId)
//                .setText("Выбери криптовалюту, чтобы подписаться на информацию о ее стоимости")
//                .setReplyMarkup(inlineKeyboardMarkup);
//    }
//
    private static SendMessage sendInlineKeyBoardListCrypts(String chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();

        String bitcoin = "BTC" + LineChartCrypts.getLineChartCrypts(BTC_NAME);
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(BTC_NAME).setCallbackData(bitcoin));

        String ethereum = "ETH" + LineChartCrypts.getLineChartCrypts(ETH_NAME);
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(ETH_NAME).setCallbackData(ethereum));

        String binance = "BNB" + LineChartCrypts.getLineChartCrypts(BNB_NAME);
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(BNB_NAME).setCallbackData(binance));

        String uniswap = "UNI" + LineChartCrypts.getLineChartCrypts(UNI_NAME);
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(UNI_NAME).setCallbackData(uniswap));

        String polkadot = "DOT" + LineChartCrypts.getLineChartCrypts(DOT_NAME);
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(DOT_NAME).setCallbackData(polkadot));

        String solana = "SOL" + LineChartCrypts.getLineChartCrypts(SOL_NAME);
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(SOL_NAME).setCallbackData(solana));


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId)
                .setText(IconEmoji.BOARD.get() + "Выбери криптовалюту, чтобы вывести график за сутки")
                .setReplyMarkup(inlineKeyboardMarkup);
    }
}

