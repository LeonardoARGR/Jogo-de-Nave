package com.vhs.graficos;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.vhs.main.Game;

public class UI {
	

	
	public BufferedImage[] heart;
	public boolean damage;
	public int index = 0;
	public int index2 = 0;
	public int maxIndex = 4;
	
	
	public UI() {
		heart = new BufferedImage[4];
		
		for(int i = 0; i < heart.length; i++) {
			heart[i] = Game.spritesheet.getSprite(0 + (16*i), 32, 16, 16);
		}
	}
	
	
	public void tick() {
		
		
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
		
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 23));
			
		g.drawString("Score: " + Game.score, 10, 30);
		
		
		
		if(Game.player.life > 0) {
			g.drawImage(heart[index], 20, Game.HEIGHT*Game.SCALE-50, 32, 32, null);
			if(Game.player.life > 4) {
				g.drawImage(heart[index2], 55, Game.HEIGHT*Game.SCALE-50, 32, 32, null);
			}
		}
		
	}
	
}
