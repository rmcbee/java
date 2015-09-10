import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;


public class minesweep extends GraphicsProgram {
	
	/** height of the board */
	private static final int BOARD_HEIGHT = 500;
	
	/** width of the board */
	private static final int BOARD_WIDTH = 500;
	
	/** number of rows */
	private static final int NROWS = 20;
	
	/** number of cols */
	private static final int NCOLS = 20;
	
	/**width of each square */
	private static final int SQ_WIDTH = BOARD_WIDTH / NROWS;
	
	/**heght of each square */
	private static final int SQ_HEIGHT = BOARD_HEIGHT / NCOLS;
	
	private static final int EASY_MED_HARD_WIDTH = BOARD_WIDTH / 6;
	
	private static final int EASY_MED_HARD_HEIGHT = BOARD_HEIGHT / 12; 
	
	
	
	/** instance array to store the state of each square */
	private int[][] grid = new int[NROWS][NCOLS];
	
	/** instance GObject to reference each square when it is clicked */
	private static GObject gobj;
	
	/** boolean to make sure the user does not loose by presing a bomb on the first round */
	private static boolean notFirstClick = false;

	private static boolean start = false;
	
	
	public void run() {
		
		initStartScreen();
		
		
		addMouseListeners();
		
		while(!start)
		{
			
		
		}
		
		
		initGrid();  //creates the grid
		
	}
	
	
	public void  mouseClicked(MouseEvent e)
	{
		//checks if the a object has been hit
		gobj = getElementAt(e.getX(), e.getY());
		
		if(gobj.getColor() == Color.BLACK)
		{
			add(new GRect(gobj.getX(), gobj.getY(), SQ_WIDTH , SQ_HEIGHT));
			remove(gobj);
			
			dislayInfo(gobj);
			
			checkForBomb(gobj);
		}
	}
	
	private void initStartScreen()
	{
		//creates the easy button
		GRect easy = new GRect(EASY_MED_HARD_WIDTH, EASY_MED_HARD_HEIGHT);
		easy.setLocation((BOARD_HEIGHT - easy.getWidth()) * 1 / 6, BOARD_HEIGHT * 7 / 12);
		easy.setFilled(true);
		easy.setFillColor(Color.GRAY);
		add(easy);
		
		GLabel easyLabel = new GLabel("Easy", 0, 0);
		
		easyLabel.setLocation((BOARD_HEIGHT - easy.getWidth()) * 1 / 6 + easy.getWidth() / 2 - easyLabel.getWidth() / 2, BOARD_HEIGHT * 7 / 12 + easy.getHeight() / 2 - easyLabel.getHeight() / 2);
		
		add(easyLabel);
		
		//creates the medium button
		GRect medium = new GRect(EASY_MED_HARD_WIDTH, EASY_MED_HARD_HEIGHT);
		medium.setLocation((BOARD_HEIGHT - medium.getWidth()) * 3 / 6, BOARD_HEIGHT * 7 / 12);
		medium.setFilled(true);
		medium.setFillColor(Color.GRAY);
		add(medium);
		
		//creates the hard button
		GRect hard = new GRect(EASY_MED_HARD_WIDTH, EASY_MED_HARD_HEIGHT);
		hard.setLocation((BOARD_HEIGHT - hard.getWidth()) * 5 / 6, BOARD_HEIGHT * 7 / 12);
		hard.setFilled(true);
		hard.setFillColor(Color.GRAY);
		add(hard);
		
		
		
	}
	
	private void initGrid()
	{
		for(int i = 0; i < NROWS; i++)
		{
			for(int j = 0; j < NCOLS; j++)
			{
				//Creates an array that stores the type of each grid space. Zeros represnet mines
				grid[i][j]= rgen.nextInt(0, 3);
				
				
				GRect space = new GRect(SQ_WIDTH , SQ_HEIGHT);  //creates each square
				space.setLocation(j * SQ_WIDTH , i * SQ_HEIGHT);  //sets the location of each square
				
				
				space.setFilled(true);
				space.setFillColor(Color.RED);
				
				
				add(space);
			}
		}
		countMines(grid);
	}

	private void dislayInfo(GObject gobj)
	{
		//gets the info about the given location
		int info = grid[(int)(gobj.getY() / SQ_HEIGHT)][(int) (gobj.getX() / SQ_WIDTH)];
		
		GLabel num = new GLabel("" + info, gobj.getX() + SQ_WIDTH / 2.8, gobj.getY() + SQ_HEIGHT / 1.5);
		
		num.setColor(Color.BLUE);
		
		
		add(num);
	}
	
	private void checkForBomb(GObject gobj)
	{
		//gets the info about the given location
		int info = grid[(int)gobj.getY() / SQ_WIDTH][(int) gobj.getX() / SQ_HEIGHT];
		
		if(info == 0 && notFirstClick)
		{
			println("0");
			removeAll();
			
			GLabel endGame = new GLabel("Game Over!");
			
			endGame.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
			
			endGame.setLocation((BOARD_WIDTH - endGame.getWidth()) / 2,  (BOARD_HEIGHT - endGame.getHeight()) / 2);
			
			add(endGame);
		}
		
		notFirstClick = true;
	}
	
	private void countMines(int[][] grid)
	{
		int[][] tempGrid = new int[NROWS][NCOLS];
		

		
		//sets all values in the temp grid to 0
		for(int i = 0; i < NROWS; i++)
		{
			for(int j = 0; j < NCOLS; j++)
			{
					tempGrid[i][j] = 0;
			}
		}
		
		for(int i = 0; i < NROWS; i++)
		{
			for(int j = 0; j < NCOLS; j++)
			{
				if(grid[i][j] != 0)
				{
					//checks if there is a row above
					if(i > 0 && grid[i - 1][j] == 0)
					{
						tempGrid[i][j]++;
					}

					
					//checks if there is a row bellow
					if(i < NROWS - 1 && grid[i + 1][j] == 0)
					{
						tempGrid[i][j]++;	
					}
					
					//checks if there is a column to the left
					if(j > 0 && grid[i][j - 1] == 0)
					{
						tempGrid[i][j]++;
					}
					
					//checks if there is a column to the right
					if(j < NCOLS - 1 && grid[i][j + 1] == 0)
					{
						tempGrid[i][j]++;
						
					}
					
					//checks if there is a box to the top left
					if(i > 0 && j > 0 && grid[i - 1][j - 1] == 0)
					{
						tempGrid[i][j]++;
					}
					
					//checks if there is a box in the top right
					if(i > 0 && j < NCOLS - 1 && grid[i -1][j + 1] == 0)
					{
						tempGrid[i][j]++;
					}
					
					//checks if there is a box in the bottom left
					if(i < NROWS - 1 && j > 0 && grid[i + 1][j - 1] == 0)
					{
						tempGrid[i][j]++;
					}
					
					//checks if there is a box in the bottom right
					if(i < NROWS - 1 && j < NCOLS - 1 && grid[i + 1][j + 1] == 0)
					{
						tempGrid[i][j]++;
					}
					
					
				}
			}
		}
		
		
		//sets all values in the temp grid to 0
		for(int i = 0; i < NROWS; i++)
		{
			for(int j = 0; j < NCOLS; j++)
			{
					grid[i][j] = tempGrid[i][j];
			}
		}
		
		
	}
	
	RandomGenerator rgen = RandomGenerator.getInstance();
}
