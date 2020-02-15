package gameengine.graphics;

import java.awt.Color;
import java.awt.Graphics;

public class MyGraphics {

	public static void fillRectWithOutline(Graphics g, int x, int y, int width, int height) {
		g.fillRect(x, y, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
	}
	
}
