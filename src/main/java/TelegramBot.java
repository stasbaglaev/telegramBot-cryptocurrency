import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


public class TelegramBot extends TelegramLongPollingBot {
    private static final String BOT_USER_NAME = "QwertyITbot";
    private static final String TOKEN = "1770547715:AAGeAZ73iAUM8CXR9rpn_18kjLCTG7jNVzU";
    private long chatId;
    private String userName;
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    private static final PriceCrypts priceCrypts = new PriceCrypts();

    public static String getInfoPriceCrypto() {
        return priceCrypts.getPriceCrypto();
    }
    public static String getInfoPriceCrypto2() {
        return priceCrypts.getNameCrypto();
    }

    @Override
    public void onUpdateReceived(Update update) {
        update.getUpdateId();

        if (update.hasMessage()) {
            Message message = update.getMessage();
            System.out.println("Text message: " + message.getText());
            chatId = message.getChatId();
            userName = message.getChat().getUserName();
            SendMessage sendMessage = new SendMessage().setChatId(chatId);
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            String text = message.getText();
            if (update.getMessage().hasText()) {
                if (text.equals("/start")) {
                    try {
                        execute(sendMessage.setText("Привет, " + userName + "!"));
                        execute(sendMessage.setText(getMessage(text)));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else if (text.equals("Список криптовалют")) {
                    try {
                        execute(sendInlineKeyBoardMessage(chatId));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else if (text.equals("q")) {
                    try {
                        execute(sendMessage.setText(getInfoPriceCrypto2()));
                        execute(sendMessage.setText(getInfoPriceCrypto()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        execute(sendMessage.setText("Echo: " + message.getText()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (update.hasCallbackQuery()) {
            try {
                execute(new SendMessage().setText(update.getCallbackQuery().getData())
                        .setChatId(update.getCallbackQuery().getMessage().getChatId()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public String getMessage(String msg) {
        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        if (msg.equals("/start")) {
            keyboard.clear();
            keyboardFirstRow.clear();
            keyboardFirstRow.add("Список криптовалют");
            keyboard.add(keyboardFirstRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
        }
        return "Выбрать ...";
    }

    public static SendMessage sendInlineKeyBoardMessage(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("Биткоин").setCallbackData("Стоимость Биткоина: " + getInfoPriceCrypto()));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("Эфириум").setCallbackData("Стоимость Эфириума: " + getInfoPriceCrypto()));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("Tether").setCallbackData("Стоимость Tether: "));
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText("Список криптовалют").setReplyMarkup(inlineKeyboardMarkup);
    }

    @Override
    public String getBotUsername() {
        return BOT_USER_NAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

}
