package com.example.lawrence.tracegame;



import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Jack on 6/8/2017.
 */

public class Button {
    public static final int CENTER=-1;
    public static final int LEFTALIGN=-2;
    public static final int RIGHTALIGN=-3;

    private static int ScreenX;
    private static int ScreenY;

    public int x = 0;
    public int y = 0;
    public int height = 0;
    public int width = 0;
    public int textSize = 50;
    public String textB = "";
    public int color = Color.RED;
    public int background = Color.GRAY;
    Rect textbox = new Rect();
    Paint paint = new Paint();
    int alpha=255;
    boolean isImage = false;
    DrawImage img;

    public Button(String text, int y, int size){
        this.y=y;
        textSize=size;
        textB=text;
        paint.setTextSize(textSize);
        Rect bounds= new Rect();
        paint.getTextBounds(text,0,textB.length(),bounds);
        System.out.println(bounds.width());
        this.height=bounds.height();
        this.width=bounds.width();
        setX(-1);
        textbox = new Rect(x-15, y - height-15, x + width+15, y+15);
    }
    public Button(String text, int x, int y, int size){

        textSize=size;
        textB=text;
        paint.setTextSize(textSize);
        Rect bounds= new Rect();
        paint.getTextBounds(text,0,textB.length(),bounds);
        System.out.println(bounds.width());
        this.height=bounds.height();
        this.width=bounds.width();
        this.x = x-width/2;
        this.y = y-height/2;
        textbox = new Rect(x-10, y+10, x + width+10, y - height-10);
    }

    public Button(String text, int x, int y, int height, int width) {
        textB = text;
        this.x = x-width/2;
        this.y = y-height/2;
        this.height = height;
        this.width = width;
        textbox = new Rect(x-width/2, y-height/2, x + width/2, y+height/2);
    }
    public Button(int imageFile, int x, int y, int height, int width) {
        textB = "";
        this.x = x-width/2;
        this.y = y-height/2;
        this.height = height;
        this.width = width;
        textbox = new Rect(x-width/2, y-height/2, x+width/2, y + height/2);
        img = new DrawImage(GamePanel.context, imageFile,width,height,x-width/2,y-height/2);
        isImage = true;
    }
    public Button(String text, int x, int y, int height, int width, int textSize) {
        textB = text;
        this.x = x-width/2;
        this.y = y-height/2;
        this.height = height;
        this.width = width;
        textbox = new Rect(x, y, x + width, y - height);
        this.textSize = textSize;
    }

    public Button(String text, int x, int y, int height, int width, int size, int textColor) {
        this.textB = text;
        this.x = x-width/2;
        this.y = y-height/2;
        this.height = height;
        this.width = width;
        textbox = new Rect(x, y, x + width, y - height);
        this.textSize = size;
        color = textColor;
    }

    public Button(String text, int x, int y, int height, int width, int size, int textColor, int backgroundColor) {
        text = text;
        textbox = new Rect(x, y, x + width, y - height);
        textSize = size;
        color = textColor;
        background = backgroundColor;
    }

    public static void setScreenDim(int x, int y){
        ScreenX=x;
        ScreenY=y;
    }

    public void setX(int pos){
        if(pos==-1){
            x=(ScreenX-width)/2;
        }
        if(pos==-2){
            x=20;
        }
        if(pos==-3){
            x=ScreenX-width-20;
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String getText() {
        return this.textB;
    }

    public void setText(String text) {
        text = text;
    }

    public void setTextSize(int size) {
        this.textSize = size;
    }

    public void setTextColor(int color) {
        color = color;
    }

    public void setBackground(int color) {
        background = color;
    }

    public int getWidth() {
        return this.getWidth();
    }

    public int getHeight() {
        return this.height;
    }

    public Rect getRect() {
        return this.textbox;
    }

    public void setAlpha(int alpha){
        this.alpha=alpha;
    }

    public void draw(Canvas canvas) {
        if(isImage) {
            img.draw(canvas);
        }
        else {
            paint.setColor(this.background);
            paint.setAlpha(alpha);
            canvas.drawRect(textbox, paint);

            paint.setColor(this.color);
            paint.setAlpha(alpha);
            paint.setTextSize(this.textSize);
            canvas.drawText(this.textB, this.x, this.y+height/2, paint);
        }
    }

    public boolean overlaps(Point pt) {
        return pt.x < this.textbox.right && pt.x > this.textbox.left && pt.y > this.textbox.top && pt.y < this.textbox.bottom;
    }
}




