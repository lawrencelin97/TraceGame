package com.example.lawrence.tracegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;


public class ClassicMode extends GameMode {
    private Player player;
    public static Point playerPoint;

    private int streak=0;
    private int mult=1;

    private Grid grid;
    private Puzzle puzzle;

    public int score = 0;
    public int errors = 0;

    private final int levelUpPace=2;// controls how quickly the game progresses (lower number = faster)
    private int levelUpCap=0;
    private int lengthIncreaseCap=0;
    private int maxGrowth=1;
    private int stage=0;//1-5 representing 2x2 to 6x6, starts at 0 b/c game didn't start yet
    private int lengthConstant=-1;//when game starts becomes 0
    private int minPuzzleLength=1;

    private boolean gameOver=false;
    private boolean messedUp=false;

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
                resetStreak();
                messedUp=false;
            }
            if (correct) {
                sound1.start();
                correct = false;
                score+=puzzle.getPuzzleLength()*mult;
                streak++;
                if(streak==10){
                    if(mult<5)
                        mult++;
                    streak=0;
                }
                checkLevelUp();
                reset();
            }
        }
    }
    public void resetStreak(){
        streak=0;
        mult=1;
    }
    public void checkTime()
    {
        currentTime=System.currentTimeMillis();
        if(currentTime>=endTime)
        {
            sound4.start();
            resetStreak();
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

        canvas.drawText("X"+mult+" Streak: "+streak, x*36/100, y*31/100, paint);

        drawScoreTime(canvas);
        drawErrors(canvas);
        if(gameOver){
            gameOver(score);
            gameOver=false;
        }
        super.draw(canvas);
    }
    public void actionMove(int x, int y){
        playerPoint.set(x,y);
    }
    public void actionUp(int x, int y){
        playerPoint.set(0, 0);
        correct = player.yay(puzzle.getPuzzle());
        if(player.getInitial()!=null&&!correct) {
            messedUp = true;
            sound3.start();
        }
        player.clear();
    }

    public void checkLevelUp(){
        if(score>=levelUpCap&&stage<5){
            stage++;
            grid.setSize(stage+1);
            lengthConstant++;
            puzzle.setLengthParam(minPuzzleLength,lengthConstant);

            player.updateGrid();

            maxGrowth=puzzle.getLengthLimit(stage+1)-minPuzzleLength;
            levelUpCap+=maxGrowth*puzzle.getLengthLimit(stage+1)*levelUpPace*stage;
            lengthIncreaseCap=score+minPuzzleLength*levelUpPace*stage;
        }else if(score>=lengthIncreaseCap&&maxGrowth>0){
            minPuzzleLength++;
            maxGrowth--;
            lengthIncreaseCap+=minPuzzleLength*levelUpPace*stage;
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
        String scoreText = "Score: " + score;
        int rectX = x/100;
        int rectY = y*1/200;
        int rectWidth = x-rectX;
        int rectHeight = y/30;
        int textSize = rectHeight-2*rectY;
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
        paint.setTextSize(y/20);
        c.drawText(scoreText, (x-paint.measureText(scoreText))/2,y*27/100, paint);
        //c.drawText(timeText,rectWidth-timeWidth,rectHeight-rectY,paint);
    }
    public void drawErrors(Canvas c)
    {
        paint.setColor(Color.RED);
        paint.setTextSize(200);
        for(int i =0; i<errors;i++)
        {
            c.drawText("X",10+110*i,x/20+200,paint);
        }
    }
}
