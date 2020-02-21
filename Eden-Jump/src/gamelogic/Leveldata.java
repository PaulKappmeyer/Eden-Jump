package gamelogic;

import gamelogic.enemies.Enemy;
import gamelogic.tiledMap.Map;

public class Leveldata {

	private Map map;
	private int playerX;
	private int playerY;
	private Enemy[] enemies;
	
	public Leveldata(Map map, int playerX, int playerY, Enemy[] enemies) {
		this.map = map;
		this.playerX = playerX;
		this.playerY = playerY;
		this.enemies = enemies;
	}
	
	//-----------------------------Getters
	public Map getMap() {
		return map;
	}
	
	public int getPlayerX() {
		return playerX;
	}
	
	public int getPlayerY() {
		return playerY;
	}
	
	public Enemy[] getEnemies() {
		return enemies;
	}
}
