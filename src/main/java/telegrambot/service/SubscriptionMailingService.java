package telegrambot.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegrambot.bot.TelegramBot;

public class SubscriptionMailingService implements Runnable {
    private final TelegramBot telegramBot;
    private static final Logger LOGGER = LogManager.getLogger(SubscriptionMailingService.class);
    private static final int SENDER_SLEEP_TIME = 1000;

    public SubscriptionMailingService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void run() {
        LOGGER.info("[STARTED] MessageSenderBySubscriptionService.  Bot class: " + telegramBot.getBotName());
        try {
            while (true) {
                for (Object object = telegramBot.sendSubscriptionQueue.poll(); object != null; object = telegramBot.sendSubscriptionQueue.poll()) {
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
            MessageSenderService.MessageType messageType = messageType(object);
            switch (messageType) {
                case EXECUTE:
                    if (SendMessage.class.equals(object.getClass())) {
                        BotApiMethod<Message> message = (BotApiMethod<Message>) object;
                        LOGGER.debug("Use Execute for " + object);
                        telegramBot.execute(message);
                        break;
                    }
                default:
                    LOGGER.warn("Cant detect type of object. " + object);
            }
        } catch (TelegramApiException e) {
            LOGGER.error("TelegramApiRequestException " + e.getMessage(), e);
        }
    }

    private MessageSenderService.MessageType messageType(Object object) {
        if ((object instanceof PartialBotApiMethod)) return MessageSenderService.MessageType.EXECUTE;
        return MessageSenderService.MessageType.NOT_DETECTED;
    }

    enum MessageType {
        EXECUTE, NOT_DETECTED,
    }
}
