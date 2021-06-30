package telegrambot.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum Command {
    NONE("NONE"), NOTFORME("NOTFORME"), START("Привет"),
    HELP("Помощь"), PRICE("Список криптовалют"),
    GRAPH("График");

    @Getter
    private String name;
}