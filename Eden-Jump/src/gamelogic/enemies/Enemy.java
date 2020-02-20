package gamelogic.enemies;

import java.awt.Color;
import java.awt.Graphics;

import gameengine.PhysicsObject;
import gameengine.graphics.MyGraphics;

public class Enemy extends PhysicsObject{

	private float walkSpeed = 80;
	
	public Enemy(float x, float y) {
		super(x, y, 100, 100);
		movementVector.x = walkSpeed;
	}
	
	@Override
	public void update(float tslf) {
		super.update(tslf);
		
		if(collisionMatrix[LEF] != null) {
			movementVector.x = walkSpeed;
		} else if(collisionMatrix[RIG] != null) {
			movementVector.x = -walkSpeed;
		}
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		MyGraphics.fillRectWithOutline(g, (int)position.x, (int)position.y, width, height);
		
		hitbox.draw(g);
	}
	
}
