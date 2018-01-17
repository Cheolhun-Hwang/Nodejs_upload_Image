package com.hch.hooney.nodejs_upload_image.Image;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.hch.hooney.nodejs_upload_image.R;

import java.io.IOException;

/**
 * Created by hooney on 2018. 1. 11..
 */

public class ImageCTRL {
    private Context context;

    public ImageCTRL(Context context) {
        this.context = context;
    }

    public Bitmap setImage(ImageView targetImageview, String targetURI, int maxheight){
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(targetURI));

            //리사이즈
            int height = bitmap.getHeight();
            int width = bitmap.getWidth();

            Bitmap resized = null;

            //높이가 800이상 일때
            while (height > maxheight) {
                resized = Bitmap.createScaledBitmap(bitmap, (width * maxheight) / height,
                        maxheight, true);
                height = resized.getHeight();
                width = resized.getWidth();
            }

            //배치해놓은 ImageView에 set
            targetImageview.setImageBitmap(resized);

            //imageView.setImageBitmap(bitmap)

            return resized;
        } catch (IOException e) {
            e.printStackTrace();
            targetImageview.setImageResource(R.drawable.ic_insert_photo_black_24dp);
        }
        return null;
    }
}
