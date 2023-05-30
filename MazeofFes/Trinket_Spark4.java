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

import java.awt.Image;

public class Trinket_Spark4 {

	private GamePanel panel;
	private int x;
	private int y;
	private int width;
	private int height;

	private Image spark4;

	private boolean check = false;
 
	public Trinket_Spark4 (GamePanel p, int xPos, int yPos) {
		panel = p;

		x = xPos;
		y = yPos;

		width = 60;
		height = 70;

		spark4  = ImageManager.loadImage ("images/spark4.png");

	}


	public void draw (Graphics2D g2) {

		g2.drawImage(spark4, x, y, width, height, null);

	}

	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, width, height);
	}

	public boolean collidesWithFes (FesPlayer fes) {
		Rectangle2D.Double myRect = fes.getBoundingRectangle();
		Rectangle2D.Double spark4 = new Rectangle2D.Double (x, y, width, height);

		if (myRect.intersects(spark4)){
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

	public Image getImage(){
		return spark4;
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