package com.pocket.solution.boardtrainer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Bitmap;
import android.graphics.Matrix;

public class BoardTouchListener implements View.OnTouchListener {

    final ImageView boardView;
    final DisplayMetrics metrics;
    final Bitmap board;
    float mx = 0 , my = 0;

    boolean allowScroll = false;

    public BoardTouchListener(ImageView boardView, DisplayMetrics metrics) {
        this.boardView = boardView;
        this.metrics = metrics;
        board = ((BitmapDrawable)this.boardView.getDrawable()).getBitmap();
    }

    public boolean onTouch(View arg0, MotionEvent event) {

        float curX, curY;

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                mx = event.getX();
                my = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                curX = event.getX();
                curY = event.getY();
                float scrollByX = (mx - curX);
                float scrollByY = (my - curY);
                mx = curX;
                my = curY;
                break;
            case MotionEvent.ACTION_UP:
                curX = event.getX();
                curY = event.getY();
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setColor(Color.BLUE);
                float[] out = new float[2];
                float[] in = { curX, curY };
                Matrix matrix = boardView.getImageMatrix();
                Matrix inverse = new Matrix();
                matrix.invert(inverse);
                inverse.mapPoints(out, in);
                Canvas canvas = new Canvas(board);
                canvas.drawCircle(out[0] / metrics.density, out[1] / metrics.density, 50, paint);
                boardView.invalidate();
                break;
        }

        return true;
    }
}
