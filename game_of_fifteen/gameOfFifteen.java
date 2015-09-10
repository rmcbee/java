import acm.graphics.*;
import acm.program.*;
import acm.util.*;


import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class gameOfFifteen  extends GraphicsProgram{
	
	private static final int BOARD_LENGTH = 400;
	
	private static final int SQ_PER_SIDE = 4;
	
	private static final int SQ_LENGTH = BOARD_LENGTH / SQ_PER_SIDE;
	
	private static final int RANDOM_SCRAMBLES = 100; //the number of random scrambles the borad receives in the begining
	
	private static final int X = 0;
	
	private static final int Y = 1;
	
	private static final char LEFT = 'a';
			
	private static final char RIGHT = 'd';
	
	private static final char UP = 'w';
	
	private static final char DOWN = 's';
	
	public void run()
	{
		addKeyListeners();
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		initBoard();
	}
	
	public void keyTyped(KeyEvent e) {

		switch(e.getKeyChar())
	    {
	    	case(LEFT): moveLeft(); break;
	    	
	    	case(RIGHT): moveRight(); break;
	    	
	    	case(UP): moveUp(); break;
	    	
	    	case(DOWN): moveDown(); break;
	    }
		
		checkWin();
		
	}
	
	private void initBoard()
	{	
		initGrid();
		
		randomizeSquare();
		
		printNums();
	}
	
	//creates the squares the numbers will be in
	private void printSquares()
	{
		for(int i = 0; i < SQ_PER_SIDE; i++)
		{
			for(int j = 0; j < SQ_PER_SIDE; j++)
			{
				add(new GRect(j * SQ_LENGTH, i * SQ_LENGTH, BOARD_LENGTH / SQ_PER_SIDE, BOARD_LENGTH / SQ_PER_SIDE));
			}
		}
	}
	
	private void initGrid()
	{
		for(int i = 0; i < SQ_PER_SIDE; i++)
		{
			for(int j = 0; j < SQ_PER_SIDE; j++)
			{
				grid[i][j] = i * 4 + j + 1;  //generates the value for each box
			}
		}
		
		grid[SQ_PER_SIDE - 1][SQ_PER_SIDE -1] = 0;  //sets the empty box to 0
		
		printNums();
		
		//sets the location of the empty square
		location[X] = 3;
		location[Y] = 3;
	}
	
	/** prints the grid to the screen */
	private void printNums()
	{
		
		removeAll();
		
		printSquares();
		
		for(int i = 0; i < SQ_PER_SIDE; i++)
		{
			for(int j = 0; j < SQ_PER_SIDE; j++)
			{

				num[i][j] = new GLabel("" + grid[i][j]);
				num[i][j].setFont("Arial-"+ SQ_LENGTH / 4);
				num[i][j].setLocation(j * SQ_LENGTH + (SQ_LENGTH - num[i][j].getWidth()) / 2, i * SQ_LENGTH + (SQ_LENGTH + num[i][j].getHeight()) / 2);
				
				if(grid[i][j] != 0)
				{
					add(num[i][j]);
				}
				else
				{
					remove(num[i][j]);  //removes the number in the empty slot
				}
			}
		}
	}
	
	private void randomizeSquare()
	{
		int randomNum;
		
		for(int i = 0; i < RANDOM_SCRAMBLES; i++)
		{
			randomNum = rgen.nextInt(1, 4);
			
			switch(randomNum)
			{
				case 1: moveLeft();
						break;
				
				case 2: moveRight();
						break;
				
				case 3: moveUp();
						break;
						
				case 4: moveDown();
			}
		}
		
	}
	
	/** moves the piece to the right of the empty space to the left */
	private void moveLeft()
	{
		if(location[X] < SQ_PER_SIDE - 1)
		{
			swapSpaces(location[X], location[Y], location[X] + 1, location[Y]);
		}
	}
	
	/** moves the piece to the left of the empty space to the right */
	private void moveRight()
	{
		if(location[X] > 0)
		{
			swapSpaces(location[X], location[Y], location[X] - 1, location[Y]);
		}
	}
	
	private void moveUp()
	{
		if(location[Y] < SQ_PER_SIDE - 1)
		{
			swapSpaces(location[X], location[Y], location[X], location[Y] + 1);
		}
	}
	
	private void moveDown()
	{
		if(location[Y] > 0)
		{
			swapSpaces(location[X], location[Y], location[X], location[Y] - 1);
		}
	}
	
	private void swapSpaces(int emptyX, int emptyY, int movingX, int movingY)
	{
		//swaps the values inside the two boxes
		grid[emptyY][emptyX] = grid[movingY][movingX];
		grid[movingY][movingX] = 0;
		
		//swaps the location of the two boxes 
		location[X] = movingX;
		location[Y] = movingY;
		
		
		printNums();
	}
	
	private void checkWin()
	{
		for(int i = 0; i < SQ_PER_SIDE; i++)
		{
			for(int j = 0; j < SQ_PER_SIDE; j++)
			{
				if(grid[i][j] != i * 4 + j + 1  || grid[SQ_PER_SIDE - 1][SQ_PER_SIDE - 1] != 0) return;
			}
		}
		removeAll();
	}
	
	
	/** stores the x and y locaton of the empty space */
	int[] location = new int[2];
	
	/** an array where all of the number positions will be stored */
	int[][] grid = new int[SQ_PER_SIDE][SQ_PER_SIDE];
	
	/** a glabel that will store the number in each box */
	GLabel[][] num = new GLabel[SQ_PER_SIDE][SQ_PER_SIDE];
	
	/** creates a random generator instance to generate the board */
	RandomGenerator rgen = new RandomGenerator();
}
