package gameengine.graphics;

import gameengine.maths.Vector2D;
import gamelogic.Main;
import gamelogic.player.Player;

public class Camera {

	private Vector2D position;
	private Player player;
	
	private int width;
	private int height;
	
	private float velocity = 2.0f; 
	private float setValue = 0.5f;
	
	public Camera(int width, int height) {
		this.position = new Vector2D();
		this.width = width;
		this.height = height;
	}
	
	public void update(float tslf) {
		if(player != null) {
			float goalX = player.getX() + player.getWidth()/2 - Main.SCREEN_WIDTH/2;
			float goalY = player.getY() + player.getHeight()/2 - Main.SCREEN_HEIGHT/2;
			
			//Calculating the differences between the goal position and the current position of the camera
			float diffX = goalX - position.x;
			float amountX = diffX * velocity;
			position.x += amountX * tslf;

			float diffY = goalY - position.y;
			float amountY = diffY * velocity;
			position.y += amountY * tslf;
			
			//At this point the difference is too small so the value gets set to avoid shaking of the camera
			if(-setValue < diffX && diffX < setValue) position.x = goalX;
			if(-setValue < diffY && diffY < setValue) position.y = goalY;
		}
	}
	
	public void setFocusedObject(Player object) {
		this.player = object;
	}
	
	public boolean isVisibleOnCamera(float x, float y, int width, int height) {
		if(x + width > position.x && x < position.x + this.width && y + height > position.y && y < position.y + this.height) return true;
		return false;
	}
	
	//--------------------------------Getters
	public void setX(float x) {
		position.x = x;
	}
	
	public void setY(float y) {
		position.y = y;
	}
	
	public float getX(){
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}
}
