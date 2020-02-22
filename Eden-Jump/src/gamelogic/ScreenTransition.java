package gamelogic;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import gameengine.maths.Vector2D;

public class ScreenTransition {

	private boolean isActive = false;
	private boolean isActivating = false;
	private boolean isDeactivating = false;
	private Vector2D position;
	private float width;
	private float height;
	private float velocity = Main.SCREEN_WIDTH * 1.5f;
	
	private String text = null;
	private int textWidth = Main.SCREEN_WIDTH;
	private int textHeight = Main.SCREEN_HEIGHT;
	private Font font = new Font("Arial", Font.BOLD, 400);
	
	private List<ScreenTransitionListener> listeners = new ArrayList<>();
	
	public ScreenTransition() {
		this.position = new Vector2D(-Main.SCREEN_WIDTH, 0);
		this.width = Main.SCREEN_WIDTH;
		this.height = Main.SCREEN_HEIGHT;
	}

	public void update(float tslf) {
		if(isActive) {
			//Activating
			if(isActivating) {
				position.x += velocity * tslf;
				if(0 < position.x) {
					position.x = 0;
					isActivating = false;
					throwTransitionActivationFinishedEvent();
				}
			}
			//Deactivating
			else if(isDeactivating) {
				position.x += velocity * tslf;
				if(Main.SCREEN_WIDTH < position.x) {
					position.x = -Main.SCREEN_WIDTH;
					
					isDeactivating = false;
					isActive = false;
					throwTransitionFinishedEvent();
				}
			}
		}
	}

	public void draw(Graphics g) {
		if(isActive) {
			g.setColor(Color.BLACK);
			g.fillRect((int)position.x, (int)position.y, (int)width, (int)height);
			
			if(text != null) {
				g.setColor(Color.WHITE);
				// Get the FontMetrics
				FontMetrics metrics = g.getFontMetrics(font);
				// Determine the X coordinate for the text
				int x = (int)(position.x + (textWidth - metrics.stringWidth(text)) / 2);
				// Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
				int y = (int)(position.y + ((textHeight - metrics.getHeight()) / 2) + metrics.getAscent());
				// Set the font
				g.setFont(font);
				// Draw the String
				g.drawString(text, x, y);
			}
		}
	}

	public void activate() {
		position.x = -Main.SCREEN_WIDTH;
		isActive = true;
		isActivating = true;
		isDeactivating = false;
	}

	public void deactivate() {
		isDeactivating = true;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	//------------------------Listener
	public void throwTransitionActivationFinishedEvent() {
		for (ScreenTransitionListener screenTransitionListener : listeners) {
			screenTransitionListener.onTransitionActivationFinished();
		}
	}
	
	public void throwTransitionFinishedEvent() {
		for (ScreenTransitionListener screenTransitionListener : listeners) {
			screenTransitionListener.onTransitionFinished();
		}
	}
	
	public void addScreenTransitionListener(ScreenTransitionListener listener) {
		listeners.add(listener);
	}
}
