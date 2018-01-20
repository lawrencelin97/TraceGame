package com.example.lawrence.tracegame;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);


        GamePanel game = (GamePanel) findViewById(R.id.gamepanel);

        //surface.setZOrderOnTop(true);
        //game.getHolder().addCallback(game);
        //SurfaceHolder holder=surface.getHolder();
        //holder.setFormat(PixelFormat.TRANSLUCENT);
        /*{
            int x=50;
            Paint paint=new Paint();

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // Do some drawing when surface is ready
                Canvas canvas = holder.lockCanvas();
                canvas.drawColor(Color.RED);
                paint.setColor(Color.BLACK);
                canvas.drawCircle(500,1000,x,paint);
                x+=10;
                holder.unlockCanvasAndPost(canvas);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
        });*/

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //game.getHolder().addCallback(game);
        //setContentView(game);

    }
}
