package com.pocket.solution.boardtrainer;

import java.lang.*;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.graphics.*;
import android.util.DisplayMetrics;

public class MainActivity extends AppCompatActivity {

    private BoardTouchListener boardTouchListener;
    private BoardData boardData = new BoardData();
    private enum State { Initial, Zoom }
    private State state = State.Initial;

    public BoardData getBoardData() {
        return boardData;
    }

    public ImageView GetImageView() {
        return findViewById(R.id.board);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bitmap board = BoardData.LoadBitmap(getResources(), R.drawable.board);

        ImageView imageView = GetImageView();
        imageView.setAdjustViewBounds(true);
        imageView.setImageBitmap(board);

        switch( state ) {
            case Initial:
                imageView.setScaleX(1);
                imageView.setScaleY(1);
                break;
            case Zoom:
                imageView.setScaleX(3);
                imageView.setScaleY(3);
                break;
        }

        // board touch control
        boardTouchListener = new BoardTouchListener(this);
        imageView.setOnTouchListener(boardTouchListener);

        // implement hold marking button
        //final Button button = this.findViewById(R.id.mark_hold);
        //OnClickListener buttonListener = new ButtonClickListener(button, boardTouchListener);
        //button.setOnClickListener(buttonListener);
    }

    @Override
    public void onBackPressed() {
        switch( state ) {
            case Initial:
                super.onBackPressed();
                break;
            case Zoom:
                ImageView view = GetImageView();
                view.setScaleX(1);
                view.setScaleY(1);
                view.scrollTo(0, 0);
                view.invalidate();
                state = State.Initial;
                break;
        }
    }

    @Override
    protected void onPause () {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("BoardData", this.boardData);
        outState.putSerializable("State", this.state);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.boardData = (BoardData)savedInstanceState.getSerializable("BoardData");
        if( this.boardData != null ) {
            ImageView imageView = findViewById(R.id.board);
            Bitmap board = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
            this.boardData.MarkAllHolds(board);
            this.state = (State)savedInstanceState.getSerializable("State");
        }
    }

    public void OnClick(float curX, float curY) {
        ImageView view = GetImageView();
        float[] in = {curX + view.getScrollX(), curY + view.getScrollY()};

        if (state == State.Initial) {
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
            float[] out = convertViewCoordinatesToBitmap(in);
            Bitmap board = ((BitmapDrawable)view.getDrawable()).getBitmap();
            getBoardData().markHold(board, out[0], out[1]);
            view.invalidate();
        }
    }

    public void OnScroll(float cx, float cy) {
        if( state == State.Zoom ) {
            ImageView view = GetImageView();
            view.setScrollX(view.getScrollX() - (int)cx);
            view.setScrollY(view.getScrollY() - (int)cy);
        }
    }

    public float[] convertViewCoordinatesToBitmap(float[] in) {
        float[] out = new float[in.length];
        ImageView view = GetImageView();
        Matrix matrix = view.getImageMatrix();
        Matrix inverse = new Matrix();
        matrix.invert(inverse);
        inverse.mapPoints(out, in);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        for( int i = 0; i < out.length; i++ )
            out[i] /= metrics.density;

        return out;
    }

    public float[] convertBitmapCoordinatesToView(float[] in) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float[] out = new float[in.length];
        for( int i = 0; i < out.length; i++ )
            out[i] = in[i] * metrics.density;

        ImageView view = GetImageView();
        Matrix matrix = view.getImageMatrix();
        matrix.mapPoints(out, out);
        return out;
    }
}
