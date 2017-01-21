package com.conuhax.clarifind.Utilities;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by michal wozniak on 1/21/2017.
 */

public class ImageManager {


    /**
     * Load image from asset folder
     *
     *  eg : Bitmap image = ImageManager.getBitmapFromAsset(this,"hammer.jpg");
     *
     * @param context
     * @param filePath
     * @return
     */
    public static Bitmap getBitmapFromAsset(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            // handle exception
        }
        Log.d("getBitmapFromAsset", "processed");
        return bitmap;
    }
}
