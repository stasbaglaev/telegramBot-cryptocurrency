package telegrambot.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum Command {
    NONE("NONE"), NOTFORME("NOTFORME"), START("Привет"),
    HELP("Помощь"), REQUEST("Запросить"),SUBSCRIBE("Подписаться"),
    UNSUBSCRIBE("Отписаться"),
    DUMPING("Задать"),
    GRAPH("Построить"),
    DUMPINGBTC(""),
    DUMPINGETH(""),
    DUMPINGBNB(""),
    DUMPINGUNI(""),
    DUMPINGDOT(""),
    DUMPINGSOL(""),;

    @Getter
    private String name;
}