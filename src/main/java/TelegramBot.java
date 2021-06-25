import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;


public class TelegramBot extends TelegramLongPollingBot {
    private static final Logger log = Logger.getLogger(TelegramBot.class);
    private String userName;
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    @Override
    public void onUpdateReceived(Update update) {
        update.getUpdateId();                                   // Обновить информацию о пользователе
        Message message = update.getMessage();                   // Получаем текст входящего сообщения
        System.out.println("Text message: " + message.getText());
        long chat_id = message.getChatId();
        userName = message.getChat().getUserName();
        SendMessage sendMessage = new SendMessage().setChatId(chat_id);            // Создаем объект, в котором опишем сообщение, которое хотим послать в ответ
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        String text = message.getText();

        if (text.equals("Начать") || text.equals("/Start")) {
            sendMessage.setText("Привет, " + userName + "!");
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            sendMessage.setText(getMessage(text));
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            sendMessage.setText("Echo: " + message.getText());  // Укажем текст сообщения
        }
       /* try {

            execute(sendMessage);                               // Отправим сообщение
        } catch (TelegramApiException e) {
            e.printStackTrace();                                // Это обработка исключительных ситуаций - на случай если что-то пойдет не так
        }*/
        log.debug("new Update recieve");
    }

    public String getMessage(String msg) {
        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        if (msg.equals("Начать") || msg.equals("/Start")) {
            keyboard.clear();
            keyboardFirstRow.clear();
            keyboardFirstRow.add("Список команд");
            keyboard.add(keyboardFirstRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
        }
        return "Выбрать ...";
    }

    @Override
    public String getBotUsername() {
        return "QwertyITbot";
    }

    @Override
    public String getBotToken() {
        return "1770547715:AAGeAZ73iAUM8CXR9rpn_18kjLCTG7jNVzU";
    }

}
