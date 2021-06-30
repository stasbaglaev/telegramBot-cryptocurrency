package telegrambot.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import telegrambot.bot.TelegramBot;
import telegrambot.command.Command;
import telegrambot.command.ParsedCommand;

public class SystemHandler extends AbstractHandler {
    private static final Logger LOGGER = LogManager.getLogger(SystemHandler.class);
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
                //telegramBot.sendQueue.add(getMessageHelp(chatId));
                break;
        }
        return "";
    }

    private SendMessage getMessageStart(String chatId, String userNameInChat) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableMarkdown(true);
        StringBuilder displayedMessage = new StringBuilder();
        displayedMessage.append("Привет, *").append(userNameInChat).append("*! ");
        displayedMessage.append("Меня зовут ").append(telegramBot.getBotName()).append(END_LINE);
        displayedMessage.append("Чтобы перейти к списку валют используй команду /price");
        sendMessage.setText(displayedMessage.toString());
        //sendMessage.setReplyMarkup(replyKeyboardMarkup);
        //getMenu();
        return sendMessage;
    }

//    private SendMessage getMessageHelp(String chatID) {
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(chatID);
//        sendMessage.enableMarkdown(true);
//
//        StringBuilder text = new StringBuilder();
//        text.append("*This is help message*").append(END_LINE).append(END_LINE);
//        text.append("/start - show start message").append(END_LINE);
//        text.append("/help - show help message").append(END_LINE);
//        sendMessage.setText(text.toString());
//        return sendMessage;
//    }



//    private static void getMenu() {
//        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
//        KeyboardRow keyboardFirstRow = new KeyboardRow();
//        replyKeyboardMarkup.setSelective(true);
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboard(false);
//        keyboard.clear();
//        keyboardFirstRow.clear();
//        //keyboardFirstRow.add("Валюта");
//        keyboardFirstRow.add("Список криптовалют");
//        keyboard.add(keyboardFirstRow);
//        replyKeyboardMarkup.setKeyboard(keyboard);
//    }



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
