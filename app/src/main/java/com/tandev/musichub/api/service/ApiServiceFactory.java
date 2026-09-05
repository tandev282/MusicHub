package com.tandev.musichub.api.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tandev.musichub.api.ApiService;
import com.tandev.musichub.constants.Constants;
import com.tandev.musichub.helper.uliti.AddCookiesInterceptor;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceFactory {
    private static volatile ApiService apiServiceInstance;
    private static final Object LOCK = new Object();
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    private static String getCookie() throws IOException {
        URL url = new URL(Constants.BASE_URL_MOBILE);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setRequestMethod("GET");
            Map<String, List<String>> headers = connection.getHeaderFields();
            StringBuilder cookie = new StringBuilder();
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                if (entry.getKey() != null && entry.getKey().equalsIgnoreCase("Set-Cookie")) {
                    for (String value : entry.getValue()) {
                        cookie.append(value).append("; ");
                    }
                }
            }
            return cookie.toString();
        } finally {
            connection.disconnect();
        }
    }

    public interface ApiServiceCallback {
        void onServiceCreated(ApiService service);

        void onError(Exception e);
    }

    public static void createServiceAsync(final ApiServiceCallback callback) {
        executor.execute(() -> {
            try {
                ApiService service = getOrCreateService();
                if (callback != null) {
                    callback.onServiceCreated(service);
                }
            } catch (IOException e) {
                if (callback != null) {
                    callback.onError(e);
                }
            }
        });
    }

    private static ApiService getOrCreateService() throws IOException {
        if (apiServiceInstance == null) {
            synchronized (LOCK) {
                if (apiServiceInstance == null) {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .addInterceptor(new AddCookiesInterceptor(getCookie()))
                            .build();

                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd HH:mm:ss")
                            .create();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Constants.BASE_URL_MOBILE)
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

                    apiServiceInstance = retrofit.create(ApiService.class);
                }
            }
        }
        return apiServiceInstance;
    }
}
