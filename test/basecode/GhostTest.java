package basecode;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * J-Unit testing for the Ghost class 
 * @author PacJean
 * @version 5/11/2021
 */
class GhostTest {
	//initialization of new ghost class 
	private Ghost ghost = new Ghost(100,100,4);

	@BeforeEach
	void setUp() throws Exception {
		ghost = new Ghost(100,100,4);
	}

	@Test
	void testNewXPosition() {
		/*
		 * Purpose: Checking return value when X position is changed
		 * Method: setGhostPosX 
		 * Parameter: 50
		 * Correct Result: 50 
		 */
		ghost.setGhostPosX(50);
		assertEquals(50,ghost.getGhostPosX());
	}
	
	@Test
	void testNewYPosition() {
		/*
		 * Purpose: Checking return value when Y position is changed
		 * Method: setGhostPosY 
		 * Parameter: 50
		 * Correct Result: 50 
		 */
		ghost.setGhostPosY(50);
		assertEquals(50,ghost.getGhostPosY());
	}
	
	@Test 
	void testGetNewSpeed() {
		/*
		 * Purpose: Checking return value of speed 
		 * Method: getGhostSpeed 
		 * Correct Result: 4
		 */
		assertEquals(4, ghost.getGhostSpeed());
	}
	
	@Test 
	void testGetDirection() {
		/*
		 * Purpose: Checking return value of Ghost direction 
		 * Method: getGhostDir
		 * Correct Result: 0
		 */
		assertEquals(0, ghost.getGhostDir());
	}

}
