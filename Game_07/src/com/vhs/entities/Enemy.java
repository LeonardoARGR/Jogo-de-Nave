package com.vhs.entities;

import java.awt.image.BufferedImage;

import com.vhs.main.Game;

public class Enemy extends Entity{
	
	public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		life = 1;
		
		maskx = 3;
		masky = 3;
		maskw = 10;
		maskh = 10;
	}
	
	public void tick() {
		y += speed;
		if(y > Game.HEIGHT+16) {
			Game.entities.remove(this);
			
		}
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Bullet) {
				if(Entity.isColidding(this, e)) {
					life--;
					Game.entities.remove(e);
					if(life == 0) {
						Explosion explosion = new Explosion(x, y, 16, 16, 0, null);
						Game.entities.add(explosion);
						Game.score++;
						Game.entities.remove(this);
						return;
					}
					break;
				}
			}
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
