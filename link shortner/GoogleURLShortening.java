package com.play.ground.google;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

public class GoogleURLShortening {

    private static final String GOOGLE_URL_SHORT_API = "https://www.googleapis.com/urlshortener/v1/url";
    private static final String GOOGLE_API_KEY = "<google-app-api-key>";

    public static void main(String[] args) {
        String longURL = "https://developers.google.com/url-shortener/v1/getting_started#expand";

        String googleShortURL = shortenUrl(longURL);
        String googleBackToLongURL = getActualURLAgainstGoogleShortURL(googleShortURL);

        System.out.println("Short URL by Google API: " + googleShortURL);
        System.out.println("Long URL against Short URL by Google API: " + googleBackToLongURL);
    }

    public static String shortenUrl(String longUrl) {
        if (longUrl == null || longUrl.isEmpty()) {
            return longUrl;
        }

        longUrl = ensureUrlProtocol(longUrl);

        String json = "{\"longUrl\": \"" + longUrl + "\"}";
        String apiURL = GOOGLE_URL_SHORT_API + "?key=" + GOOGLE_API_KEY;

        return executeHttpPost(apiURL, json, "id");
    }

    public static String getActualURLAgainstGoogleShortURL(String shortUrl) {
        if (shortUrl == null || shortUrl.isEmpty()) {
            return shortUrl;
        }

        shortUrl = ensureUrlProtocol(shortUrl);

        String apiURL = GOOGLE_URL_SHORT_API + "?key=" + GOOGLE_API_KEY + "&shortUrl=" + shortUrl;

        return executeHttpGet(apiURL, "longUrl");
    }

    private static String ensureUrlProtocol(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return "http://" + url;
        }
        return url;
    }

    private static String executeHttpPost(String apiURL, String json, String responseKey) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost postRequest = new HttpPost(apiURL);
            postRequest.setHeader("Content-Type", "application/json");
            postRequest.setEntity(new StringEntity(json, "UTF-8"));

            HttpResponse response = httpClient.execute(postRequest);
            String responseText = EntityUtils.toString(response.getEntity());

            return parseResponse(responseText, responseKey);
        } catch (IOException e) {
            return "error";
        }
    }

    private static String executeHttpGet(String apiURL, String responseKey) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet getRequest = new HttpGet(apiURL);

            HttpResponse response = httpClient.execute(getRequest);
            String responseText = EntityUtils.toString(response.getEntity());

            return parseResponse(responseText, responseKey);
        } catch (IOException e) {
            return "error";
        }
    }

    private static String parseResponse(String responseText, String responseKey) {
        Gson gson = new Gson();
        HashMap<String, String> res = gson.fromJson(responseText, HashMap.class);
        return res.get(responseKey);
    }
}
