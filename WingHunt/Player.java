package WingHunt;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

import MazeofFes.Trinket_Berry;
import Application.BackgroundManager;
import Application.GamePanel;
import Application.ImageManager;

import javax.swing.JPanel;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Point;

public class Player {			

   private static final int DX = 15;	// amount of X pixels to move in one keystroke
   private static final int DY = 32;	// amount of Y pixels to move in one keystroke

   private static final int TILE_SIZE = 64;

   private GamePanel panel;		// reference to the JFrame on which player is drawn
   private TileMap tileMap;
   private BackgroundManager bgManager;

   private int x;			// x-position of player's sprite
   private int y;			// y-position of player's sprite

   Graphics2D g2;
   private Dimension dimension;

   private Image playerImage, playerLeftImage, playerRightImage, resizedRight, resizedLeft;
   private Image playerLeftImage1, playerRightImage1, resizedRight1, resizedLeft1;

   private boolean jumping;
   private int timeElapsed;
   private int startY;
   private int jumpDist;

   private boolean goingUp;
   private boolean goingDown;

   private int moveTracker;

   private boolean inAir;
   private int initialVelocity;
   private int startAir;
   private boolean goingRight;

   private Trinket_Berry berries[];

   public Player (GamePanel panel, TileMap t, BackgroundManager b, Trinket_Berry berries[]) {
      this.panel = panel;
	  this.berries = berries;

      tileMap = t;			// tile map on which the player's sprite is displayed
      bgManager = b;			// instance of BackgroundManager

      goingUp = goingDown = false;
      inAir = false;
	  goingRight = false;

	  moveTracker = 0;
	  jumpDist = 10;

      playerLeftImage = ImageManager.loadImage("images/fes_left1.png");
      playerRightImage = ImageManager.loadImage("images/fes_right1.png");
	  playerLeftImage1 = ImageManager.loadImage("images/fes_left2.png");
      playerRightImage1 = ImageManager.loadImage("images/fes_right2.png");
	  resizedRight = playerRightImage.getScaledInstance(80, 100, Image.SCALE_SMOOTH);
	  resizedLeft = playerLeftImage.getScaledInstance(80, 100, Image.SCALE_SMOOTH);
	  resizedRight1 = playerRightImage1.getScaledInstance(80, 100, Image.SCALE_SMOOTH);
	  resizedLeft1 = playerLeftImage1.getScaledInstance(80, 100, Image.SCALE_SMOOTH);
	  playerImage = resizedRight;

   }


   public Point collidesWithTile(int newX, int newY) {

      int playerWidth = playerImage.getWidth(null);
      int offsetY = tileMap.getOffsetY();
	  int xTile = tileMap.pixelsToTiles(newX);
	  int yTile = tileMap.pixelsToTiles(newY - offsetY);

	  if (tileMap.getTile(xTile, yTile) != null) {
	        Point tilePos = new Point (xTile, yTile);
	  	return tilePos;
	  }
	  else {
		return null;
	  }
   }


   public Point collidesWithTileDown (int newX, int newY) {

	  int playerWidth = playerImage.getWidth(null);
      int playerHeight = playerImage.getHeight(null);
      int offsetY = tileMap.getOffsetY();
	  int xTile = tileMap.pixelsToTiles(newX);
	  int yTileFrom = tileMap.pixelsToTiles(y - offsetY);
	  int yTileTo = tileMap.pixelsToTiles(newY - offsetY + playerHeight);

	  for (int yTile=yTileFrom; yTile<=yTileTo; yTile++) {
		if (tileMap.getTile(xTile, yTile) != null) {
	        	Point tilePos = new Point (xTile, yTile);
	  		return tilePos;
	  	}
		else {
			if (tileMap.getTile(xTile+1, yTile) != null) {
				int leftSide = (xTile + 1) * TILE_SIZE;
				if (newX + playerWidth > leftSide) {
				    Point tilePos = new Point (xTile+1, yTile);
				    return tilePos;
			        }
			}
		}
	  }

	  return null;
   }


   public Point collidesWithTileUp (int newX, int newY) {

	  int playerWidth = playerImage.getWidth(null);

      	  int offsetY = tileMap.getOffsetY();
	  int xTile = tileMap.pixelsToTiles(newX);

	  int yTileFrom = tileMap.pixelsToTiles(y - offsetY);
	  int yTileTo = tileMap.pixelsToTiles(newY - offsetY);
	 
	  for (int yTile=yTileFrom; yTile>=yTileTo; yTile--) {
		if (tileMap.getTile(xTile, yTile) != null) {
	        	Point tilePos = new Point (xTile, yTile);
	  		return tilePos;
		}
		else {
			if (tileMap.getTile(xTile+1, yTile) != null) {
				int leftSide = (xTile + 1) * TILE_SIZE;
				if (newX + playerWidth > leftSide) {
				    Point tilePos = new Point (xTile+1, yTile);
				    return tilePos;
			        }
			}
		}
				    
	  }

	  return null;
   }
 
/*

   public Point collidesWithTile(int newX, int newY) {

	 int playerWidth = playerImage.getWidth(null);
	 int playerHeight = playerImage.getHeight(null);

      	 int fromX = Math.min (x, newX);
	 int fromY = Math.min (y, newY);
	 int toX = Math.max (x, newX);
	 int toY = Math.max (y, newY);

	 int fromTileX = tileMap.pixelsToTiles (fromX);
	 int fromTileY = tileMap.pixelsToTiles (fromY);
	 int toTileX = tileMap.pixelsToTiles (toX + playerWidth - 1);
	 int toTileY = tileMap.pixelsToTiles (toY + playerHeight - 1);

	 for (int x=fromTileX; x<=toTileX; x++) {
		for (int y=fromTileY; y<=toTileY; y++) {
			if (tileMap.getTile(x, y) != null) {
				Point tilePos = new Point (x, y);
				return tilePos;
			}
		}
	 }
	
	 return null;
   }
*/


   public synchronized void move (int direction) {

      int newX = x;
      Point tilePos = null;
	  moveTracker++;

      if (!panel.isVisible ()) return;
      
      if (direction == 1) {		// move left
	  	//handle alternating images to create mock running animation
		goingRight = false;
		if (moveTracker%2 == 0){
			playerImage = resizedLeft;
		}
		else{
			playerImage = resizedLeft1;
		}

          newX = x - DX;
	  if (newX < 0) {
		x = 0;
		return;
	  }
		
	  tilePos = collidesWithTile(newX, y);
      }	
	  else 
	  if (direction == 2) {		// move right
		//handle alternating images to create mock running animation
		goingRight = true;
		if (moveTracker%2 == 0){
			playerImage = resizedRight;
		}
		else{
			playerImage = resizedRight1;
		}
	  	

      	  int playerWidth = playerImage.getWidth(null);
          newX = x + DX;

      	  int tileMapWidth = tileMap.getWidthPixels();

	  if (newX + playerImage.getWidth(null) >= tileMapWidth) {
	      x = tileMapWidth - playerImage.getWidth(null);
	      return;
	  }

	  tilePos = collidesWithTile(newX+playerWidth, y);			
      }
      else				// jump
      if (direction == 3 && !jumping) {	
          jump();
	  return;
      }
    
      if (tilePos != null) {  
         if (direction == 1) {
	     System.out.println (": Collision going left");
             x = ((int) tilePos.getX() + 1) * TILE_SIZE;	   // keep flush with right side of tile
	 }
         else
         if (direction == 2) {
	     System.out.println (": Collision going right");
      	     int playerWidth = playerImage.getWidth(null);
             x = ((int) tilePos.getX()) * TILE_SIZE - playerWidth; // keep flush with left side of tile
	 }
      }
      else {
          if (direction == 1) {
	      x = newX;
	      bgManager.moveLeft();
          }
	  else
	  if (direction == 2) {
	      x = newX;
	      bgManager.moveRight();
   	  }

          if (isInAir()) {
	      System.out.println("In the air. Starting to fall.");
	      if (direction == 1) {				// make adjustment for falling on left side of tile
      	          int playerWidth = playerImage.getWidth(null);
		  x = x - playerWidth + DX;
	      }
	      fall();
          }

		  if (y > 500){
			panel.startNewGame("Hunt");
		  }

		  if (y > 500){
			panel.startNewGame("Hunt");
		  }
      }
   }


   public boolean isInAir() {

      int playerHeight;
      Point tilePos;

      if (!jumping && !inAir) {   
	  playerHeight = playerImage.getHeight(null);
	  tilePos = collidesWithTile(x, y + playerHeight + 1); 	// check below player to see if there is a tile
	
  	  if (tilePos == null)				   	// there is no tile below player, so player is in the air
		return true;
	  else							// there is a tile below player, so the player is on a tile
		return false;
      }

      return false;
   }


   private void fall() {

      jumping = false;
      inAir = true;
      timeElapsed = 0;

      goingUp = false;
      goingDown = true;

      startY = y;
      initialVelocity = 0;
   }


   public void jump () {  

      if (!panel.isVisible ()) return;

      jumping = true;
      timeElapsed = 0;

      goingUp = true;
      goingDown = false;

      startY = y;
      initialVelocity = 65;
   }


   public void update () {
      int distance = 0;
      int newY = 0;
	  int newX = 0;

      timeElapsed++;

      if (jumping || inAir) {
	   distance = (int) (initialVelocity * timeElapsed - 
                             4.9 * timeElapsed * timeElapsed);
	   newY = startY - distance;
	//    if (panel.getLevel() == 1){ //	IF LEVEL 1:
	   		if (!goingRight){
				newX = this.getX() - jumpDist; //DISPLACE X WHILE JUMPING
			}
			else{
				newX = this.getX() + jumpDist;
			}
	//    } USED BEFORE FAIRY DUST IMPL. TO INCREASE DISTANCE FOR LEVEL
	//    else{
	// 	if (panel.getLevel() == 2){ //	IF LEVEL 2:
	// 		if (!goingRight){
	// 		 newX = this.getX() - 20; //FURTHER JUMPS
	// 	 }
	// 	 else{
	// 		 newX = this.getX() + 20;
	// 	 }
	// }
	//    }
	   
	

	   if (newY > y && goingUp) {
		goingUp = false;
 	  	goingDown = true;
	   }

	   if (goingUp) {
		Point tilePos = collidesWithTileUp (x, newY);	
	   	if (tilePos != null) {				// hits a tile going up
		   	System.out.println ("Jumping: Collision Going Up!");

      	  		int offsetY = tileMap.getOffsetY();
			int topTileY = ((int) tilePos.getY()) * TILE_SIZE + offsetY;
			int bottomTileY = topTileY + TILE_SIZE;

		   	y = bottomTileY;
		   	fall();
		}
	   	else {
			y = newY;
			x = newX;
			System.out.println ("Jumping: No collision.");
	   	}
            }
	    else
	    if (goingDown) {			
		Point tilePos = collidesWithTileDown (x, newY);	
	   	if (tilePos != null) {				// hits a tile going up
		    System.out.println ("Jumping: Collision Going Down!");
	  	    int playerHeight = playerImage.getHeight(null);
		    goingDown = false;

      	            int offsetY = tileMap.getOffsetY();
		    int topTileY = ((int) tilePos.getY()) * TILE_SIZE + offsetY;

	            y = topTileY - playerHeight;
	  	    jumping = false;
		    inAir = false;
	       }
	       else {
		    y = newY;
		    System.out.println ("Jumping: No collision.");
	       }
	   }
      }

	//   for (int i = 0; i < berries.length; i++){
	// 	if (collidesWithPlayer(berries[i])){
	// 		tileMap.deleteObj(i);
	// 	}
	//   }
   }

   public void increaseJumpDist(){
		jumpDist = jumpDist + 10;
   }

   public void moveUp () {

      if (!panel.isVisible ()) return;

      y = y - DY;
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
      return playerImage;
   }

   public Rectangle2D.Double getBoundingRectangle() {
      int playerWidth = playerImage.getWidth(null);
      int playerHeight = playerImage.getHeight(null);

      return new Rectangle2D.Double (x, y, playerWidth, playerHeight);
   }

}