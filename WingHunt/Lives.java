package WingHunt;
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

public class Lives{

	private GamePanel panel;

   	private int x;
   	private int y;

   	private int width;
   	private int height;

    private Image heart, noheart;
    
	private int numLives;

 	private Player player;	// bag sprite
    private SoundManager soundManager;

	public Lives (GamePanel p, int xPos, int yPos, Player player) {
		panel = p;

		this.player = player;

		width = 20;
		height = 20;

		numLives = panel.getScore();

		// random = new Random();

		x = xPos;
		y = yPos;

        heart  = ImageManager.loadImage ("images/heart.png");
        noheart  = ImageManager.loadImage ("images/lostheart.png");


        soundManager = SoundManager.getInstance();
	}

	// public void draw (Graphics2D g2, int xPos, int yPos, Image image) {
    //     g2.drawImage(image, width, height, null);
	// }


	// public void move() {

	// 	if (!panel.isVisible ()) return;

	// 	for (int i = 0; i < 5; i++){
	// 		for (int j = 0; j < numLives; j++){
	// 			draw(g2, x = x +20, heart);
	// 		}
	// 		draw(g2, x = x +20, noheart);
	// 	}
	// }


	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public Image getHeartImg(){
        return heart;
	}

	public Image getNoHeartImg(){
        return noheart;
	}


}