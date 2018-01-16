package com.hch.hooney.nodejs_upload_image;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.hch.hooney.nodejs_upload_image.Image.ImageCTRL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public final static int SIGNAL_toGallery = 4004;
    private Button upload;
    private ImageView image;

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
            image = (ImageView) findViewById(R.id.loadimage);


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
                    new ImageCTRL(getApplicationContext()).setImage(image, data.toString(), 500);
                }
                break;
        }
    }
}
