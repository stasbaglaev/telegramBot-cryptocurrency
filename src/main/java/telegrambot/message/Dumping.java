package telegrambot.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import telegrambot.command.IconEmoji;


@AllArgsConstructor
@NoArgsConstructor
public enum Dumping {
    HELP(IconEmoji.EXCLAMATION.get() + "*Необходимо задать значение x* - процент, на который должна измениться стоимость криптовалюты, чтобы пришло уведомление, например "),
    EVENT(IconEmoji.SUBSCRIBE.get() + "Событие зафиксировано! Понижение стоимости ");


    @Getter
    private String message;
}