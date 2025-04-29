package ru.sportlive.mvp.services;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@Service
@Transactional
public class TGService {

    public String sendMessage(String chatId, String message) throws IOException {
        if (chatId == null){
            return null;
        }
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("BOT_TOKEN");

        String API_URL = "https://api.telegram.org/bot"+token+"/sendMessage?chat_id="+chatId+"&text="+message+"&parse_mode=HTML";
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type","application/x-www-form-urlencoded");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        System.out.println(content);
        return content.toString();
    }


    public String sendMessage(String chatId, String message,String urlButton, String buttonName) throws IOException {
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("BOT_TOKEN");
        String url2 = "&reply_markup={\"inline_keyboard\":[[{\"text\":\""+buttonName+"\",\"web_app\":{\"url\":\""+urlButton+"\"}}]]}";
        String API_URL = "https://api.telegram.org/bot"+token+"/sendMessage?chat_id="+chatId+"&text="+message+"&parse_mode=HTML"+url2;
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        System.out.println(content);
        return content.toString();
    }
}
