package com.gcstudios.entities;

import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;

public class Bullet extends Entity{

	public Bullet(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		
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
