package telegrambot.command;

import com.vdurmont.emoji.EmojiParser;

public enum IconEmoji {
    GRAPH(":chart_with_upwards_trend:"),
    POINTER_DOWN(":point_down:"),
    SUBSCRIBE(":white_check_mark:"),
    UNSUBSCRIBE(":x:"),
    HELP(":sos:"),
    MONEY(":moneybag:"),
    BOARD(":clipboard:"),
    HOURGLASS(":hourglass_flowing_sand:"),
    EXCLAMATION(":exclamation:"),
    POINTER_RIGHT(":point_right:");


    private String value;

    public String get() {
        return EmojiParser.parseToUnicode(value);
    }

    IconEmoji(String value) {
        this.value = value;
    }

}
