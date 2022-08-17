package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;

public class Explosion extends Entity{
	
	public int index = 0;
	public int maxIndex = 2;
	public int frames = 0;
	public int maxFrames;
	
	public BufferedImage[] explosionSprites;
	public int explosionChoice;

	public Explosion(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		
		explosionChoice = Entity.rand.nextInt(2);
		
		explosionSprites = new BufferedImage[6];
		
		for(int i = 0; i < explosionSprites.length; i++) {
			explosionSprites[i] = Game.spritesheet.getSprite(16 + (i*16), 16, 16, 16);
		}
		
	}
	
	public void tick() {
		
		if(index == maxIndex) {
			maxFrames = 10;
		}else {
			maxFrames = 7;
		}
		
		frames++;
		if(frames >= maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex) {
				Game.entities.remove(this);
				return;
			}
		}
		
	}
	
	public void render(Graphics g) {
		if(explosionChoice == 0) {
			g.drawImage(explosionSprites[index], this.getX(), this.getY(), null);
		}else {
			g.drawImage(explosionSprites[index+3], this.getX(), this.getY(), null);
		}
	}
	
}
