package gamelogic.player;

import java.awt.Color;
import java.awt.Graphics;

import gameengine.GameObject;
import gameengine.graphics.MyGraphics;

public class Player extends GameObject{

	private int width;
	private int height;
	
	public Player(float x, float y) {
		super(x, y);
		this.width = 128;
		this.height = 256;
	}
	
	@Override
	public void update(float tslf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(Color.YELLOW);
		MyGraphics.fillRectWithOutline(graphics, (int)getX(), (int)getY(), width, height);
	}

}
