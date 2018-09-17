package com.pocket.solution.boardtrainer;

import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Bitmap;
import android.graphics.Matrix;

public class BoardTouchListener implements View.OnTouchListener {

    final private MainActivity activity;
    private float mx = 0 , my = 0;
    private int origX = 0, origY = 0;

    public BoardTouchListener(MainActivity activity) {
        this.activity = activity;
    }

    public boolean onTouch(View arg0, MotionEvent event) {

        float curX, curY;

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                mx = event.getX();
                my = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                curX = event.getX();
                curY = event.getY();
                if( curX == mx && curY == my )
                    activity.OnClick(curX, curY);
                else
                    activity.OnScroll(curX - mx, curY - my);

                break;
        }

        return true;
    }
}
