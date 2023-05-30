package MazeofFes;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.util.Random;

public class Boundary {

	private static final int WIDTH = 1090;
	private static final int HEIGHT = 5;

	private JPanel panel;
	private int x;
	private int y;


	public Boundary (JPanel p, int xPos, int yPos) { 

		panel = p;

		x = xPos;
		y = yPos;
	}


	public void draw (Graphics2D g2) {
      		Rectangle2D.Double boundary = new Rectangle2D.Double(x, y, WIDTH, HEIGHT);
     		g2.setColor (Color.BLACK);
     		g2.fill(boundary);
	}


	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, WIDTH, HEIGHT);
	}


	public int getX() {
		return x;
	}


	public int getY() {
		return y;
	}


	public int getWidth() {
		return WIDTH;
	}


	public int getHeight() {
		return HEIGHT;
	}
}