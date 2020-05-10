package com.example.lifescheduler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpRequest {
    public String request(String url){
        HttpURLConnection connection;
        BufferedReader reader;
        try {
            connection = (HttpURLConnection)(new URL(url)).openConnection();
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer sb = new StringBuffer();
            String linie = "";
            while ((linie = reader.readLine()) != null) {
                sb.append(linie);
            }

            inputStream.close();
            reader.close();
            connection.disconnect();

            String result = sb.toString();

            return result;
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
