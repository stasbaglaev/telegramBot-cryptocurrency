package telegrambot.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import telegrambot.ability.SubscriptionInformationCrypts;
import telegrambot.command.IconEmoji;


@AllArgsConstructor
@NoArgsConstructor
public enum Unsubscription {
    BTC(IconEmoji.UNSUBSCRIBE.get() + "Подписка на BTC отменена!", IconEmoji.EXCLAMATION.get() + "Подписка на BTC и так не активная!"),
    ETH(IconEmoji.UNSUBSCRIBE.get() + "Подписка на ETH отменена!", IconEmoji.EXCLAMATION.get() + "Подписка на ETH и так не активная!"),
    BNB(IconEmoji.UNSUBSCRIBE.get() + "Подписка на BNB отменена!", IconEmoji.EXCLAMATION.get() + "Подписка на BNB и так не активная!"),
    UNI(IconEmoji.UNSUBSCRIBE.get() + "Подписка на UNI отменена!", IconEmoji.EXCLAMATION.get() + "Подписка на UNI и так не активная!"),
    DOT(IconEmoji.UNSUBSCRIBE.get() + "Подписка на DOT отменена!", IconEmoji.EXCLAMATION.get() + "Подписка на DOT и так не активная!"),
    SOL(IconEmoji.UNSUBSCRIBE.get() + "Подписка на SOL отменена!", IconEmoji.EXCLAMATION.get() + "Подписка на SOL и так не активная!");

    @Getter
    private String activeMessage;
    @Getter
    private String notActiveMessage;

    public static String determineStatus(Unsubscription crypt, String name, String chatId) {
        String message = null;
        if (!SubscriptionInformationCrypts.getSubscription(chatId).contains(name)) {
            message = crypt.getNotActiveMessage();
        } else {
            message = crypt.getActiveMessage();
        }
        return message;
    }
}
