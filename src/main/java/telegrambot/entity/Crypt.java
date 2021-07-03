package telegrambot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Crypt {
    BTC("BTC"), ETH("ETH"), BNB("BNB"),
    UNI("UNI"), DOT("DOT"), SOL("SOL");

    @Getter
    private final String name;
}
