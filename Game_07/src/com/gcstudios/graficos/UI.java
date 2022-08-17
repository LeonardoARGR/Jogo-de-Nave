package com.gcstudios.graficos;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;

public class UI {
	
	public static int frames;
	public static int seconds;
	public static int minutes;
	
	public BufferedImage[] heart;
	public boolean damage;
	public int index = 0;
	public int index2 = 0;
	public int maxIndex = 4;
	
	public int progress = 0;
	public int progressY = (((Game.HEIGHT*Game.SCALE) / 2) - 150) + 300;
	
	public UI() {
		heart = new BufferedImage[4];
		
		for(int i = 0; i < heart.length; i++) {
			heart[i] = Game.spritesheet.getSprite(0 + (16*i), 32, 16, 16);
		}
	}
	
	
	public void tick() {
		frames++;
		if(frames % 20 == 0) {
			if(progress <= 300) {
				progress++;
				progressY--;
			}
		}
		
		if(frames >= 60) {
			frames = 0;
			seconds++;
			if(seconds >= 60) {
				seconds = 0;
				minutes++;
			}
		}
		
		if(damage) {
			if(Game.player.life >= 4) {
				index2++;
			}else {
				index++;
			}
			damage = false;
		}
		
	}

	public void render(Graphics g) {
		
		String formatTime = "";
		if(minutes < 10) {
			formatTime += "0" + minutes + ":";
		}else {
			formatTime += minutes + ":";
		}
		
		if(seconds < 10) {
			formatTime += "0" + seconds;
		}else {
			formatTime += seconds;
		}
			
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 23));
		g.drawString(formatTime, (Game.WIDTH*Game.SCALE)-70 , 30);
			
		g.drawString("Score: " + Game.score, 10, 30);
		
		g.setColor(Color.black);
		g.fillRect((Game.WIDTH*Game.SCALE) - 60, ((Game.HEIGHT*Game.SCALE) / 2) - 150, 30, 300);
		g.setColor(Color.white);
		g.drawRect((Game.WIDTH*Game.SCALE) - 60, ((Game.HEIGHT*Game.SCALE) / 2) - 150, 30, 300);
		g.fillRect((Game.WIDTH*Game.SCALE) - 60, progressY, 30, progress);
		
		if(Game.player.life > 0) {
			g.drawImage(heart[index], 20, Game.HEIGHT*Game.SCALE-50, 32, 32, null);
			if(Game.player.life > 4) {
				g.drawImage(heart[index2], 55, Game.HEIGHT*Game.SCALE-50, 32, 32, null);
			}
		}
		
	}
	
}
