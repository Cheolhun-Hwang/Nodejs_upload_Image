package com.hch.hooney.nodejs_upload_image.OkHttp;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by qewqs on 2018-01-16.
 */

public class Post_Image {
    private final String TAG = "Post_Image ...";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    private String Url;

    private Handler handler;
    private Context mContext;

    public Post_Image(String url, Handler h, Context context) {
        Url = url;
        this.handler = h;
        this.mContext = context;
    }

    public void upload(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        final byte[] bitmapdata = stream.toByteArray();

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"gimages\";"),
                        RequestBody.create(MEDIA_TYPE_PNG, bitmapdata))
                .build();
        final Request request = new Request.Builder()
                .url(Url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.e(TAG, e.toString());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String message = response.toString();
                Log.i(TAG, message);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
