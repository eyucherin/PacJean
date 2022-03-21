package basecode;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 * J-Unit testing for the board class 
 * @author PacJean
 * @version 5/11/2021
 */
class pacmanTest {
	//initialization of new pacman class. 
	private Pacman pacman = new Pacman(50,50);

	@BeforeEach
	void setUp() throws Exception {
		pacman = new Pacman(50,50);
	}

	@Test
	void testNewXPosition() {
		/*
		 * Purpose: Checking return value when X position is changed
		 * Method: setX
		 * Parameter: 70
		 * Correct Result: 70 
		 */
		pacman.setX(70);
		assertEquals(70,pacman.getX());
	}
	
	@Test 
	void testNewYPosition() {
		/*
		 * Purpose: Checking return value when Y position is changed
		 * Method: setY
		 * Parameter: 70
		 * Correct Result: 70 
		 */
		pacman.setY(70);
		assertEquals(70,pacman.getY());
		
	}
	@Test 
	void testGetSpeed() {
		/*
		 * Purpose: Checking return value of speed 
		 * Method: getSpeed
		 * Correct Result: 2
		 */
		assertEquals(4,pacman.getSpeed());
	}
	
	@Test 
	void positionAfterMovingLeft() {
		/*
		 * Purpose: Checking return value when pacman moves left
		 * Method: moveLeft
		 * Correct Result: 46 for getX and 50 for getY. 
		 */
		pacman.moveLeft();
		assertEquals(46,pacman.getX());
		assertEquals(50,pacman.getY());
	}
	
	@Test 
	void positionAfterMovingRight() {
		/*
		 * Purpose: Checking return value when pacman moves right
		 * Method: moveRight
		 * Correct Result: 54 for getX and 50 for getY. 
		 */
		pacman.moveRight();
		assertEquals(54,pacman.getX());
		assertEquals(50,pacman.getY());
	}
	
	@Test 
	void positionAfterMovingUp() {
		/*
		 * Purpose: Checking return value when pacman moves up
		 * Method: moveUp 
		 * Correct Result: 50 for getX and 46 for getY. 
		 */
		pacman.moveUp();
		assertEquals(50,pacman.getX());
		assertEquals(46,pacman.getY());
	}
	
	@Test 
	void positionAfterMovingDown() {
		/*
		 * Purpose: Checking return value when pacman moves down
		 * Method: moveDown
		 * Correct Result: 50 for getX and 54 for getY. 
		 */
		pacman.moveDown();
		assertEquals(50,pacman.getX());
		assertEquals(54,pacman.getY());
	}

}
