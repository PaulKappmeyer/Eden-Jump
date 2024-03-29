/*
 * 
 */
package gameengine;

import java.awt.Graphics;

import gameengine.graphics.MyWindow;
import gameengine.input.KeyboardInputManager;
import gameengine.input.MouseInputManager;

/**
 * 
 * @author Paul Kappmeyer & Daniel Lucarz
 *
 */
public abstract class GameBase {
	protected MyWindow window;

	private final int MAXFPS = 120;
	private final long MAXLOOPTIME = 1000/MAXFPS;
	private long firstFrame;
	private int frames;
	private int fps;
	
	//-----------------------------------------------ABSTRACT METHODS FOR SUB-CLASS
	public abstract void init();
	public abstract void update(float tslf);
	public abstract void draw(Graphics graphics);
	//-----------------------------------------------END ABSTRACT METHODS

	/**
	 * Creates a new window and starts the game loop
	 * @param title The title of the window
	 * @param width The width of the window
	 * @param height The height of the window
	 */
	public void start(String title, int width, int height) {
		window = new MyWindow(title, width, height);

		//Adding inputManagers to window
		window.addKeyListener(new KeyboardInputManager());
		MouseInputManager mouseInputManager = new MouseInputManager(window);
		window.addMouseListener(mouseInputManager);
		window.addMouseMotionListener(mouseInputManager);
		window.addMouseWheelListener(mouseInputManager);
		
		long StartOfInit = System.currentTimeMillis();
		init(); //Calling method init() in the sub-class
		long StartOfGame = System.currentTimeMillis();
		System.out.println("Time needed for initialization: [" + (StartOfGame - StartOfInit) + "ms]");
		
		long timestamp;
		long oldTimestamp;
		
		long lastFrame = System.currentTimeMillis();
		while (true) {
			//Calculating time since last frame
			long thisFrame = System.currentTimeMillis();
			float tslf = (float)(thisFrame - lastFrame) / 1000f;
			lastFrame = thisFrame;
			
			if (thisFrame > firstFrame + 1000) {
				firstFrame = thisFrame;
				fps = frames;
				frames = 0;
			}
			frames++;
			
			oldTimestamp = System.currentTimeMillis();
			
			//----------------------------------Updating
			update(tslf); //Calling method update() in the sub-class 
			
			timestamp = System.currentTimeMillis();
			if (timestamp - oldTimestamp > MAXLOOPTIME) {
				continue; // too late
			}
			
			//-----------------------------------Rendering
			Graphics g = window.beginDrawing();
			draw(g); //Calling method draw() in the sub-class
			window.endDrawing(g);
			
			timestamp = System.currentTimeMillis();
			if (timestamp - oldTimestamp <= MAXLOOPTIME) {
				try {
					Thread.sleep(MAXLOOPTIME - (timestamp - oldTimestamp));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public int getFPS() {
		return fps;
	}
}
