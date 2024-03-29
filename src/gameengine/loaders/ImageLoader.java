/*
 * 
 */
package gameengine.loaders;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;

import javax.imageio.ImageIO;

/**
 * 
 * @author Paul Kappmeyer & Daniel Lucarz
 *
 */
public final class ImageLoader {
	
	/**
	 * Loads and returns a BufferedImage from a specified source
	 * @param source The source to load the BufferImage from
	 * @return The BufferedImage
	 * @throws Exception 
	 */
	public static BufferedImage loadImage(String path) throws Exception{
		File file = new File(path);
		if(!file.exists()) throw new FileNotFoundException("This file could not be found");
		if(!file.isFile()) throw new Exception("The given path is not a file");
		
		BufferedImage image = ImageIO.read(file);
		return image;
	}
}