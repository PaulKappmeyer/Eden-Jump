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
		RectHitbox hitbox = player.getHitbox();
		newPositon.x += player.movementVector.x * tslf;
		newPositon.y += player.movementVector.y * tslf;

		//Finding the closest obstacles to the player in all 4 directions;
		float closestBot = Float.MAX_VALUE;
		float closestTop = Float.MAX_VALUE;
		float closestLef = Float.MAX_VALUE;
		float closestRig = Float.MAX_VALUE;
		RectHitbox bot = null;
		RectHitbox top = null;
		RectHitbox lef = null;
		RectHitbox rig = null;
		
		for (int i = 0; i < obstacles.length; i++) {
			RectHitbox obstacle = obstacles[i];
			//Find closest obstacle below player
			if(hitbox.getX() < obstacle.getX() + obstacle.getWidth() && hitbox.getX() + hitbox.getWidth() > obstacle.getX() && hitbox.getY() + hitbox.getHeight() <= obstacle.getY()) {
				//When current bottom side of player is above top side of obstacle
				if(obstacle.getY() - (hitbox.getY() + hitbox.getHeight()) < closestBot) {
					//When the top side of the obstacle is closer to the bottom side of the player
					bot = obstacle;
					closestBot = obstacle.getY() - (hitbox.getY() + hitbox.getHeight());
				}
			}
			//Find closest obstacle above player
			if(hitbox.getX() < obstacle.getX() + obstacle.getWidth() && hitbox.getX() + hitbox.getWidth() > obstacle.getX() && hitbox.getY() >= obstacle.getY() + obstacle.getHeight()) {
				//When current top side of player is below bottom side of obstacle
				if(hitbox.getY() - (obstacle.getY() + obstacle.getHeight()) < closestTop) {
					top = obstacle;
					closestTop = hitbox.getY() - (obstacle.getY() + obstacle.getHeight());
				}
			}
			//Find closest obstacle right to the player
			if(hitbox.getY() < obstacle.getY() + obstacle.getHeight() && hitbox.getY() + hitbox.getHeight() > obstacle.getY() && hitbox.getX() + hitbox.getWidth() <= obstacle.getX()) {
				//When current right side of player is left to left side of obstacle
				if(obstacle.getX() - (hitbox.getX() + hitbox.getWidth()) < closestRig) {
					rig = obstacle;
					closestRig = obstacle.getX() - (hitbox.getX() + hitbox.getWidth());
				}
			}
			//Find closest obstacle left to the player
			if(hitbox.getY() < obstacle.getY() + obstacle.getHeight() && hitbox.getY() + hitbox.getHeight() > obstacle.getY() && hitbox.getX() >= obstacle.getX() + obstacle.getWidth()) {
				//When current left side of player is right to right side of obstacle
				if(hitbox.getX() - (obstacle.getX() + obstacle.getWidth()) < closestLef) {
					lef = obstacle;
					closestLef = hitbox.getX() - (obstacle.getX() + obstacle.getWidth());
				}
			}
		}
		if(bot != null) {
			if(newPositon.y + (hitbox.getOffsetY() + hitbox.getHeight()) > bot.getY()) {
				//When new bottom side of player is below top side of obstacle
				position.y = bot.getY() - (hitbox.getOffsetY() + hitbox.getHeight());
				player.movementVector.y = 0;
				player.isJumping = false;
			}
		}
		if(top != null) {
			if(newPositon.y + hitbox.getOffsetY() < top.getY() + top.getHeight()) {
				//When new top side of player is above bottom side of obstacle
				position.y = (top.getY() + top.getHeight()) - hitbox.getOffsetY();
				player.movementVector.y = 0;
			}
		}
		if(rig != null) {
			if(newPositon.x + (hitbox.getOffsetX() + hitbox.getWidth()) > rig.getX()) {
				//When new right side of player is right to left side of obstacle
				position.x = rig.getX() - (hitbox.getOffsetX() + hitbox.getWidth());
				player.movementVector.x = 0;
			}
		}
		
		if(lef != null) {
			if(newPositon.x + hitbox.getOffsetX() < lef.getX() + lef.getWidth()) {
				//When new left side of player is left to right side of obstacle
				position.x = (lef.getX() + lef.getWidth()) - hitbox.getOffsetX();
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
			RectHitbox ob = obstacles[i];
			if(camera.isVisibleOnCamera(ob.getX(), ob.getY(), ob.getWidth(), ob.getHeight())) ob.draw(g);
		}
	}

	public void drawBackground(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
	}
}
