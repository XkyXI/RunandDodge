package com.example.minghui.runanddodge;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

public class Projectile_Direct extends Projectile {
    // randomly start from either left or right side
    // fixed speed
    // moves directly toward the target player


    public Projectile_Direct(Rect target) {
        super();

        this.startingLoc = generateStartingLocation();
        this.rect = new RectF(startingLoc.x, startingLoc.y, startingLoc.x + 20, startingLoc.y + 20);
        this.speed = 10;
        this.direction = calculateDirection(startingLoc, new Point(target.centerX(), target.centerY()));
        this.color = Color.GREEN;

//        this.effect = BitmapFactory.decodeResource(Consts.CURRENT_CONTEXT.getResources(), R.drawable.fire_effect1);
//        Matrix matrix = new Matrix();
//        matrix.setRotate(360.0f - (float)Math.toDegrees(direction));
//        this.effect = Bitmap.createBitmap(effect, 0, 0, effect.getWidth(), effect.getHeight(), matrix, true);

//        Animation anim = new Animation(new Bitmap[]{effect}, 2);

    }

    private Point generateStartingLocation() {
        int offset = Consts.PROJECTILE_DEFAULT_SIZE / 2 + 1;
        int x = Math.random() < 0.5 ? offset : Consts.SCREEN_WIDTH - offset;
        int y = (int) (Math.random() * Consts.SCREEN_HEIGHT);
        return new Point(x, y);
    }

    private double calculateDirection(Point start, Point end) {
        int dx = end.x - start.x;
        int dy = end.y - start.y;
        return Math.atan2(dx, dy);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
//        canvas.drawBitmap(effect, rect.centerX(), rect.centerY(), new Paint());
    }
}
