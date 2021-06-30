package telegrambot.command;

import com.vdurmont.emoji.EmojiParser;

public enum IconEmoji {
    GRAPH(":chart_with_upwards_trend:"),
    MONEY(":moneybag:");


    private String value;

    public String get() {
        return EmojiParser.parseToUnicode(value);
    }

    IconEmoji(String value) {
        this.value = value;
    }

}
