package telegrambot.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import telegrambot.ability.SubscriptionInformationCrypts;
import telegrambot.command.IconEmoji;


@AllArgsConstructor
@NoArgsConstructor
public enum Subscription {
    BTC(IconEmoji.SUBSCRIBE.get() + "Подписка на BTC оформлена!", IconEmoji.EXCLAMATION.get() + "Подписка на BTC и так активная!"),
    ETH(IconEmoji.SUBSCRIBE.get() + "Подписка на ETH оформлена!", IconEmoji.EXCLAMATION.get() + "Подписка на ETH и так активная!"),
    BNB(IconEmoji.SUBSCRIBE.get() + "Подписка на BNB оформлена!", IconEmoji.EXCLAMATION.get() + "Подписка на BNB и так активная!"),
    UNI(IconEmoji.SUBSCRIBE.get() + "Подписка на UNI оформлена!", IconEmoji.EXCLAMATION.get() + "Подписка на UNI и так активная!"),
    DOT(IconEmoji.SUBSCRIBE.get() + "Подписка на DOT оформлена!", IconEmoji.EXCLAMATION.get() + "Подписка на DOT и так активная!"),
    SOL(IconEmoji.SUBSCRIBE.get() + "Подписка на SOL оформлена!", IconEmoji.EXCLAMATION.get() + "Подписка на SOL и так активная!");

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