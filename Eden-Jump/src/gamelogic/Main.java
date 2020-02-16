package gamelogic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import gameengine.GameBase;
import gameengine.GameObject;
import gameengine.graphics.Camera;
import gameengine.hitbox.RectHitbox;
import gameengine.input.KeyboardInputManager;
import gamelogic.player.Player;

public class Main extends GameBase{

	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 860;

	public static Player player;
	public static Camera camera;
	public static RectHitbox[] obstacles;

	public static void main(String[] args) {
		Main main = new Main();
		main.start("Eden Jump", SCREEN_WIDTH, SCREEN_HEIGHT);
	}

	@Override
	public void init() {
		player = new Player(400, 400);
		obstacles = new RectHitbox[6];
		obstacles[0] = new RectHitbox(new GameObject(0, SCREEN_HEIGHT), 0, 0, 1000, 100);
		obstacles[1] = new RectHitbox(new GameObject(600, SCREEN_HEIGHT - 35), 0, 0, 100, 100);
		obstacles[2] = new RectHitbox(new GameObject(100, SCREEN_HEIGHT - 200), 0, 0, 100, 100);
		obstacles[3] = new RectHitbox(new GameObject(300, SCREEN_HEIGHT - 35), 0, 0, 100, 100);
		obstacles[4] = new RectHitbox(new GameObject(300, SCREEN_HEIGHT + 200), 0, 0, 1000, 100);
		obstacles[5] = new RectHitbox(new GameObject(700, SCREEN_HEIGHT - 200), 0, 0, 100, 100);
		
		camera = new Camera();
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

		player.draw(g);

		for (int i = 0; i < obstacles.length; i++) {
			RectHitbox ob = obstacles[i];
			if(camera.isVisibleOnCamera(ob.getX(), ob.getY(), ob.getWidth(), ob.getHeight())) ob.draw(g);
		}
	}

	public void drawBackground(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
	}
}
