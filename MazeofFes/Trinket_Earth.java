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

public class Trinket_Earth {

	private GamePanel panel;
	private int x;
	private int y;
	private int width;
	private int height;

	private Image earth;

	private boolean check = false;
 
	public Trinket_Earth (GamePanel p, int xPos, int yPos) {
		panel = p;

		x = xPos;
		y = yPos;

		width = 60;
		height = 70;

		earth  = ImageManager.loadImage ("images/earth.png");
	}


	public void draw (Graphics2D g2) {

		g2.drawImage(earth, x, y, width, height, null);

	}

	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, width, height);
	}

	public boolean collidesWithFes (FesPlayer fes) {
		Rectangle2D.Double myRect = fes.getBoundingRectangle();
		Rectangle2D.Double earth = new Rectangle2D.Double (x, y, width, height);

		if (myRect.intersects(earth)){
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