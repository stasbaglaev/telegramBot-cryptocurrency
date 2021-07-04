package telegrambot.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegrambot.ability.DumpingCrypts;
import telegrambot.ability.RequestInformationCrypts;
import telegrambot.ability.SubscriptionInformationCrypts;
import telegrambot.bot.TelegramBot;
import telegrambot.command.Command;
import telegrambot.command.IconEmoji;
import telegrambot.command.ParsedCommand;
import telegrambot.entity.Crypt;
import telegrambot.message.Dumping;

import java.util.ArrayList;
import java.util.List;

public class DumpingCryptsHandler extends AbstractHandler {
    private static final Logger LOGGER = LogManager.getLogger(SubscriptionInformationCrypts.class);
    private static final String END_LINE = "\n";
    private static final RequestInformationCrypts requestInformationCrypts = new RequestInformationCrypts();
    private static final String BTC_NAME = Crypt.BTC.getName();
    private static final String ETH_NAME = Crypt.ETH.getName();
    private static final String BNB_NAME = Crypt.BNB.getName();
    private static final String UNI_NAME = Crypt.UNI.getName();
    private static final String DOT_NAME = Crypt.DOT.getName();
    private static final String SOL_NAME = Crypt.SOL.getName();


    private static final String MESSAGE_EVENT = "Событие зафиксировано! Понижение стоимости ";

    public DumpingCryptsHandler(TelegramBot telegramBot) {
        super(telegramBot);
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        Command command = parsedCommand.getCommand();
        switch (command) {

            case DUMPING:
                telegramBot.sendQueue.add(sendMessageStatusCommand(chatId));
                telegramBot.sendQueue.add(sendInlineKeyBoardListCrypts(chatId));
                break;

            case DUMPINGBTC:
                String textBtc = parsedCommand.getText();
                if ("".equals(textBtc)) {
                    telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText(Dumping.HELP.getMessage() + "/dumpingBTC 1").enableMarkdown(true));
                }
                int percentBtc = 0;
                try {
                    if (Integer.parseInt(textBtc) >= 1) {
                        if (Integer.parseInt(textBtc) < 100) {
                            percentBtc = Integer.parseInt(textBtc.trim());
                        } else {
                            telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText(Dumping.HELP.getMessage() + "/dumpingBTC 1").enableMarkdown(true));
                        }
                    } else {
                        telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText(Dumping.HELP.getMessage() + "/dumpingBTC 1").enableMarkdown(true));
                    }
                } catch (NumberFormatException e) {
                    LOGGER.error("NumberFormatException command DUMPINGBTC");
                }
                if (percentBtc > 0) {
                    telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText((Dumping.EVENT.getMessage() + "*BTC*" + " на " + percentBtc + "%")).enableMarkdown(true));
                    Thread thread = new Thread(new DumpingCrypts(telegramBot, chatId, "BTC", percentBtc));
                    thread.start();
                }
                break;

            case DUMPINGETH:
                String textEth = parsedCommand.getText();
                if ("".equals(textEth)) {
                    telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText(Dumping.HELP.getMessage() + "/dumpingETH 1").enableMarkdown(true));
                }
                int percentEth = 0;
                try {
                    if (Integer.parseInt(textEth) >= 1) {
                        if (Integer.parseInt(textEth) < 100) {
                            percentEth = Integer.parseInt(textEth.trim());
                        } else {
                            telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText(Dumping.HELP.getMessage() + "/dumpingETH 1").enableMarkdown(true));
                        }
                    } else {
                        telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText(Dumping.HELP.getMessage() + "/dumpingETH 1").enableMarkdown(true));
                    }
                } catch (NumberFormatException e) {
                    LOGGER.error("NumberFormatException command DUMPINGETH");
                }
                if (percentEth > 0) {
                    telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText((Dumping.EVENT.getMessage() + "*ETH*" + " на " + percentEth + "%")).enableMarkdown(true));
                    Thread thread = new Thread(new DumpingCrypts(telegramBot, chatId, "ETH", percentEth));
                    thread.start();
                }
                break;

            case DUMPINGBNB:
                String textBnb = parsedCommand.getText();
                if ("".equals(textBnb)) {
                    telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText(Dumping.HELP.getMessage() + "/dumpingBNB 1").enableMarkdown(true));
                }
                int percentBnb = 0;
                try {
                    if (Integer.parseInt(textBnb) >= 1) {
                        if (Integer.parseInt(textBnb) < 100) {
                            percentBnb = Integer.parseInt(textBnb.trim());
                        } else {
                            telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText(Dumping.HELP.getMessage() + "/dumpingBNB 1").enableMarkdown(true));
                        }
                    } else {
                        telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText(Dumping.HELP.getMessage() + "/dumpingBNB 1").enableMarkdown(true));
                    }
                } catch (NumberFormatException e) {
                    LOGGER.error("NumberFormatException command DUMPINGBNB");
                }
                if (percentBnb > 0) {
                    telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText((Dumping.EVENT.getMessage() + "*BNB*" + " на " + percentBnb + "%")).enableMarkdown(true));
                    Thread thread = new Thread(new DumpingCrypts(telegramBot, chatId, "BNB", percentBnb));
                    thread.start();
                }
                break;

            case DUMPINGUNI:
                String textUni = parsedCommand.getText();
                if ("".equals(textUni)) {
                    telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText(Dumping.HELP.getMessage() + "/dumpingUNI 1").enableMarkdown(true));
                }
                int percentUni = 0;
                try {
                    if (Integer.parseInt(textUni) >= 1) {
                        if (Integer.parseInt(textUni) < 100) {
                            percentUni = Integer.parseInt(textUni.trim());
                        } else {
                            telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText(Dumping.HELP.getMessage() + "/dumpingUNI 1").enableMarkdown(true));
                        }
                    } else {
                        telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText(Dumping.HELP.getMessage() + "/dumpingUNI 1").enableMarkdown(true));
                    }
                } catch (NumberFormatException e) {
                    LOGGER.error("NumberFormatException command DUMPINGUNI");
                }
                if (percentUni > 0) {
                    telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText((Dumping.EVENT.getMessage() + "*UNI*" + " на " + percentUni + "%")).enableMarkdown(true));
                    Thread thread = new Thread(new DumpingCrypts(telegramBot, chatId, "UNI", percentUni));
                    thread.start();
                }
                break;

            case DUMPINGDOT:
                String textDot = parsedCommand.getText();
                if ("".equals(textDot)) {
                    telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText(Dumping.HELP.getMessage() + "/dumpingDOT 1").enableMarkdown(true));
                }
                int percentDot = 0;
                try {
                    if (Integer.parseInt(textDot) >= 1) {
                        if (Integer.parseInt(textDot) < 100) {
                            percentDot = Integer.parseInt(textDot.trim());
                        } else {
                            telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText(Dumping.HELP.getMessage() + "/dumpingDOT 1").enableMarkdown(true));
                        }
                    } else {
                        telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText(Dumping.HELP.getMessage() + "/dumpingDOT 1").enableMarkdown(true));
                    }
                } catch (NumberFormatException e) {
                    LOGGER.error("NumberFormatException command DUMPINGDOT");
                }
                if (percentDot > 0) {
                    telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText((Dumping.EVENT.getMessage() + "*DOT*" + " на " + percentDot + "%")).enableMarkdown(true));
                    Thread thread = new Thread(new DumpingCrypts(telegramBot, chatId, "DOT", percentDot));
                    thread.start();
                }
                break;

            case DUMPINGSOL:
                String textSol = parsedCommand.getText();
                if ("".equals(textSol)) {
                    telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText(Dumping.HELP.getMessage() + "/dumpingSOL 1").enableMarkdown(true));
                }
                int percentSol = 0;
                try {
                    if (Integer.parseInt(textSol) >= 1) {
                        if (Integer.parseInt(textSol) < 100) {
                            percentSol = Integer.parseInt(textSol.trim());
                        } else {
                            telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText(Dumping.HELP.getMessage() + "/dumpingSOL 1").enableMarkdown(true));
                        }
                    } else {
                        telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText(Dumping.HELP.getMessage() + "/dumpingSOL 1").enableMarkdown(true));
                    }
                } catch (NumberFormatException e) {
                    LOGGER.error("NumberFormatException command DUMPINGSOL");
                }
                if (percentSol > 0) {
                    telegramBot.sendQueue.add(new SendMessage().setChatId(chatId).setText((Dumping.EVENT.getMessage() + "*SOL*" + " на " + percentSol + "%")).enableMarkdown(true));
                    Thread thread = new Thread(new DumpingCrypts(telegramBot, chatId, "SOL", percentSol));
                    thread.start();
                }
                break;
        }
        return "";
    }

    private static SendMessage sendMessageStatusCommand(String chatId) {
        return new SendMessage().setChatId(chatId)
                .setText(IconEmoji.HOURGLASS.get() + "Список криптовалют формируется, пожалуйста, подождите");
    }

    private static SendMessage sendInlineKeyBoardListCrypts(String chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();


        String bitcoin = IconEmoji.POINTER_RIGHT.get() + " Используй команду /dumpingBTC x";
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(BTC_NAME).setCallbackData(bitcoin));

        String ethereum = IconEmoji.POINTER_RIGHT.get() + " Используй команду /dumpingETH x";
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(ETH_NAME).setCallbackData(ethereum));

        String binance = IconEmoji.POINTER_RIGHT.get() + " Используй команду /dumpingBNB x";
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(BNB_NAME).setCallbackData(binance));

        String uniswap = IconEmoji.POINTER_RIGHT.get() + " Используй команду /dumpingUNI x";
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(UNI_NAME).setCallbackData(uniswap));

        String polkadot = IconEmoji.POINTER_RIGHT.get() + " Используй команду /dumpingDOT x";
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(DOT_NAME).setCallbackData(polkadot));

        String solana = IconEmoji.POINTER_RIGHT.get() + " Используй команду /dumpingSOL x";
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(SOL_NAME).setCallbackData(solana));

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId)
                .setText(IconEmoji.BOARD.get() + "Выбери криптовалюту, чтобы задать событие для нее")
                .setReplyMarkup(inlineKeyboardMarkup);
    }
}
