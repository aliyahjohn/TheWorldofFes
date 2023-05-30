package WingHunt;
import java.util.Random;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

import Application.ImageManager;
import Effects.*;

import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;


public class Boss {

	Animation animation;

	private static final int XSIZE = 50;		// width of the image
	private static final int YSIZE = 50;		// height of the image
	//private static final int DX = 2;		// amount of pixels to move in one update
	private static final int YPOS = 150;		// vertical position of the image

	private JPanel panel;				// JPanel on which image will be drawn
	private Dimension dimension;
	private int x;
	private int y;
	private int dx;
	private int dy;

	private Player player;

	private Image spriteImage;			// image for sprite

	//Graphics2D g2;

	int time, timeChange;				// to control when the image is grayed
	boolean originalImage, grayImage;


	public Boss (JPanel panel, Player player) {
		this.panel = panel;
		//Graphics g = window.getGraphics ();
		//g2 = (Graphics2D) g;

		animation = new Animation();

		dimension = panel.getSize();
		Random random = new Random();
		// x = 5000;
		x = 861;
		y = 190;
		dx = 5;
		dy = 0;

		this.player = player;

		time = 0;				// range is 0 to 10
		timeChange = 1;				// set to 1
		originalImage = true;
		grayImage = false;

		spriteImage = ImageManager.loadImage("images/boss1.png");

		int imageWidth = (int) spriteImage.getWidth(null) / 8;
		int imageHeight = spriteImage.getHeight(null);

		for (int i=0; i < 8; i++) {

			BufferedImage frameImage = new BufferedImage (imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) frameImage.getGraphics();
     
			g.drawImage(spriteImage, 
					0, 0, imageWidth, imageHeight,
					i*imageWidth, 0, (i*imageWidth)+imageWidth, imageHeight,
					null);

			animation.addFrame(frameImage, 100);
		}
	}


	public void start() {
		x = 5;
        y = 10;
		animation.start();
	}

	public void draw(Graphics2D g2) {
		g2.drawImage(animation.getImage(), x, y, 150, 150, null);
	}


	public boolean collidesWithPlayer () {
		Rectangle2D.Double myRect = getBoundingRectangle();
		Rectangle2D.Double playerRect = player.getBoundingRectangle();
		
		if (myRect.intersects(playerRect)) {
			System.out.println ("Collision with player!");
			return true;
		}
		else
			return false;
	}


	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, XSIZE, YSIZE);
	}


	public void update() {	
		animation.update();

		x = x + dx;

		if (x < 700 || x > 900)
			dx = dx * -1;

	}


   	public int getX() {
      		return x;
   	}


   	public void setX(int x) {
      		this.x = x;
   	}


   	public int getY() {
      		return y;
   	}


   	public void setY(int y) {
      		this.y = y;
   	}


   	public Image getImage() {
      	return animation.getImage();
   	}

}