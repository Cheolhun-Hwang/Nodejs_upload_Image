package com.hch.hooney.nodejs_upload_image.HttpURLConnection;

import android.graphics.Bitmap;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hooney on 2018. 1. 11..
 */

public class Post {
    private static final String TAG = "HTTP URL CONNECTION POST...";

    private String URL;

    public Post(String url) {
        this.URL = url;
    }

    public String uploadImage(Bitmap bitmap, String filename) throws IOException{
        String crlf = "\r\n";
        String twoHyphens = "--";
        String boundary =  "*****";

        // request 준비
        HttpURLConnection httpUrlConnection = null;
        URL url = new URL("http://example.com/server.cgi");
        httpUrlConnection = (HttpURLConnection) url.openConnection();
        httpUrlConnection.setUseCaches(false);
        httpUrlConnection.setDoOutput(true);

        httpUrlConnection.setRequestMethod("POST");
        httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
        httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");
        httpUrlConnection.setRequestProperty(
                "Content-Type", "multipart/form-data;boundary=" + boundary);

// content wrapper시작
        DataOutputStream request = new DataOutputStream(
                httpUrlConnection.getOutputStream());

        request.writeBytes(twoHyphens + boundary + crlf);
        request.writeBytes("Content-Disposition: form-data; name=\"" +
                filename + "\";filename=\"" +
                filename + "\"" + crlf);
        request.writeBytes(crlf);

// Bitmap을 ByteBuffer로 전환
        byte[] pixels = new byte[bitmap.getWidth() * bitmap.getHeight()];
        for (int i = 0; i < bitmap.getWidth(); ++i) {
            for (int j = 0; j < bitmap.getHeight(); ++j) {
                //we're interested only in the MSB of the first byte,
                //since the other 3 bytes are identical for B&W images
                pixels[i + j] = (byte) ((bitmap.getPixel(i, j) & 0x80) >> 7);
            }
        }
        request.write(pixels);

// content wrapper종료
        request.writeBytes(crlf);
        request.writeBytes(twoHyphens + boundary +
                twoHyphens + crlf);

// buffer flush
        request.flush();
        request.close();

// Response받기
        InputStream responseStream = new
                BufferedInputStream(httpUrlConnection.getInputStream());
        BufferedReader responseStreamReader =
                new BufferedReader(new InputStreamReader(responseStream));
        String line = "";
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = responseStreamReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        responseStreamReader.close();
        String response = stringBuilder.toString();


//Response stream종료
        responseStream.close();

// connection종료
        httpUrlConnection.disconnect();

        return response;
    }
}
