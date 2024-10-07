package ru.sportlive.mvp.services;

import okhttp3.*;
import org.json.JSONObject;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class MoneyPaymentService {

    private static final String ACCESS_TOKEN = System.getenv("ACCESS_TOKEN");
    public String getPaymentHistory() throws Exception {

        String API_URL = "https://yoomoney.ru/api/operation-history";
        URL url = new URL(API_URL + "?records=10");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + ACCESS_TOKEN);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        return content.toString();
    }


}



