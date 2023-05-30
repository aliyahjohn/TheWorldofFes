package MazeofFes;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.Random;

public class PoisonMaze {

	private JPanel panel;
	private int x;
	private int y;

	private int width, width1;
	private int height, height1;

	private ArrayList<Rectangle2D.Double> trunk = new ArrayList<Rectangle2D.Double>();
	private boolean check = false;
	FesPlayer fes;


	public PoisonMaze (JPanel p, int xPos, int yPos) { 

		panel = p;

		x = xPos;
		y = yPos;

		width = 60;
		height = 350;

		//swaps values to rotate
		width1 = height;
		height1 = width;
	}

//HORI. DISTANCE BETWEEN WALLS, 75: Fes width (80) + wall width (60) + 20
//VERT. DISTANCE BETWEEN WALLS, 60: Fes height (100) + 10  >> use for y increments

	public void draw (Graphics2D g2) {
			trunk.add(new Rectangle2D.Double(x, y, width, height+20));
			g2.setColor (new Color(255, 252, 244));
			g2.fill(trunk.get(0));
	
			trunk.add(new Rectangle2D.Double(x+155, y+300, width, height));
			g2.setColor (new Color(255, 252, 244));
			g2.fill(trunk.get(1));

			trunk.add(new Rectangle2D.Double(x+310, y+150, width, height-180));
			g2.setColor (new Color(255, 252, 244));
			g2.fill(trunk.get(2));

			trunk.add(new Rectangle2D.Double(x+310, y+450, width, height-180));
			g2.setColor (new Color(255, 252, 244));
			g2.fill(trunk.get(3));

			trunk.add(new Rectangle2D.Double(x+460, y+180, width, height-50));
			g2.setColor (new Color(255, 252, 244));
			g2.fill(trunk.get(4));

			trunk.add(new Rectangle2D.Double(x+160, y+130, width1+100, height1)); 
			g2.setColor (new Color(255, 252, 244));
			g2.fill(trunk.get(5));

			// trunk.add(new Rectangle2D.Double(x+160, y+80, width, height-200));
			// g2.setColor (new Color(255, 252, 244));
			// g2.fill(trunk.get(6));

			// trunk.add(new Rectangle2D.Double(x+320, y, width, 60));
			// g2.setColor (new Color(255, 252, 244));
			// g2.fill(trunk.get(7));

			// trunk.add(new Rectangle2D.Double(x+480, y+140, width1-80, height1)); 
			// g2.setColor (new Color(255, 252, 244));
			// g2.fill(trunk.get(8));

			// trunk.add(new Rectangle2D.Double(x+150, y+380, width1+75, height1)); 
			// g2.setColor (new Color(255, 252, 244));
			// g2.fill(trunk.get(9));

			// trunk.add(new Rectangle2D.Double(x+300, y+220, width, height-130));
			// g2.setColor (new Color(255, 252, 244));
			// g2.fill(trunk.get(10));

			trunk.add(new Rectangle2D.Double(x+465, y+420, width1+40, height1)); 
			g2.setColor (new Color(255, 252, 244));
			g2.fill(trunk.get(6));

			// trunk.add(new Rectangle2D.Double(x+465, y+310, width, height-150));
			// g2.setColor (new Color(255, 252, 244));
			// g2.fill(trunk.get(12));

			// trunk.add(new Rectangle2D.Double(x+440, y+140, width1-160, height1)); 
			// g2.setColor (new Color(255, 252, 244));
			// g2.fill(trunk.get(13));

			trunk.add(new Rectangle2D.Double(x+700, y, width, height-50));
			g2.setColor (new Color(255, 252, 244));
			g2.fill(trunk.get(7));

			// trunk.add(new Rectangle2D.Double(x+440, y+140, width, height-120));
			// g2.setColor (new Color(255, 252, 244));
			// g2.fill(trunk.get(15));

			// trunk.add(new Rectangle2D.Double(x+370, y+240, width1-85, height1)); 
			// g2.setColor (new Color(255, 252, 244));
			// g2.fill(trunk.get(16));

	}


	//loop checks all rectangles for collision with fesPlayer
	public boolean checkCollision(FesPlayer fes) {
		Rectangle2D.Double myRect = fes.getBoundingRectangle();

		for (int i = 0; i < 20; i++){
			if (myRect.intersects(trunk.get(i))){
				check = true;
			}
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
