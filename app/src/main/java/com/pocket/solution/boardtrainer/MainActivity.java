package com.pocket.solution.boardtrainer;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.*;
import android.graphics.drawable.*;
import android.widget.*;
import android.view.*;
import java.lang.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView switcherView = this.findViewById(R.id.img);

        switcherView.setOnTouchListener(new View.OnTouchListener() {

            float mx = 0 , my = 0;

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
                        float scrollX = switcherView.getScrollX();
                        float scrollY = switcherView.getScrollY();
                        float newScrollX = scrollX + scrollByX;
                        float newScrollY = scrollY + scrollByY;
                        switcherView.scrollTo((int) newScrollX, (int) newScrollY);
                        mx = curX;
                        my = curY;
                        break;
                    case MotionEvent.ACTION_UP:
                        curX = event.getX();
                        curY = event.getY();
                        switcherView.scrollBy((int) (mx - curX), (int) (my - curY));
                        break;
                }

                return true;
            }
        });
    }
}
