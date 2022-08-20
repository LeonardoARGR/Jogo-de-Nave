package com.vhs.entities;

import com.vhs.main.Game;

public class EnemySpawn {
	
	public int targetTime = 60*2;
	public int curTime = 0;
	
	public int enemies = 0;
	public int maxEnemies = 4;
	
	public void tick() {
		curTime++;
		if(curTime >= targetTime) {
			curTime = 0;
			int xx = Entity.rand.nextInt(Game.WIDTH-80) + 32;
			int yy = 0;
			if(Game.GAME_LEVEL == 1) {
				//Invocando o primeiro inimigo
				Enemy enemy = new Enemy(xx, yy, 16, 16, Entity.rand.nextInt(2)+1, Game.spritesheet.getSprite(16, 0, 16, 16));
				Game.entities.add(enemy);
			}else if(Game.GAME_LEVEL == 2) {
				//Invocando o primeiro inimigo
				Enemy enemy1 = new Enemy(xx, yy, 16, 16, Entity.rand.nextInt(2)+1, Game.spritesheet.getSprite(16, 0, 16, 16));
				Game.entities.add(enemy1);
				
				//Invocando o segundo inimigo
				if(calculateEnemies() < 2 && enemies < maxEnemies) {
					Enemy2 enemy2 = new Enemy2(32, 0, 16, 16, 2, Game.spritesheet.getSprite(32, 0, 16, 16));
					Game.entities.add(enemy2);
					enemies++;
				}
			}
		}
		
	}
	
	public int calculateEnemies() {
		int enemies = 0;
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Enemy2) {
				enemies++;
			}
		}
		return enemies;
	}
}
