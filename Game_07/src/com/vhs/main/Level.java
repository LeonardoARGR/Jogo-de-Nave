package com.vhs.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Level {
	
	public static int frames;
	public static int seconds;
	public static int minutes;
	
	public int progress = 0;
	public int progressY = (((Game.HEIGHT*Game.SCALE) / 2) - 150) + 300;
	
	public void tick() {
		frames++;
		if(frames % 20 == 0) {
			if(progress <= 300) {
				progress+=3;
				progressY-=3;
			}
		}
		
		if(progress >= 300) {
			Game.GAME_STATE = "NEXT_LEVEL";
			progress = 0;
			progressY = (((Game.HEIGHT*Game.SCALE) / 2) - 150) + 300;
		}
		
		if(frames >= 60) {
			frames = 0;
			seconds++;
			if(seconds >= 60) {
				seconds = 0;
				minutes++;
			}
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
		
		g.setColor(Color.black);
		g.fillRect((Game.WIDTH*Game.SCALE) - 60, ((Game.HEIGHT*Game.SCALE) / 2) - 150, 30, 300);
		g.setColor(Color.white);
		g.drawRect((Game.WIDTH*Game.SCALE) - 60, ((Game.HEIGHT*Game.SCALE) / 2) - 150, 30, 300);
		g.fillRect((Game.WIDTH*Game.SCALE) - 60, progressY, 30, progress);
		
	}

}
