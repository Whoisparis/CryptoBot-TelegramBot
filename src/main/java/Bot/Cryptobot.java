package Bot;

import lombok.Data;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import service.CryptoCurrencyService;

import java.util.ArrayList;
import java.util.List;

public class Cryptobot extends TelegramLongPollingBot {
    private static final int STATE_WAITING_COMMAND = 1;
    private static final int STATE_WAITING_INPUT = 2;

    private int currentState = STATE_WAITING_COMMAND;
    @Override
    public String getBotToken() {
        return "6533106490:AAH3MONhDifAI8vRhz-LzrB1tB7hXApJgJU";
    }

    @Override
    public String getBotUsername() {
        return "YourCryptoMasterCheckerBot";
    }

    CryptoCurrencyService cryptoCurrencyService = new CryptoCurrencyService();
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            String text = message.getText();
            long chatId = message.getChatId();

            if (text.equals("/start")) {
                // Создание клавиатуры с кнопками
                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                keyboardMarkup.setSelective(true);
                keyboardMarkup.setResizeKeyboard(true);
                keyboardMarkup.setOneTimeKeyboard(false);

                List<KeyboardRow> KeyboardRows = new ArrayList<>();
                KeyboardRow row = new KeyboardRow();
                KeyboardRow row1 = new KeyboardRow();
                row.add("\uD83D\uDCB0 btc");
                row.add("gg");
                row1.add("ff");
                KeyboardRows.add(row);
                KeyboardRows.add(row1);
                keyboardMarkup.setKeyboard(KeyboardRows);
                new SendMessage();
                SendMessage sendMessage = SendMessage.builder()
                        .chatId(chatId)
                        .text("Привет! Выберите кнопку из меню.")
                        .replyMarkup(keyboardMarkup)
                        .build();

                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (text.equals("\uD83D\uDCB0 btc")) {
                getBtcPrice(chatId);
            } else if (text.equals("ticket")) {
                getTicketPrice(chatId,message);
            }
        }
    }
    public void getBtcPrice(long chatId) {
        cryptoCurrencyService.setBtcPrice();
        new SendMessage();

        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text("BTC price: " + cryptoCurrencyService.getBtcPrice() + " $")
                .build();
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void getTicketPrice(long chatId, Message msg) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text("Enter cryptocurrency ticket")
                .build();
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        if (msg.hasText()){
            String txt = String.valueOf(msg);
            cryptoCurrencyService.setPriceYourCurrency(txt);
            SendMessage sndMessage =  SendMessage.builder()
                    .chatId(chatId)
                    .text(txt + cryptoCurrencyService.getPriceYourCurrency())
                    .build();
            try {
                execute(sndMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }

    }

       /* if (message != null && message.hasText()) {
            String text = message.getText();
            System.out.println(message.getFrom().getUserName() + ": " + text);
            String response = "Вы написали: " + text;

            SendMessage sendMessage = SendMessage.builder()
                    .chatId(message.getChatId())
                    .text(response)
                    .build();
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }*/

    }



