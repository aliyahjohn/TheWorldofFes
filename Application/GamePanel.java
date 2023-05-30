package Application;
import javax.swing.JPanel;

import MazeofFes.Boundary;
import MazeofFes.FesPlayer;
import MazeofFes.LeftBoundary;
import MazeofFes.PoisonMaze;
import MazeofFes.Trinket_Wind;
import MazeofFes.Trinket_Berry;
import MazeofFes.Trinket_Earth;
import MazeofFes.Trinket_Fire;
import MazeofFes.Trinket_Spark4;

import DustCollector.Acorn;
import DustCollector.FairyBag;
import DustCollector.FallingFairyDust;

import Effects.SoundManager;

import WingHunt.TileMap;
import WingHunt.TileMapManager;

// import archive.ItemsPanel;

import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.security.cert.PolicyNode;
import java.time.chrono.ThaiBuddhistChronology;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
   A component that displays all the game entities
*/

public class GamePanel extends JPanel
		       implements Runnable {
   
	private SoundManager soundManager;
	// private Bag bag;

	private boolean isRunning;
	private boolean isPaused;
    private int mazescore, dustscore, berryLives;

	private Thread mazeThread, draftThread, fairydustThread, wingHuntThread;

	private String currThr;

	private BufferedImage image;
	private Image background, comingsoon, collectorBackground;

	// private BrightnessFX background;

	//MAZE OF FES:
	private FesPlayer fes;
	private Boundary boundary;
	private LeftBoundary leftbound;
	private PoisonMaze poisonMaze;
	// private Bag bag;

	private Trinket_Earth earth;
	private Trinket_Fire fire;
	private Trinket_Wind wind;
	private Trinket_Berry berry;
	private Trinket_Spark4 spark1;
	private Trinket_Spark4 spark2;
	private Trinket_Spark4 spark3;
	private Trinket_Spark4 spark4;
	// private FesAnimation flyanimation;
	// private ImageFX bag;

	private DustCollector.FairyBag bag;
	private FallingFairyDust dust[];
	// private boolean dustdropped;
	private DustCollector.Acorn acorn;

	private boolean levelChange;
	private int level;
	private TileMapManager tileManager;
	private TileMap	tileMap;
	private boolean gameOver;

	public GamePanel () {
		isRunning = false;
		isPaused = false;

		// this.items = items;
		mazescore = 0;
		dustscore = 0;
		berryLives = 1;

		currThr = null;
		// dustdropped = false;

		soundManager = SoundManager.getInstance();

		background =  ImageManager.loadImage ("images/background.png");
		comingsoon =  ImageManager.loadImage ("images/comingsoon.png");
		collectorBackground = ImageManager.loadImage ("images/BG.png");
	
		level = 1;
		levelChange = false;
	}

    public void updateScore(int value){
		if (currThr =="Maze"){
			mazescore = mazescore + value;

			if(mazescore == 8){
				soundManager.playClip("complete", false);
			}
		}
		
		if (currThr == "Fairydust"){
			dustscore = dustscore + value;
		}
		
		if (currThr == "Hunt"){
			berryLives = berryLives + value;
			if (berryLives == 0){
				startNewGame("Hunt");
			}
		}
	}



	public int getScore(){
		if (currThr =="Maze"){
			return mazescore;
		}
		
		if (currThr == "Fairydust"){
			return dustscore;
		}
		
		if (currThr == "Hunt"){
			return berryLives;
		}

		return 0;
	}


	public void createGameEntities(String game) {
		image = new BufferedImage (1020, 612, BufferedImage.TYPE_INT_RGB);

		if (game == "Maze"){

			boundary = new Boundary (this, 0, 612);
			leftbound = new LeftBoundary (this, -5, 0);
			poisonMaze = new PoisonMaze(this, 80, 0);

			// bag = new Bag(this, 651, 0);
			earth = new Trinket_Earth(this, 500, 20);
			fire = new Trinket_Fire(this, 700, 520);
			wind = new Trinket_Wind(this, 15, 110);
			berry = new Trinket_Berry(this, 310, 500);
			spark1 = new Trinket_Spark4(this, 460, 195); //dark pink
			spark2 = new Trinket_Spark4(this, 160, 450); //blue
			spark3 = new Trinket_Spark4(this, 620, 335); // light pink
			spark4 = new Trinket_Spark4(this, 180, 20); //light blue

			fes = new FesPlayer (this, 140, 290, boundary, leftbound, poisonMaze, earth, fire, wind, berry, spark1, spark2, spark3, spark4);
			// flyanimation = new FesAnimation(this, 0, 290, fes);
			
			mazescore = 0;
		}
		if (game == "Draft"){
			
		}
		if (game == "Fairydust"){
			dustscore = 0;

			bag = new FairyBag (this, 50, 500);
			acorn = new Acorn (this, 200, 10, bag);
			dust = new FallingFairyDust [4];
			dust[0] = new FallingFairyDust (this, 300, 10, bag);
			dust[1] = new FallingFairyDust (this, 400, 10, bag);
			dust[2] = new FallingFairyDust (this, 700, 10, bag); 
			dust[3] = new FallingFairyDust (this, 500, 10, bag); 
		}
		if (game == "Hunt"){
			berryLives = 1;
		}

	}


	public void run () {
		try {
			isRunning = true;
			while (isRunning) {
				if (!isPaused)
					gameUpdate();
				//check which thread and pass as string
				gameRender(currThr);
				Thread.sleep (50);	
			}
		}
		catch(InterruptedException e) {}
	}


	public void deleteObj(String type){
		if (type == "Earth"){
			earth = null;
		}
		else if (type == "Fire"){
			fire = null;
		}
		else if (type == "Wind"){
			wind = null;
		}
		else if (type == "Berry"){
			berry = null;
		}
		else if (type == "Spark1"){
			spark1 = null;
		}
		else if (type == "Spark2"){
			spark2 = null;
		}
		else if (type == "Spark3"){
			spark3 = null;
		}
		else if (type == "Spark4"){
			spark4 = null;
		}

	}

	public void gameUpdate() {

		if (currThr == "Maze"){
			if (berry != null){
				berry.update();
			}
		}
		else if (currThr == "Fairydust"){
			for (int i=0; i<4; i++) {
				dust[i].move();
			}
			if (acorn != null){
				acorn.move();
			}
		}
		else if(currThr == "Hunt"){

			tileMap.update();

			if (levelChange) {
				levelChange = false;
				tileManager = new TileMapManager (this);
	
				try {
					String filename = "maps/map" + level + ".txt";
					tileMap = tileManager.loadMap(filename) ;
					int w, h;
					w = tileMap.getWidth();
					h = tileMap.getHeight();
					System.out.println ("Changing level to Level " + level);
					System.out.println ("Width of tilemap " + w);
					System.out.println ("Height of tilemap " + h);
				}
				catch (Exception e) {		// no more maps: terminate game
					gameOver = true;
					System.out.println(e);
					System.out.println("Game Over"); 
					endGame();
					startNewGame("Hunt");
	/*
					System.exit(0);
	*/
				}
	
				// createGameEntities("Hunt");
				return;
			}

		}
		
	}

	public void clear(){ //home button functionality

		if (currThr == "Maze"){
			fes = null;
			boundary = null;
			leftbound = null;
			poisonMaze = null;
			earth = null;
			fire = null;
			wind = null;
			berry = null;
			spark1 = null;
			spark2 = null;
			spark3 = null;
			spark4 = null;
			image = null; //delete background
		}
		if (currThr == "Draft"){
			image = null; //delete background
		}
		if (currThr == "Fairydust"){
			bag = null;
			dust = null;
			acorn = null;
		}
		if (currThr == "Hunt"){
			image = null; //delete background
		}
		
		endGame();
	}



	public void updatePlayer (int direction) {

		if (currThr == "Maze"){
			if (fes != null && !isPaused) {
				fes.move(direction);
				// flyanimation.move(direction);
			}
		}
		else if (currThr == "Fairydust"){
			if (bag != null && !isPaused) {
				bag.move(direction);
			}
		}
		else if (currThr == "Hunt"){

		}

	}

	
	public void gameRender(String game) {

		if (game == "Maze"){
			// draw the game objects on the image
			Graphics2D imageContext = (Graphics2D) image.getGraphics();

			imageContext.drawImage(background, 0, 0, null);	// draw the background image

			if (fes != null){
				fes.draw(imageContext);
			}

			if (boundary != null){
				boundary.draw(imageContext);
			}

			if (leftbound != null){
				leftbound.draw(imageContext);
			}

			if (poisonMaze != null){
				poisonMaze.draw(imageContext);
			}

			if (earth != null){
				earth.draw(imageContext);
			}

			if (fire != null){
				fire.draw(imageContext);
			}

			if (wind != null){
				wind.draw(imageContext);
			}

			if (berry != null){
				berry.draw(imageContext);
			}

			if (spark1 != null){
				spark1.draw(imageContext);
			}

			if (spark2 != null){
				spark2.draw(imageContext);
			}

			if (spark3 != null){
				spark3.draw(imageContext);
			}

			if (spark4 != null){
				spark4.draw(imageContext);
			}

			Graphics2D g2 = (Graphics2D) getGraphics();	// get the graphics context for the panel
			g2.drawImage(image, 0, 0, 1020, 612, null);

			imageContext.dispose();
			g2.dispose();
		}//end of Maze Render
		if (game == "Draft"){
			Graphics2D iC = (Graphics2D) image.getGraphics();

			iC.drawImage(comingsoon, 0, 0, null);	// draw the background image
			Graphics2D g2 = (Graphics2D) getGraphics();	// get the graphics context for the panel
			g2.drawImage(image, 0, 0, 1020, 612, null);

			iC.dispose();
			g2.dispose();
		}
		if (game == "Fairydust"){
			Graphics2D imageContext = (Graphics2D) image.getGraphics();
			
			imageContext.drawImage(collectorBackground, 0, 0, null);	// draw the background image
		
			if (bag != null){
				bag.draw(imageContext);
			}

			if (dust != null) {
				for (int i=0; i<4; i++)
					dust[i].draw(imageContext);
			}
			
			if (acorn != null){
				acorn.draw(imageContext);
			}
			Graphics2D g2 = (Graphics2D) getGraphics();	// get the graphics context for the panel
			g2.drawImage(image, 0, 0, 1020, 612, null);


			imageContext.dispose();
			g2.dispose();
		}
		if (game == "Hunt"){
			Graphics2D imageContext = (Graphics2D) image.getGraphics();

			tileMap.draw (imageContext);

		
			if (gameOver) {
				Color darken = new Color (0, 0, 0, 125);
				imageContext.setColor (darken);
				imageContext.fill (new Rectangle2D.Double (0, 0, 1020, 612));
				// soundManager.playClip("complete", false);
				return;
			}

			Graphics2D g2 = (Graphics2D) getGraphics();	// get the graphics context for the panel
			g2.drawImage(image, 0, 0, 1020, 612, null);	// draw the image on the graphics context

			imageContext.dispose();
		}
	}


	public void startGame(String game) {				// initialise and start the game thread 
		
		endGame(); //in the event a thread is already running
		
		if (game == "Maze"){
			if (mazeThread == null) {
				soundManager.playClip ("background", true);
				createGameEntities("Maze");
				mazeThread = new Thread (this);			
				mazeThread.start();
				currThr = "Maze";

				if (berry != null) {
					berry.start();
				}
			}
		}
		if (game == "Fairydust"){
			if (fairydustThread == null) {
				soundManager.playClip ("background", true);
				createGameEntities("Fairydust");
				fairydustThread = new Thread (this);			
				fairydustThread.start();
				currThr = "Fairydust";
			}
		}
		if (game == "Draft"){
			if (draftThread == null) {
				createGameEntities("Draft");
				draftThread = new Thread (this);			
				draftThread.start();
				currThr = "Draft";
			}
		}
		if (game == "Hunt"){
			if (wingHuntThread == null) {
				soundManager.playClip ("background", true);
				createGameEntities("Hunt");
				currThr = "Hunt";

				gameOver = false;

				tileManager = new TileMapManager (this);

				try {
					tileMap = tileManager.loadMap("maps/map1.txt");
					int w, h;
					w = tileMap.getWidth();
					h = tileMap.getHeight();
					System.out.println ("Width of tilemap " + w);
					System.out.println ("Height of tilemap " + h);
				}
				
				catch (Exception e) {
					System.out.println(e);
					System.exit(0);
				}

				wingHuntThread = new Thread (this);			
				wingHuntThread.start();

				if (berry != null) {
					berry.start();
				}
			}
		}

	}


	public void startNewGame(String game) {				// initialise and start a new game thread 
		endGame(); //end game to start new game
		isPaused = false;


		if (currThr == null || !isRunning) {
			if (game == "Maze"){
				soundManager.playClip ("background", true);
				createGameEntities("Maze");
				mazeThread = new Thread (this);			
				mazeThread.start();
				currThr = "Maze";

				if (berry != null) {
					berry.start();
				}
			}
			if (game == "Fairydust"){
				soundManager.playClip ("background", true);
				createGameEntities("Fairydust");
				fairydustThread = new Thread (this);			
				fairydustThread.start();
				currThr = "Fairydust";
			}
			if (game == "Draft"){
				createGameEntities("Draft");
				draftThread = new Thread (this);			
				draftThread.start();
				currThr = "Draft";
			}
			if (game == "Hunt"){
				soundManager.playClip ("background", true);
				createGameEntities("Hunt");
				currThr = "Hunt";

				gameOver = false;
				level = 1;

				tileManager = new TileMapManager (this);

				try {
					tileMap = tileManager.loadMap("maps/map1.txt");
					int w, h;
					w = tileMap.getWidth();
					h = tileMap.getHeight();
					System.out.println ("Width of tilemap " + w);
					System.out.println ("Height of tilemap " + h);
				}
				catch (Exception e) {
					System.out.println(e);
					System.exit(0);
				}
				wingHuntThread = new Thread (this);			
				wingHuntThread.start();
			}
		}

	}

	public void pauseGame(){				// pause the game (don't update game entities)
		
		if (isRunning) {
			if (isPaused){ //Resume
				isPaused = false;
				soundManager.pauseClip("background", false);
			}
			else{ //Pause
				isPaused = true;
				soundManager.pauseClip("background", true);
			}
		}
	}

	public void endGame() {					// end the game thread
		isRunning = false;
		currThr = null;
		mazeThread = null;
		fairydustThread = null;
		draftThread = null;
		wingHuntThread = null;
	}

	public String getCurrThr(){
		return currThr;
	}

	public int getLevel(){
		return level;
	}

	public void endLevel() {
		level = level + 1;
		levelChange = true;
	}

	public void jump() {
		if (!gameOver)
			tileMap.jump();
	}

	public void moveLeft() {
		if (!gameOver)
			tileMap.moveLeft();
	}


	public void moveRight() {
		if (!gameOver)
			tileMap.moveRight();
	}


}