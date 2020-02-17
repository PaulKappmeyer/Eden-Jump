package mapeditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;

import gameengine.GameBase;
import gameengine.graphics.Camera;
import gameengine.input.KeyboardInputManager;
import gameengine.input.MouseInputManager;
import gameengine.maths.Vector2D;

class MapEditor extends GameBase{

	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 860;

	public static Camera camera;

	private boolean isPressed;
	private Vector2D oldMousePosition; //saves position of the mouse when mouse is dragged
	private Vector2D oldCameraPosition; //saves position of the camera when mouse is dragged

	private EditorTiledMap map;

	private boolean saved = false;

	private int screenSplit = 1000;
	
	public static void main(String[] args) {
		MapEditor mapeditor = new MapEditor();
		mapeditor.start("MapEditor Eden Jump", SCREEN_WIDTH, SCREEN_HEIGHT);
	}

	@Override
	public void init() {
		map = new EditorTiledMap(100, 10, 50);
		camera = new Camera(screenSplit, SCREEN_HEIGHT);
		oldMousePosition = new Vector2D();
		oldCameraPosition = new Vector2D();
	}

	@Override
	public void update(float tslf) {
		float mouseX = MouseInputManager.getMouseX();
		float mouseY = MouseInputManager.getMouseY();

		if(MouseInputManager.isButtonDown(MouseEvent.BUTTON3)) {
			if(!isPressed) {
				oldCameraPosition.x = camera.getX();
				oldCameraPosition.y = camera.getY();
				oldMousePosition.x = mouseX;
				oldMousePosition.y = mouseY;
				isPressed = true;
			}
			camera.setX(oldCameraPosition.x + (oldMousePosition.x - mouseX));
			camera.setY(oldCameraPosition.y + (oldMousePosition.y - mouseY));
		}else {
			isPressed = false;
		}

		map.update(tslf);

		if(KeyboardInputManager.isKeyDown(KeyEvent.VK_CONTROL) && KeyboardInputManager.isKeyDown(KeyEvent.VK_S)) {
			if(!saved) {
				try {
					MapSaver.wirteMap(map);
					saved = true;
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}else {
			saved = false;
		}
	}

	@Override
	public void draw(Graphics g) {
		drawBackground(g);

		g.translate(-(int)camera.getX(), -(int)camera.getY());

		map.draw(g);
		
		g.translate(+(int)camera.getX(), +(int)camera.getY());
		
		//Fill background
		g.setColor(Color.WHITE);
		g.fillRect(screenSplit, 0, SCREEN_WIDTH-screenSplit, SCREEN_HEIGHT);
		
		//Draw Screen-split line
		g.setColor(Color.BLACK);
		g.drawLine(screenSplit, 0, screenSplit, SCREEN_HEIGHT);
	}

	public void drawBackground(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
	}
}
