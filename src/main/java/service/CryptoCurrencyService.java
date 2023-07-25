package service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CryptoCurrencyService {
    private Integer btcPrice;
    private Double ticketPrice;
    private String apiKey = "0738baf7-b707-446b-9336-f8cf853a1dda";

    public Integer getBtcPrice() {
        return btcPrice;
    }

    public void setBtcPrice() {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest")
                .newBuilder();
        urlBuilder.addQueryParameter("symbol", "btc");

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-CMC_PRO_API_KEY", apiKey)
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
        urlBuilder.addQueryParameter("symbol", "ticket");

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-CMC_PRO_API_KEY", apiKey)
                .addHeader("Accept", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            System.out.println(responseBody);
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(responseBody).getAsJsonObject();
            Double cryptoPrice = jsonObject.get("data").getAsJsonObject()
                    .get("ticket").getAsJsonObject()
                    .get("quote").getAsJsonObject()
                    .get("USD").getAsJsonObject()
                    .get("price").getAsDouble();

            ticketPrice = cryptoPrice;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Double getPriceYourCurrency() {
        return ticketPrice;
    }
}
