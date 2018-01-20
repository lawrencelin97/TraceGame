package com.example.lawrence.connectthedots;

import android.graphics.Point;
import android.graphics.Rect;

public class Dot {

    private Point point;
    private Rect rectangle;

    public Dot(int xdim,int ydim, int width) {
        point=new Point(xdim,ydim);
        rectangle = new Rect(xdim-width,ydim+width,xdim+width,ydim-width);
    }

    public Point getPoint() {
        return point;
    }
    public Rect getRectangle(){
        return rectangle;
    }
}
