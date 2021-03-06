package telegrambot.command;

import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ParserCommand {
    private static final Logger LOGGER = LogManager.getLogger(ParserCommand.class);
    private static final String PREFIX_FOR_COMMAND = "/";
    private static final String DELIMITER_COMMAND_BOT_NAME = "@";
    private final String botName;

    public ParserCommand(String botName) {
        this.botName = botName;
    }

    public ParsedCommand getParsedCommand(String text) {
        String trimText = "";
        if (text != null) trimText = text.trim();
        ParsedCommand result = new ParsedCommand(Command.NONE, trimText);

        if ("".equals(trimText)) return result;
        LOGGER.debug("trimText: " + trimText);
        Pair<String, String> commandAndText = getDelimitedCommandFromText(trimText);

        if (isCommand(commandAndText.getKey())) {
            if (isCommandForMe(commandAndText.getKey())) {
                String commandForParse = cutCommandFromFullText(commandAndText.getKey());
                LOGGER.info("commandForParse " + commandForParse);
                Command commandFromText = getCommandFromText(commandForParse);
                LOGGER.info("commandFromText " + commandFromText);
                result.setText(commandAndText.getValue());
                result.setCommand(commandFromText);
            } else {
                result.setCommand(Command.NOTFORME);
                result.setText(commandAndText.getValue());
            }
        }
        return result;
    }

    private String cutCommandFromFullText(String text) {
        if (text.startsWith(PREFIX_FOR_COMMAND)) {
            return text.contains(DELIMITER_COMMAND_BOT_NAME) ?
                    text.substring(1, text.indexOf(DELIMITER_COMMAND_BOT_NAME)) :
                    text.substring(1);
        } else return text;
    }

    private Command getCommandFromText(String text) {
        if (text.equals(Command.REQUEST.getName())) {
            return Command.REQUEST;
        } else if (text.equals(Command.UNSUBSCRIBE.getName())) {
            return Command.UNSUBSCRIBE;
        } else if (text.equals(Command.HELP.getName())) {
            return Command.HELP;
        } else if (text.equals(Command.GRAPH.getName())) {
            return Command.GRAPH;
        } else if (text.equals(Command.SUBSCRIBE.getName())) {
            return Command.SUBSCRIBE;
        } else if (text.equals(Command.DUMPING.getName())) {
            return Command.DUMPING;
        } else if (text.equals(Command.DUMPINGBTC.getName())) {
            return Command.DUMPINGBTC;
        } else if (text.equals(Command.DUMPINGETH.getName())) {
            return Command.DUMPINGETH;
        }else if (text.equals(Command.DUMPINGBNB.getName())) {
            return Command.DUMPINGBNB;
        }else if (text.equals(Command.DUMPINGUNI.getName())) {
            return Command.DUMPINGUNI;
        }else if (text.equals(Command.DUMPINGDOT.getName())) {
            return Command.DUMPINGDOT;
        }else if (text.equals(Command.DUMPINGSOL.getName())) {
            return Command.DUMPINGSOL;
        }else if (text.equals(Command.GRAPHBTC.getName())) {
            return Command.GRAPHBTC;
        }else if (text.equals(Command.GRAPHETH.getName())) {
            return Command.GRAPHETH;
        }else if (text.equals(Command.GRAPHBNB.getName())) {
            return Command.GRAPHBNB;
        }else if (text.equals(Command.GRAPHUNI.getName())) {
            return Command.GRAPHUNI;
        }else if (text.equals(Command.GRAPHDOT.getName())) {
            return Command.GRAPHDOT;
        }else if (text.equals(Command.GRAPHSOL.getName())) {
            return Command.GRAPHSOL;
        }
        else {
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


    private Pair<String, String> getDelimitedCommandFromText(String trimText) {
        Pair<String, String> commandText;

        if (trimText.contains(" ")) {
            int indexOfSpace = trimText.indexOf(" ");
            commandText = new Pair<>(trimText.substring(0, indexOfSpace), trimText.substring(indexOfSpace + 1));
        } else commandText = new Pair<>(trimText, "");
        return commandText;
    }

    private boolean isCommandForMe(String command) {
        if (command.contains(DELIMITER_COMMAND_BOT_NAME)) {
            String botNameForEqual = command.substring(command.indexOf(DELIMITER_COMMAND_BOT_NAME) + 1);
            return botName.equals(botNameForEqual);
        }
        return true;
    }

    private boolean isCommand(String text) {
        return ((text.startsWith(PREFIX_FOR_COMMAND)) || (text.equals(Command.SUBSCRIBE.getName())) || (text.equals(Command.REQUEST.getName())) ||
                (text.equals(Command.UNSUBSCRIBE.getName())) || (text.equals(Command.HELP.getName())) || (text.equals(Command.GRAPH.getName()))
                || (text.equals(Command.DUMPING.getName())) || (text.equals(Command.DUMPINGBTC.getName())) || (text.equals(Command.DUMPINGETH.getName()))
                || (text.equals(Command.DUMPINGBNB.getName())) || (text.equals(Command.DUMPINGUNI.getName())) || (text.equals(Command.DUMPINGDOT.getName()))
                || (text.equals(Command.DUMPINGSOL.getName())) || (text.equals(Command.GRAPHBTC.getName())) || (text.equals(Command.GRAPHETH.getName()))
                || (text.equals(Command.GRAPHBNB.getName())) || (text.equals(Command.GRAPHUNI.getName())) || (text.equals(Command.GRAPHDOT.getName()))
                || (text.equals(Command.GRAPHSOL.getName())));
    }
}
