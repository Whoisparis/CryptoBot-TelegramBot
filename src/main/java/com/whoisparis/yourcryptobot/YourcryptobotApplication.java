package com.whoisparis.yourcryptobot;

import com.whoisparis.yourcryptobot.Bot.Cryptobot;
import com.whoisparis.yourcryptobot.Config.BotConfig;
import com.whoisparis.yourcryptobot.service.CryptoCurrencyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

@SpringBootApplication
public class YourcryptobotApplication {

    public static void main(String[] args) {
        SpringApplication.run(YourcryptobotApplication.class, args);

    }
}

