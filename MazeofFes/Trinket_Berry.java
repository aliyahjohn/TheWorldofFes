package MazeofFes;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.time.temporal.WeekFields;
import java.util.ArrayList;

import javax.swing.JPanel;

import Application.GamePanel;
import Application.ImageManager;
import MazeofFes.FesPlayer;
import WingHunt.Player;
import Effects.Animation;

import java.awt.Image;

public class Trinket_Berry {

	private GamePanel panel;
	private int x;
	private int y;
	private int width;
	private int height;

	private Image berry, berry1, berry2;
	private Animation animation;

	private boolean check = false;
 
	public Trinket_Berry (GamePanel p, int xPos, int yPos) {
		panel = p;

		x = xPos;
		y = yPos;

		width = 70;
		height = 65;

		animation = new Animation();

		// load images for blinking face animation
		berry  = ImageManager.loadImage ("images/berry.png");
		berry1 = ImageManager.loadImage("images/berry1.PNG");
		berry2 = ImageManager.loadImage("images/berry2.PNG");
	
		// create animation object and insert frames
		animation.addFrame(berry, 250);
		animation.addFrame(berry1, 150);
		animation.addFrame(berry2, 150);
		animation.addFrame(berry, 150);
		animation.addFrame(berry1, 200);
		animation.addFrame(berry2, 150);
	}

	public void start() {
		animation.start();
	}

	
	public void update() {
		animation.update();
	}


	public void draw (Graphics2D g2) {
		g2.drawImage(animation.getImage(), x, y, width, height, null);
	}

	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, width, height);
	}

	public boolean collidesWithFes (FesPlayer fes) {
		Rectangle2D.Double myRect = fes.getBoundingRectangle();
		Rectangle2D.Double berry = new Rectangle2D.Double (x, y, width, height);

		if (myRect.intersects(berry)){
			check = true;
		}

		return check;
	}

	public boolean collidesWithPlayer (Player player) {
		Rectangle2D.Double berryRect = getBoundingRectangle();
		Rectangle2D.Double playerRect = player.getBoundingRectangle();
		
		if (berryRect.intersects(playerRect)) {
			System.out.println ("Collision with player!");
			return true;
		}
		else
			return false;
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
  
	public Image getImage() {
		return animation.getImage();
	 }
}