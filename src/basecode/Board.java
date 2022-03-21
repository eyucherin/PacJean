package basecode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * This class initializes the Board class for the game
 * 
 * @author PacJean
 * @version 5/11/2021
 */
public class Board extends JPanel implements ActionListener {
	// aspects of the game field
	private Dimension d;
	// status of the player's progression through the game
	private int livesLeft;
	private int level;
	private static int PADDING = 10;
	// the player character
	private Pacman pac;
	// the ghosts and their starting coordinates
	List<Ghost> ghosts = new ArrayList<Ghost>();
	private Ghost ghost1;
	private int counter;
	int ghostX;
	int ghostY;
	private Color wallColor;

	private int score = 0;
	// booleans tracking game status:
	private boolean startGame = false;
	private boolean pauseGame = false;
	private boolean gameTerminated = false;

	private boolean gameOver = false;
	private boolean gameCompleted = false;
	// the default sprite of the player's head
	private Image blob = new ImageIcon("src/images/blob3.0.png").getImage();
	// a timer and a counter to track which frame the program is on
	Timer timer;
	// an empty 2D 15x15 array to track the status of each square in the level
	public static int[][] grid = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
	// the length in pixels of each grid in the level
	static int gSize;

	/**
	 * Constructor for the Board class.
	 * 
	 * @param w the width of the display
	 * @param h the height of the display
	 */
	public Board(int w, int h) {
		Random rand = new Random();
		int randomColorR = rand.nextInt(256);
		int randomColorG = rand.nextInt(256);
		int randomColorB = rand.nextInt(256);
		d = new Dimension(w, h);
		livesLeft = 3;
		level = 0;
		pac = new Pacman(14, 14);
		gSize = (h - 80) / grid.length;
		ghostX = 7 * gSize;
		ghostY = 7 * gSize;
		addGhost();
		counter = 0;
		wallColor = new Color(randomColorR, randomColorG, randomColorB);
		addKeyListener(new Keyboard());
		setFocusable(true);
		setBackground(Color.white);

		nextLevel();
		loseLife();
		timer = new Timer(40, this);
	}

	@Override
	public void addNotify() {
		super.addNotify();
	}

	/**
	 * Creates new ghost objects and adds them to the ghosts arraylist.
	 */
	private void addGhost() {
		ghost1 = new Ghost(ghostX, ghostY, 1);
		ghosts.add(ghost1);

	}

	/**
	 * Checks if the specified pixel coordinates contain an obstacle
	 * 
	 * @param x the x-coordinate (in pixels)
	 * @param y the x-coordinate (in pixels)
	 * @return false if there is an obstacle, true otherwise
	 */
	public static boolean checkWalls(int x, int y) {

		x -= 12;
		y -= 12;
		int i = x / gSize;
		int i2 = (x + 20) / gSize;
		int j = y / gSize;
		int j2 = (y + 20) / gSize;

		if (i2 > grid.length || j2 > grid.length) {
			return false;
		}

		return ((grid[i][j] != 1) && (grid[i2][j] != 1) && (grid[i][j2] != 1) && (grid[i2][j2] != 1));
	}

	/**
	 * Checks if the input coordinates are over the edge of the grid
	 * 
	 * @param x the x-coordinate (pixels)
	 * @param y the y-coordinate (pixels)
	 * @return an int: 0 if it's not over the edge 1 if it's over the right edge 2
	 *         if it's over the bottom edge 3 if it's over the left edge 4 if it's
	 *         over the top edge
	 */
	static int checkEdge(int x, int y) {
		// right
		if (x >= PADDING + ((grid.length - 1) * gSize)) {
			return 1;
			// down
		} else if (y >= PADDING + ((grid.length - 1) * gSize)) {
			return 2;
			// left
		} else if (x <= PADDING) {
			return 3;
			// up
		} else if (y <= PADDING) {
			return 4;
		}
		return 0;
	}

	/**
	 * Reads in a file with a 15 x 15 grid of integers from 0 to 2 and saves it to
	 * the grid
	 * 
	 * @param FileName the name of the file
	 * @throws FileNotFoundException if there is no such file
	 */
	private void readFile(String FileName) throws FileNotFoundException {
		try {
			Scanner s = new Scanner(new File(FileName));

			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					if (s.next().contentEquals("1")) {
						grid[i][j] = 1;
					} else {
						grid[i][j] = 2;
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("No level found.");
		}
	}

	class Keyboard extends KeyAdapter {
		/**
		 * This method takes keyboard input and initializes different functions for the
		 * game. 1. s or s to start game. 2. p or P to pause game. 3. left key to move
		 * pacman to the left. 4. right key to move pacman to the right. 5. upward key
		 * to move pacman to up. 6. downward key to move pacman to down. 7. esc key to
		 * terminate game.
		 * 
		 * @param keyEvent e
		 */
		@Override

		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			char startKey = e.getKeyChar();

			switch (checkEdge(pac.getX(), pac.getY())) {
			case (1):
				pac.setX(PADDING + 4);
				break;
			case (2):
				pac.setY(PADDING + 4);
				break;
			case (3):
				pac.setX(PADDING + ((grid.length - 1) * gSize));
				break;
			case (4):
				pac.setY(PADDING + ((grid.length - 1) * gSize));
				break;
			}

			if (startKey == 's' || startKey == 'S') {
				startGame = true;
				System.out.println("Game Started");
				timer.start();
			}

			if (startGame && !pauseGame && !gameTerminated && !gameOver && !gameCompleted) {
				if (startKey == 'p' || startKey == 'P') {
					pauseGame = true;
					System.out.println("Game is Paused");
					timer.stop();
				}
				if (key == KeyEvent.VK_ESCAPE) {
					gameTerminated = true;
					System.out.println("Game is Terminated");
					timer.stop();
				}
				if (key == KeyEvent.VK_LEFT && pac.getX() > 0) {
					if (checkWalls(pac.getX() - pac.getSpeed(), pac.getY())) {
						pac.moveLeft();
					}
				} else if (key == KeyEvent.VK_RIGHT && pac.getX() < d.width - 36) {
					if (checkWalls(pac.getX() + pac.getSpeed(), pac.getY())) {
						pac.moveRight();
					}
				} else if (key == KeyEvent.VK_UP && pac.getX() < d.width - 36) {
					if (checkWalls(pac.getX(), pac.getY() - pac.getSpeed())) {
						pac.moveUp();
					}
				} else if (key == KeyEvent.VK_DOWN && pac.getX() < d.height - 36) {
					if (checkWalls(pac.getX(), pac.getY() + pac.getSpeed())) {
						pac.moveDown();
					}
				}
				if (grid[getGridLocation(pac.getX())][getGridLocation(pac.getY())] == 2) {
					eatPellet();
					grid[getGridLocation(pac.getX())][getGridLocation(pac.getY())] = 0;
				}
				if (levelComplete()) {
					System.out.println("your princess is in another castle");
					nextLevel();
				}

			} else if (startKey == 'p' || startKey == 'P') {
				pauseGame = false;
				System.out.println("Game resumed");
				timer.restart();
			}

			repaint();
		}

	}

	/**
	 * Checks if the level is complete Level is complete if there are no more
	 * pellets to be eaten by the pacman
	 */
	public boolean levelComplete() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == 2) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Pacman loses a life if it touches any one of the ghosts The position of
	 * pacman is set to the same location on the upper left corner everytime a life
	 * is lost
	 */
	private void loseLife() {
		for (Ghost ghost : ghosts) {
			if (Math.abs(pac.getX() - ghost.getGhostPosX()) < 22.5
					&& Math.abs(pac.getY() - ghost.getGhostPosY()) < 22.5) {
				livesLeft--;
				pac.setX(14);
				pac.setY(14);
				pac.resetTail();
			}
		}

		repaint();
	}

	/**
	 * If the level is 1, next level calls ReadFile and passes grid1.txt As the
	 * level increases, new grids of the corresponding level number is passed into
	 * the ReadFile If the level equals 12, gameCompleted is set to true
	 */
	private void nextLevel() {
		Random rand = new Random();
		int randomColorR = rand.nextInt(256);
		int randomColorG = rand.nextInt(256);
		int randomColorB = rand.nextInt(256);
		wallColor = new Color(randomColorR, randomColorG, randomColorB);

		level++;
		addGhost();

		if (level == 1) {
			try {
				readFile("grid1.txt");
			} catch (FileNotFoundException e) {
				System.out.println("File not found");
			}
		}

		if (levelComplete()) {

			if (level == 12) {
				gameCompleted = true;
			}

			pac.setX(14);
			pac.setY(14);

			for (Ghost gh : ghosts) {
				if (level >= 1 && level <= 4) {
					gh.setGhostPosX(7 * gSize);
					gh.setGhostPosY(7 * gSize);
				} else if (level >= 5 && level <= 8) {
					gh.setGhostPosX(6 * gSize);
					gh.setGhostPosY(6 * gSize);
				} else if (level == 9) {
					gh.setGhostPosX(6 * gSize);
					gh.setGhostPosY(3 * gSize);
				} else if (level == 10) {
					gh.setGhostPosX(11 * gSize);
					gh.setGhostPosY(11 * gSize);
				} else if (level == 11) {
					List<Ghost> g1 = ghosts.subList(0, 7);
					for (Ghost g : g1) {
						g.setGhostPosX(7 * gSize);
						g.setGhostPosY(6 * gSize);
					}
					List<Ghost> g2 = ghosts.subList(7, ghosts.size());
					for (Ghost g : g2) {
						g.setGhostPosX(7 * gSize);
						g.setGhostPosY(9 * gSize);
					}
					pac.setSpeed(2);

				}
			}

			pac.resetTail();
			try {
				readFile("grid" + String.valueOf(level) + ".txt");
			} catch (FileNotFoundException e) {
				System.out.println("File not found");
			}
		}
	}

	private int getGridLocation(int pos) {
		return (pos - PADDING + 8) / gSize;
	}

	/**
	 * Method that allows for the score to increase when pacman eats the pellets.
	 */
	private void eatPellet() {
		score++;
	}

	@Override
	/**
	 * This method allows for the program to contain graphics. It draws strings into
	 * the screen as well as the game components. As long as the game is running, it
	 * will repaint everytime we call accordingly.
	 * 
	 * @param: Graphics g
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Font smallFont = new Font("Helvetica", Font.BOLD, 14);
		Graphics2D g2d = (Graphics2D) g;

		Font startFont = new Font("Helvetica", Font.BOLD, 20);

		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, d.width, d.height);
		pac.draw(counter, g2d, this);
		for (Ghost gh : ghosts) {
			gh.draw(counter, g2d, this);

		}
		g2d.setFont(smallFont);
		g2d.setColor(Color.white);
		g2d.drawString("Score: " + score, 276, 355);
		g2d.drawString("Lives Left: ", 10, 355);

		g2d.setFont(smallFont);
		g2d.setColor(Color.yellow);
		g2d.drawString("Level: " + level, 276, 380);

		for (short i = 0; i < livesLeft; i++) {
			g2d.drawImage(blob, i * 28 + 90, 340, this);

		}

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {

				if (grid[i][j] == 1) {
					g.setColor(wallColor);
					g.fillRect(PADDING + i * gSize, PADDING + j * gSize, gSize, gSize);
				} else if (grid[i][j] == 2) {
					g.setColor(new Color(255, 200, 0));
					g.fillOval(PADDING + i * gSize + (gSize / 2) - 2, PADDING + j * gSize + (gSize / 2) - 2, gSize / 4,
							gSize / 4);
				}
				g.setColor(wallColor);
				g.drawRect(PADDING, PADDING, gSize * grid.length, gSize * grid.length);

			}
		}

		if (!startGame) {
			g2d.setFont(startFont);
			g2d.setColor(Color.yellow);
			g2d.drawString("Press \'s\' or \'S\' to start", 74, 150);
		}

		if (pauseGame) {
			g2d.setFont(startFont);
			g2d.setColor(Color.yellow);
			g2d.drawString("Game Paused", 100, 130);
		}

		if (gameTerminated) {
			g2d.setFont(startFont);
			g2d.setColor(Color.red);
			g2d.drawString("Game has been terminated", 74, 160);
		}

		if (livesLeft == 0) {
			g2d.setFont(startFont);
			g2d.setColor(Color.yellow);
			gameOver = true;
			g2d.drawString("GAME OVER!", 100, 30);
			timer.stop();
		}

		if (gameCompleted == true) {
			timer.stop();
			g2d.setFont(startFont);
			g2d.setColor(Color.red);
			g2d.drawRect(30, 10, 230, 50);

			g2d.setColor(Color.white);
			g2d.fillRect(30, 10, 230, 50);
			g2d.setColor(Color.black);
			g2d.drawString("CONGRATULATIONS!", 30, 30);
			g2d.drawString("YOUR SCORE:" + score, 30, 50);

		}

		Toolkit.getDefaultToolkit().sync();
		g2d.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (startGame && !pauseGame && !gameTerminated && !gameOver && !gameCompleted) {
			for (int i = 0; i <= level; i++) {
				Ghost g = ghosts.get(i);

				g.moveGhost(counter);

			}
			counter++;
			repaint();
		}
		loseLife();

	}
}