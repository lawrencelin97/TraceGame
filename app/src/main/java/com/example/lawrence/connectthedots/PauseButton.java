package com.example.lawrence.connectthedots;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by lawrence on 7/27/2017.
 */

public class PauseButton extends Button {

    public PauseButton(int x, int y, int size){
        this.x=x;
        this.y=y;
        this.height=size;
        this.width=size;
        textbox = new Rect(x,y,x+width,y+height);
    }
    @Override
    public void draw(Canvas canvas) {
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(width/10);
        canvas.drawCircle(x+width/2,y+width/2,width/2,paint);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(x+width*2/7,y+width*2/7,x+width*3/7,y+width*5/7,paint);
        canvas.drawRect(x+width*4/7,y+width*2/7,x+width*5/7,y+width*5/7,paint);
        if(pressed){
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(width/10);
            paint.setAlpha(100);
            canvas.drawCircle(x+width/2,y+width/2,width/2,paint);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(x+width*2/7,y+width*2/7,x+width*3/7,y+width*5/7,paint);
            canvas.drawRect(x+width*4/7,y+width*2/7,x+width*5/7,y+width*5/7,paint);
        }
    }
}
