package com.vhs.entities;

import java.awt.image.BufferedImage;

import com.vhs.main.Game;

public class Bullet extends Entity{

	public Bullet(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		maskx = 7;
		masky = 5;
		maskw = 2;
		maskh = 6;
	}
	
	public void tick() {
		
		depth = 1;
		
		y-=speed;
		
		if(y < -16) {
			Game.entities.remove(this);
			return;
		}
		
	}

}
