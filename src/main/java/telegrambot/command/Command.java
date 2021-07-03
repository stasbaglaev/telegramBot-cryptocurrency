package telegrambot.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum Command {
    NONE("NONE"), NOTFORME("NOTFORME"), START("Привет"),
    HELP("Помощь"), REQUEST("Запросить стоимость"),SUBSCRIBE("Подписаться"),
    UNSUBSCRIBE("Отписаться"),
    GRAPH("Построить график");

    @Getter
    private String name;
}