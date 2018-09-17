package com.pocket.solution.boardtrainer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.res.*;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Pair;
import java.util.*;

public class BoardData implements java.io.Serializable {

    private List<Pair<Float, Float>> markedHolds = new ArrayList<>();

    static public Bitmap LoadBitmap(Resources res, int id) {
        BitmapFactory.Options myOptions = new BitmapFactory.Options();
        myOptions.inDither = true;
        myOptions.inScaled = false;
        myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// important
        myOptions.inPurgeable = true;

        Bitmap resourceBitmap = BitmapFactory.decodeResource(res, id, myOptions);
        Bitmap workingBitmap = Bitmap.createBitmap(resourceBitmap);
        return workingBitmap.copy(Bitmap.Config.ARGB_8888, true);
    }

    public void MarkAllHolds(Bitmap board) {
        for (Pair<Float, Float> hold: markedHolds ) {
            drawMark(board, hold.first, hold.second);
        }
    }

    public void markHold(Bitmap board, float cx, float cy) {
        markedHolds.add(new Pair<>(cx, cy));
        drawMark(board, cx, cy);
    }

    private void drawMark(Bitmap board, float cx, float cy) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        Canvas canvas = new Canvas(board);
        canvas.drawCircle(cx, cy, 20, paint);
    }
}
