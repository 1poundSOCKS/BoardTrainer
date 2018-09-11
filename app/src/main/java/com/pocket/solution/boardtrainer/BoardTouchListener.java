package com.pocket.solution.boardtrainer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Bitmap;

public class BoardTouchListener implements View.OnTouchListener {

    final ImageView boardView;
    final Bitmap board;
    float mx = 0 , my = 0;

    boolean allowScroll = true;
    boolean setLeftHand = false;
    boolean setRightHand = false;

    public BoardTouchListener(ImageView boardView) {
        this.boardView = boardView;
        board = ((BitmapDrawable)this.boardView.getDrawable()).getBitmap();
    }

    public void EnableScroll() {
        allowScroll = true;
        setLeftHand = false;
        setRightHand = false;
    }

    public void EnableSetLeftHand() {
        allowScroll = false;
        setLeftHand = true;
        setRightHand = false;
    }

    public void EnableSetRightHand() {
        allowScroll = false;
        setLeftHand = false;
        setRightHand = true;
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
                float scrollX = boardView.getScrollX();
                float scrollY = boardView.getScrollY();
                float newScrollX = scrollX + scrollByX;
                float newScrollY = scrollY + scrollByY;
                if( allowScroll )
                    boardView.scrollTo((int) newScrollX, (int) newScrollY);
                mx = curX;
                my = curY;
                break;
            case MotionEvent.ACTION_UP:
                curX = event.getX();
                curY = event.getY();
                if( allowScroll )
                    boardView.scrollBy((int) (mx - curX), (int) (my - curY));
                else if( setLeftHand ) {
                    Paint paint = new Paint();
                    paint.setAntiAlias(true);
                    paint.setColor(Color.BLUE);
                    Canvas canvas = new Canvas(board);
                    canvas.drawCircle(curX * 4, curY * 4, 25, paint);
                }
                break;
        }

        return true;
    }
}
