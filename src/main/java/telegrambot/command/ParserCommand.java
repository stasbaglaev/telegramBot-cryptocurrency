package telegrambot.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ParserCommand {
    private static final Logger LOGGER = LogManager.getLogger(ParserCommand.class);
    private static final String PREFIX_FOR_COMMAND = "/";
    private final String DELIMITER_COMMAND_BOTNAME = "@";
    private final String botName;

    public ParserCommand(String botName) {
        this.botName = botName;
    }

    public ParsedCommand getParsedCommand(String text) {
        String trimText = "";
        if (text != null) trimText = text.trim();
        ParsedCommand result = new ParsedCommand(Command.NONE, trimText);

        if ("".equals(trimText)) return result;
        LOGGER.info("trimText: " + trimText);


        if (isCommand(trimText)) {
            if (isCommandForMe(trimText)) {
                String commandForParse = cutCommandFromFullText(trimText);
                LOGGER.info("commandForParse " + commandForParse);
                Command commandFromText = getCommandFromText(commandForParse);
                LOGGER.info("commandFromText " + commandFromText);
                result.setCommand(commandFromText);
            } else {
                result.setCommand(Command.NOTFORME);
            }
        }
        return result;
    }

    private String cutCommandFromFullText(String text) {
        if (text.startsWith(PREFIX_FOR_COMMAND)) {
            return text.contains(DELIMITER_COMMAND_BOTNAME) ?
                    text.substring(1, text.indexOf(DELIMITER_COMMAND_BOTNAME)) :
                    text.substring(1);
        } else return text;

    }

    private Command getCommandFromText(String text) {
        LOGGER.debug("text: " + text);
        if (text.equals(Command.PRICE.getName())) {
            return Command.PRICE;
        } else {
            String upperCaseText = text.toUpperCase().trim();
            Command command = Command.NONE;
            try {
                command = Command.valueOf(upperCaseText);
                LOGGER.debug("Can't parse command: " + command);
            } catch (IllegalArgumentException e) {
                LOGGER.debug("Can't parse command: " + text);
            }
            return command;
        }
    }

    private boolean isCommandForMe(String command) {
        if (command.contains(DELIMITER_COMMAND_BOTNAME)) {
            String botNameForEqual = command.substring(command.indexOf(DELIMITER_COMMAND_BOTNAME) + 1);
            return botName.equals(botNameForEqual);
        }
        return true;
    }

    private boolean isCommand(String text) {
        return text.startsWith(PREFIX_FOR_COMMAND) || text.equals(Command.PRICE.getName());
    }
}
