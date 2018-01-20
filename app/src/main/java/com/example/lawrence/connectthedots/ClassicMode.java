package com.example.lawrence.connectthedots;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;


public class ClassicMode extends GameMode {
    private Player player;
    public static Point playerPoint;

    //private int streak=0;
    //private int mult=1;

    private Grid grid;
    private Puzzle puzzle;

    public int score = 0;
    public int errors = 0;

    private final int[] levelUpPace={15,100,300,600,1000};// controls how quickly the game progresses (lower number = faster)
    private int levelUpCap=0;
    private int lengthIncreaseCap=0;
    private int lengthIncreaseCapCounter;
    private int maxGrowth=1;
    private int stage=0;//1-5 representing 2x2 to 6x6, starts at 0 b/c game didn't start yet
    private int lengthConstant=-1;//when game starts becomes 0
    private int minPuzzleLength=1;

    private boolean gameOver=false;
    private boolean messedUp=false;

    private int strikeCounter;

    public ClassicMode(GamePanel panel, int x, int y,Context context){
        super(panel,x,y,10,context);
        createGame();
    }

    public void createGame(){
        grid = new Grid(x, y);
        player = new Player(grid);
        puzzle = new Puzzle(grid);
        playerPoint = new Point(150, 150);

        grid.setPuzzle(puzzle);

        checkLevelUp();
    }
    public void update(){
        super.update();
        if(!pauseDelay) {
            player.update(playerPoint);
            checkTime();
            if(messedUp) {
                //resetStreak();
                messedUp=false;
            }
            if (correct) {
                sound1.start();
                correct = false;
                score+=puzzle.getPuzzleLength()/*mult*/;
                /*streak++;
                if(streak==10){
                    if(mult<5)
                        mult++;
                    streak=0;
                }*/
                checkLevelUp();
                reset();
            }
        }
    }
    /*public void resetStreak(){
        streak=0;
        mult=1;
    }*/
    public void checkTime()
    {
        currentTime=System.currentTimeMillis();
        if(currentTime>=endTime)
        {
            sound4.start();
            //resetStreak();
            strikeCounter=12;
            errors++;
            if(errors==3)
                gameOver=true;
            reset();
        }
    }
    public void draw(Canvas canvas){
        paint.setAlpha(500);
        puzzle.draw(canvas);
        grid.draw(canvas);

        player.draw(canvas);

        paint.setColor(Color.RED);
        paint.setTextSize(50);

        //canvas.drawText("X"+mult+" Streak: "+streak, x*36/100, y*31/100, paint);

        drawScoreTime(canvas);
        drawErrors(canvas);
        if(gameOver){
            gameOver(score);
            gameOver=false;
        }
        super.draw(canvas);
    }
    public void actionDown(Point point){
        super.actionDown(point);
        playerPoint.set(point.x,point.y);
    }
    public void actionMove(int x, int y){
        playerPoint.set(x,y);
    }
    public void actionUp(Point point){
        super.actionUp(point);
        playerPoint.set(0, 0);
        correct = player.yay(puzzle.getPuzzle());
        if(player.getInitial()!=null&&!correct) {
            messedUp = true;
            sound3.start();
        }
        player.clear();
    }

    public void checkLevelUp(){
        if(score>=levelUpCap&&stage<6){
            stage++;
            grid.setSize(stage+1);
            lengthConstant++;
            puzzle.setLengthParam(minPuzzleLength,lengthConstant);

            player.updateGrid();

            maxGrowth=puzzle.getLengthLimit(stage+1)-minPuzzleLength;
            levelUpCap=levelUpPace[stage-1];
            if(stage==1) {
                lengthIncreaseCapCounter=(levelUpCap)/(maxGrowth+1);
                lengthIncreaseCap = lengthIncreaseCapCounter;
            }
            else {
                lengthIncreaseCapCounter=(levelUpCap-levelUpPace[stage - 2])/(maxGrowth+1);
                lengthIncreaseCap = levelUpPace[stage - 2] + lengthIncreaseCapCounter;
            }
            System.out.println(lengthIncreaseCap+" "+lengthIncreaseCapCounter);
        }else if(score>=lengthIncreaseCap&&minPuzzleLength<puzzle.getLengthLimit(stage+1)){
            minPuzzleLength++;
            lengthIncreaseCap+=lengthIncreaseCapCounter;
            puzzle.setLengthParam(minPuzzleLength,lengthConstant);
        }
    }
    public void reset()
    {
        player.clear();
        puzzle.generate();
        timeLeft=timerMS;
        startTime = System.currentTimeMillis();
        endTime=startTime+timerMS;
    }

    public void drawScoreTime(Canvas c) {
        String scoreText = /*"SCORE: " +*/ ""+score;
        int rectX = x/100;
        int rectY = y*1/200;
        int rectWidth = x-rectX;
        int rectHeight = y/30;
        float time = (float)(endTime-currentTime)/1000;
        /*String timeText = "";
        if(time<=9)
            timeText = "Time: 00:0" + time;
        else
            timeText = "Time: 00:" + time;*/

        //paint.setTextSize(textSize);
        //float timeWidth = paint.measureText(timeText);

        paint.setColor(Color.GRAY);
        c.drawRect(rectX, rectY, rectWidth, rectY+ rectHeight, paint);

        paint.setColor(Color.RED);
        paint.setStrokeWidth(rectHeight*50/100);
        c.drawLine(rectX,rectY+(rectHeight)/2,((x-2*rectX)*(1-(time/timerSeconds)))+rectX,rectY+(rectHeight)/2,paint);
        paint.setTextSize(y/9);
        c.drawText(scoreText, (x-paint.measureText(scoreText))/2,y*28/100, paint);
        //c.drawText(timeText,rectWidth-timeWidth,rectHeight-rectY,paint);
    }
    public void drawErrors(Canvas c)
    {
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(x*1/100);
        int start=x*29/100;
        int width=x*14/100;
        c.drawRect(start,y*11/200,start+width,y*31/200,paint);
        c.drawRect(start+width,y*11/200,start+2*width,y*31/200,paint);
        c.drawRect(start+2*width,y*11/200,start+3*width,y*31/200,paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        int timer=5;
        for(int i =0; i<errors;i++) {
            if(strikeCounter>0&&i==errors-1){
                if(strikeCounter<=timer) {
                    paint.setTextSize(x);
                    float xpos=(x-paint.measureText("X"))/2;
                    paint.setTextSize(x/5+((float)strikeCounter/timer*(4*x / 5)));
                    c.drawText("X",(x*299/1000+i*x*14/100)+(float)strikeCounter/timer*(xpos-(x*299/1000+i*x*14/100)),(y*147/1000)+(y*700/1000-y*147/1000)*strikeCounter/timer,paint);
                }else {
                    paint.setTextSize(x);
                    c.drawText("X",(x-paint.measureText("X"))/2,y*700/1000,paint);
                }
                strikeCounter--;
            }else {
                paint.setTextSize(x/5);
                c.drawText("X", x * 299 / 1000 + i * x * 14 / 100, y * 147 / 1000, paint);
            }
        }
    }
}
