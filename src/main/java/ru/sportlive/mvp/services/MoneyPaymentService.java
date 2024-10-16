package ru.sportlive.mvp.services;

import okhttp3.*;
import org.json.JSONObject;
import org.springframework.stereotype.Service;


import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;

@Service
public class MoneyPaymentService {

    private static final String ACCESS_TOKEN = System.getenv("ACCESS_TOKEN");
    private static final String SHA1 = System.getenv("SHA1");

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

    public String getDetailedPaymentHistory(String operationId) throws Exception {

        // URL для получения истории операций с дополнительными деталями
        String API_URL = "https://yoomoney.ru/api/operation-details";
        // Создаем URL с параметром details=true для получения полной информации
        URL url = new URL(API_URL + "?operation_id="+ operationId);
        // Открываем соединение
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // Устанавливаем метод запроса - GET
        conn.setRequestMethod("GET");
        // Добавляем заголовок авторизации с токеном доступа
        conn.setRequestProperty("Authorization", "Bearer " + ACCESS_TOKEN);
        conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        // Читаем ответ от API
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        // Читаем строку за строкой и добавляем в буфер
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        // Закрываем поток
        in.close();

        // Возвращаем полученные данные в виде строки
        return content.toString();
    }
    public String createPaymentLink(String receiver, String amount, String label, String successURL) throws IOException {
        String payUrl = "https://yoomoney.ru/quickpay/confirm?receiver=" + receiver
                + "&quickpay-form=shop"  // Используй shop, если хочешь форму для покупок, либо 'button', как в примере
                + "&paymentType=AC"  // Платеж с карты, можно заменить на другие значения: PC, MC и т.д.
                + "&amount_due=" + amount
                + "&label=" + label
                + "&successURL=" + successURL;  // URL, куда перенаправят после успешной оплаты
        return "{\"url\" : "+ payUrl +" }";
    }


    public String getNotification() throws Exception {

        String API_URL = "https://yoomoney.ru/api/";
        URL url = new URL(API_URL);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization","Bearer "+ ACCESS_TOKEN);
        connection.setRequestProperty("Content-type","application/x-www-form-urlencoded");

        String urlParams =
                "notification_type="+
                "&operation_id="+
                "&amount="+
                "&currency="+
                "&datetime="+
                "&sender" +
                "&codepro" +
                "&notification_secret=" +SHA1+
                "&label=";


        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            wr.writeBytes(urlParams);
            wr.flush();
        }


        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();

        return content.toString();
    }

//    public String createInvoice(String amount, String recipient,String comment) throws Exception {
//
//        // URL для создания счета
//        String API_URL = "https://yoomoney.ru/api/request-payment";
//
//        // Открываем соединение
//        URL url = new URL(API_URL);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//        // Устанавливаем метод запроса - POST
//        conn.setRequestMethod("POST");
//        conn.setRequestProperty("Authorization", "Bearer " + ACCESS_TOKEN);
//        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//        conn.setDoOutput(true);
//
//        // Данные для отправки: получатель, сумма и описание
//        String urlParameters = "pattern_id=p2p&to=" + recipient + "&amount=" + amount+"&message=пополнения личного кабинета"+"&comment="+comment;
//
//        // Отправляем запрос
//        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
//            wr.writeBytes(urlParameters);
//            wr.flush();
//        }
//
//        // Читаем ответ от API
//        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        String inputLine;
//        StringBuilder content = new StringBuilder();
//
//        while ((inputLine = in.readLine()) != null) {
//            content.append(inputLine);
//        }
//
//        // Закрываем поток
//        in.close();
//
//        // Возвращаем полученные данные (например, ID счета и статус)
//        return content.toString();
//    }

    public String paymentResponse(String reguest_id) throws Exception {

        String API_URL = "https://yoomoney.ru/api/process-payment";
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorisation",ACCESS_TOKEN);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String inputLine;
        StringBuilder builder = new StringBuilder();

        while ((inputLine = reader.readLine()) != null) {
            builder.append(inputLine);
        }
        reader.close();

        return builder.toString();
    }

}



