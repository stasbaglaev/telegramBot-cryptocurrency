package telegrambot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Crypt {
    BTC("BTC"), Ethereum("Ethereum"), Litecoin("Litecoin")
    ,ETH("ETH"),BNB("BNB"),DOGE("DOGE"),DOT("DOT"),ADA("ADA");

    @Getter
    private final String name;
}
