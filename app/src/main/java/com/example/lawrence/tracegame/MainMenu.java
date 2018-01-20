package com.example.lawrence.tracegame;

import android.graphics.Canvas;
import android.graphics.Point;

/**
 * Created by lawrence on 6/8/2017.
 */

public class MainMenu {
    private final int gameModes=1;

    Button newGame;
    Button resume;
    Button gameMode;

    GamePanel panel;

    private int x;
    private int y;
    private int mode=1;

    public MainMenu(int x, int y, GamePanel gamePanel){
        this.x=x;
        this.y=y;
        //newGame=new Button("New Game",x/2,y/10,y/20,x/5);
        //resume=new Button("Resume",x/2,2*y/10,y/20,x/5);
        //gameMode=new Button("Game Mode",x/2,3*y/10,y/20,x/5);
        newGame=new Button(R.drawable.new_game,x/2,y/5,y/12,x/3);
        resume=new Button(R.drawable.resume,x/2,y/5+y/6,y/12,x/3);
        gameMode=new Button(R.drawable.game_mode,x/2,y/5+y/3,y/12,x/3);

        panel=gamePanel;
    }
    public void draw(Canvas canvas){
        newGame.draw(canvas);
        resume.draw(canvas);
        gameMode.draw(canvas);
    }
    public void actionDown(Point point){

        if(newGame.overlaps(point)){
            System.out.println("Start");
            panel.createGame(mode);
        }
        if(resume.overlaps(point)){
            panel.resume();
        }
        if(gameMode.overlaps(point)){//currently set to 3 modes
            if(mode<gameModes)
                mode++;
            else
                mode=1;
        }
    }
}




