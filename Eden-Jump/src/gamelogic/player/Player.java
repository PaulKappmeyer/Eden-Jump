package gamelogic.player;

import java.awt.Color;
import java.awt.Graphics;

import gameengine.GameObject;
import gameengine.graphics.MyGraphics;
import gameengine.hitbox.RectHitbox;
import gameengine.maths.Vector2D;

public class Player extends GameObject{

	private int width;
	private int height;
	
	private float walkSpeed = 350;
	private float jumpPower = 1000;
	private float GRAVITY = 1100;
	
	public boolean isJumping = false;
	public RectHitbox headHitbox;
	public RectHitbox groundHitbox;
	public RectHitbox hitbox;
	public Vector2D movementVector;
	
	public Player(float x, float y) {
		super(x, y);
		this.width = 128;
		this.height = 256;
		this.movementVector = new Vector2D();
		this.headHitbox = new RectHitbox(this, 0, 0, width, height/2);
		this.groundHitbox = new RectHitbox(this, 0, height/2, width, height/2);
		this.hitbox = new RectHitbox(this, 0, 0, width, height);
	}
	
	@Override
	public void update(float tslf) {
		movementVector.x = 0;
		if(PlayerInput.isLeftKeyDown()) {
			movementVector.x = -walkSpeed;
		}
		
		if(PlayerInput.isRightKeyDown()) {
			movementVector.x = +walkSpeed;
		}
		
		if(PlayerInput.isJumpKeyDown() && !isJumping) {
			movementVector.y = -jumpPower;
			isJumping = true;
		}
		
		movementVector.y += GRAVITY * tslf;
		
		headHitbox.update();
		groundHitbox.update();
		hitbox.update();
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.YELLOW);
		MyGraphics.fillRectWithOutline(g, (int)getX(), (int)getY(), width, height);
		
//		headHitbox.draw(g);
//		groundHitbox.draw(g);
//		hitbox.draw(g);
	}

	
	//-------------------------------------------Getters
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
