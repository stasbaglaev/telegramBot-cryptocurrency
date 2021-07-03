package telegrambot.message;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import telegrambot.command.IconEmoji;


@AllArgsConstructor
@NoArgsConstructor
public enum Unsubscription {
    BTC(IconEmoji.UNSUBSCRIBE.get() + "Подписка на BTC отменена!"),
    ETH(IconEmoji.UNSUBSCRIBE.get() + "Подписка на ETH отменена!"),
    BNB(IconEmoji.UNSUBSCRIBE.get() + "Подписка на BNB отменена!"),
    UNI(IconEmoji.UNSUBSCRIBE.get() + "Подписка на UNI отменена!"),
    DOT(IconEmoji.UNSUBSCRIBE.get() + "Подписка на DOT отменена!"),
    SOL(IconEmoji.UNSUBSCRIBE.get() + "Подписка на SOL отменена!");

    @Getter
    private String textMessage;
}
