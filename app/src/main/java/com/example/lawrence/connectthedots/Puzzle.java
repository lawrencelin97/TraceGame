package com.example.lawrence.connectthedots;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;

public class Puzzle implements GameObject{
    private Grid grid;
    Dot[] gridDots;
    Point[][] drawPoints;

    private int puzzleLength=1;
    private int[] lengthLimit= {3,8,15,21,29};
    private int minPuzzleLength=1;
    private int lengthConstant=0;

    private Paint paint = new Paint();

    private int radius;

    private ArrayList<Dot> puzzle;

    public Puzzle(Grid grid){
        this.grid=grid;
        paint.setColor(Color.GRAY);
    }
    public void draw(Canvas canvas){
        /*float adj = paint.getStrokeWidth()/2;
        float t = puzzle.get(0).getRectangle().bottom;
        float b = puzzle.get(0).getRectangle().top;
        float r = puzzle.get(0).getRectangle().right;
        float l = puzzle.get(0).getRectangle().left;

        canvas.drawLine(l-adj,t,r+adj,t,paint);
        canvas.drawLine(l,t,l,b,paint);
        canvas.drawLine(l-adj,b,r+adj,b,paint);
        canvas.drawLine(r,t,r,b,paint);*/

        //canvas.drawRect(puzzle.get(0).getRectangle(),paint);
        for (int i = 0; i < puzzle.size()-1; i++) {
            //Point pt1 = puzzle.get(i).getPoint();
            Point pt1;
            Point pt2;
            //System.out.println("hi");
            if(drawPoints[i][0]==null)
                pt1 = puzzle.get(i).getPoint();
            else
                pt1=drawPoints[i][0];
            if(drawPoints[i][1]==null)
                pt2 = puzzle.get(i+1).getPoint();
            else
                pt2=drawPoints[i][1];

            canvas.drawLine(pt1.x, pt1.y, pt2.x, pt2.y, paint);
        }
    }
    @Override
    public void update(){
    }

    public void generate(){
        gridDots=grid.getDots();
        paint.setStrokeWidth(grid.getDots()[0].getRectangle().width()*15/100);
        generatePuzzleLength();
        boolean repeat=false;

        Dot blocked;

        puzzle=new ArrayList<Dot>();
        ArrayList<ArrayList<Dot>> potential=new ArrayList<ArrayList<Dot>>();

        //int first=(int)Math.floor(Math.random()*gridDots.length);

        puzzle.add(gridDots[(int)Math.floor(Math.random()*gridDots.length)]);

        int counter=0;

        while(counter<puzzleLength){
            if(!repeat) {
                potential.add(grid.getAdjacent(puzzle.get(counter)));
                int counter2 = 0;
                while (counter2 < potential.get(counter).size()) {
                    if (puzzle.contains(potential.get(counter).get(counter2))) {
                        potential.get(counter).remove(counter2);
                    } else {
                        counter2++;
                    }
                }
            }
            if(potential.get(counter).size()==0){
                blocked=puzzle.get(counter);
                puzzle.remove(counter);
                potential.get(counter-1).remove(blocked);
                potential.remove(counter);
                counter--;
                repeat=true;
            }else {
                puzzle.add(potential.get(counter).get((int) Math.floor(Math.random() * potential.get(counter).size())));
                counter++;
                repeat=false;
            }
        }
        drawPoints= new Point[puzzle.size()-1][2];
        for(int c=0;c<puzzle.size()-1;c++){
            if(puzzle.get(c).getPoint().x==puzzle.get(c+1).getPoint().x){
                if(puzzle.get(c).getPoint().y-puzzle.get(c+1).getPoint().y>0){//first is below second
                    drawPoints[c][0]=new Point(puzzle.get(c).getPoint().x,puzzle.get(c).getPoint().y-radius);
                    drawPoints[c][1]=new Point(puzzle.get(c+1).getPoint().x,puzzle.get(c+1).getPoint().y+radius);
                }else{
                    drawPoints[c][0]=new Point(puzzle.get(c).getPoint().x,puzzle.get(c).getPoint().y+radius);
                    drawPoints[c][1]=new Point(puzzle.get(c+1).getPoint().x,puzzle.get(c+1).getPoint().y-radius);
                }
            }else if(puzzle.get(c).getPoint().y==puzzle.get(c+1).getPoint().y){
                if(puzzle.get(c).getPoint().x-puzzle.get(c+1).getPoint().x>0){//first is right of left
                    drawPoints[c][0]=new Point(puzzle.get(c).getPoint().x-radius,puzzle.get(c).getPoint().y);
                    drawPoints[c][1]=new Point(puzzle.get(c+1).getPoint().x+radius,puzzle.get(c+1).getPoint().y);
                }else{
                    drawPoints[c][0]=new Point(puzzle.get(c).getPoint().x+radius,puzzle.get(c).getPoint().y);
                    drawPoints[c][1]=new Point(puzzle.get(c+1).getPoint().x-radius,puzzle.get(c+1).getPoint().y);
                }
            }else{
                int var=(int)(radius/Math.sqrt(2));
                if(puzzle.get(c).getPoint().x-puzzle.get(c+1).getPoint().x>0){//first is right of second
                    if(puzzle.get(c).getPoint().y-puzzle.get(c+1).getPoint().y>0){//first is below second
                        drawPoints[c][0]=new Point(puzzle.get(c).getPoint().x-var,puzzle.get(c).getPoint().y-var);
                        drawPoints[c][1]=new Point(puzzle.get(c+1).getPoint().x+var,puzzle.get(c+1).getPoint().y+var);
                    }else{//first is above
                        drawPoints[c][0]=new Point(puzzle.get(c).getPoint().x-var,puzzle.get(c).getPoint().y+var);
                        drawPoints[c][1]=new Point(puzzle.get(c+1).getPoint().x+var,puzzle.get(c+1).getPoint().y-var);
                    }
                }else{//first is left of second
                    if(puzzle.get(c).getPoint().y-puzzle.get(c+1).getPoint().y>0){//first is below second
                        drawPoints[c][0]=new Point(puzzle.get(c).getPoint().x+var,puzzle.get(c).getPoint().y-var);
                        drawPoints[c][1]=new Point(puzzle.get(c+1).getPoint().x-var,puzzle.get(c+1).getPoint().y+var);
                    }else{//first is above
                        drawPoints[c][0]=new Point(puzzle.get(c).getPoint().x+var,puzzle.get(c).getPoint().y+var);
                        drawPoints[c][1]=new Point(puzzle.get(c+1).getPoint().x-var,puzzle.get(c+1).getPoint().y-var);
                    }
                }
            }

        }
    }
    public void setRadius(int rad){radius=rad;}
    public int getPuzzleLength(){return puzzleLength;}
    public ArrayList<Dot> getPuzzle()
    {
        return puzzle;
    }
    public void generatePuzzleLength(){
        puzzleLength=minPuzzleLength+(int)Math.floor(Math.random()*(2*lengthConstant));
        System.out.print("min: "+minPuzzleLength+", cons: "+lengthConstant+", pre-check: "+puzzleLength);
        if(puzzleLength>lengthLimit[grid.getSize()-2]){
            puzzleLength=lengthLimit[grid.getSize()-2];
        }
        System.out.println(",post-check: "+puzzleLength);
    }
    public int getLengthLimit(int gridSize){
        if(gridSize<0)
            return 0;
        else
            return lengthLimit[gridSize-2];
    }
    public void setLengthParam(int min,int constant){
        minPuzzleLength=min;
        lengthConstant=constant;
    }
}
