package com.example.lawrence.connectthedots;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.support.constraint.solver.widgets.Rectangle;

public abstract class GameMode {
    GamePanel panel;
    SharedPreferences sharedPref;
    protected int x,y;
    protected boolean pauseDelay=false;
    public int highscore = 0;

    protected int prePauseSecs = 3;
    protected long startTime;
    protected int timerSeconds;   //multiply by 1000 for ms

    protected long timerMS;
    protected long currentTime;
    protected long timeLeft;
    protected long endTime;
    protected long delayStart;

    protected boolean correct=false;
    public boolean GAMEOVER = false;

    MediaPlayer mp;
    MediaPlayer sound1;
    private static MediaPlayer touch1;
    private static MediaPlayer touch2;
    MediaPlayer sound3;
    MediaPlayer sound4;
    private static boolean touch=true;

    protected Button pauseButton;

    protected Paint paint = new Paint();

    Context context;

    public GameMode(GamePanel panel, int x, int y,int time, Context context){

        this.panel=panel;
        sharedPref = panel.context.getSharedPreferences(panel.context.getString(R.string.hiscore), panel.context.MODE_PRIVATE);
        this.highscore = sharedPref.getInt(panel.context.getString(R.string.hiscore),0);

        this.context=context;

        this.x=x;
        this.y=y;
        pauseButton = new PauseButton(x*87/100, y*12/200, x*9/100);

        timerSeconds=time;
        timerMS=timerSeconds*1000;
        timeLeft=timerSeconds*1000;

        startTime = System.currentTimeMillis();
        endTime=startTime+timerMS;

        sound1= MediaPlayer.create(context,R.raw.correct);
        touch1=MediaPlayer.create(context,R.raw.touch);
        touch2=MediaPlayer.create(context,R.raw.touch);
        sound3=MediaPlayer.create(context,R.raw.nope);
        sound4=MediaPlayer.create(context,R.raw.wrong);

        mp = MediaPlayer.create(context, R.raw.foxhunt);
        mp.setVolume(0.3f,0.3f);

        mp.setLooping(true);
        mp.start();
    }
    public void pauseDelay(){
        delayStart=System.currentTimeMillis();
        pauseDelay=true;
    }
    public void resume() {
        mp.start();
        startTime=System.currentTimeMillis();
        endTime=startTime+timeLeft;
    }
    public void pause() {
        panel.setState(0);
        mp.pause();
        if(!pauseDelay)
            timeLeft=timeLeft-currentTime+startTime;
    }
    public void update(){
        if(pauseDelay){
            if(System.currentTimeMillis()-delayStart>=3000) {
                pauseDelay = false;
                resume();
            }
        }
    }
    public void draw(Canvas canvas){
        pauseButton.draw(canvas);
        if(pauseDelay){
            paint.setColor(Color.BLACK);
            paint.setTextSize(y/2);

            Rect bounds=new Rect();
            int delay=3-(int)((System.currentTimeMillis()-delayStart)/1000);
            int fade=(((int)(System.currentTimeMillis()-delayStart))/10)%100;
            paint.getTextBounds(""+delay,0,1,bounds);
            if(fade>25)
                paint.setAlpha(255*(100-fade)/75);
            canvas.drawText(""+delay,(x-paint.measureText(""+delay))/2,(y+bounds.height())/2,paint);
        }
    }
    public void actionDown(Point point){
        pauseButton.pressed(point);
    }
    public void actionUp(Point point){
        if (pauseButton.released(point)) {
            pause();
        }
    }
    public void gameOver(int score){
        mp.stop();
        if(this.highscore<=score) {
            this.highscore = score;
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(panel.context.getString(R.string.hiscore), score);
            editor.commit();
            System.out.println(score);
        }
        else
        {
            System.out.println(score);
            System.out.println("High  " + this.highscore);
        }
        panel.gameOver(score,this.highscore);
    }
    public static void touch(){
        if(touch){
            touch1.start();
            touch=false;
        }else {
            touch2.start();
            touch=true;
        }
    }
    public abstract void createGame();
    public abstract void actionMove(int x, int y);
}




