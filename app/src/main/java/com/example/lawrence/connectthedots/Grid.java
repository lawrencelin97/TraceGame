package com.example.lawrence.connectthedots;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;


public class Grid implements GameObject{
    public Dot[] dots;
    private int numDots;
    private int size;
    private int radius;

    Puzzle puzzle;

    private int space;
    private int margin;

    private int xDim,yDim;

    DrawImage img;
    Paint paint;
    private boolean image=false;

    public Grid(int x, int y){
        xDim=x;
        yDim=y;
        paint= new Paint();
    }

    public void buildGrid() {
        numDots=size*size;
        dots=new Dot[numDots];

        margin=(int)(xDim/(1.45*size+1.05));
        space=(xDim-(2*margin))/(size-1);
        int xPos = margin;
        int yPos = yDim -xDim+margin-yDim/30;

        radius=(int)(space/(6.6-(float)size/2.3));
        puzzle.setRadius(radius);

        for (int yCounter = 0; yCounter < size; yCounter++) {
            for (int xCounter = 0; xCounter < size; xCounter++) {
                dots[yCounter * size + xCounter] = new Dot(xPos, yPos,radius);
                xPos += space;
            }
            xPos = margin;
            yPos += space;
        }
        setImage(0);
    }

    public ArrayList<Dot> getAdjacent(Dot currDot)
    {
        int noDots = (int)(Math.sqrt(numDots));
        int current = 0;
        ArrayList<Dot> adjacent = new ArrayList<Dot>();
        for(int i=0;i<dots.length;i++){
            if(currDot==dots[i])
                current = i;
        }
        boolean leftAllign = current%noDots==0;
        boolean rightAllign = (current+1)%noDots==0;
        boolean topAllign = current<noDots;
        boolean bottomAllign = current>=(numDots-noDots);
        if(leftAllign)
        {
            adjacent.add(dots[current+1]);
            if(topAllign)
            {
                adjacent.add(dots[current+noDots]);
                adjacent.add(dots[current+noDots+1]);
            }
            else if(bottomAllign)
            {
                adjacent.add(dots[current-noDots]);
                adjacent.add(dots[current-noDots+1]);
            }
            else{
                adjacent.add(dots[current-noDots]);
                adjacent.add(dots[current-noDots+1]);
                adjacent.add(dots[current+noDots]);
                adjacent.add(dots[current+noDots+1]);
            }
        }
        else if(rightAllign)
        {
            adjacent.add(dots[current-1]);
            if(topAllign)
            {
                adjacent.add(dots[current+noDots]);
                adjacent.add(dots[current+noDots-1]);
            }
            else if(bottomAllign)
            {
                adjacent.add(dots[current-noDots]);
                adjacent.add(dots[current-noDots-1]);
            }
            else{
                adjacent.add(dots[current-noDots]);
                adjacent.add(dots[current-noDots-1]);
                adjacent.add(dots[current+noDots]);
                adjacent.add(dots[current+noDots-1]);
            }
        }
        else
        {
            adjacent.add(dots[current-1]);
            adjacent.add(dots[current+1]);
            if(topAllign)
            {
                adjacent.add(dots[current+noDots-1]);
                adjacent.add(dots[current+noDots]);
                adjacent.add(dots[current+noDots+1]);
            }
            else if(bottomAllign)
            {
                adjacent.add(dots[current-noDots-1]);
                adjacent.add(dots[current-noDots]);
                adjacent.add(dots[current-noDots+1]);
            }
            else
            {
                adjacent.add(dots[current-noDots-1]);
                adjacent.add(dots[current-noDots]);
                adjacent.add(dots[current-noDots+1]);
                adjacent.add(dots[current+noDots-1]);
                adjacent.add(dots[current+noDots]);
                adjacent.add(dots[current+noDots+1]);
            }
        }

        return adjacent;
    }
    public void setSize(int size){
        this.size=size;
        buildGrid();
        puzzle.generate();
    }
    @Override
    public void draw(Canvas canvas){
        for(int counter=0;counter<dots.length;counter++){

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(xDim/80);
            if(puzzle.getPuzzle().contains(dots[counter]))
                paint.setColor(Color.GRAY);
            else
                paint.setColor(Color.RED);
            canvas.drawCircle(dots[counter].getPoint().x,dots[counter].getPoint().y,radius,paint);
            //paint.setColor(Color.TRANSPARENT);
            paint.setStyle(Paint.Style.FILL);
            //if(dots[counter]!=puzzle.getPuzzle().get(0))
            //canvas.drawCircle(dots[counter].getPoint().x,dots[counter].getPoint().y,dots[counter].getRectangle().width()*44/100,paint);

            if(image)
                setDraw(canvas, counter, img);
            else{
                if(dots[counter]==puzzle.getPuzzle().get(0))
                    paint.setColor(Color.GRAY);
                else
                    paint.setColor(Color.RED);
                setDraw(canvas,counter,paint);
            }
        }
    }
    public void setDraw(Canvas canvas, int counter, Paint paint) {
        //draw basic rectangle of color paint
        canvas.drawCircle(dots[counter].getPoint().x,dots[counter].getPoint().y,radius/2,paint);
    }
    public void setDraw(Canvas canvas, int counter, DrawImage img)
    {
        int l = dots[counter].getRectangle().left+radius/2;
        int t = dots[counter].getRectangle().bottom+radius/2;

        img.setLocation(l,t);

        img.draw(canvas);
    }
    public void setImage(int opt){
        image=true;
        switch (opt){
            case 0:
                image = false;
                break;
            case 1:
                img = new DrawImage(GamePanel.context,R.drawable.poop2);
                int w = dots[0].getRectangle().width()/2;
                int h = dots[0].getRectangle().height()/2;
                img.setSize(w,h);
                break;

        }

    }
    @Override
    public void update(){

    }

    public int getRadius(){return radius;}
    public Dot[] getDots(){
        return dots;
    }
    public int getSize(){return size;}
    public int getX(){return xDim;}
    public int getY(){return yDim;}
    public void setPuzzle(Puzzle puzzle){
        this.puzzle=puzzle;
    }
}












