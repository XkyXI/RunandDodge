package com.example.minghui.runanddodge;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

public class Projectile implements GameObject {

    protected RectF rect;
    protected double speed;
    protected double direction;
    protected Point startingLoc;
    protected int color;
    protected boolean alreadyCollided;

    protected Bitmap effect;
    protected AnimationManager animationManager;



    public Projectile() {
        this.alreadyCollided = false;
        this.effect = null;
        this.animationManager = null;
    }

    public Projectile(double speed, double direction, Point startingLocation, int color) {
        this.startingLoc = startingLocation;
        this.rect = new RectF(startingLocation.x - 10, startingLocation.y - 10,
                startingLocation.x + 10, startingLocation.y + 10);
        this.speed = speed;
        this.direction = direction;
        this.color = color;
    }

    public boolean playerCollide(Player player) {
        if (!alreadyCollided && RectF.intersects(rect, player.getPlayerRectF())) {
            alreadyCollided = true;
            return true;
        }
        return false;
    }

    public RectF getRect() {
        return rect;
    }

    @Override
    public void draw(Canvas canvas) {
        if (rect == null || startingLoc == null || alreadyCollided) return;

        Paint paint = new Paint();
        paint.setColor(color);

//        canvas.save();
//        canvas.rotate((float)Math.toDegrees(direction));
        canvas.drawRect(rect, paint);
//        canvas.restore();
//        drawPath(canvas);

    }

    @Override
    public void update() {
        float xOffset = (float) (speed * Math.sin(direction));
        float yOffset = (float) (speed * Math.cos(direction));
        rect.offset(xOffset, yOffset);
    }

    public void drawPath(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[]{40, 20}, 0));
        paint.setStrokeWidth(2);

        float slope = (float) (Math.cos(direction) / Math.sin(direction));
        int x = startingLoc.x < Consts.SCREEN_WIDTH / 2 ? Consts.SCREEN_WIDTH : 0;
        float y = (x - startingLoc.x) * slope + startingLoc.y;

        canvas.drawLine(startingLoc.x, startingLoc.y + 10, x, y + 10, paint);
    }
}
