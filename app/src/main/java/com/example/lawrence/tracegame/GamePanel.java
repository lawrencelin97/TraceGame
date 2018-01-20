package com.example.lawrence.tracegame;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import static android.content.Context.MODE_PRIVATE;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    public static Context context;

    MainMenu mainMenu;
    private int state=0;//0 = main menu/pause, 1=game, 2=resume delay
    GameMode game;
    GameOver gameOver;

    private Point size;
    Paint paint = new Paint();
    private boolean done=false;

    public GamePanel(Context context) {
        super(context);
        this.context = context;

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        size = new Point();
        display.getSize(size);

        mainMenu = new MainMenu(size.x, size.y, this);

        setFocusable(true);

        Button.setScreenDim(size.x,size.y);
    }

    public void createGame(int mode) {
        switch(mode){
            case 1:
                game=new ClassicMode(this,size.x,size.y,context);
                state=1;
                done=false;
                break;
            default:
                state=0;
                break;
        }
    }
    public void resume(){
        if(game!=null){
            if(done){
                state=-1;
            }else{
                state=1;
                game.pauseDelay();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Point pressed = new Point((int) event.getX(), (int) event.getY());
                switch(state){
                    case -1:
                        gameOver.actionDown(pressed);
                        break;
                    case 0:
                        mainMenu.actionDown(pressed);
                        break;
                    case 1:
                        game.actionDown(pressed);
                        break;
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if(state==1){
                    game.actionMove((int) event.getX(), (int) event.getY());
                }

                break;
            case MotionEvent.ACTION_UP:
                if (state==1) {
                    game.actionUp((int) event.getX(), (int) event.getY());
                }
                break;
        }
        return true;
    }

    public void update() {
        if(state==1){
            game.update();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //canvas.drawColor(Color.WHITE);
        switch (state){
            case -1:
                game.draw(canvas);
                gameOver.draw(canvas);
                break;
            case 0:
                mainMenu.draw(canvas);
                break;
            case 1:
                game.draw(canvas);
                break;
        }
     /*  else if(player.isGameOver() && player.isReturnMain(playerPoint))
       {
           mainMenu.draw(canvas);
           playerPoint.set(player.gameOver.returnMenu.getX()+1,player.gameOver.returnMenu.getY()-1);
           player.GAMEOVER = false;
       } */

    }
    public void gameOver(int score,int high){
        gameOver=new GameOver(score, high,size.x, size.y,this,context);
        state=-1;
        done=true;
    }

    public void setState(int state){
        this.state=state;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(holder, this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }
}





