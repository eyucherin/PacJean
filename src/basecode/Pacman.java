package basecode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

/**
 * This class initializes the Pacman class for the game
 * 
 * @author PacJean
 * @version 5/11/2021
 */

public class Pacman {
	// coordinates of Pacman (in pixels)
	private int posX, posY;
	// direction Pacman is facing and speed of movement (in pixels)
	private int direction, speed;
	// a list keeping some of the past locations of Pacman so it can draw the tail
	private int[][] locations;

	/**
	 * Initializes the player-controlled pacman.
	 * 
	 * @param x the starting x position in pixels
	 * @param y the starting y position in pixels
	 */
	public Pacman(int x, int y) {
		posX = x;
		posY = y;
		direction = 1;
		speed = 4;

		locations = new int[49][2];
		resetTail();
	}

	/**
	 * Changes the x position of Pacman.
	 * 
	 * @param x the new position
	 * @return the new position
	 */
	public int setX(int x) {
		posX = x;
		return posX;
	}

	/**
	 * Changes the y position of Pacman.
	 * 
	 * @param y the new position
	 * @return the new position
	 */
	public int setY(int y) {
		posY = y;
		return posY;
	}

	/**
	 * Gets the current x position of Pacman.
	 * 
	 * @return current x position
	 */
	public int getX() {
		return posX;
	}

	/**
	 * Gets the current y position of Pacman.
	 * 
	 * @return current y position
	 */
	public int getY() {
		return posY;
	}

	/**
	 * Gets the speed of Pacman.
	 * 
	 * @return current speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * Changes the speed of Pacman
	 * 
	 * @param : newSpeed
	 */
	public void setSpeed(int newSpeed) {
		speed = newSpeed;
	}

	/**
	 * Shift the Pacman over and make it face the appropriate direction.
	 */
	public void moveLeft() {
		saveLocation();
		posX -= speed;
		direction = 2;
	}

	/**
	 * Shift the Pacman over and make it face the appropriate direction.
	 */
	public void moveRight() {
		saveLocation();
		posX += speed;
		direction = 0;
	}

	/**
	 * Shift the Pacman over and make it face the appropriate direction.
	 */
	public void moveUp() {
		saveLocation();
		posY -= speed;
		direction = 3;
	}

	/**
	 * Shift the Pacman over and make it face the appropriate direction.
	 */
	public void moveDown() {
		saveLocation();
		posY += speed;
		direction = 1;
	}

	/**
	 * Draw the Pacman and its tail according to the current and past coordinates.
	 * 
	 * @param counter the current frame
	 * @param g2d     the graphics panel for drawing
	 * @param o       the ImageObserver for drawing
	 */
	public void draw(int counter, Graphics g2d, ImageObserver o) {
		// draws the tail
		g2d.setColor(new Color(162, 186, 185));
		for (int i = 0; i < (locations.length - 1) / (speed / 2); i += 12 / (speed / 2)) {
			if (locations[i] != null) {
				g2d.fillOval(locations[i][0] + 6, locations[i][1] + 6, 10 - (i / 12), 10 - (i / 12));
			}
		}

		// draws the Pacman
		g2d.drawImage(new ImageIcon("src/images/blob" + direction + "." + Math.abs(3 - ((counter / 2) % 6)) + ".png")
				.getImage(), posX, posY, o);
	}

	/**
	 * Saves the last few locations of the Pacman so the draw method can include a
	 * tail.
	 */
	private void saveLocation() {
		for (int i = locations.length - 1; i > 0; i--) {
			locations[i][0] = locations[i - 1][0];
			locations[i][1] = locations[i - 1][1];
		}
		locations[0][0] = posX;
		locations[0][1] = posY;
	}

	/**
	 * Resets all the values in the locations array so that when Pacman dies or
	 * starts a new level, the tail stays with the head.
	 */
	public void resetTail() {
		for (int[] spot : locations) {
			spot[0] = 14;
			spot[1] = 14;
		}
	}
}