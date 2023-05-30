package Application;
import javax.swing.*;			// need this for GUI objects

// import archive.ItemsPanel;

import java.awt.*;			// need this for Layout Managers
import java.awt.event.*;		// need this to respond to GUI events
import java.awt.image.BufferedImage;

import Effects.SoundManager;

public class GameWindow extends JFrame 
				implements ActionListener,
					   KeyListener
{
	// declare instance variables for user interface objects

	// declare labels 
	private String score;
	private JLabel scoreDisplay, instructions;
	
	// declare buttons

	private JButton startMaze, startBreaker, startCollector, startHunt;
	private JButton pause, restart, quit;

	private Image temp;
	private ImageIcon restartIcon;
	private ImageIcon pauseIcon;
	private ImageIcon resumeIcon;
	private ImageIcon homeIcon;
	private ImageIcon newPauseIcon, newResumeIcon;

	private Container c;

	private SoundManager soundManager;

	private JPanel mainPanel;
	private GamePanel gamePanel;
	private JPanel controlPanel;
	private JPanel buttonPanel;

	private ImageIcon mazeImage, breakerImage, collectImage, huntImage;
	private BufferedImage image;

	@SuppressWarnings({"unchecked"})
	public GameWindow() {
 
		setTitle ("The World of Fes");
		setSize (1090, 760);

		score = "0";

		soundManager = SoundManager.getInstance();

		// create user interface objects
		// create buttons
		mazeImage = new ImageIcon("images/mazeIcon.png");
		temp = mazeImage.getImage().getScaledInstance(490, 291, Image.SCALE_SMOOTH);
		ImageIcon newMazeIcon = new ImageIcon(temp);
	    startMaze = new JButton (newMazeIcon);

		breakerImage = new ImageIcon("images/draftIcon.png");
		temp = breakerImage.getImage().getScaledInstance(490, 291, Image.SCALE_SMOOTH);
		ImageIcon newBreakerIcon = new ImageIcon(temp);
	    startBreaker = new JButton (newBreakerIcon);


		huntImage = new ImageIcon("images/huntIcon.png");
		temp = huntImage.getImage().getScaledInstance(490, 291, Image.SCALE_SMOOTH);
		ImageIcon newHuntIcon = new ImageIcon(temp);
	    startHunt = new JButton (newHuntIcon);


		collectImage = new ImageIcon("images/dustIcon.png");
		temp = collectImage.getImage().getScaledInstance(490, 291, Image.SCALE_SMOOTH);
		ImageIcon newDustIcon = new ImageIcon(temp);
		startCollector = new JButton (newDustIcon);
		

		
		homeIcon = new ImageIcon("images/buttonHome.png");
		temp = homeIcon.getImage().getScaledInstance(98, 94, Image.SCALE_SMOOTH);
		ImageIcon newHomeIcon = new ImageIcon(temp);
		quit = new JButton(newHomeIcon);


		pauseIcon = new ImageIcon("images/buttonPause.png");
		temp = pauseIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		newPauseIcon = new ImageIcon(temp);
		pause = new JButton(newPauseIcon);


		restartIcon = new ImageIcon("images/buttonRest.png");
		temp = restartIcon.getImage().getScaledInstance(100, 98, Image.SCALE_SMOOTH);
		ImageIcon newRestartIcon = new ImageIcon(temp);
		restart = new JButton(newRestartIcon);


		resumeIcon = new ImageIcon("images/buttonPlay.png");
		temp = resumeIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		newResumeIcon = new ImageIcon(temp);


		// add listener to each button (same as the current object)
		startMaze.addActionListener(this);
		startBreaker.addActionListener(this);
		startCollector.addActionListener(this);
		startHunt.addActionListener(this);

		pause.addActionListener(this);
		restart.addActionListener(this);
		quit.addActionListener(this);

		//score Label: 
		scoreDisplay = new JLabel("", SwingConstants.CENTER);
		// scoreDisplay.setHorizontalAlignment(JLabel.CENTER);

		//score Label:
		instructions = new JLabel("", SwingConstants.CENTER);
		// instructions.setHorizontalAlignment(JLabel.CENTER);

		// create mainPanel
		mainPanel = new JPanel(); 
		controlPanel = new JPanel();
		buttonPanel = new JPanel();

		controlPanel.setLayout(new GridLayout(1, 3));
		controlPanel.setPreferredSize(new Dimension(1012, 100));

		buttonPanel.setLayout(new GridLayout(1, 3));
		buttonPanel.setPreferredSize(new Dimension(300, 100));
		
		//stylize buttons
		pause.setOpaque(false);
		pause.setContentAreaFilled(false);
		pause.setBorderPainted(false);

		restart.setOpaque(false);
		restart.setContentAreaFilled(false);
		restart.setBorderPainted(false);

		quit.setOpaque(false);
		quit.setContentAreaFilled(false);
		quit.setBorderPainted(false);

		buttonPanel.add(pause);
		buttonPanel.add(restart);
		buttonPanel.add(quit);
		buttonPanel.setBackground(null);

		controlPanel.add(instructions);
		controlPanel.add(buttonPanel);
		controlPanel.add(scoreDisplay);
		controlPanel.setBackground(null);


		createGamePanel();

		// background =  ImageManager.loadImage ("images/background.png");
		// image = new BufferedImage (1090, 700, BufferedImage.TYPE_INT_RGB);
		// Graphics2D imageContext = (Graphics2D) image.getGraphics();
		// imageContext.drawImage(background, 0, 0, null);	// draw the background image
		// imageContext.dispose();

		mainPanel.add(gamePanel);
		mainPanel.add(controlPanel);

		mainPanel.setBackground(new Color(255, 229, 164));

		// set up mainPanel to respond to keyboard and mouse
		// gamePanel.addMouseListener(this);
		mainPanel.addKeyListener(this);

		// add mainPanel to window surface
		c = getContentPane();
		c.add(mainPanel);

		// set properties of window
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);

		// set status bar message
		// statusBarTF.setText("Application started.");
	}


	public void createGamePanel(){
		gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension(1020, 612));


		GridLayout buttongrid = new GridLayout(2, 2);
		gamePanel.setLayout(buttongrid);


		// add buttons to gamePanel
		gamePanel.add (startMaze);
		gamePanel.add (startBreaker);
		gamePanel.add (startCollector);
		gamePanel.add (startHunt);
	}

	// implement single method in ActionListener interface

	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();
		JButton sourceButton = (JButton) e.getSource();
		
		// statusBarTF.setText(command + " button clicked.");


		//PAUSE
		if (sourceButton.equals(pause)) {
			
			gamePanel.pauseGame();
			if (sourceButton.getIcon().equals(newPauseIcon)){
				pause.setIcon(newResumeIcon);
			}
			else{
				pause.setIcon(newPauseIcon);
			}
		}

		//RETURN HOME
		if (sourceButton.equals(quit)) {
			gamePanel.clear();
			gamePanel.repaint();
			soundManager.stopClip("background");
			scoreDisplay.setText(" ");
			instructions.setText("");
		}
		


		//START A GAME
		if (sourceButton.equals(startMaze)) {
			score = String.valueOf(gamePanel.getScore());

			gamePanel.startGame("Maze");
			scoreDisplay.setText("<HTML><b> ITEMS COLLECTED: " + score + "/8 </HTML>");
			instructions.setText("<HTML> Help Fes collect trinkets. <br> Don't run into the walls! </font></HTML>");
		}

		if (sourceButton.equals(startCollector)){
			score = String.valueOf(gamePanel.getScore());

			gamePanel.startGame("Fairydust");
			scoreDisplay.setText("<HTML><b> FAIRYDUST COLLECTED: " + score + "</HTML>");
			instructions.setText("<HTML><b> Watch your brag grow with fairydust <br> but keep an eye out for the acorns! </HTML>");
		}


		if (sourceButton.equals(startBreaker)) {
			gamePanel.startGame("Draft");
		}

		if (sourceButton.equals(startHunt)) {
			score = String.valueOf(gamePanel.getScore());

			gamePanel.startGame("Hunt");
			scoreDisplay.setText("<HTML><b> LIVES: " + score + "/6 </HTML>");
			if (gamePanel.getLevel() == 1){
				instructions.setText("<HTML> Eat the berries to grow stronger! <br> You'll need the lives. </HTML>");
			}
			else{
				instructions.setText("<HTML> Collect fairydust to make further <br> jumps. Avoid the troll's boulders!. </HTML>");
			}
		}



		//RESTART A GAME
		if (sourceButton.equals(restart)){
			String currThr = gamePanel.getCurrThr();

			if (currThr != null){
				gamePanel.startNewGame(currThr);
			}
		}

		mainPanel.requestFocus();
	}

	// implement methods in KeyListener interface

	public void keyPressed(KeyEvent e) {
        score = String.valueOf(gamePanel.getScore());

		if (gamePanel.getCurrThr() == "Maze"){
			scoreDisplay.setText("<HTML><b> ITEMS COLLECTED: " + score + "/8 </HTML>");
			instructions.setText("<HTML> Help Fes collect trinkets. <br> Don't run into the walls! </font></HTML>");
		}
		else if (gamePanel.getCurrThr() == "Fairydust"){
			scoreDisplay.setText("<HTML><b> FAIRYDUST COLLECTED: " + score + "</HTML>");
			instructions.setText("<HTML><b> Watch your brag grow with fairydust <br> but keep an eye out for the acorns! </HTML>");
		}
		else if (gamePanel.getCurrThr() == "Hunt"){
			scoreDisplay.setText("<HTML><b> LIVES: " + score + "/6 </HTML>");
			if (gamePanel.getLevel() == 1){
				instructions.setText("<HTML> Eat the berries to grow stronger! <br> You'll need the lives. </HTML>");
			}
			else{
				instructions.setText("<HTML> Collect fairydust to make further <br> jumps. Avoid the troll's boulders!. </HTML>");
			}

		}
		
		int keyCode = e.getKeyCode();
		String keyText = e.getKeyText(keyCode);
		// keyTF.setText(keyText + " pressed.");

		if (keyCode == KeyEvent.VK_LEFT) {
			gamePanel.updatePlayer (1);
			if (gamePanel.getCurrThr() == "Hunt"){
				gamePanel.moveLeft();
			}
		}

		if (keyCode == KeyEvent.VK_RIGHT) {
			gamePanel.updatePlayer (2);
			if (gamePanel.getCurrThr() == "Hunt"){
				gamePanel.moveRight();
			}
		}

		if (keyCode == KeyEvent.VK_UP) {
			gamePanel.updatePlayer (3);
		}

		if (keyCode == KeyEvent.VK_SPACE) {
			if (gamePanel.getCurrThr() == "Hunt"){
				gamePanel.jump();
			}
		}

		if (keyCode == KeyEvent.VK_DOWN) {
			gamePanel.updatePlayer (4);
		}
	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {

	}

}