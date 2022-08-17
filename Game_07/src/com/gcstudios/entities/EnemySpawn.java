package com.gcstudios.entities;

import com.gcstudios.main.Game;

public class EnemySpawn {
	
	public int targetTime = 60*2;
	public int curTime = 0;
	
	public void tick() {
		curTime++;
		if(curTime >= targetTime) {
			curTime = 0;
			int xx = Entity.rand.nextInt(Game.WIDTH-16);
			int yy = 0;
			
			Enemy enemy = new Enemy(xx, yy, 16, 16, Entity.rand.nextInt(2)+1, Game.spritesheet.getSprite(16, 0, 16, 16));
			Game.entities.add(enemy);
		}
		
	}
}
