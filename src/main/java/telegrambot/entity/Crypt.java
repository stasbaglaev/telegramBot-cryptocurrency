package telegrambot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Crypt {
    Bitcoin("Bitcoin"), Ethereum("Ethereum"), Litecoin("Litecoin");

    @Getter
    private final String name;
}
