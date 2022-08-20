package com.vhs.entities;

import java.awt.image.BufferedImage;

import com.vhs.main.Game;

public class EnemyBullet extends Entity{

	public EnemyBullet(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		maskx = 7;
		masky = 5;
		maskw = 2;
		maskh = 6;
	}
	
	public void tick() {
		
		depth = 1;
		
		y+=speed;
		
		if(y > Game.HEIGHT) {
			Game.entities.remove(this);
			return;
		}
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Player) {
				if(Entity.isColidding(this, e)) {
					Game.player.life--;
					Game.ui.damage = true;
					Game.entities.remove(this);
					return;
				}
			}
		}
	}

}
