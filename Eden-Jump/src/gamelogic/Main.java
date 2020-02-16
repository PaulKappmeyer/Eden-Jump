package gamelogic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import gameengine.GameBase;
import gameengine.GameObject;
import gameengine.graphics.Camera;
import gameengine.hitbox.RectHitbox;
import gameengine.input.KeyboardInputManager;
import gameengine.maths.Vector2D;
import gamelogic.player.Player;

public class Main extends GameBase{

	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 860;

	private Player player;
	private Camera camera;
	private RectHitbox[] obstacles;

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

		doPhysics(tslf);

		camera.update(tslf);
	}

	public void doPhysics(float tslf) {
		Vector2D position = player.position;
		Vector2D newPositon = new Vector2D(player.getX(), player.getY());
		newPositon.x += player.movementVector.x * tslf;
		newPositon.y += player.movementVector.y * tslf;

		//Find closest obstacle below player
		float closest = Float.MAX_VALUE;
		RectHitbox ground = null;
		for (int i = 0; i < obstacles.length; i++) {
			RectHitbox obstacle = obstacles[i];
			if(position.x < obstacle.getX() + obstacle.getWidth() && position.x + player.getWidth() > obstacle.getX() && position.y + player.getHeight() <= obstacle.getY()) {
				//When current bottom side of player is above top side of obstacle
				if(obstacle.getY() - (position.y + player.getHeight()) < closest) {
					//When the top side of the obstacle is closer to the bottom side of the player
					ground = obstacle;
					closest = obstacle.getY() - (position.y + player.getHeight());
				}
			}
		}
		if(ground != null) {
			if(newPositon.y + player.hitbox.getOffsetY() + player.hitbox.getHeight() > ground.getY()) {
				//When new bottom side of player is below top side of obstacle
				position.y = ground.getY() - player.getHeight();
				player.movementVector.y = 0;
				player.isJumping = false;
			}
		}

		//Find closest obstacle above player
		closest = Float.MAX_VALUE;
		RectHitbox top = null;
		for (int i = 0; i < obstacles.length; i++) {
			RectHitbox obstacle = obstacles[i];
			if(position.x < obstacle.getX() + obstacle.getWidth() && position.x + player.getWidth() > obstacle.getX() && position.y >= obstacle.getY() + obstacle.getHeight()) {
				//When current top side of player is below bottom side of obstacle
				if(position.y - (obstacle.getY() + obstacle.getHeight()) < closest) {
					top = obstacle;
					closest = position.y - (obstacle.getY() + obstacle.getHeight());
				}
			}
		}
		if(top != null) {
			if(newPositon.y < top.getY() + top.getHeight()) {
				//When new top side of player is above bottom side of obstacle
				position.y = top.getY() + top.getHeight();
				player.movementVector.y = 0;
			}
		}

		//Find closest obstacle right to the player
		closest = Float.MAX_VALUE;
		RectHitbox right = null;
		for (int i = 0; i < obstacles.length; i++) {
			RectHitbox obstacle = obstacles[i];
			if(position.y < obstacle.getY() + obstacle.getHeight() && position.y + player.getHeight() > obstacle.getY() && position.x + player.getWidth() <= obstacle.getX()) {
				//When current right side of player is left to left side of obstacle
				if(obstacle.getX() - (position.x + player.getWidth()) < closest) {
					right = obstacle;
					closest = obstacle.getX() - (position.x + player.getWidth());
				}
			}
		}
		if(right != null) {
			if(newPositon.x + player.getWidth() > right.getX()) {
				//When new right side of player is right to left side of obstacle
				position.x = right.getX() - player.getWidth();
				player.movementVector.x = 0;
			}
		}
		
		//Find closest obstacle left to the player
		closest = Float.MAX_VALUE;
		RectHitbox left = null;
		for (int i = 0; i < obstacles.length; i++) {
			RectHitbox obstacle = obstacles[i];
			if(position.y < obstacle.getY() + obstacle.getHeight() && position.y + player.getHeight() > obstacle.getY() && position.x >= obstacle.getX() + obstacle.getWidth()) {
				//When current left side of player is right to right side of obstacle
				if(position.x - (obstacle.getX() + obstacle.getWidth()) < closest) {
					left = obstacle;
					closest = position.x - (obstacle.getX() + obstacle.getWidth());
				}
			}
		}
		if(left != null) {
			if(newPositon.x < left.getX() + left.getWidth()) {
				//When new left side of player is left to right side of obstacle
				position.x = left.getX() + left.getWidth();
				player.movementVector.x = 0;
			}
		}
		
		position.x += player.movementVector.x * tslf;
		position.y += player.movementVector.y * tslf;
	}

	@Override
	public void draw(Graphics g) {
		drawBackground(g);

		g.translate((int)-camera.getX(), (int)-camera.getY());

		player.draw(g);

		for (int i = 0; i < obstacles.length; i++) {
			obstacles[i].draw(g);
		}
	}

	public void drawBackground(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
	}

}
