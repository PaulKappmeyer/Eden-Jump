package gamelogic;

import java.awt.Color;
import java.awt.Graphics;

import gameengine.GameBase;
import gamelogic.player.Player;

public class Main extends GameBase{

	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 860;
	
	private Player player;
	
	public static void main(String[] args) {
		Main main = new Main();
		main.start("Eden Jump", SCREEN_WIDTH, SCREEN_HEIGHT);
	}

	@Override
	public void init() {
		player = new Player(400, 400);
	}

	@Override
	public void update(float tslf) {
		player.update(tslf);
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		player.draw(graphics);
	}
	
}
