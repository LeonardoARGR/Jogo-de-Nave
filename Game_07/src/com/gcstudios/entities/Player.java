package com.gcstudios.entities;

import java.awt.image.BufferedImage;
import com.gcstudios.main.Game;

public class Player extends Entity{

	public boolean left;
	public boolean right;
	
	public boolean isShooting;
	public boolean shoot;
	
	public int maxDelay = 20;
	public int delay = maxDelay;
	
	public int life = 8;
	
	public Player(int x, int y, int width, int height,double speed, BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		
		
	}
	
	public void tick() {
		 
		if(delay < maxDelay) {
			delay++;
		}
		
		depth = 2;
		
		//Sistema de movimentação
		
		if(right) {
			x+=speed;
		}else if(left) {
			x-=speed;
		}
		
		if(x > Game.WIDTH) {
			x = -16;
		}else if(x < -16) {
			x = Game.WIDTH;
		}
		
		//Sistema de Tiro
		
		if(isShooting) {
			if(delay >= maxDelay) {
				shoot = true;
				delay = 0;
			}
			
		}
		
		if(shoot) {
			shoot = false;
			int xx = this.getX();
			int yy = this.getY();
			Bullet bullet = new Bullet(xx, yy, 16, 16, 7, Game.spritesheet.getSprite(0, 16, 16, 16));
			Game.entities.add(bullet);
		}
		
		//Fim de jogo
		if(life <= 0) {
			System.exit(1);
		}
		
	}


}
