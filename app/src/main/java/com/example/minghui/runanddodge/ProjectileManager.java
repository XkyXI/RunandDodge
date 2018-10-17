package com.example.minghui.runanddodge;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.ArrayList;

public class ProjectileManager {
    private ArrayList<Projectile> projectiles;
    private Player player;

    public ProjectileManager(Player player) {
        projectiles = new ArrayList<>();
        this.player = player;

        generateProjectiles();
    }

    private void generateProjectiles() {
        while (projectiles.size() < 10)
            projectiles.add(new Projectile_Direct(player.getPlayerRect()));
    }

    public boolean projectilHit() {
        for (Projectile p : projectiles) {
            if (p.playerCollide(player)) {
                Stats.hits++;
                return true;
            }
        }
        return false;
    }

    public void update() {
        for (Projectile p : projectiles)
            p.update();
        for (int i = 0; i < projectiles.size(); i++) {
            RectF r = projectiles.get(i).getRect();
            if (r.centerX() < 0 || r.centerX() > Consts.SCREEN_WIDTH || r.centerY() < 0 || r.centerY() > Consts.SCREEN_HEIGHT) {
                projectiles.remove(i);
                i--;
                projectiles.add(new Projectile_Direct(player.getPlayerRect()));
            }
        }
        projectilHit();

//        System.out.println("Hits: " + Stats.hits);
//        System.out.println("L: " + Stats.leftProjectileCount + ", R: " + Stats.rightProjectileCount);
    }

    public void draw(Canvas canvas) {
        for (Projectile p : projectiles)
            p.draw(canvas);
    }
}
