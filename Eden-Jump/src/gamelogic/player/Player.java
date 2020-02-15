package gamelogic.player;

import java.awt.Color;
import java.awt.Graphics;

import gameengine.GameObject;
import gameengine.graphics.MyGraphics;
import gameengine.maths.Vector2D;
import gamelogic.Main;

public class Player extends GameObject{

	private int width;
	private int height;
	
	private float walkSpeed = 350;
	private float jumpPower = 500;
	private Vector2D movementVector;
	private boolean isJumping = false;
	private float GRAVITY = 500;
	
	public Player(float x, float y) {
		super(x, y);
		this.width = 128;
		this.height = 256;
		this.movementVector = new Vector2D();
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
		
		position.x += movementVector.x * tslf;
		position.y += movementVector.y * tslf;
	
		if(position.y + height > Main.SCREEN_HEIGHT) {
			position.y = Main.SCREEN_HEIGHT - height;
			movementVector.y = 0;
			isJumping = false;
		}
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.YELLOW);
		MyGraphics.fillRectWithOutline(g, (int)getX(), (int)getY(), width, height);
	}

}
