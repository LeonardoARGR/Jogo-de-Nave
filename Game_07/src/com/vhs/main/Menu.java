package com.vhs.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Menu {
	
	public boolean enter;
	public boolean up;
	public boolean down;
	
	public int selected = 0;
	
	public int frames = 0;
	public int maxFrames = 40;
	public boolean show = true;
	
	public void tick() {
		if(Game.GAME_STATE == "MENU") {
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
		}else if(Game.GAME_STATE == "NEXT_LEVEL") {
			if(up || down) {
				up = false;
				down = false;
				if(selected == 0) {
					selected = 1;
				}else if(selected == 1) {
					selected = 0;
				}
			}else if(enter) {
				enter = false;
				if(selected == 0) {
					Game.nextLevel();
					return;
				}else if(selected == 1) {
					System.exit(1);
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(Game.GAME_STATE == "MENU") {
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 28));
			if(show) {
				g.drawString("Pressione ENTER para começar", 90, (Game.HEIGHT*Game.SCALE)/2 - 25);
			}
		}else if(Game.GAME_STATE == "NEXT_LEVEL") {
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 40));
			g.drawString("NÍVEL " + Game.GAME_LEVEL + " CONCLUÍDO", 100, 200);
			
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("Continuar", ((Game.WIDTH*Game.SCALE)/2) - 75, 360);
			g.drawString("Sair", ((Game.WIDTH*Game.SCALE)/2) - 35, 460);
			if(selected == 0) {
				g.drawString(">", ((Game.WIDTH*Game.SCALE)/2) - 95, 360);
			}else if(selected == 1) {
				g.drawString(">", ((Game.WIDTH*Game.SCALE)/2) - 55, 460);
			}
		}
	}
	
}
