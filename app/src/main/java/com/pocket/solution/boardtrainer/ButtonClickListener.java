package com.pocket.solution.boardtrainer;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;

public class ButtonClickListener implements View.OnClickListener {

    final Button button;
    final BoardTouchListener boardTouchListener;
    int clicks = 0;

    ButtonClickListener(Button button, BoardTouchListener boardTouchListener) {
        this.button = button;
        this.button.setBackgroundColor(Color.WHITE);
        this.button.setText("Off");
        this.boardTouchListener = boardTouchListener;
    }

    @Override
    public void onClick(View v) {
        clicks++;
        int state = clicks % 3;
        switch( state ) {
            case 0:
                this.button.setBackgroundColor(Color.WHITE);
                this.button.setText("Off");
                //boardTouchListener.EnableScroll();
                break;
            case 1:
                this.button.setBackgroundColor(Color.GREEN);
                this.button.setText("Left hand");
                //boardTouchListener.EnableSetLeftHand();
                break;
            case 2:
                this.button.setBackgroundColor(Color.BLUE);
                this.button.setText("Right hand");
                //boardTouchListener.EnableSetRightHand();
                break;
        }
    }
}
