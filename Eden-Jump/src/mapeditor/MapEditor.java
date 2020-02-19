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
import gamelogic.GameResources;

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

	public Palette paletteTiles; // index = 0;
	public Palette paletteObjects; // index = 1;

	private int selectedPalette = 0;
	
	public static void main(String[] args) {
		MapEditor mapeditor = new MapEditor();
		mapeditor.start("MapEditor Eden Jump", SCREEN_WIDTH, SCREEN_HEIGHT);
	}

	@Override
	public void init() {
		GameResources.load();
		
		map = new EditorTiledMap(100, 20, 50);
		camera = new Camera(screenSplit, SCREEN_HEIGHT);
		oldMousePosition = new Vector2D();
		oldCameraPosition = new Vector2D();

		PaletteItem[] paletteItems = new PaletteItem[6];
		paletteItems[0] = new PaletteItem("Air", 0, null);
		paletteItems[1] = new PaletteItem("Solid", 1, GameResources.solid);
		paletteItems[2] = new PaletteItem("Spikes_downwards", 2, GameResources.spikes_downwards);
		paletteItems[3] = new PaletteItem("Spikes_upwards", 3, GameResources.spikes_upwards);
		paletteItems[4] = new PaletteItem("Spikes_leftwards", 4, GameResources.spikes_leftwards);
		paletteItems[5] = new PaletteItem("Spikes_rightwards", 5, GameResources.spikes_rightwards);
		
		paletteTiles = new Palette(screenSplit + 15, 10, paletteItems);
		paletteTiles.setSelectedIndex(0);

		paletteItems = new PaletteItem[2];
		paletteItems[0] = new PaletteItem("Player", 0, null);
		paletteItems[1] = new PaletteItem("Enemy", 0, null);

		paletteObjects = new Palette(screenSplit + 15, 400, paletteItems);
	}

	@Override
	public void update(float tslf) {
		float mouseX = MouseInputManager.getMouseX();
		float mouseY = MouseInputManager.getMouseY();

		//Scrolling on the map
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

		//Updating the map
		map.update(tslf);

		//Saving the map
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

		//Updating the palette
		paletteTiles.update(tslf);
		paletteObjects.update(tslf);

		if(MouseInputManager.isButtonDown(MouseEvent.BUTTON1)) {
			if(paletteTiles.getX() < mouseX && mouseX < paletteTiles.getX() + paletteTiles.getWidth() && paletteTiles.getY() < mouseY && mouseY < paletteTiles.getY() + paletteTiles.getHeight()) {
				selectedPalette = 0;
				paletteObjects.setSelectedIndex(-1);
			}
			if(paletteObjects.getX() < mouseX && mouseX < paletteObjects.getX() + paletteObjects.getWidth() && paletteObjects.getY() < mouseY && mouseY < paletteObjects.getY() + paletteObjects.getHeight()) {
				selectedPalette = 1;
				paletteTiles.setSelectedIndex(-1);
			}
		}
		
		//Set value when tile is selected
		if(selectedPalette == 0) {
			EditorTile mouseOver = map.getMouseOver();
			if(mouseOver != null) {
				if(camera.isVisibleOnCamera(mouseOver.getX(), mouseOver.getY(), mouseOver.getSize(), mouseOver.getSize())) { 
					if(MouseInputManager.isButtonDown(MouseEvent.BUTTON1)) {
						mouseOver.setValue(paletteTiles.getSelectedPaletteItem().getValue());
						mouseOver.setImage(paletteTiles.getSelectedPaletteItem().getImage());
					}
				}
			}
		} else if(selectedPalette == 1) {
			EditorTile mouseOver = map.getMouseOver();
			if(mouseOver != null) {
				if(camera.isVisibleOnCamera(mouseOver.getX(), mouseOver.getY(), mouseOver.getSize(), mouseOver.getSize())) { 
					if(MouseInputManager.isButtonDown(MouseEvent.BUTTON1) && paletteObjects.getSelectedPaletteItem().getName().equals("Player")) {
						map.setPlayerPositon(map.getMouseTileX(), map.getMouseTileY());
					}
				}
			}
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

		paletteTiles.draw(g);
		paletteObjects.draw(g);
	}

	public void drawBackground(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
	}
}
