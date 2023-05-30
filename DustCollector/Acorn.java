package DustCollector;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;
import javax.swing.JPanel;

import Application.GamePanel;
import Application.ImageManager;
import Effects.SoundManager;

import java.util.Random;

public class Acorn{

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

    private Image acorn;
    
	private Random random;

 	private FairyBag bag;	// bag sprite
    private SoundManager soundManager;

	public Acorn (GamePanel p, int xPos, int yPos, FairyBag bag) {
		panel = p;
		backgroundColour = panel.getBackground ();

		this.bag = bag;

		width = 40;
		height = 40;

		random = new Random();

		setLocation();

		// x = xPos;
		// y = yPos;

		dx = 0;		// no movement along x-axis	
		dy = 5; 	// would like the fairy to drop down

        acorn  = ImageManager.loadImage ("images/acorn.gif");

        soundManager = SoundManager.getInstance();
	}

   
	public void setLocation() {
		int panelWidth = panel.getWidth();
		x = random.nextInt (panelWidth - width);
		y = 10;
	}


	public void draw (Graphics2D g2) {
        g2.drawImage(acorn, x, y, width, height, null);
	}


	public void move() {

		if (!panel.isVisible ()) return;

		x = x + dx;
		y = y + dy;

		int height = panel.getHeight();
		
		if (collidesWithBag()) { //end game
            soundManager.playClip("death", false);
			panel.startNewGame("Fairydust");
		}
		else
		if (y > height) {
			setLocation();
			dy = dy + 1;	// increase increment each time alien hits bottom of GamePanel
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

	public boolean collidesWithBag() {
		Rectangle2D.Double myRect = getBoundingRectangle();
		Rectangle2D.Double bagRect = bag.getBoundingRectangle();
      
		return myRect.intersects(bagRect); 
	}

	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, width, height);
	}

}