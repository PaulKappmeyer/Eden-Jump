package gamelogic;

import gamelogic.tiledMap.Map;

public class Leveldata {

	private Map map;
	private int playerX;
	private int playerY;
	
	public Leveldata(Map map, int playerX, int playerY) {
		this.map = map;
		this.playerX = playerX;
		this.playerY = playerY;
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
}
