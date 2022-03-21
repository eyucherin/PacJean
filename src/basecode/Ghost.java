package basecode;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;

/**
 * This class initializes the Ghost class for the game
 * 
 * @author PacJean
 * @version 5/11/2021
 */

public class Ghost {

	// coordinates of ghost objects, ghost speed and ghost // direction
	private int gPosX, gPosY, gSpeed, ghostDir;

	// ghost image
	private static Image ghostImage = new ImageIcon("src/images/Ghost.png").getImage();

	private static int maxDir;
	private static int minDir;

	public Ghost(int x, int y, int speed) {

		gPosX = x;
		gPosY = y;
		gSpeed = speed;
		ghostDir = 0;

		maxDir = 4;
		minDir = 1;

	}

	/**
	 * Sets the x position of the ghost.
	 * 
	 * @param x the new position
	 */
	public void setGhostPosX(int x) {
		gPosX = x;
	}

	/**
	 * Sets the y position of the ghost.
	 * 
	 * @param y the new position
	 */
	public void setGhostPosY(int y) {
		gPosY = y;
	}

	/**
	 * Sets the speed of the ghost.
	 * 
	 * @param x the new speed
	 */
	public void setGhostSpeed(int speed) {
		gSpeed = speed;
	}

	/**
	 * Gets the current x position of ghost object.
	 * 
	 * @return current x position
	 */
	public int getGhostPosX() {
		return gPosX;
	}

	/**
	 * Gets the current y position of ghost object.
	 * 
	 * @return current y position
	 */
	public int getGhostPosY() {
		return gPosY;
	}

	/**
	 * Gets the ghost speed.
	 * 
	 * @return ghost speed
	 */
	public int getGhostSpeed() {
		return gSpeed;
	}

	/**
	 * Gets ghost direction.
	 * 
	 * @return ghost direction
	 */
	public int getGhostDir() {
		return ghostDir;
	}

	/**
	 * Determines the ghost direction and uses the counter to update the ghost
	 * position (x and y coordinates). Then checks for walls and edges and * moves
	 * the ghost according to the ghost direction.
	 * 
	 * @param counter the current frame
	 */
	public void moveGhost(int counter) {
		// set ghost direction
		if (counter % 5 == 0) {
			ghostDir = (int) Math.floor(Math.random() * (maxDir - minDir + 1) + minDir);

		}
		// move ghost according to ghost direction
		if (ghostDir == 1 && checkWalls(getGhostPosX() + 4, getGhostPosY())
				&& Board.checkEdge(getGhostPosX() + 4, getGhostPosY()) == 0) { // move right
			gPosX += 4;
		} else if (ghostDir == 2 && checkWalls(getGhostPosX(), getGhostPosY() + 4)
				&& Board.checkEdge(getGhostPosX(), getGhostPosY() + 4) == 0) { // move down
			gPosY += 4;

		} else if (ghostDir == 3 && checkWalls(getGhostPosX() - 4, getGhostPosY())
				&& Board.checkEdge(getGhostPosX() - 4, getGhostPosY()) == 0) { // move left
			gPosX -= 4;

		} else if (ghostDir == 4 && checkWalls(getGhostPosX(), getGhostPosY() - 4)
				&& Board.checkEdge(getGhostPosX(), getGhostPosY() - 4) == 0) { // move up
			gPosY -= 4;

		}

	}

	/**
	 * Draw the ghost according to x and y coordinates.
	 * 
	 * @param counter the current frame
	 * @param g2d     the graphics panel for drawing
	 * @param o       the ImageObserver for drawing
	 */
	public void draw(int counter, Graphics g2d, ImageObserver o) {
		g2d.drawImage(ghostImage, getGhostPosX(), getGhostPosY(), 20, 20, o);

	}

	/**
	 * Calls on the checkWalls method from the Board class.
	 * 
	 * @param x the x-coordinate (in pixels)
	 * @param y the x-coordinate (in pixels)
	 * @return false if there is an obstacle, true otherwise
	 */
	private static boolean checkWalls(int x, int y) {
		return Board.checkWalls(x, y);
	}

}
