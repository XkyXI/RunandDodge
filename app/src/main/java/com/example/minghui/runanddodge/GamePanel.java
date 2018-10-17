package com.example.minghui.runanddodge;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;

    private Player player;
    private Point playerPoint;

    private JoystickController controller;

    private boolean movingPlayer;

    private int touchX, touchY;

    private ProjectileManager projectileManager;

    private DisplayStats displayStats;

    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);

        Consts.CURRENT_CONTEXT = context;
        Consts.INIT_TIME = System.currentTimeMillis();

        thread = new MainThread(getHolder(), this);

        player = new Player(new Rect(0, 0, 100, 100), Color.BLUE);
        playerPoint = new Point(Consts.SCREEN_WIDTH / 2 - player.getPlayerRect().width() / 2,
                Consts.SCREEN_HEIGHT / 2 - player.getPlayerRect().height() / 2);
        player.update(playerPoint);

        setFocusable(true);

        controller = new JoystickController();

        movingPlayer = false;

        projectileManager = new ProjectileManager(player);

        displayStats = new DisplayStats();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchX = (int)event.getX();
        touchY = (int)event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                controller.update(touchX, touchY);
                movingPlayer = true;
                break;
            case MotionEvent.ACTION_MOVE:
                controller.update(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                controller.reset();
                movingPlayer = false;
                break;
            case MotionEvent.ACTION_OUTSIDE:
                controller.reset();
                break;
        }

        return true;
//        return super.onTouchEvent(event);
    }

    public void updatePlayer(int x, int y) {
        int step = (int) (controller.getPower(x, y) * 10);
        int newX = (int) (playerPoint.x + step * Math.sin(controller.getAngle(x, y)));
        int newY = (int) (playerPoint.y + step * Math.cos(controller.getAngle(x, y)));
        playerPoint.set(newX, newY);
    }

    public void update() {
        player.update(playerPoint);

        if (movingPlayer) {
            updatePlayer(touchX, touchY);
        }
        projectileManager.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(Color.WHITE);
//        canvas.drawColor(Color.rgb(154, 205, 50));

        projectileManager.draw(canvas);

        player.draw(canvas);

        controller.draw(canvas);

        displayStats.draw(canvas);
    }
}
