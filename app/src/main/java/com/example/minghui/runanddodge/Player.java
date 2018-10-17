package com.example.minghui.runanddodge;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

public class Player implements GameObject {

    private Rect rect;
    private int color;

    public Player(Rect rect, int color) {
        this.rect = rect;
        this.color = color;
    }

    public Rect getPlayerRect() {
        return rect;
    }
    public RectF getPlayerRectF() { return new RectF(rect); }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rect, paint);
    }

    @Override
    public void update() {

    }

    public void update(Point point) {
        // place the center of the player at the point
        if (point.x < 0)
            point.x = 0;
        else if (point.x + rect.width() > Consts.SCREEN_WIDTH)
            point.x = Consts.SCREEN_WIDTH - rect.width();
        if (point.y < 0)
            point.y = 0;
        else if (point.y + rect.width() > Consts.SCREEN_HEIGHT)
            point.y = Consts.SCREEN_HEIGHT - rect.width();

        rect.set(point.x, point.y, point.x + rect.width(), point.y + rect.height());
    }
}
