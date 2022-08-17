package com.gcstudios.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Menu {
	
	public boolean enter;
	
	public int frames = 0;
	public int maxFrames = 40;
	public boolean show = true;
	
	public void tick() {
		frames++;
		if(frames >= maxFrames) {
			frames = 0;
			if(show) {
				show = false;
			}else {
				show = true;
			}
		}
		if(enter) {
			enter = false;
			Game.GAME_STATE = "NORMAL";
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 28));
		if(show) {
			g.drawString("Pressione ENTER para começar", 90, (Game.HEIGHT*Game.SCALE)/2 - 25);
		}
	}
	
}
