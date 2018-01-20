package com.example.lawrence.connectthedots;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;


public class GameOver {
    private int score;
    private int highScore;
    Paint paint=new Paint();
    private int x;
    private int y;
    GamePanel panel;
    Button returnButton;
    Rect back;
    Context c;

    MediaPlayer gameOverSound;

    private int fade=0;
    private int delay=0;

    public GameOver(int score, int high, int x, int y,GamePanel gamePanel, Context c){
        this.c = c;
        this.score=score;
        highScore=high;
        this.x=x;
        this.y=y;
        panel=gamePanel;
        returnButton=new Button("Return to Main Menu",1000,y/30);
        back=new Rect(0,0,x,y);
        gameOverSound=MediaPlayer.create(c,R.raw.gameover);
    }
    public void draw(Canvas canvas){
        DrawImage theEnd = new DrawImage(c, R.drawable.gameover,200,100,50,300);

        paint.setColor(Color.BLACK);
        paint.setAlpha(fade);
        canvas.drawRect(back,paint);
        paint.setTextSize(y/20);

        paint.setColor(Color.RED);
        paint.setAlpha(fade);
        returnButton.setAlpha(fade);


        if(delay<100)
            delay+=6;
        else{
            if(fade<255){
                fade+=8;
            }
            if(fade>255){
                fade=255;
                gameOverSound.start();
            }
        }

        Rect bounds=new Rect();
        paint.getTextBounds("Game Over",0,1,bounds);
        returnButton.draw(canvas);

        canvas.drawText("Game Over",(x-paint.measureText("Game Over"))/2,(y+bounds.height())/4,paint);
        canvas.drawText("Score: "+score,(x-paint.measureText("Game Over"))/2-100,(y+bounds.height())/4+100,paint);
        canvas.drawText("High Score: "+highScore,(x-paint.measureText("Game Over"))/2-100,(y+bounds.height())/4+200,paint);
        theEnd.Fade(canvas,paint);



    }
    public void actionDown(Point point){
        returnButton.pressed(point);
    }
    public void actionUp(Point point){
        if (returnButton.released(point)) {
            panel.setState(0);
        }
    }
}










