package com.whoisparis.yourcryptobot.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.whoisparis.yourcryptobot.Config.CryptoConfig;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class CryptoCurrencyService {

    protected final CryptoConfig cryptoConfig;

    public CryptoCurrencyService(CryptoConfig cryptoConfig) {
        this.cryptoConfig = cryptoConfig;
    }

    public String getApiKey() {
        return cryptoConfig.getApiKey();
    }

    private Integer btcPrice;
    private Double ticketPrice;


    public Integer getBtcPrice() {
        return btcPrice;
    }

    public void setBtcPrice() {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest")
                .newBuilder();
        urlBuilder.addQueryParameter("symbol", "BTC");

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-CMC_PRO_API_KEY", getApiKey())
                .addHeader("Accept", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            System.out.println(responseBody);
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(responseBody).getAsJsonObject();
            Double bitcoinValue = jsonObject.get("data").getAsJsonObject()
                    .get("BTC").getAsJsonObject()
                    .get("quote").getAsJsonObject()
                    .get("USD").getAsJsonObject()
                    .get("price").getAsDouble();

            btcPrice = bitcoinValue.intValue();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPriceYourCurrency(String ticket) {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest")
                .newBuilder();
        urlBuilder.addQueryParameter("symbol", ticket);

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-CMC_PRO_API_KEY", getApiKey())
                .addHeader("Accept", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            System.out.println(responseBody);
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(responseBody).getAsJsonObject();
            Double ticketValue = jsonObject.get("data").getAsJsonObject()
                    .get(ticket).getAsJsonObject()
                    .get("quote").getAsJsonObject()
                    .get("USD").getAsJsonObject()
                    .get("price").getAsDouble();

            ticketPrice = ticketValue;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Double getPriceYourCurrency() {
        return ticketPrice;
    }
}
