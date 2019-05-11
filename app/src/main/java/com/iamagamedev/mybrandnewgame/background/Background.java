package com.iamagamedev.mybrandnewgame.background;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**
 * Created by Михан on 31.05.2017.
 */

public class Background {

    public Bitmap bitmap;
    public Bitmap bitmapReversed;

    public int width;
    public int height;

    public boolean reversedFirst;
    public int xClip;
    public float y;
    public float endY;
    public int z;

    public float speed;
    boolean isParallax;

    public Background(Context context, int yPixelsPerMetre,
                      int screenWidth, BackgroundData data){
        int resID = context.getResources().getIdentifier(data.bitmapName,
                "drawable", context.getPackageName());

        bitmap = BitmapFactory.decodeResource(context.getResources(), resID);

        reversedFirst = false;
        xClip = 0;
        y = data.startY;
        endY = data.endY;
        z = data.layer;
        isParallax = data.isParallax;
        speed = data.speed;

        bitmap = Bitmap.createScaledBitmap(bitmap, screenWidth,
                data.height * yPixelsPerMetre, true);
        width = bitmap.getWidth();
        height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.setScale(-1, 1);
        bitmapReversed = Bitmap.createBitmap(bitmap, 0, 0,
                width, height, matrix, true);
    }
}
