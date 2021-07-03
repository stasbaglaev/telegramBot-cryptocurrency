package telegrambot.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import telegrambot.command.IconEmoji;


@AllArgsConstructor
@NoArgsConstructor
public enum Subscription {
    BTC(IconEmoji.SUBSCRIBE.get() + "Подписка на BTC оформлена!"),
    ETH(IconEmoji.SUBSCRIBE.get() + "Подписка на ETH оформлена!"),
    BNB(IconEmoji.SUBSCRIBE.get() + "Подписка на BNB оформлена!"),
    UNI(IconEmoji.SUBSCRIBE.get() + "Подписка на UNI оформлена!"),
    DOT(IconEmoji.SUBSCRIBE.get() + "Подписка на DOT оформлена!"),
    SOL(IconEmoji.SUBSCRIBE.get() + "Подписка на SOL оформлена!");

    @Getter
    private String textMessage;
}