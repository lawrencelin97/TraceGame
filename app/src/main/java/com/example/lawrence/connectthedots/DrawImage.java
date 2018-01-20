package com.example.lawrence.connectthedots;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by Jack on 6/23/2017.
 */

public class DrawImage extends View {

    FileOutputStream out;
    Bitmap bmp;
    int drawableFile;
    Paint paint = new Paint();
    int width;
    int height;
    int y;
    int x;
    String fileName;

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        create();
        canvas.drawBitmap(bmp, x, y, paint);
    }

    public DrawImage(Context context, int file) {
        super(context);
        this.drawableFile = file;
        TypedValue value = new TypedValue();
        getResources().getValue(file, value, true);
        fileName = value.string.toString().substring(13, value.string.toString().length());
    }

    public DrawImage(Context context, int file, int width, int height, int x, int y) {
        super(context);
        this.drawableFile = file;
        this.width = width;
        this.height = height;
        TypedValue value = new TypedValue();
        getResources().getValue(file, value, true);
        fileName = value.string.toString().substring(13, value.string.toString().length());
        this.y = y;
        this.x = x;

    }

    public void create() {
        bmp = BitmapFactory.decodeResource(getResources(), drawableFile);
        // reduceResolution();
        bmp = BitmapFactory.decodeResource(getResources(), drawableFile);
        bmp = Bitmap.createScaledBitmap(bmp, width, height, true);
    }


    public void Fade(Canvas canvas, Paint pt) {
        super.draw(canvas);
        create();
        canvas.drawBitmap(bmp, x, y, pt);
    }

    public void scale(double multiplier) {
        this.width = (int) (Math.ceil(multiplier * width));
        this.height = (int) (Math.ceil(multiplier * height));
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(int w, int h) {
        this.width = w;
        this.height = h;
    }

    public void reduceResolution() {

        File f = new File(fileName);
        f.delete();
        f = new File(fileName);
        try {
            out = new FileOutputStream(f);
            bmp.compress(Bitmap.CompressFormat.JPEG, 30, out);
            out.flush();
            out.close();
            System.out.println("compressed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}









