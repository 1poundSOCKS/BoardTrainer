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
    int origX = 0, origY = 0;
    enum State { Initial, Zoom }
    State state = State.Initial;

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
                break;

            case MotionEvent.ACTION_UP:
                curX = event.getX();
                curY = event.getY();
                float[] in = {curX + boardView.getScrollX(), curY + boardView.getScrollY()};

                if (state == State.Initial) {
                    origX = boardView.getScrollX();
                    origY = boardView.getScrollY();
                    int boardWidth = boardView.getMeasuredWidth();
                    int boardHeight = boardView.getMeasuredHeight();
                    int scrollX = (int)(boardView.getScrollX() + curX - boardWidth / 2);
                    int scrollY = (int)(boardView.getScrollY() + curY - boardHeight / 2);
                    boardView.scrollTo(scrollX, scrollY);
                    boardView.setScaleX(3);
                    boardView.setScaleY(3);
                    boardView.invalidate();
                    state = State.Zoom;
                }
                else {
                    Paint paint = new Paint();
                    paint.setAntiAlias(true);
                    paint.setColor(Color.RED);
                    Canvas canvas = new Canvas(board);
                    float[] out = convertViewCoordinatesToBitmap(in);
                    canvas.drawCircle(out[0], out[1], 20, paint);
                    boardView.invalidate();
                }

                break;
        }

        return true;
    }

    public float[] convertViewCoordinatesToBitmap(float[] in) {
        float[] out = new float[in.length];
        Matrix matrix = boardView.getImageMatrix();
        Matrix inverse = new Matrix();
        matrix.invert(inverse);
        inverse.mapPoints(out, in);
        for( int i = 0; i < out.length; i++ )
            out[i] /= metrics.density;
        return out;
    }

    public float[] convertBitmapCoordinatesToView(float[] in) {
        float[] out = new float[in.length];
        for( int i = 0; i < out.length; i++ )
          out[i] = in[i] * metrics.density;

        Matrix matrix = boardView.getImageMatrix();
        matrix.mapPoints(out, out);
        return out;
    }

    public void onBackPressed() {
        boardView.setScaleX(1);
        boardView.setScaleY(1);
        boardView.scrollTo(origX, origY);
        boardView.invalidate();
        state = State.Initial;
    }
}
