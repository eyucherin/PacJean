package basecode;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * This class is where the program runs. The main function lives here.
 * 
 * @author PacJean
 * @version 5/11/2021
 */

public class BlobAnimation extends JFrame {

	private final int WINDOW_WIDTH = 380;
	private final int WINDOW_HEIGHT = 420;
	private Board myBoard = new Board(WINDOW_WIDTH, WINDOW_HEIGHT);

	public BlobAnimation() {
		setTitle("BlobAnimation");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		add(myBoard, BorderLayout.CENTER);
		setVisible(true);
	}

	public static void main(String[] args) {
		BlobAnimation myGame = new BlobAnimation();
		Sound.playsound("pacman_beginning.wav");
	}
}
