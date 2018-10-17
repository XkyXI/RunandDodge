package com.example.minghui.runanddodge;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class JoystickController {

    private int CENTER_X = Consts.SCREEN_WIDTH / 2;
    private int CENTER_Y = 85 * Consts.SCREEN_HEIGHT / 100;

    private Bitmap ball;
    private Bitmap bound;
    private int boundRadius;

    private int ballX, ballY;


    public JoystickController() {
        ball = BitmapFactory.decodeResource(Consts.CURRENT_CONTEXT.getResources(), R.drawable.ball);
        ball = scaleDown(ball, Consts.SCREEN_WIDTH, 12, true);
        bound = BitmapFactory.decodeResource(Consts.CURRENT_CONTEXT.getResources(), R.drawable.bound);
        bound = scaleDown(bound, Consts.SCREEN_WIDTH, 4, true);

        boundRadius = 95 * bound.getWidth() / 200;

        ballX = CENTER_X;
        ballY = CENTER_Y;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bound, CENTER_X - bound.getWidth() / 2, CENTER_Y - bound.getHeight() / 2, null);
        canvas.drawBitmap(ball, ballX - ball.getWidth() / 2, ballY - ball.getHeight() / 2, null);
    }

    public void reset() {
        ballX = CENTER_X;
        ballY = CENTER_Y;
    }

    public void update(int movedX, int movedY) {
        int distance = calculateDistance(movedX, movedY);
        double angle = getAngle(movedX, movedY);

        if (distance > boundRadius) {
            ballX = (int) (CENTER_X + (boundRadius * Math.sin(angle)));
            ballY = (int) (CENTER_Y + (boundRadius * Math.cos(angle)));
        }
        else {
            ballX = movedX;
            ballY = movedY;
        }
    }

    public double getPower(int movedX, int movedY) {
        int distance = calculateDistance(movedX, movedY);
        return distance > boundRadius ? 1.0 : ((double) distance / boundRadius);
    }

    public double getAngle(int movedX, int movedY) {
        double dx = movedX - CENTER_X;
        double dy = movedY - CENTER_Y;
        return Math.atan2(dx, dy);
    }

    private int calculateDistance(int movedX, int movedY) {
        return (int) (Math.sqrt(Math.pow(movedX - CENTER_X, 2) + Math.pow(movedY - CENTER_Y, 2)));
    }

    private static Bitmap scaleDown(Bitmap image, int width, int ratio, boolean filter) {
        int imgWidth = image.getWidth();
        double scaleRatio = imgWidth / width;
        int newImgWidth = (int) (imgWidth / (scaleRatio * ratio));
        int newImgHeight = (int) (image.getHeight() / (scaleRatio * ratio));
        return Bitmap.createScaledBitmap(image, newImgWidth, newImgHeight, filter);
    }
}
