package telegrambot.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import telegrambot.ability.SubscriptionInformationCrypts;
import telegrambot.command.IconEmoji;


@AllArgsConstructor
@NoArgsConstructor
public enum Subscription {
    BTC(IconEmoji.SUBSCRIBE.get() + "Подписка на BTC оформлена!", IconEmoji.EXCLAMATION.get() + "Подписка на BTC итак активная!"),
    ETH(IconEmoji.SUBSCRIBE.get() + "Подписка на ETH оформлена!", IconEmoji.EXCLAMATION.get() + "Подписка на ETH итак активная!"),
    BNB(IconEmoji.SUBSCRIBE.get() + "Подписка на BNB оформлена!", IconEmoji.EXCLAMATION.get() + "Подписка на BNB итак активная!"),
    UNI(IconEmoji.SUBSCRIBE.get() + "Подписка на UNI оформлена!", IconEmoji.EXCLAMATION.get() + "Подписка на UNI итак активная!"),
    DOT(IconEmoji.SUBSCRIBE.get() + "Подписка на DOT оформлена!", IconEmoji.EXCLAMATION.get() + "Подписка на DOT итак активная!"),
    SOL(IconEmoji.SUBSCRIBE.get() + "Подписка на SOL оформлена!", IconEmoji.EXCLAMATION.get() + "Подписка на SOL итак активная!");

    @Getter
    private String notActiveMessage;
    @Getter
    private String activeMessage;


    public static String determineStatus(Subscription crypt, String name, String chatId) {
        String message = null;
        if (!SubscriptionInformationCrypts.getSubscription(chatId).contains(name)) {
            message = crypt.getNotActiveMessage();
        } else {
            message = crypt.getActiveMessage();
        }
        return message;
    }
}