package gamelogic;

import java.awt.image.BufferedImage;

import gameengine.loaders.ImageLoader;

public final class GameResources {

	public static BufferedImage solid;
	public static BufferedImage spikes_upwards;
	public static BufferedImage spikes_downwards;
	public static BufferedImage spikes_leftwards;
	public static BufferedImage spikes_rightwards;
	public static BufferedImage gras;
	public static BufferedImage dirt;
	
	public static BufferedImage enemy;
	
	public static void load() {
		try {
			solid = ImageLoader.loadImage(".\\gfx\\solid.png");
			spikes_downwards = ImageLoader.loadImage(".\\gfx\\spikes_downwards.png");
			spikes_upwards = ImageLoader.loadImage(".\\gfx\\spikes_upwards.png");
			spikes_leftwards = ImageLoader.loadImage(".\\gfx\\spikes_leftwards.png");
			spikes_rightwards = ImageLoader.loadImage(".\\gfx\\spikes_rightwards.png");
			gras = ImageLoader.loadImage(".\\gfx\\gras.png");
			dirt = ImageLoader.loadImage(".\\gfx\\dirt.png");
			enemy = ImageLoader.loadImage(".\\gfx\\enemy.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
