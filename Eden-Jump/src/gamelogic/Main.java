package gamelogic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import gameengine.GameBase;
import gameengine.graphics.Camera;
import gameengine.hitbox.CollisionMatrix;
import gameengine.input.KeyboardInputManager;
import gameengine.loaders.MapLoader;
import gamelogic.player.Player;
import gamelogic.tiledMap.Map;
import gamelogic.tiledMap.Spikes;

public class Main extends GameBase{

	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 860;

	public static Player player;
	public static Camera camera;
	public static Map map;
	private Leveldata leveldata;
	
	private boolean isPlayerAlive = true;
	private ScreenTransition screenTransition = new ScreenTransition();
	
	private boolean DEBUGGING = false;
	
	public static void main(String[] args) {
		Main main = new Main();
		main.start("Eden Jump", SCREEN_WIDTH, SCREEN_HEIGHT);
	}

	@Override
	public void init() {
		GameResources.load();

		leveldata = null;
		try {
			leveldata = MapLoader.loadMap(".\\maps\\map.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		map = leveldata.getMap();
		player = new Player(leveldata.getPlayerX() * map.getTileSize(), leveldata.getPlayerY() * map.getTileSize());
		
		camera = new Camera(SCREEN_WIDTH, SCREEN_HEIGHT);
		camera.setFocusedObject(player);
	}
	
	public void restart() {
		if(DEBUGGING) {
			restartLevel();
			return;
		}
		isPlayerAlive = false;
		screenTransition.activate();
	}

	public void restartLevel() {
		player = new Player(leveldata.getPlayerX() * map.getTileSize(), leveldata.getPlayerY() * map.getTileSize());
		camera = new Camera(SCREEN_WIDTH, SCREEN_HEIGHT);
		camera.setFocusedObject(player);
		isPlayerAlive = true;
	}
	
	@Override
	public void update(float tslf) {
		if(KeyboardInputManager.isKeyDown(KeyEvent.VK_N)) init();
		if(KeyboardInputManager.isKeyDown(KeyEvent.VK_ESCAPE)) System.exit(0);

		if(isPlayerAlive && !screenTransition.isActive()) {
			player.update(tslf);
			
			//Player death
			if(map.getFullHeight() + 100 < player.getY()) restart();
			if(player.getCollisionMatrix()[CollisionMatrix.BOT] instanceof Spikes) restart();
			if(player.getCollisionMatrix()[CollisionMatrix.TOP] instanceof Spikes) restart();
			if(player.getCollisionMatrix()[CollisionMatrix.LEF] instanceof Spikes) restart();
			if(player.getCollisionMatrix()[CollisionMatrix.RIG] instanceof Spikes) restart();
			
			camera.update(tslf);
		}
		
		screenTransition.update(tslf);
		if(screenTransition.isActive() && !screenTransition.isActivating() && !screenTransition.isDeactivating()) {
			restartLevel();
			screenTransition.deactivate();
		}
	}

	@Override
	public void draw(Graphics g) {
		drawBackground(g);

		g.translate((int)-camera.getX(), (int)-camera.getY());

		map.draw(g);
		
		player.draw(g);
		
		if(Camera.SHOW_CAMERA) camera.draw(g); //used for debugging
		
		g.translate((int)+camera.getX(), (int)+camera.getY());
		
		screenTransition.draw(g);
	}

	public void drawBackground(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
	}
}
