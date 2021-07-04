package telegrambot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import telegrambot.bot.TelegramBot;
import telegrambot.command.Command;
import telegrambot.command.IconEmoji;
import telegrambot.command.ParsedCommand;

import java.util.ArrayList;

public class SystemHandler extends AbstractHandler {
    private static final String END_LINE = "\n";
    private static final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    public SystemHandler(TelegramBot telegramBot) {
        super(telegramBot);
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        Command command = parsedCommand.getCommand();
        String userNameInChat = update.getMessage().getChat().getUserName();

        switch (command) {
            case START:
                telegramBot.sendQueue.add(getMessageStart(chatId, userNameInChat));
                break;
            case HELP:
                telegramBot.sendQueue.add(getMessageHelp(chatId));
                break;
        }
        return "";
    }

    private SendMessage getMessageStart(String chatId, String userNameInChat) {
        String displayedMessage = "Привет, *" + userNameInChat + "*! " +
                "Меня зовут " + telegramBot.getBotName() + END_LINE + END_LINE +
                "Для навигации используйте меню " + IconEmoji.POINTER_DOWN.get();
        //sendMessage.setReplyMarkup(replyKeyboardMarkup);
        getMenu();
        return new SendMessage().setChatId(chatId)
                .setReplyMarkup(replyKeyboardMarkup)
                .enableMarkdown(true)
                .setText(displayedMessage);
    }

    private SendMessage getMessageHelp(String chatID) {
        StringBuilder text = new StringBuilder();
        text.append("*Описание команд*").append(END_LINE).append(END_LINE);
        text.append("/start - начать работу").append(END_LINE);
        text.append("/request - запросить данные по курсам криптовалют").append(END_LINE);
        text.append("/subscribe - подписаться на получение рассылки с данными по курсам криптовалют").append(END_LINE);
        text.append("/unsubscribe - отписаться от получения рассылки с данными по курсам криптовалют").append(END_LINE);
        text.append("/dumping *x* - задать событие вида \"падение курса криптовалюты от текущего значения на X%\" ").append(END_LINE);
        text.append("/graph - построить график").append(END_LINE);
        text.append("/help - вызвать описание команд").append(END_LINE);
        return new SendMessage().setChatId(chatID)
                .enableMarkdown(true)
                .setText(text.toString());
    }


    private static void getMenu() {
        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow1 = new KeyboardRow();
        KeyboardRow keyboardFirstRow2 = new KeyboardRow();
        KeyboardRow keyboardFirstRow3 = new KeyboardRow();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        keyboard.clear();
        keyboardFirstRow1.clear();
        keyboardFirstRow2.clear();
        keyboardFirstRow3.clear();
        keyboardFirstRow1.add("Запросить стоимость");
        keyboardFirstRow1.add("Тейк-профит");
        keyboardFirstRow2.add("Подписаться");
        keyboardFirstRow2.add("Отписаться");
        keyboardFirstRow3.add("Построить график");
        keyboardFirstRow3.add("Помощь");
        keyboard.add(keyboardFirstRow1);
        keyboard.add(keyboardFirstRow2);
        keyboard.add(keyboardFirstRow3);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }



    /*public static SendMessage sendInlineKeyBoardListCurrency(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("USD").setCallbackData("USD"));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("EURO").setCallbackData("EURO"));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("RUB").setCallbackData("RUB"));
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText("Выбери валюту:").setReplyMarkup(inlineKeyboardMarkup);
    }*/
}
