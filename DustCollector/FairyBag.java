package DustCollector;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

import Application.ImageManager;

public class FairyBag {

	private JPanel panel;
	private int x;
	private int y;
	private int width;
	private int height;
    private int collisionCount;

	private int dx;
	private int dy;

	private Rectangle2D.Double bag;

	private Color backgroundColour;
    private Image bagImage;

    // private FallingFairyDust dust[];

	public FairyBag (JPanel p, int xPos, int yPos) {
		panel = p;
		backgroundColour = panel.getBackground ();

		x = xPos;
		y = yPos;

        // this.dust = dust;

        collisionCount = 0;

		dx = 20;
		dy = 0;

		width = 90;
		height = 110;

        bagImage = ImageManager.loadImage ("images/bag.png");
	}


	public void draw (Graphics2D g2) {
        g2.drawImage(bagImage, x, y, width, height, null);
	}



/*
	public void move (int direction) {

		if (!panel.isVisible ()) return;
      
		if (direction == 1)
			x = x - dx;			// move left
		else
		if (direction == 2)
			x = x + dx;			// move right
  
	}
*/


	public void move (int direction) {

		if (!panel.isVisible ()) return;
      
		if (direction == 1) {
			x = x - dx;
          
			if (x < -40)			// move to right of GamePanel
				x = panel.getWidth() - 30;
		}
		else 
		if (direction == 2) {
			x = x + dx;
          
			if (x > panel.getWidth() -30)			// move to left of GamePanel
				x = -40;
		}

        // for (int i = 0; i <4; i++){
        //     if (dust[i].collidesWithBag()){
        //         collisionCount++;
        //     }
        // }

	}

    public void sizeUp(){
        width = width + 2;
        height = height + 2;
        y = y - 2;
    }

	public boolean isOnBag (int x, int y) {
		if (bag == null)
			return false;

		return bag.contains(x, y);
	}


	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, width, height);
	}

}