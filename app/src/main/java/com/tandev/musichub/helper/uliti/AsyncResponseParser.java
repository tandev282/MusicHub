package com.tandev.musichub.helper.uliti;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;

public class AsyncResponseParser {
    private static final ExecutorService executor = Executors.newFixedThreadPool(2);
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    public interface Parser<T> {
        T parse(String json) throws Exception;
    }

    public interface SuccessCallback<T> {
        void onSuccess(T data);
    }

    public interface ErrorCallback {
        void onError(Exception e);
    }

    public static <T> void parse(ResponseBody responseBody, Parser<T> parser, SuccessCallback<T> successCallback, ErrorCallback errorCallback) {
        executor.execute(() -> {
            try {
                T data = parser.parse(responseBody.string());
                mainHandler.post(() -> successCallback.onSuccess(data));
            } catch (Exception e) {
                mainHandler.post(() -> {
                    if (errorCallback != null) {
                        errorCallback.onError(e);
                    }
                });
            }
        });
    }
}
