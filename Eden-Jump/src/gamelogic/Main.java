package gamelogic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import gameengine.GameBase;
import gameengine.PhysicsObject;
import gameengine.graphics.Camera;
import gameengine.input.KeyboardInputManager;
import gameengine.loaders.MapLoader;
import gamelogic.player.Player;
import gamelogic.tiledMap.Map;

public class Main extends GameBase{

	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 860;

	public static Player player;
	public static Camera camera;
	public static Map map;

	public static void main(String[] args) {
		Main main = new Main();
		main.start("Eden Jump", SCREEN_WIDTH, SCREEN_HEIGHT);
	}

	@Override
	public void init() {
		player = new Player(400, 400);
		player.walkSpeed = 500;
		player.jumpPower = 2300;
		PhysicsObject.GRAVITY = 70;

		try {
			map = MapLoader.loadMap(".\\maps\\map.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}

		camera = new Camera(SCREEN_WIDTH, SCREEN_HEIGHT);
		camera.setFocusedObject(player);
	}

	@Override
	public void update(float tslf) {
		if(KeyboardInputManager.isKeyDown(KeyEvent.VK_N)) init();
		if(KeyboardInputManager.isKeyDown(KeyEvent.VK_ESCAPE)) System.exit(0);

		player.update(tslf);

		camera.update(tslf);
	}

	@Override
	public void draw(Graphics g) {
		drawBackground(g);

		g.translate((int)-camera.getX(), (int)-camera.getY());

		map.draw(g);

		player.draw(g);
		
		camera.draw(g);
	}

	public void drawBackground(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
	}
}
