package com.whoisparis.yourcryptobot.Bot;

import com.whoisparis.yourcryptobot.Config.BotConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.whoisparis.yourcryptobot.service.CryptoCurrencyService;

import java.util.ArrayList;
import java.util.List;


@Component
public class Cryptobot extends TelegramLongPollingBot {
    private static final int STATE_WAITING_COMMAND = 1;
    private static final int STATE_WAITING_INPUT = 2;
    private int currentState = STATE_WAITING_COMMAND;

    final BotConfig botConfig;
    final CryptoCurrencyService cryptoCurrencyService;

    @Autowired
    public Cryptobot(CryptoCurrencyService cryptoCurrencyService, BotConfig botConfig) {
        this.botConfig = botConfig;
        this.cryptoCurrencyService = cryptoCurrencyService;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            String chatId = String.valueOf(message.getChatId());
            String text = message.getText();
            if (currentState == STATE_WAITING_COMMAND) {
                // Обработка состояния ожидания команды
                if (text.equals("/start")) {
                    sendStartMessage(chatId);
                } else if (text.equals("BTC")) {
                    cryptoCurrencyService.setBtcPrice();
                    Integer btcPrice = cryptoCurrencyService.getBtcPrice();
                    System.out.println(btcPrice);
                    sendMessageTo("BTC PRICE: " + btcPrice + " $",chatId);
                } else if (text.equals("TICKET PRICE")) {
                    sendMessageTo("Write crypto ticket", chatId);
                    currentState = STATE_WAITING_INPUT;
                }
            } else if (currentState == STATE_WAITING_INPUT && !(text.equals("esc"))) {
                cryptoCurrencyService.setPriceYourCurrency(text.toUpperCase());
                Double ticketPrice = cryptoCurrencyService.getPriceYourCurrency();
                System.out.println(ticketPrice);
                sendMessageTo(text + " price: " + ticketPrice + " $", chatId);
            } else if (text.equals("esc")) {
                currentState = STATE_WAITING_COMMAND;
            }
        }
    }

    public void sendStartMessage(String chatId) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> KeyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("BTC");
        row.add("TICKET PRICE");
        KeyboardRows.add(row);
        keyboardMarkup.setKeyboard(KeyboardRows);
        try {
            execute(new SendMessage().builder()
                    .chatId(chatId)
                    .text("Hello! Check menu and try me!")
                    .replyMarkup(keyboardMarkup)
                    .build());
        } catch (TelegramApiException e) {
                e.printStackTrace();
        }
    }

    public void sendMessageTo(String message, String chatId) {
        try {
            execute(new SendMessage(chatId,message));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}




