package gameengine;

import gameengine.hitbox.CollisionMatrix;
import gameengine.hitbox.RectHitbox;
import gameengine.maths.Vector2D;
import gamelogic.Main;
import gamelogic.tiledMap.Tile;

public class PhysicsObject extends GameObject{
	public static float GRAVITY = 70;

	protected Vector2D movementVector;

	protected RectHitbox hitbox;
	
	protected Tile[] collisionMatrix;

	public PhysicsObject() {
		super();
		this.movementVector = new Vector2D();
		this.hitbox = new RectHitbox(this, 0, 0, 0, 0);
		this.collisionMatrix = new Tile[4];
	}

	public PhysicsObject(float x, float y, int width, int height) {
		super(x, y, width, height);
		this.movementVector = new Vector2D();
		this.hitbox = new RectHitbox(this, 0, 0, width, height);
		this.collisionMatrix = new Tile[4];
	}

	@Override
	public void update(float tslf) {
		movementVector.y += (GRAVITY * GRAVITY) * tslf;

		updateCollisionMatrix(tslf); // checking collision based on the new position -> current movement Vector

		Tile bot = collisionMatrix[CollisionMatrix.BOT];
		if(bot != null) {
			position.y = bot.getHitbox().getY() - (hitbox.getOffsetY() + hitbox.getHeight());
			movementVector.y = 0;
		}
		Tile top = collisionMatrix[CollisionMatrix.TOP];
		if(top != null) {
			position.y = (top.getHitbox().getY() + top.getHitbox().getHeight()) - hitbox.getOffsetY();
			movementVector.y = 0;

		}
		Tile lef = collisionMatrix[CollisionMatrix.LEF];
		if(lef != null) {
			position.x = (lef.getHitbox().getX() + lef.getHitbox().getWidth()) - hitbox.getOffsetX();
			movementVector.x = 0;
		}
		Tile rig = collisionMatrix[CollisionMatrix.RIG];
		if(rig != null) {
			position.x = rig.getHitbox().getX() - (hitbox.getOffsetX() + hitbox.getWidth());
			movementVector.x = 0;
		}

		position.x += movementVector.x * tslf;
		position.y += movementVector.y * tslf;
		
		hitbox.update(); // -> saving old position
	}

	public void updateCollisionMatrix(float tslf) {
		Vector2D newPositon = new Vector2D(getX(), getY());
		newPositon.x += movementVector.x * tslf;
		newPositon.y += movementVector.y * tslf;

		//Finding the closest obstacles to the player in all 4 directions;
		Tile[] matrix = new Tile[4];
		float closestBot = Float.MAX_VALUE;
		float closestTop = Float.MAX_VALUE;
		float closestLef = Float.MAX_VALUE;
		float closestRig = Float.MAX_VALUE;
		Tile bot = null;
		Tile top = null;
		Tile lef = null;
		Tile rig = null;

		for (int i = 0; i < Main.map.getWidth(); i++) {
			for (int j = 0; j < Main.map.getHeight(); j++) {
				Tile tile = Main.map.getTiles()[i][j];
				RectHitbox obstacle = tile.getHitbox();
				if(obstacle == null) continue;
				
				//Find closest obstacle below player
				if(hitbox.getX() < obstacle.getX() + obstacle.getWidth() && hitbox.getX() + hitbox.getWidth() > obstacle.getX() && hitbox.getY() + hitbox.getHeight() <= obstacle.getY()) {
					//When current bottom side of player is above top side of obstacle
					if(obstacle.getY() - (hitbox.getY() + hitbox.getHeight()) < closestBot) {
						//When the top side of the obstacle is closer to the bottom side of the player
						bot = tile;
						closestBot = obstacle.getY() - (hitbox.getY() + hitbox.getHeight());
					}
				}
				//Find closest obstacle above player
				if(hitbox.getX() < obstacle.getX() + obstacle.getWidth() && hitbox.getX() + hitbox.getWidth() > obstacle.getX() && hitbox.getY() >= obstacle.getY() + obstacle.getHeight()) {
					//When current top side of player is below bottom side of obstacle
					if(hitbox.getY() - (obstacle.getY() + obstacle.getHeight()) < closestTop) {
						top = tile;
						closestTop = hitbox.getY() - (obstacle.getY() + obstacle.getHeight());
					}
				}
				//Find closest obstacle right to the player
				if(hitbox.getY() < obstacle.getY() + obstacle.getHeight() && hitbox.getY() + hitbox.getHeight() > obstacle.getY() && hitbox.getX() + hitbox.getWidth() <= obstacle.getX()) {
					//When current right side of player is left to left side of obstacle
					if(obstacle.getX() - (hitbox.getX() + hitbox.getWidth()) < closestRig) {
						rig = tile;
						closestRig = obstacle.getX() - (hitbox.getX() + hitbox.getWidth());
					}
				}
				//Find closest obstacle left to the player
				if(hitbox.getY() < obstacle.getY() + obstacle.getHeight() && hitbox.getY() + hitbox.getHeight() > obstacle.getY() && hitbox.getX() >= obstacle.getX() + obstacle.getWidth()) {
					//When current left side of player is right to right side of obstacle
					if(hitbox.getX() - (obstacle.getX() + obstacle.getWidth()) < closestLef) {
						lef = tile;
						closestLef = hitbox.getX() - (obstacle.getX() + obstacle.getWidth());
					}
				}
			}
		}
		if(bot != null) {
			if(newPositon.y + (hitbox.getOffsetY() + hitbox.getHeight()) > bot.getHitbox().getY()) {
				//When new bottom side of player is below top side of obstacle
				matrix[CollisionMatrix.BOT] = bot;
			}
		}
		if(top != null) {
			if(newPositon.y + hitbox.getOffsetY() < top.getHitbox().getY() + top.getHitbox().getHeight()) {
				//When new top side of player is above bottom side of obstacle
				matrix[CollisionMatrix.TOP] = top;
			}
		}
		if(lef != null) {
			if(newPositon.x + hitbox.getOffsetX() < lef.getHitbox().getX() + lef.getHitbox().getWidth()) {
				//When new left side of player is left to right side of obstacle
				matrix[CollisionMatrix.LEF] = lef;
			}
		}
		if(rig != null) {
			if(newPositon.x + (hitbox.getOffsetX() + hitbox.getWidth()) > rig.getHitbox().getX()) {
				//When new right side of player is right to left side of obstacle
				matrix[CollisionMatrix.RIG] = rig;
			}
		}	
		this.collisionMatrix = matrix; //Set the matrix
	}

	//-----------------------------------------------------Getters
	public Tile[] getCollisionMatrix() {
		return collisionMatrix;
	}
	
	public float getMovementX() {
		return movementVector.x;
	}
	
	public float getMovementY() {
		return movementVector.y;
	}
}
