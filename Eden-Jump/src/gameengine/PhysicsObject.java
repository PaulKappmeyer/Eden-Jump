package gameengine;

import gameengine.hitbox.CollisionMatrix;
import gameengine.hitbox.RectHitbox;
import gameengine.maths.Vector2D;
import gamelogic.Main;
import gamelogic.tiledMap.SolidTile;
import gamelogic.tiledMap.Tile;

public class PhysicsObject extends GameObject{
	public static float GRAVITY = 70;

	protected Vector2D movementVector;

	protected RectHitbox hitbox;
	protected RectHitbox[] collisionMatrix;

	public PhysicsObject() {
		super();
		this.movementVector = new Vector2D();
		this.hitbox = new RectHitbox(this, 0, 0, 0, 0);
		this.collisionMatrix = new RectHitbox[4];
	}

	public PhysicsObject(float x, float y, int width, int height) {
		super(x, y, width, height);
		this.movementVector = new Vector2D();
		this.hitbox = new RectHitbox(this, 0, 0, width, height);
		this.collisionMatrix = new RectHitbox[4];
	}

	@Override
	public void update(float tslf) {
		movementVector.y += (GRAVITY * GRAVITY) * tslf;

		updateCollisionMatrix(tslf); // checking collision based on the new position -> current movement Vector

		if(collisionMatrix[CollisionMatrix.BOT] != null) {
			position.y = collisionMatrix[CollisionMatrix.BOT].getY() - (hitbox.getOffsetY() + hitbox.getHeight());
			movementVector.y = 0;
		}
		if(collisionMatrix[CollisionMatrix.TOP] != null) {
			position.y = (collisionMatrix[CollisionMatrix.TOP].getY() + collisionMatrix[CollisionMatrix.TOP].getHeight()) - hitbox.getOffsetY();
			movementVector.y = 0;

		}
		if(collisionMatrix[CollisionMatrix.LEF] != null) {
			position.x = (collisionMatrix[CollisionMatrix.LEF].getX() + collisionMatrix[CollisionMatrix.LEF].getWidth()) - hitbox.getOffsetX();
			movementVector.x = 0;
		}
		if(collisionMatrix[CollisionMatrix.RIG] != null) {
			position.x = collisionMatrix[CollisionMatrix.RIG].getX() - (hitbox.getOffsetX() + hitbox.getWidth());
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
		RectHitbox[] matrix = new RectHitbox[4];
		float closestBot = Float.MAX_VALUE;
		float closestTop = Float.MAX_VALUE;
		float closestLef = Float.MAX_VALUE;
		float closestRig = Float.MAX_VALUE;
		RectHitbox bot = null;
		RectHitbox top = null;
		RectHitbox lef = null;
		RectHitbox rig = null;

		for (int i = 0; i < Main.map.getWidth(); i++) {
			for (int j = 0; j < Main.map.getHeight(); j++) {
				Tile tile = Main.map.getTiles()[i][j];
				RectHitbox obstacle = null;
				if(tile instanceof SolidTile) {
					obstacle = ((SolidTile)tile).getHitbox();
				}
				if(obstacle == null) continue;
				
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
		}
		if(bot != null) {
			if(newPositon.y + (hitbox.getOffsetY() + hitbox.getHeight()) > bot.getY()) {
				//When new bottom side of player is below top side of obstacle
				matrix[CollisionMatrix.BOT] = bot;
			}
		}
		if(top != null) {
			if(newPositon.y + hitbox.getOffsetY() < top.getY() + top.getHeight()) {
				//When new top side of player is above bottom side of obstacle
				matrix[CollisionMatrix.TOP] = top;
			}
		}
		if(lef != null) {
			if(newPositon.x + hitbox.getOffsetX() < lef.getX() + lef.getWidth()) {
				//When new left side of player is left to right side of obstacle
				matrix[CollisionMatrix.LEF] = lef;
			}
		}
		if(rig != null) {
			if(newPositon.x + (hitbox.getOffsetX() + hitbox.getWidth()) > rig.getX()) {
				//When new right side of player is right to left side of obstacle
				matrix[CollisionMatrix.RIG] = rig;
			}
		}	
		this.collisionMatrix = matrix; //Set the matrix
	}

}
