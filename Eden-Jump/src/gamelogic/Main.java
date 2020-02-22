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
import gamelogic.level.PlayerWinListener;

public class Main extends GameBase implements PlayerDieListener, PlayerWinListener{

	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 860;
	public static final boolean DEBUGGING = false;

	private ScreenTransition screenTransition = new ScreenTransition();

	private Leveldata[] levels;
	private Level currentLevel;
	private int currentLevelIndex;

	public static void main(String[] args) {
		Main main = new Main();
		main.start("Eden Jump", SCREEN_WIDTH, SCREEN_HEIGHT);
	}

	@Override
	public void init() {
		GameResources.load();

		currentLevelIndex = 0;

		levels = new Leveldata[2];
		try {
			levels[0] = LeveldataLoader.loadLeveldata(".\\maps\\map0.txt");
			levels[1] = LeveldataLoader.loadLeveldata(".\\maps\\map1.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		currentLevel = new Level(levels[currentLevelIndex]);

		currentLevel.addPlayerDieListener(this);
		currentLevel.addPlayerWinListener(this);
	}

	@Override
	public void onPlayerDeath() {
		if(DEBUGGING) {
			currentLevel.restartLevel();
			return;
		}
		screenTransition.setText("LOSE");
		screenTransition.activate();
	}

	@Override
	public void onPlayerWin() {
		screenTransition.setText("WIN");
		screenTransition.activate();
	}

	private void changeLevel() {
		if(currentLevelIndex < levels.length-1) {
			currentLevelIndex++;
			currentLevel = new Level(levels[currentLevelIndex]);

			currentLevel.addPlayerDieListener(this);
			currentLevel.addPlayerWinListener(this);
		}
	}

	@Override
	public void update(float tslf) {
		if(KeyboardInputManager.isKeyDown(KeyEvent.VK_N)) init();
		if(KeyboardInputManager.isKeyDown(KeyEvent.VK_ESCAPE)) System.exit(0);

		currentLevel.update(tslf);

		screenTransition.update(tslf);

		if(!currentLevel.isActive() && currentLevel.isPlayerDead()) {
			if(screenTransition.isActive() && !screenTransition.isActivating() && !screenTransition.isDeactivating()) {
				currentLevel.restartLevel();
				screenTransition.deactivate();
			}
		}
		if(!currentLevel.isActive() && currentLevel.isPlayerWin()) {
			if(currentLevelIndex < levels.length-1) {
				if(screenTransition.isActive() && !screenTransition.isActivating() && !screenTransition.isDeactivating()) {
					changeLevel();
					screenTransition.deactivate();
				}
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		drawBackground(g);

		currentLevel.draw(g);

		screenTransition.draw(g);
	}

	public void drawBackground(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
	}
}
