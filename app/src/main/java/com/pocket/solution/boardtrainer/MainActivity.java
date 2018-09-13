package com.pocket.solution.boardtrainer;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import java.lang.*;
import android.graphics.*;
import android.view.View.*;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BitmapFactory.Options myOptions = new BitmapFactory.Options();
        myOptions.inDither = true;
        myOptions.inScaled = false;
        myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// important
        myOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.board, myOptions);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);

        Bitmap workingBitmap = Bitmap.createBitmap(bitmap);
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);

        Canvas canvas = new Canvas(mutableBitmap);
        //canvas.drawCircle(60, 50, 25, paint);

        ImageView imageView = (ImageView)findViewById(R.id.board);
        imageView.setAdjustViewBounds(true);
        imageView.setImageBitmap(mutableBitmap);
        //imageView.setScaleX(4);
        //imageView.setScaleY(4);

        // implement board view scrolling
        final ImageView boardView = this.findViewById(R.id.board);
        BoardTouchListener boardTouchListener = new BoardTouchListener(boardView);
        imageView.setOnTouchListener(boardTouchListener);

        // implement hold marking button
        //final Button button = this.findViewById(R.id.mark_hold);
        //OnClickListener buttonListener = new ButtonClickListener(button, boardTouchListener);
        //button.setOnClickListener(buttonListener);
    }
}
