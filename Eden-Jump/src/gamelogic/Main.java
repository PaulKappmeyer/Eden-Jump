package gamelogic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import gameengine.GameBase;
import gameengine.input.KeyboardInputManager;
import gameengine.loaders.LeveldataLoader;
import gamelogic.level.Level;
import gamelogic.level.Leveldata;
import gamelogic.level.PlayerDieListener;

public class Main extends GameBase implements PlayerDieListener{

	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 860;
	public static final boolean DEBUGGING = false;

	private static ScreenTransition screenTransition = new ScreenTransition();

	private Level level;

	public static void main(String[] args) {
		Main main = new Main();
		main.start("Eden Jump", SCREEN_WIDTH, SCREEN_HEIGHT);
	}

	@Override
	public void init() {
		GameResources.load();

		Leveldata leveldata = null;
		try {
			leveldata = LeveldataLoader.loadMap(".\\maps\\map.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		level = new Level(leveldata);

		level.addPlayerDieListener(this);
	}

	@Override
	public void onPlayerDeath() {
		if(DEBUGGING) {
			level.restartLevel();
			return;
		}
		screenTransition.activate();
	}
	
	public static void win() {
		screenTransition.activate();
	}

	@Override
	public void update(float tslf) {
		if(KeyboardInputManager.isKeyDown(KeyEvent.VK_N)) init();
		if(KeyboardInputManager.isKeyDown(KeyEvent.VK_ESCAPE)) System.exit(0);

		level.update(tslf);

		screenTransition.update(tslf);
		
		if(!level.isActive() && level.isPlayerDead()) {
			if(screenTransition.isActive() && !screenTransition.isActivating() && !screenTransition.isDeactivating()) {
				level.restartLevel();
				screenTransition.deactivate();
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		drawBackground(g);

		level.draw(g);

		screenTransition.draw(g);
	}

	public void drawBackground(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
	}
}
