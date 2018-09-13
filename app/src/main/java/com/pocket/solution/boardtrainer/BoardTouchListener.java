package com.pocket.solution.boardtrainer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Bitmap;
import android.graphics.Matrix;

public class BoardTouchListener implements View.OnTouchListener {

    final ImageView boardView;
    final Bitmap board;
    float mx = 0 , my = 0;

    boolean allowScroll = false;

    public BoardTouchListener(ImageView boardView) {
        this.boardView = boardView;
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
                //float[] matrix = new float[9];
                //boardView.getImageMatrix().getValues(matrix);
                float[] out = new float[2];
                float[] in = { curX, curY };
                Matrix inverse = new Matrix();
                boardView.getImageMatrix().invert(inverse);
                inverse.postTranslate(boardView.getScrollX(), boardView.getScrollY());
                //inverse.mapPoints(in);
                inverse.mapPoints(out, in);
                Canvas canvas = new Canvas(board);
                //float xPos = curX / matrix[Matrix.MSCALE_X];
                //float yPos = (curY - matrix[Matrix.MTRANS_Y]) / matrix[Matrix.MSCALE_Y];
                canvas.drawCircle(out[0], out[1], 25, paint);
                boardView.invalidate();
                break;
        }

        return true;
    }
}
