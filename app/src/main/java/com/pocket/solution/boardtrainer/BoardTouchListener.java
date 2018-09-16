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
    private enum State { Initial, Zoom }
    private State state = State.Initial;

    public BoardTouchListener(MainActivity activity) {
        this.activity = activity;
    }

    public boolean onTouch(View arg0, MotionEvent event) {

        ImageView view = this.activity.GetImageView();

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
                float[] in = {curX + view.getScrollX(), curY + view.getScrollY()};

                if (state == State.Initial) {
                    origX = view.getScrollX();
                    origY = view.getScrollY();
                    int boardWidth = view.getMeasuredWidth();
                    int boardHeight = view.getMeasuredHeight();
                    int scrollX = (int)(view.getScrollX() + curX - boardWidth / 2);
                    int scrollY = (int)(view.getScrollY() + curY - boardHeight / 2);
                    view.scrollTo(scrollX, scrollY);
                    view.setScaleX(3);
                    view.setScaleY(3);
                    view.invalidate();
                    state = State.Zoom;
                }
                else {
                    float[] out = this.activity.convertViewCoordinatesToBitmap(in);
                    Bitmap board = ((BitmapDrawable)view.getDrawable()).getBitmap();
                    this.activity.getBoardData().markHold(board, out[0], out[1]);
                    view.invalidate();
                }

                break;
        }

        return true;
    }

    public void onBackPressed() {
        ImageView view = this.activity.GetImageView();
        view.setScaleX(1);
        view.setScaleY(1);
        view.scrollTo(origX, origY);
        view.invalidate();
        state = State.Initial;
    }
}
