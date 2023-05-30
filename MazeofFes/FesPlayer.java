package MazeofFes;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.time.temporal.WeekFields;

import javax.swing.JPanel;

import Application.GamePanel;
import Application.ImageManager;
import Effects.SoundManager;

import java.awt.Image;

public class FesPlayer {

	private GamePanel panel;
	private int x;
	private int y;
	private int width;
	private int height;

	private int dx;
	private int dy;

	private Image fesImg;
	private Image fesLeft;
	private Image fesRight;

	private SoundManager soundManager;

	private Boundary boundary;
	private LeftBoundary leftbound;
	private PoisonMaze poisonMaze;
	private Trinket_Earth earth;
	private Trinket_Fire fire;
	private Trinket_Wind wind;
	private Trinket_Berry berry;
	private Trinket_Spark4 spark1;
	private Trinket_Spark4 spark2;
	private Trinket_Spark4 spark3;
	private Trinket_Spark4 spark4;

	public FesPlayer (GamePanel p, int xPos, int yPos, Boundary boundary, LeftBoundary leftbound, 
						PoisonMaze maze,
						Trinket_Earth earth,
						Trinket_Fire fire,
						Trinket_Wind wind,
						Trinket_Berry berry,
						Trinket_Spark4 spark1,
						Trinket_Spark4 spark2,
						Trinket_Spark4 spark3,
						Trinket_Spark4 spark4
					 ) {
		panel = p;

		x = xPos;
		y = yPos;

		dx = 15;
		dy = 15;

		this.boundary = boundary;
		this.leftbound = leftbound;
		this.earth = earth;
		this.fire = fire;
		this.wind = wind;
		this.berry = berry;
		this.spark1 = spark1;
		this.spark2 = spark2;
		this.spark3 = spark3;
		this.spark4 = spark4;
		// this.bag = bag;
		poisonMaze = maze;

		width = 80;
		height = 100;


		fesLeft = ImageManager.loadImage ("images/fes_left1.png");
		fesRight = ImageManager.loadImage ("images/fes_right1.png");

		fesImg = fesRight;

		soundManager = SoundManager.getInstance();
	}


	public void draw (Graphics2D g2) {

		g2.drawImage(fesImg, x, y, width, height, null);

	}


	public void move (int direction) {

		if (!panel.isVisible ()) return;
      
		if (direction == 1) {
			x = x - dx;
			fesImg = fesLeft;     
			// move to left of GamePanel
		}
		else 
		if (direction == 2) {
			x = x + dx;
        	fesImg = fesRight;
			if (x > 1000){ //Maze complete
				panel.startNewGame("Maze");
				soundManager.playClip("bye", false);
			}     
			// move to right of GamePanel
		}
		else 
		if (direction == 3) {
			y = y - dy;
			if (y < 0)
				y = 0;
			// move up 
		}
		else 
		if (direction == 4) {
			y = y + dy;
			// move down 
		}

		if (collidesWithBoundary()) {
			if (direction == 3)
				y = boundary.getY() + boundary.getHeight();
		else
			if (direction == 4)
				y = boundary.getY() - height;
		 }

		 
		if (collidesWithLeftBoundary()) {
			if (direction == 1)
				x = leftbound.getX() + leftbound.getWidth();
		}

		if (poisonMaze.checkCollision(this)) { //reset player: after adding items update this to run proper game restart 
			soundManager.playClip("death", false);
			panel.startNewGame("Maze");
		}

		if (earth != null){
			if (earth.collidesWithFes(this)) { 
				panel.updateScore(1); 
				panel.deleteObj("Earth");
				soundManager.playClip("dirt", false);
				// bag.removeGray("Earth");
				earth = null;
			}
		}

		if (fire != null){
			if (fire.collidesWithFes(this)) { 
				panel.updateScore(1); 
				panel.deleteObj("Fire");
				soundManager.playClip("fire", false);
				// bag.removeGray("Fire");
				fire = null;
			}
		}

		if (wind != null){
			if (wind.collidesWithFes(this)) { 
				panel.updateScore(1); 
				panel.deleteObj("Wind");
				soundManager.playClip("wind", false);
				// bag.removeGray("Wind");
				wind = null;
			}
		}

		if (berry != null){
			if (berry.collidesWithFes(this)) { 
				panel.updateScore(1); 
				panel.deleteObj("Berry");
				soundManager.playClip("berry", false);
				dx = dx + 5;
				dy = dy + 5;
				// bag.removeGray("Berry");
				berry = null;
			}
		}

		if (spark1 != null){
			if (spark1.collidesWithFes(this)) { 
				panel.updateScore(1); 
				panel.deleteObj("Spark1");
				soundManager.playClip("twinkle", false);
				// bag.removeGray("Spark1");
				spark1 = null;
			}
		}

		if (spark2 != null){
			if (spark2.collidesWithFes(this)) { 
				panel.updateScore(1); 
				panel.deleteObj("Spark2");
				soundManager.playClip("twinkle", false);
				// bag.removeGray("Spark2");
				spark2 = null;
			}
		}

		if (spark3 != null){
			if (spark3.collidesWithFes(this)) { 
				panel.updateScore(1); 
				panel.deleteObj("Spark3");
				soundManager.playClip("twinkle", false);
				// bag.removeGray("Spark3");
				spark3 = null;
			}
		}

		if (spark4 != null){
			if (spark4.collidesWithFes(this)) { 
				panel.updateScore(1); 
				panel.deleteObj("Spark4");
				soundManager.playClip("twinkle", false);
				// bag.removeGray("Spark4");
				spark4 = null;
			}
		}

	}


	public boolean isOnFes (int x, int y) {
		Rectangle2D.Double myRectangle = getBoundingRectangle();
		return myRectangle.contains(x, y);
	}



	public boolean collidesWithBoundary () {
		Rectangle2D.Double myRect = getBoundingRectangle();
		Rectangle2D.Double boundaryRect = boundary.getBoundingRectangle();
		
		return myRect.intersects(boundaryRect); 
	}

	public boolean collidesWithLeftBoundary () {
		Rectangle2D.Double myRect = getBoundingRectangle();
		Rectangle2D.Double lboundaryRect = leftbound.getBoundingRectangle();
		
		return myRect.intersects(lboundaryRect); 
	}

	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, width, height);
	}

	public int getX() {
		return x;
	}


	public int getY() {
		return y;
	}


	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}
  
}