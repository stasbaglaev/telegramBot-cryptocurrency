import com.crypto.cryptocompare.api.CryptoCompareApi;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import telegrambot.bot.TelegramBot;
import telegrambot.handler.DistributionHandler;
import telegrambot.service.MessageRecipientService;
import telegrambot.service.MessageSenderService;
import telegrambot.service.SubscriptionMailingService;

public class App {
    private static final Logger log = LogManager.getLogger(App.class);
    private static final String BOT_NAME = "QwertyITbot";
    private static final String BOT_TOKEN = "1770547715:AAGeAZ73iAUM8CXR9rpn_18kjLCTG7jNVzU";
    private static final int RECONNECT_PAUSE = 10000;
    private static final int PRIORITY_FOR_RECIPIENT = 3;

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        TelegramBot telegramBot = new TelegramBot(BOT_NAME, BOT_TOKEN);
        MessageRecipientService messageRecipientService = new MessageRecipientService(telegramBot);
        MessageSenderService messageSenderService = new MessageSenderService(telegramBot);
        SubscriptionMailingService subscriptionMailingService = new SubscriptionMailingService(telegramBot);
        DistributionHandler distributionHandler = new DistributionHandler(telegramBot);

        try {
            telegramBotsApi.registerBot(telegramBot);
            log.info("TelegramAPI started!");
        } catch (TelegramApiRequestException e) {
            log.error("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: " + e.getMessage());
            try {
                Thread.sleep(RECONNECT_PAUSE);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }

        Thread receiver = new Thread(messageRecipientService, "MessageRecipientService");
        receiver.setDaemon(true);
        receiver.setPriority(PRIORITY_FOR_RECIPIENT);
        receiver.start();

        Thread sender = new Thread(messageSenderService, "MessageSenderService");
        sender.setDaemon(true);
        sender.setPriority(Thread.MIN_PRIORITY);
        sender.start();

        Thread subscriptionMailer = new Thread(subscriptionMailingService, "MessageSenderBySubscriptionService");
        subscriptionMailer.setDaemon(true);
        subscriptionMailer.setPriority(Thread.MIN_PRIORITY);
        subscriptionMailer.start();

        Thread distributionHandlers = new Thread(distributionHandler, "SubscriptionService");
        distributionHandlers.setDaemon(true);
        distributionHandlers.setPriority(Thread.MIN_PRIORITY);
        distributionHandlers.start();
    }
}
