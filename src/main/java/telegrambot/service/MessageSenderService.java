package telegrambot.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegrambot.ability.LineChartCrypts;
import telegrambot.bot.TelegramBot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MessageSenderService implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(MessageSenderService.class);
    private static final int SENDER_SLEEP_TIME = 1000;
    private final TelegramBot telegramBot;
    private static final LineChartCrypts lineChartCrypts = new LineChartCrypts();

    public MessageSenderService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void run() {
        LOGGER.info("[STARTED] MessageSenderService.  Bot class: " + telegramBot.getBotName());
        try {
            while (true) {
                for (Object object = telegramBot.sendQueue.poll(); object != null; object = telegramBot.sendQueue.poll()) {
                    LOGGER.debug("Get new message to send " + object);
                    send(object);
                }
                try {
                    Thread.sleep(SENDER_SLEEP_TIME);
                } catch (InterruptedException e) {
                    LOGGER.error("Take interrupt while operate message list", e);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Exception method run");
        }
    }

    private void send(Object object) {
        try {
            MessageType messageType = messageType(object);
            LOGGER.info("Получить тип объекта " + messageType);
            switch (messageType) {
                case EXECUTE:
                    if (SendMessage.class.equals(object.getClass())) {
                        BotApiMethod<Message> message = (BotApiMethod<Message>) object;
                        LOGGER.debug("Use Execute for " + object);
                        telegramBot.execute(message);
                        break;
                    } else if (SendPhoto.class.equals(object.getClass())) {
                        PartialBotApiMethod<Message> message = (PartialBotApiMethod<Message>) object;
                        LOGGER.debug("Use Execute for " + object);
                        telegramBot.execute((SendPhoto) message);
//                        try {
//                            Files.delete(Paths.get(lineChartCrypts.getFILE_NAME()));
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                        break;
                    }

                default:
                    LOGGER.warn("Cant detect type of object. " + object);
            }
        } catch (TelegramApiException e) {

//            telegramBot.sendQueue.add(new SendMessage().setChatId(chatId)
//                    .setText("Вы слишком часто отправляете запросы"));
            LOGGER.error("TelegramApiRequestException " + e.getMessage(), e);
        }
    }

    private MessageType messageType(Object object) {
        LOGGER.info("Получить тип объекта " + object.getClass());
        if ((object instanceof PartialBotApiMethod)) return MessageType.EXECUTE;
        return MessageType.NOT_DETECTED;
    }

    enum MessageType {
        EXECUTE, NOT_DETECTED,
    }
}
