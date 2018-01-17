package com.hch.hooney.nodejs_upload_image.OkHttp;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by qewqs on 2018-01-16.
 */

public class Delete {
    private static final String TAG = "OkHTTP Delete Method";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private String URL;
    private OkHttpClient client;

    public Delete(String URL) {
        this.URL = URL;
        this.client = new OkHttpClient();
    }

    public Delete(String URL, OkHttpClient client) {
        this.URL = URL;
        this.client = client;
    }

    public String send(String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(this.URL)
                .delete(body)
                .build();
        Response response = client.newCall(request).execute();
        Log.d(TAG, "Send Ok...");
        return response.body().string();
    }

    public String send() throws IOException {
        Request request = new Request.Builder()
                .url(this.URL)
                .delete()
                .build();
        Response response = client.newCall(request).execute();
        Log.d(TAG, "Send Ok...");
        return response.body().string();
    }
}
