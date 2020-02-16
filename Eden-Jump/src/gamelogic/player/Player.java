package gamelogic.player;

import java.awt.Color;
import java.awt.Graphics;

import gameengine.PhysicsObject;
import gameengine.graphics.MyGraphics;

public class Player extends PhysicsObject{
	private float walkSpeed = 350;
	private float jumpPower = 1000;

	private boolean isJumping = false;

	public Player(float x, float y) {
		super(x, y, 100, 100);
	}

	@Override
	public void update(float tslf) {
		super.update(tslf);
		
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
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.YELLOW);
		MyGraphics.fillRectWithOutline(g, (int)getX(), (int)getY(), width, height);
	}
}
