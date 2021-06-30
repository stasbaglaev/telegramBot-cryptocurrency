package telegrambot.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import telegrambot.bot.TelegramBot;

public class MessageSenderService implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(MessageSenderService.class);
    private static final int SENDER_SLEEP_TIME = 1000;
    private final TelegramBot telegramBot;

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
            LOGGER.error(e);
        }
    }

    private void send(Object object) {
        try {
            MessageType messageType = messageType(object);
            switch (messageType) {
                case EXECUTE:
                    BotApiMethod<Message> message = (BotApiMethod<Message>) object;
                    LOGGER.debug("Use Execute for " + object);
                    telegramBot.execute(message);
                    break;
                default:
                    LOGGER.warn("Cant detect type of object. " + object);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private MessageType messageType(Object object) {
        if (object instanceof BotApiMethod) return MessageType.EXECUTE;
        return MessageType.NOT_DETECTED;
    }

    enum MessageType {
        EXECUTE, NOT_DETECTED,
    }
}
