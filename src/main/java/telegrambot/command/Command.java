package telegrambot.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum Command {
    NONE("NONE"), NOTFORME("NOTFORME"), START("Привет"),
    HELP("Помощь"), REQUEST("Запросить"), SUBSCRIBE("Подписаться"),
    UNSUBSCRIBE("Отписаться"),
    DUMPING("Тейк-профит"),
    GRAPH("Построить"),
    DUMPINGBTC(""),
    DUMPINGETH(""),
    DUMPINGBNB(""),
    DUMPINGUNI(""),
    DUMPINGDOT(""),
    DUMPINGSOL(""),
    GRAPHBTC(""),
    GRAPHETH(""),
    GRAPHBNB(""),
    GRAPHUNI(""),
    GRAPHDOT(""),
    GRAPHSOL("")
    ;

    @Getter
    private String name;
}