package com.hch.hooney.nodejs_upload_image.OkHttp;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by qewqs on 2018-01-16.
 */

public class Get {
    private static final String TAG = "OkHTTP Get Method";
    private String URL;
    private OkHttpClient client;

    public Get(String URL) {
        this.URL = URL;
        this.client = new OkHttpClient();
        Log.d(TAG, URL);
    }

    public Get(String URL, OkHttpClient client) {
        this.URL = URL;
        this.client = client;
    }

    public String send() throws IOException {
        Request request = new Request.Builder()
                .url(URL)
                .build();
        Response response = client.newCall(request).execute();
        Log.d(TAG, "Send Ok...");
        return response.body().string();
    }
}
