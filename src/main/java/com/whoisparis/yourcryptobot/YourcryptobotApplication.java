package com.whoisparis.yourcryptobot;

import Bot.Cryptobot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import service.CryptoCurrencyService;

@SpringBootApplication
public class YourcryptobotApplication {

    public static void main(String[] args) throws TelegramApiException{
        SpringApplication.run(YourcryptobotApplication.class, args);
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        Cryptobot cryptobot = new Cryptobot();
        try {
            botsApi.registerBot(cryptobot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

