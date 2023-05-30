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

import java.awt.Image;

public class Trinket_Fire {

	private GamePanel panel;
	private int x;
	private int y;
	private int width;
	private int height;

	private Image fire;

	private boolean check = false;
 
	public Trinket_Fire (GamePanel p, int xPos, int yPos) {
		panel = p;

		x = xPos;
		y = yPos;

		width = 60;
		height = 70;

		fire  = ImageManager.loadImage ("images/fire.png");

	}


	public void draw (Graphics2D g2) {

		g2.drawImage(fire, x, y, width, height, null);

	}

	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, width, height);
	}

	public boolean collidesWithFes (FesPlayer fes) {
		Rectangle2D.Double myRect = fes.getBoundingRectangle();
		Rectangle2D.Double fire = new Rectangle2D.Double (x, y, width, height);

		if (myRect.intersects(fire)){
			check = true;
		}

		return check;
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