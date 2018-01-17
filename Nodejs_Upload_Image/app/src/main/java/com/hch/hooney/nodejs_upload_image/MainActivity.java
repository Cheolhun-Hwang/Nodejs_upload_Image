package com.hch.hooney.nodejs_upload_image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.hch.hooney.nodejs_upload_image.Image.ImageCTRL;
import com.hch.hooney.nodejs_upload_image.OkHttp.Post;
import com.hch.hooney.nodejs_upload_image.OkHttp.Post_Image;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public final static int SIGNAL_toGallery = 4004;
    private Button upload;
    private Button test;
    private ImageView image;

    private Thread t_upload;
    private Bitmap bitmap;
    private String res;
    private String filePath;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!(init())){
            Log.e(TAG, "Init Method Error");
            Toast.makeText(getApplicationContext(), "Init Error", Toast.LENGTH_SHORT).show();
        }

        if(!(event())){
            Log.e(TAG, "Event Method Error");
            Toast.makeText(getApplicationContext(), "Event Error", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean init(){
        try{
            //resource
            upload = (Button) findViewById(R.id.upload);
            test = (Button) findViewById(R.id.test);
            image = (ImageView) findViewById(R.id.loadimage);

            //variable
            handler = new Handler();

            test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("file", "test.jpg");
                                Post post = new Post("http://192.168.25.3:65001/images/file");
                                final String res = post.send(jsonObject.toString());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "RES : " + res, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();
                }
            });
            t_upload = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{

                        Post_Image post_image = new Post_Image("http://192.168.25.3:65001/images/upload?fname=test.jpg", handler, getApplicationContext());
                        post_image.upload(bitmap);

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private boolean event(){
        try{
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                    intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, SIGNAL_toGallery);
                }
            });
            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    t_upload.start();
                }
            });

            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case SIGNAL_toGallery:
                if(resultCode == RESULT_OK){
                    Log.d(TAG, data.getData().toString());
                    filePath = data.getData().toString();
                    ImageCTRL imageCTRL = new ImageCTRL(getApplicationContext());
                    this.bitmap = imageCTRL.setImage(image, data.getDataString(), 600);
                }
                break;
            default:
                break;
        }
    }

}
