package telegrambot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Currency {
    USD("USD"), EUR("EUR"), RUB("RUB");

    @Getter
    private final String name;

}
