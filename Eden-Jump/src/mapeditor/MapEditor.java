package mapeditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import gameengine.GameBase;
import gameengine.graphics.Camera;
import gameengine.input.MouseInputManager;
import gameengine.maths.Vector2D;

public class MapEditor extends GameBase{

	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 860;

	public static final Color MOUSE_OVER = new Color(0, 255, 0, 150);

	private int[][] tiles;
	private int width; //width of the map in number of tiles
	private int height; //height of the map in number of tiles
	private int size; //the size of one tile;

	private Camera camera;

	private boolean isPressed;
	private Vector2D oldMousePosition = new Vector2D(); //saves position of the mouse when mouse is dragged
	private Vector2D oldCameraPosition = new Vector2D(); //saves position of the camera when mouse is dragged
	
	
	public static void main(String[] args) {
		MapEditor mapeditor = new MapEditor();
		mapeditor.start("MapEditor Eden Jump", SCREEN_WIDTH, SCREEN_HEIGHT);
	}

	@Override
	public void init() {
		width = 100;
		height = 10;
		tiles = new int[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				tiles[x][y] = 0;
			}
		}
		size = 100;

		camera = new Camera();
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

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				float tileX = x * size - camera.getX();
				float tileY = y * size - camera.getY();
				tiles[x][y] = 0;
				if(mouseX >= tileX && mouseX <= tileX + size && mouseY >= tileY && mouseY <= tileY + size) {
					tiles[x][y] = 1;
				}
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		drawBackground(g);

		g.translate(-(int)camera.getX(), -(int)camera.getY());

		//Fill tiles
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int value = tiles[x][y];
				if(value == 0) continue;
				g.setColor(MOUSE_OVER);
				g.fillRect(x * size, y * size, size, size);
			}
		}

		//Draw Outline
		g.setColor(Color.BLACK);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				g.drawRect(x * size, y * size, size, size);
			}
		}
	}

	public void drawBackground(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
	}
}
