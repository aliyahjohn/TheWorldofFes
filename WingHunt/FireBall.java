package WingHunt;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;
import javax.swing.JPanel;

import Application.ImageManager;
import Application.GamePanel;
import Effects.*;

import java.util.Random;

public class FireBall{

	private GamePanel panel;

   	private int x;
   	private int y;

   	private int width;
   	private int height;
    // private int collisionCount;

	private int dx;		// increment to move along x-axis
	private int dy;		// increment to move along y-axis

	Ellipse2D.Double head;	// ellipse drawn for face

	private Color backgroundColour;

    private Image fire, scaled;
    
	private Random random;

 	private Player player;	// bag sprite
     private SoundManager soundManager;

	public FireBall (GamePanel p, int xPos, int yPos, Player player) {
		panel = p;
		backgroundColour = panel.getBackground ();

		this.player = player;

		width = 60;
		height = 55;

		// random = new Random();

		setLocation();

		x = xPos;
		y = yPos;

		dx = 60;		// movement along x-axis	
		dy = 0; 	// no movement on y

        fire  = ImageManager.loadImage ("images/fireball.png");
		// Image scaled = fire.getScaledInstance(width, height, Image.SCALE_SMOOTH);


        soundManager = SoundManager.getInstance();
	}

   
	public void setLocation() {
		// int panelWidth = panel.getWidth();
		// x = random.nextInt (panelWidth - width);
		x = 991;
		y = 260;
	}


	// public void draw (Graphics2D g2) {
    //     g2.drawImage(fire, x, y, width, height, null);
	// }


	public void move() {

		if (!panel.isVisible ()) return;

		x = x + dx;

		if (x > 4650) {
			setLocation();
		}

	}

    
    // public void run () {
    //     try {
    //     for (int i = 1; i <= 5000; i++) {
    //         //erase();
    //     move ();
    //         draw();
    //         Thread.sleep (50);			// increase value of sleep time to slow down ball
    //     }
    //     }
    //     catch(InterruptedException e) {}
    // }

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public Image getImage(){
		return fire;
	}



	public boolean collidesWithPlayer() {
		Rectangle2D.Double myRect = getBoundingRectangle();
		Rectangle2D.Double playerRect = player.getBoundingRectangle();
      
		return myRect.intersects(playerRect); 
	}



	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, width, height);
	}

}