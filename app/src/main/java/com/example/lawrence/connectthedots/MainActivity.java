package com.example.lawrence.connectthedots;

import android.app.ActionBar;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.*;
import android.view.ViewGroup;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        //addContentView(view, LayoutParams);
        /*ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //addContentView(view, lp);
        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService
                (this.LAYOUT_INFLATER_SERVICE);*/
        //View view = inflater.inflate(R.layout.main_menu,null);
        //this.addContentView(view,lp);

        GamePanel game = (GamePanel) findViewById(R.id.gamepanel);
        game.setZOrderOnTop(true);
        game.getHolder().addCallback(game);
        SurfaceHolder holder=game.getHolder();
        holder.setFormat(PixelFormat.TRANSPARENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
