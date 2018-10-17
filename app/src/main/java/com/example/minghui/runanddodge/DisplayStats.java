package com.example.minghui.runanddodge;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class DisplayStats {

    public DisplayStats() {}

    public void draw(Canvas canvas) {
        drawNumOfHits(canvas);
        drawTimeElapsed(canvas);
    }

    private void drawNumOfHits(Canvas canvas) {
        Paint paint = new Paint();
        paint.setTextSize(50);
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Hits: " + Stats.hits, 50, 100, paint);
    }

    private void drawTimeElapsed(Canvas canvas) {
        Paint paint = new Paint();
        paint.setTextSize(50);
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Time: " + (System.currentTimeMillis() - Consts.INIT_TIME) / 1000, 50, 250, paint);
        System.out.println(Consts.INIT_TIME);
    }
}
