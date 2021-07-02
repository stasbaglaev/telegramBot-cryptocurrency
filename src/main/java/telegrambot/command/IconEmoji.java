package telegrambot.command;

import com.vdurmont.emoji.EmojiParser;

public enum IconEmoji {
    GRAPH(":chart_with_upwards_trend:"),
    POINTER(":point_down:"),
    SUBSCRIBE(":white_check_mark:"),
    UNSUBSCRIBE(":x:"),
    HELP(":sos:"),
    MONEY(":moneybag:");


    private String value;

    public String get() {
        return EmojiParser.parseToUnicode(value);
    }

    IconEmoji(String value) {
        this.value = value;
    }

}
