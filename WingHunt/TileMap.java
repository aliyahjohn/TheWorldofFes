package WingHunt;
import java.awt.Image;
import java.security.spec.ECField;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Arrays;
import java.util.Iterator;
import javax.swing.JPanel;

import MazeofFes.Trinket_Berry;
import MazeofFes.Trinket_Spark4;
import Application.BackgroundManager;
import Application.BackgroundManager2;
import Application.GamePanel;
import Effects.SoundManager;

/**
    The TileMap class contains the data for a tile-based
    map, including Sprites. Each tile is a reference to an
    Image. Images are used multiple times in the tile map.
    map.
*/

public class TileMap {

    private static final int TILE_SIZE = 64;
    private static final int TILE_SIZE_BITS = 6;

    private Image[][] tiles;
    private int screenWidth, screenHeight;
    private int mapWidth, mapHeight;
    private int offsetY, offsetX;

    private LinkedList sprites;
    private Player player;
    private Flag flag;
    private Trinket_Berry berries[], extraBerry;
    private Trinket_Spark4 fairyDust;
    private Boss boss;
    private FireBall fireball[];
    private Lives lives;
    private winSprite wings;

    BackgroundManager bgManager;
    BackgroundManager2 bgManager2;

    private GamePanel panel;
    private Dimension dimension;
    private SoundManager soundManager;


    /**
        Creates a new TileMap with the specified width and
        height (in number of tiles) of the map.
    */
    public TileMap(GamePanel panel, int width, int height) {

        this.panel = panel;
        dimension = panel.getSize();

        screenWidth = dimension.width;
        screenHeight = dimension.height;

        System.out.println ("Width: " + screenWidth);
        System.out.println ("Height: " + screenHeight);

        mapWidth = width;
        mapHeight = height;

            // get the y offset to draw all sprites and tiles

            offsetY = screenHeight - tilesToPixels(mapHeight);
        System.out.println("offsetY: " + offsetY);

        bgManager = new BackgroundManager (panel, 12);
        bgManager2 = new BackgroundManager2(panel, 12);
        soundManager = SoundManager.getInstance();

        tiles = new Image[mapWidth][mapHeight];
        player = new Player (panel, this, bgManager, berries);
        lives = new Lives(panel, 100, 100, player);

        if (panel.getLevel() == 1){
            flag = new Flag (panel, player);

            berries = new Trinket_Berry [5];
            berries[0] = new Trinket_Berry(panel, 120, 350);
            berries[1] = new Trinket_Berry(panel, 1636, 100);
            berries[2] = new Trinket_Berry(panel, 2396, 165);
            berries[3] = new Trinket_Berry(panel, 3656, 480);
            berries[4] = new Trinket_Berry(panel, 4566, 230);
        }
        else{

            wings = new winSprite(panel, 5400, 455, player);

            fireball = new FireBall[2];
            fireball[0] = new FireBall(panel, 950, 260, player);
            fireball[0] = new FireBall(panel, 1100, 260, player);

            boss = new Boss(panel, player);
            extraBerry = new Trinket_Berry(panel, 4641, 455);
            fairyDust = new Trinket_Spark4(panel, 150, 452);
        }

       
    
        sprites = new LinkedList();

        Image playerImage = player.getImage();
        int playerHeight = playerImage.getHeight(null);

        int x, y;
        x = (dimension.width / 2) + TILE_SIZE;		// position player in middle of screen

        //x = 1000;					// position player in 'random' location
        y = dimension.height - (TILE_SIZE + playerHeight);

            player.setX(x);
            player.setY(y - 100);

        System.out.println("Player coordinates: " + x + "," + y);

    }


    /**
        Gets the width of this TileMap (number of pixels across).
    */
    public int getWidthPixels() {
	    return tilesToPixels(mapWidth);
    }


    /**
        Gets the width of this TileMap (number of tiles across).
    */
    public int getWidth() {
        return mapWidth;
    }


    /**
        Gets the height of this TileMap (number of tiles down).
    */
    public int getHeight() {
        return mapHeight;
    }


    public int getOffsetY() {
	    return offsetY;
    }

    /**
        Gets the tile at the specified location. Returns null if
        no tile is at the location or if the location is out of
        bounds.
    */
    public Image getTile(int x, int y) {
        if (x < 0 || x >= mapWidth ||
            y < 0 || y >= mapHeight)
        {
            return null;
        }
        else {
            return tiles[x][y];
        }
    }


    /**
        Sets the tile at the specified location.
    */
    public void setTile(int x, int y, Image tile) {
        tiles[x][y] = tile;
    }


    /**
        Gets an Iterator of all the Sprites in this map,
        excluding the player Sprite.
    */

    public Iterator getSprites() {
        return sprites.iterator();
    }

    /**
        Class method to convert a pixel position to a tile position.
    */

    public static int pixelsToTiles(float pixels) {
        return pixelsToTiles(Math.round(pixels));
    }


    /**
        Class method to convert a pixel position to a tile position.
    */

    public static int pixelsToTiles(int pixels) {
        return (int)Math.floor((float)pixels / TILE_SIZE);
    }


    /**
        Class method to convert a tile position to a pixel position.
    */

    public static int tilesToPixels(int numTiles) {
        return numTiles * TILE_SIZE;
    }

    /**
        Draws the specified TileMap.
    */
    public void draw(Graphics2D g2)
    {
        int mapWidthPixels = tilesToPixels(mapWidth);

        // get the scrolling position of the map
        // based on player's position

        offsetX = screenWidth / 2 -
            Math.round(player.getX()) - TILE_SIZE;
        offsetX = Math.min(offsetX, 0);
        offsetX = Math.max(offsetX, screenWidth - mapWidthPixels);

/*
        // draw black background, if needed
        if (background == null ||
            screenHeight > background.getHeight(null))
        {
            g.setColor(Color.black);
            g.fillRect(0, 0, screenWidth, screenHeight);
        }
*/
	    // draw the background first
        if (panel.getLevel() == 1){
            bgManager.draw (g2);
        }
        else if (panel.getLevel() == 2){
            bgManager2.draw(g2);
        }


        // draw the visible tiles

        int firstTileX = pixelsToTiles(-offsetX);
        int lastTileX = firstTileX + pixelsToTiles(screenWidth) + 1;
        for (int y=0; y<mapHeight; y++) {
            for (int x=firstTileX; x <= lastTileX; x++) {
                Image image = getTile(x, y);
                if (image != null) {
                    g2.drawImage(image,
                        tilesToPixels(x) + offsetX,
                        tilesToPixels(y) + offsetY,
                        null);
                }
            }
        }


        // draw player
        g2.drawImage(player.getImage(),
            Math.round(player.getX()) + offsetX,
            Math.round(player.getY()), //+ offsetY,
            null);
       

        //draw player lives
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < panel.getScore(); j++){
                g2.drawImage(lives.getHeartImg(),
                player.getX() + (j * 15) + offsetX,
                player.getY() - 30, 15, 15,
                null);
            }
            g2.drawImage(lives.getNoHeartImg(),
            player.getX() + (i * 15) + offsetX,
            player.getY() - 30, 15, 15, 
            null);
        }
        if (panel.getScore() == 6){
            g2.drawImage(lives.getHeartImg(),
                player.getX() + (5 * 15) + offsetX,
                player.getY() - 30, 15, 15,
                null);
        }

        if (panel.getLevel() == 1){
            // draw flag sprite
            Image scaled = flag.getImage().getScaledInstance(85, 100, Image.SCALE_SMOOTH);
            g2.drawImage(scaled,
            flag.getX()
             + offsetX, flag.getY(),
                null);

            //draw berries at dif pos's on map
            for (int i = 0; i < berries.length; i++){
                if (berries[i] != null){
                    g2.drawImage(berries[i].getImage(),
                    berries[i].getX() + offsetX, berries[i].getY(), 60, 55,
                    null);
                }
            }
        }
        else if (panel.getLevel() == 2){
            //draw boss sprite
            Image scaled = boss.getImage().getScaledInstance(250, 170, Image.SCALE_SMOOTH);
            g2.drawImage(scaled,
            boss.getX()
             + offsetX, boss.getY(),
                null);


            //draw projectiles
            for (int i = 0; i < fireball.length; i++){
                if (fireball[i] != null){
                    g2.drawImage(fireball[i].getImage(),
                    fireball[i].getX()
                    + offsetX, fireball[i].getY(), 40, 50,
                        null);
                }
            }

            //draw wings to collect
            // Image scaled1 = wings.getImage().getScaledInstance(85, 100, Image.SCALE_SMOOTH);
            g2.drawImage(wings.getImage(),
            wings.getX() + offsetX, wings.getY(), 60, 55,
                null);


            //draw fairydust
            if (fairyDust != null){
                g2.drawImage(fairyDust.getImage(),
                fairyDust.getX() + offsetX, fairyDust.getY(), 60, 55,
                null);
            }


            //draw extra life
            if (extraBerry != null){
                g2.drawImage(extraBerry.getImage(),
                extraBerry.getX() + offsetX, extraBerry.getY(), 60, 55,
                null);
            }
        }

/*
        // draw sprites
        Iterator i = map.getSprites();
        while (i.hasNext()) {
            Sprite sprite = (Sprite)i.next();
            int x = Math.round(sprite.getX()) + offsetX;
            int y = Math.round(sprite.getY()) + offsetY;
            g.drawImage(sprite.getImage(), x, y, null);

            // wake up the creature when it's on screen
            if (sprite instanceof Creature &&
                x >= 0 && x < screenWidth)
            {
                ((Creature)sprite).wakeUp();
            }
        }
*/

    }


    public void moveLeft() {
	int x, y;
	x = player.getX();
	y = player.getY();

	String mess = "Going left. x = " + x + " y = " + y;
	System.out.println(mess);

	player.move(1);

    }


    public void moveRight() {
	int x, y;
	x = player.getX();
	y = player.getY();

	String mess = "Going right. x = " + x + " y = " + y;
	System.out.println(mess);

	player.move(2);

    }


    public void jump() {
	int x, y;
	x = player.getX();
	y = player.getY();

	String mess = "Jumping. x = " + x + " y = " + y;
	System.out.println(mess);

	player.move(3);

    }


    public void deleteBerry(int i){
		berries[i] = null;
        panel.updateScore(1);
        soundManager.playClip("berry", false);
    }


    public void update() { //objs may be null depending on level
        player.update();
      
        if (wings != null){
            wings.update();

            if(wings.collidesWithPlayer()){
                soundManager.playClip("complete", false);
                panel.startNewGame("Hunt");
            }
        }

        if (fireball != null){
            for (int i = 0; i < fireball.length; i++){
                if(fireball[i] != null){
                    fireball[i].move();

                    if (fireball[i].collidesWithPlayer()){
                        soundManager.playClip("death", false);
                        panel.updateScore(-1);
                        fireball[i].setLocation();
                    }
                }
            }
        }
        
        if (boss != null){
            boss.update();
        }

        if (berries != null){
            for (int i = 0; i < berries.length; i++){
                if(berries[i] != null){
                    berries[i].update();

                    if (berries[i].collidesWithPlayer(player)) {
                        deleteBerry(i);
                    }
                }
            }
        }

        if (flag != null){
            flag.update();

            if (flag.collidesWithPlayer()) {
                panel.endLevel();
                return;
            }
        }
        

        if (fairyDust != null){
            if (fairyDust.collidesWithPlayer(player)){
                player.increaseJumpDist();
                fairyDust = null; //delete 
                soundManager.playClip("twinkle", false);
            }
        }

        if (extraBerry != null){
            extraBerry.update();
            if (extraBerry.collidesWithPlayer(player)){
                extraBerry = null;
                panel.updateScore(1);
                soundManager.playClip("berry", false);
            }
        }

    }

}
