package mapeditor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

class MapSaver {

	public static void wirteMap(TiledMap map) throws FileNotFoundException {

		PrintWriter printWriter = new PrintWriter(".\\maps\\map.txt");

		printWriter.println("width=" + map.getWidth());
		printWriter.println("height=" + map.getHeight());
		printWriter.println("tileSize=" + map.getTileSize());

		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {
				printWriter.print(map.getTiles()[x][y].getValue() + ",");
			}
			printWriter.println();
		}

		printWriter.flush();
		printWriter.close();

		System.out.println("successfully wrote map file");  
	}

}
