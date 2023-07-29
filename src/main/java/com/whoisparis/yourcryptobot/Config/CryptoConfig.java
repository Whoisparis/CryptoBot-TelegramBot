package com.whoisparis.yourcryptobot.Config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("classpath:application.properties")
public class CryptoConfig {
    @Value("${coinmarketcap.apiKey}")
    String apiKey;
}
