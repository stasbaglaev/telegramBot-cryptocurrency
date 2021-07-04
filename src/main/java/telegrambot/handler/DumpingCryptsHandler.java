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

public class DumpingCryptsHandler extends AbstractHandler {
    private static final String END_LINE = "\n";
    private static final RequestInformationCrypts requestInformationCrypts = new RequestInformationCrypts();
    private static final String BTC_NAME = Crypt.BTC.getName();
    private static final String ETH_NAME = Crypt.ETH.getName();
    private static final String BNB_NAME = Crypt.BNB.getName();
    private static final String UNI_NAME = Crypt.UNI.getName();
    private static final String DOT_NAME = Crypt.DOT.getName();
    private static final String SOL_NAME = Crypt.SOL.getName();
    private static final String MESSAGE_HELP = "*Необходимо задать значение x* - процент, " +
            "на который должна измениться стоимость криптовалюты, чтобы пришло уведомление" +
            ", например ";

    public DumpingCryptsHandler(TelegramBot telegramBot) {
        super(telegramBot);
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        Command command = parsedCommand.getCommand();
        switch (command) {
            case DUMPING:
                telegramBot.sendQueue.add(sendMessageStatusCommand(chatId));
                telegramBot.sendQueue.add(sendInlineKeyBoardListCrypts(chatId));
                break;
            case DUMPINGBTC:
                String percentBtc = parsedCommand.getText();
                if ("".equals(percentBtc)) {
                    telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText((IconEmoji.EXCLAMATION.get()) +
                            MESSAGE_HELP + "/dumpingBTC 10").enableMarkdown(true));
                }
                break;
            case DUMPINGETH:
                String percentEth = parsedCommand.getText();
                if ("".equals(percentEth)) {
                    telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText((IconEmoji.EXCLAMATION.get()) +
                            MESSAGE_HELP + "/dumpingETH 2").enableMarkdown(true));
                }
                break;
            case DUMPINGBNB:
                String percentBnb = parsedCommand.getText();
                if ("".equals(percentBnb)) {
                    telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText((IconEmoji.EXCLAMATION.get()) +
                            MESSAGE_HELP + "/dumpingBNB 25").enableMarkdown(true));
                }
                break;
            case DUMPINGUNI:
                String percentUni = parsedCommand.getText();
                if ("".equals(percentUni)) {
                    telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText((IconEmoji.EXCLAMATION.get()) +
                            MESSAGE_HELP + "/dumpingUNI 8").enableMarkdown(true));
                }
                break;
            case DUMPINGDOT:
                String percentDot = parsedCommand.getText();
                if ("".equals(percentDot)) {
                    telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText((IconEmoji.EXCLAMATION.get()) +
                            MESSAGE_HELP + "/dumpingDOT 15").enableMarkdown(true));
                }
                break;
            case DUMPINGSOL:
                String percentSol = parsedCommand.getText();
                if ("".equals(percentSol)) {
                    telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText((IconEmoji.EXCLAMATION.get()) +
                            MESSAGE_HELP + "/dumpingDOT 5").enableMarkdown(true));
                }
                break;
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


        String bitcoin = IconEmoji.POINTER_RIGHT.get() + " Используй команду /dumpingBTC x";
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(BTC_NAME).setCallbackData(bitcoin));

        String ethereum = IconEmoji.POINTER_RIGHT.get() + " Используй команду /dumpingETH x";
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(ETH_NAME).setCallbackData(ethereum));

        String binance = IconEmoji.POINTER_RIGHT.get() + " Используй команду /dumpingBNB x";
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(BNB_NAME).setCallbackData(binance));

        String uniswap = IconEmoji.POINTER_RIGHT.get() + " Используй команду /dumpingUNI x";
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(UNI_NAME).setCallbackData(uniswap));

        String polkadot = IconEmoji.POINTER_RIGHT.get() + " Используй команду /dumpingDOT x";
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(DOT_NAME).setCallbackData(polkadot));

        String solana = IconEmoji.POINTER_RIGHT.get() + " Используй команду /dumpingSOL x";
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(SOL_NAME).setCallbackData(solana));

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId)
                .setText(IconEmoji.BOARD.get() + "Выбери криптовалюту, чтобы задать событие для нее")
                .setReplyMarkup(inlineKeyboardMarkup);
    }
}
