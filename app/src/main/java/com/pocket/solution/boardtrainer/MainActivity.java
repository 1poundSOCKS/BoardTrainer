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
    private BoardData boardData;

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

        this.boardData = new BoardData();
        Bitmap board = BoardData.LoadBitmap(getResources(), R.drawable.board);

        ImageView imageView = GetImageView();
        imageView.setAdjustViewBounds(true);
        imageView.setImageBitmap(board);

        // board touch control
        final ImageView boardView = this.findViewById(R.id.board);
        boardTouchListener = new BoardTouchListener(this);
        imageView.setOnTouchListener(boardTouchListener);

        // implement hold marking button
        //final Button button = this.findViewById(R.id.mark_hold);
        //OnClickListener buttonListener = new ButtonClickListener(button, boardTouchListener);
        //button.setOnClickListener(buttonListener);
    }

    @Override
    public void onBackPressed() {
        if( boardTouchListener != null )
            boardTouchListener.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("BoardData", this.boardData);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.boardData = (BoardData)savedInstanceState.getSerializable("BoardData");
        if( this.boardData != null ) {
            ImageView imageView = findViewById(R.id.board);
            Bitmap board = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
            this.boardData.MarkAllHolds(board);
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
