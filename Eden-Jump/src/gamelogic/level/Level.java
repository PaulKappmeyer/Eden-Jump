package gamelogic.level;

import java.awt.Graphics;
import java.util.ArrayList;

import gameengine.PhysicsObject;
import gameengine.graphics.Camera;
import gameengine.loaders.Mapdata;
import gamelogic.GameResources;
import gamelogic.Main;
import gamelogic.enemies.Enemy;
import gamelogic.player.Player;
import gamelogic.tiledMap.Map;
import gamelogic.tiles.Flag;
import gamelogic.tiles.SolidTile;
import gamelogic.tiles.Spikes;
import gamelogic.tiles.Tile;

public class Level {

	private Leveldata leveldata;
	private Map map;
	private Enemy[] enemies;
	public static Player player;
	private Camera camera;
	
	private boolean active;
	private boolean playerDead;
	
	private ArrayList<Enemy> enemiesList = new ArrayList<>();
	
	public Level(Leveldata leveldata) {
		this.leveldata = leveldata;
		
		Mapdata mapdata = leveldata.getMapdata();
		int width = mapdata.getWidth();
		int height = mapdata.getHeight();
		int tileSize = mapdata.getTileSize();
		int [][]values = mapdata.getValues();
		
		Tile[][] tiles = new Tile[width][height];
		for (int x = 0; x < width; x++) {
			int xPosition = x * tileSize;
			for (int y = 0; y < height; y++) {
				int yPosition = y * tileSize;
				
				tiles[x][y] = new Tile(xPosition, yPosition, tileSize, null, false);
				if(values[x][y] == 0) tiles[x][y] = new Tile(xPosition, yPosition, tileSize, null, false); //Air
				else if(values[x][y] == 1) tiles[x][y] = new SolidTile(xPosition, yPosition, tileSize, GameResources.solid);
				else if(values[x][y] == 2) tiles[x][y] = new Spikes(xPosition, yPosition, tileSize, Spikes.HORIZONTAL_DOWNWARDS);
				else if(values[x][y] == 3) tiles[x][y] = new Spikes(xPosition, yPosition, tileSize, Spikes.HORIZONTAL_UPWARDS);
				else if(values[x][y] == 4) tiles[x][y] = new Spikes(xPosition, yPosition, tileSize, Spikes.VERTICAL_LEFTWARDS);
				else if(values[x][y] == 5) tiles[x][y] = new Spikes(xPosition, yPosition, tileSize, Spikes.VERTICAL_RIGHTWARDS);
				else if(values[x][y] == 6) tiles[x][y] = new SolidTile(xPosition, yPosition, tileSize, GameResources.dirt);
				else if(values[x][y] == 7) tiles[x][y] = new SolidTile(xPosition, yPosition, tileSize, GameResources.gras);
				else if(values[x][y] == 8) enemiesList.add(new Enemy(xPosition, yPosition, null)); //TODO: objects vs tiles
				else if(values[x][y] == 9) tiles[x][y] = new Flag(xPosition, yPosition, tileSize, GameResources.flag);
			}
		}
		
		map = new Map(width, height, tileSize, tiles);
		enemies = new Enemy[enemiesList.size()];
		camera = new Camera(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, 0, map.getFullWidth(), map.getFullHeight());
		restartLevel();
	}
	
	public void restartLevel() {
		for (int i = 0; i < enemiesList.size(); i++) {
			enemies[i] = new Enemy(enemiesList.get(i).getX(), enemiesList.get(i).getY(), this); 
		}
		player = new Player(leveldata.getPlayerX() * map.getTileSize(), leveldata.getPlayerY() * map.getTileSize(), this);
		camera.setFocusedObject(player);
		
		active = true;
		playerDead = false;
	}
	
	private void onPlayerDeath() {
		active = false;
		playerDead = true;
	}
	
	public void update(float tslf) {
		if(active) {
			//Update the player
			player.update(tslf);
			
			//Player death
			if(map.getFullHeight() + 100 < player.getY()) onPlayerDeath();
			if(player.getCollisionMatrix()[PhysicsObject.BOT] instanceof Spikes) onPlayerDeath();
			if(player.getCollisionMatrix()[PhysicsObject.TOP] instanceof Spikes) onPlayerDeath();
			if(player.getCollisionMatrix()[PhysicsObject.LEF] instanceof Spikes) onPlayerDeath();
			if(player.getCollisionMatrix()[PhysicsObject.RIG] instanceof Spikes) onPlayerDeath();
			
			//Update the enemies
			for (int i = 0; i < enemies.length; i++) {
				enemies[i].update(tslf);
				if(player.getHitbox().isIntersecting(enemies[i].getHitbox())) onPlayerDeath();
			}
			
			//Update the map
			map.update(tslf);
			
			//Update the camera
			camera.update(tslf);
		}
	}
	
	public void draw(Graphics g) {
		g.translate((int)-camera.getX(), (int)-camera.getY());

		//Draw the map
		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				Tile tile = map.getTiles()[x][y];
				if(tile == null) continue;
				if(camera.isVisibleOnCamera(tile.getX(), tile.getY(), tile.getSize(), tile.getSize())) tile.draw(g);
			}
		}
		
		//Draw the enemies
		for (int i = 0; i < enemies.length; i++) {
			enemies[i].draw(g);
		}
		
		//Draw the player
		player.draw(g);
		
		//used for debugging
		if(Camera.SHOW_CAMERA) camera.draw(g); 
		
		g.translate((int)+camera.getX(), (int)+camera.getY());
	}
	
	//---------------------------------------------------------Getters
	public boolean isActive() {
		return active;
	}
	
	public boolean isPlayerDead() {
		return playerDead;
	}
	
	public Map getMap() {
		return map;
	}
	
	public Player getPlayer() {
		return player;
	}
}
