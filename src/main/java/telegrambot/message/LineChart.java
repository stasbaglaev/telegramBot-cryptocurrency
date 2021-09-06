package telegrambot.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import telegrambot.command.IconEmoji;


@AllArgsConstructor
@NoArgsConstructor
public enum LineChart {
    BTC(IconEmoji.GRAPH.get() + "График для BTC строится..."),
    ETH(IconEmoji.GRAPH.get() + "График для ETH строится..."),
    BNB(IconEmoji.GRAPH.get() + "График для BNB строится..."),
    UNI(IconEmoji.GRAPH.get() + "График для UNI строится..."),
    DOT(IconEmoji.GRAPH.get() + "График для DOT строится..."),
    SOL(IconEmoji.GRAPH.get() + "График для SOL строится...");

    @Getter
    private String buildLineChartMessage;

//    public static String determineStatus(Subscription crypt, String name, String chatId) {
//        String message = null;
//        if (!SubscriptionInformationCrypts.getSubscription(chatId).contains(name)) {
//            message = crypt.getNotActiveMessage();
//        } else {
//            message = crypt.getActiveMessage();
//        }
//        return message;
//    }
}