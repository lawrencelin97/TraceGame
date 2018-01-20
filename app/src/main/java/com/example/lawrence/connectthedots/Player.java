package com.example.lawrence.connectthedots;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

public class Player implements GameObject {

    private Point initial = null;
    private Point currentPoint = new Point(0, 0);
    public ArrayList<Dot> hasReached = new ArrayList<Dot>();
    private Rect currentRect = new Rect(currentPoint.x - 5, currentPoint.y + 5, currentPoint.x + 5, currentPoint.y - 5);
    int[] size;
    int numberdots;
    int strokewidth=10;

    private Grid grid;

    private int color=Color.BLACK;
    private int x;

    private Paint paint = new Paint();
    // public EndGame gameOver;

    public Player(Grid grid){
        this.grid=grid;
        x=grid.getX();
    }

    @Override
    public void draw(Canvas canvas) {//draws the path the player took
        paint.setStrokeWidth(x/80);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        if (hasReached.size() >= 1) {
            for (int i = 0; i < hasReached.size(); i++){
                if(size[i]==0)
                    size[i]=hasReached.get(i).getRectangle().width()/5;
                else if(size[i]<hasReached.get(i).getRectangle().width()/2)
                    size[i]+=hasReached.get(i).getRectangle().width()/18;
                if(size[i]>hasReached.get(i).getRectangle().width()/2)
                    size[i]=hasReached.get(i).getRectangle().width()/2;
                canvas.drawCircle(hasReached.get(i).getPoint().x,hasReached.get(i).getPoint().y,size[i],paint);
            }
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(strokewidth);
            for (int i = 0; i < hasReached.size() - 1; i++) {
                Point pt1 = hasReached.get(i).getPoint();
                Point pt2 = hasReached.get(i + 1).getPoint();
                canvas.drawLine(pt1.x, pt1.y, pt2.x, pt2.y, paint);
            }
        }
        if (initial != null)
            canvas.drawLine(initial.x, initial.y, currentPoint.x, currentPoint.y, paint);
        else
            canvas.drawRect(currentRect, paint);
    }

    @Override
    public void update() {

    }

    public void update(Point point) {//updates player position and checks for overlap
        this.currentPoint = point;
        currentRect.set(currentPoint.x - 5, currentPoint.y + 5, currentPoint.x + 5, currentPoint.y - 5);
        //Check if current position in right dot
        updateInitial();
    }

    public void updateInitial() {//checks to see if player reached another rectangle, updates initial if so
        Dot[] dots = grid.getDots();
        for (int i = 0; i < dots.length; i++) {
            if (overlaps(dots[i].getRectangle()) && !(hasReached.contains(dots[i]))) {
                GameMode.touch();
                hasReached.add(dots[i]);
                this.initial = dots[i].getPoint();
            }
        }
    }

    public boolean overlaps(Rect r) {
        int x = currentPoint.x;
        int y = currentPoint.y;

        return x < r.right && x > r.left && y < r.top && y > r.bottom;
    }

    public void clear() {
        hasReached.clear();
        initial = null;
        currentPoint = new Point(0, 0);
        size=new int[numberdots];
        for(int i=0;i<numberdots;i++){
            size[i]=0;
        }
    }

    public boolean yay(ArrayList<Dot> path) {//checks to see if player got the puzzle correct
        boolean correct = true;
        if (hasReached.size() != path.size())
            return false;
        else {
            for (int i = 0; i < hasReached.size(); i++) {
                if (hasReached.get(i) != path.get(i))
                    correct = false;
            }
        }
        return correct;
    }

    public Point getInitial(){
        return initial;
    }

    public void updateGrid() {
        strokewidth=grid.dots[0].getRectangle().width()*20/100;
        numberdots = grid.getSize()*grid.getSize();
        clear();
    }
}

































