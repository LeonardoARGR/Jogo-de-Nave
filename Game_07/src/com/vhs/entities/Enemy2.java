package com.vhs.entities;

import java.awt.image.BufferedImage;

import com.vhs.main.Game;

public class Enemy2 extends Entity{
	
	public boolean right;
	public boolean left;
	
	public boolean shoot;
	public int maxDelay = 50;
	public int delay = maxDelay;

	public Enemy2(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		life = 3;
	}
	
	public void tick() {
		
		depth = 2;
		
		if(y != 16) {
			y++;
		}else {
			if(delay < maxDelay) {
				delay++;
			}else if(delay >= maxDelay) {
				delay = 0;
				shoot = true;
			}
			
			if(getX() <= 32) {
				right = true;
				left = false;
			}else if(getX() >= (Game.WIDTH) - 48) {
				left = true;
				right = false;
			}
		}
		
		if(right == true) {
			x++;
		}else if(left == true) {
			x--;
		}
		
		
		if(shoot) {
			shoot = false;
			int xx = this.getX();
			int yy = this.getY();
			EnemyBullet enemyBullet = new EnemyBullet(xx, yy, 16, 16, 2, Game.spritesheet.getSprite(0, 16, 16, 16));
			Game.entities.add(enemyBullet);
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
		}
	}

}
