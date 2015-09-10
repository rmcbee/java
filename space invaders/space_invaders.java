import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class space_invaders extends GraphicsProgram implements space_invader_constants{

		
	public void run()
	{
		
		addKeyListeners();
		
		while(!gameOver)
		{
			initGame();
			
			while(aliensRemain() && !gameOver)
			{
				pause(difficulty);  //pause the movement so people can see the animation
				
				moveAliens();
				moveLazer();
				moveMotherShipsLaser();
				
				
			}
			removeAll();
			
			increaseDifficulty();
		}
	}
	
	//handles all of the key movements that move the player
	public void keyPressed(KeyEvent e) {
		
		switch(e.getKeyChar())
	    {
	    	case(LEFT): moveLeft(); break;
	    	
	    	case(RIGHT): moveRight(); break;
	    	
	    	case(FIRE): fireLazer(); break;
	    }
		
	}
	
	private boolean aliensRemain()
	{
		//loops through all of the aliens
		for(int i = 0; i < N_ROWS; i++)
		{
			for(int j = 0; j < N_COLS; j++)
			{
				//returns true if any aliens remain
				if(aliens[i][j] != null)
				{
					return true;
				}
			}
		}
		
		return false;  //if no aliens remain, return false
	}
	
	//TODO
	/*private void openingScreen()
	{
		addActionListeners();
		
		add(new GRect(10, 10, 10, 10));
		
		initButton();
		
		pause(1000);
	}*/
	
	private void motherShipFire()
	{
		for(int i = 0; i < numberOfMotherShips; i++)
		{
			if(motherShips[i].getX() > player.getX() && motherShips[i].getX() < (player.getX() + PLAYER_WIDTH) || (motherShips[i].getX() + MOTHERSHIPS_WIDTH) > player.getX() && (motherShips[i].getX() + MOTHERSHIPS_WIDTH) < (player.getX() + PLAYER_WIDTH))
			{
				if(rgen.nextInt(1, motherShipFiringRate) == 5)
				{
					motherShipFireLazer(i);
				}
			}
		}
		
	}
	
	private void moveMotherShipsLaser()
	{
		gameOver = checkIfHitPlayer();
		
		for(int i = 0; i < numberOfMotherShips; i++)
		{
			if(motherShipLaser[i] != null)
			{
				//moves each laser down
				motherShipLaser[i].move(0, MOTHERSHIPS_LASER_SPEED);
			
				if(motherShipLaser[i].getY() + MOTHERSHIPS_LASER_LENGTH >= BOARD_HEIGHT)
				{
					remove(motherShipLaser[i]);
					motherShipLaser[i] = null;
				}
			}
		}
	}
	
	private boolean checkIfHitPlayer()
	{
		for(int i = 0; i < numberOfMotherShips; i++)
		{
			//if the mother ship is not null
			if(motherShipLaser[i] != null)
			{
				//if the player is touching the top left corner of the mother ship laser
				if(getElementAt(motherShipLaser[i].getX(), motherShipLaser[i].getY() + MOTHERSHIPS_LASER_LENGTH) == player)
				{
					return(true);
				}
				else if(getElementAt(motherShipLaser[i].getX() + MOTHERSHIPS_WIDTH, motherShipLaser[i].getY() + MOTHERSHIPS_LASER_LENGTH) == player)
				{
					return(true);
				}
			}
		}
		return false;
	}
	
	private void motherShipFireLazer(int index)
	{
		if(motherShipLaser[index] == null)
		{
			motherShipLaser[index] = new GRect(motherShips[index].getX() + MOTHERSHIPS_WIDTH / 2, MOTHERSHIPS_HEIGHT, MOTHERSHIPS_LASER_LENGTH, MOTHERSHIPS_LASER_LENGTH);
			motherShipLaser[index].setFilled(true);
			motherShipLaser[index].setFillColor(Color.RED);
			add(motherShipLaser[index]);
		}
	}
	
	private void initButton()
	{
		
		JButton easy = new JButton("easy");
		add(easy, SOUTH);
		
		JButton medium = new JButton("medium");
		add(medium, SOUTH);
		
		JButton hard = new JButton("hard");
		add(hard, SOUTH);
	}

	private void moveLazer()
	{
		for(int i = 0; i < MAX_LAZERS; i++)
		{
			if(laser[i] != null)
			{
				
				laser[i].move(0, LAZER_SPEED);
				
				if(laser[i].getY() < 0)
				{
					remove(laser[i]);
					laser[i] = null;
				}
				
				//checks if a laser has hit its target
				else if(laserHitAlien(i))
				{
					println("HIT!");
					
				}
			}
		}
	}
	
	private void increasePoints(GObject gobj)
	{
		//looks through each of the aliens to see if it them
		for(int i = 0; i < N_ROWS; i++)
		{
			for(int j = 0; j < N_COLS; j++)
			{
				if(gobj == aliens[i][j])
				{
					points += (N_ROWS - i);  //the higher the alien is, the more points it will be worth
				
					alien_speed = alien_speed + points / 80;
				}
			}
		}
		
		//looks through each of the mother ships to see if the laser it them
		for(int i = 0; i < numberOfMotherShips; i++)
		{
			if(gobj == motherShips[i])
			{
				points += motherShipPoints;
			}
		}
	}
	
	private boolean laserHitAlien(int index)
	{
		//the y is -1 so that getElement at does not pick up the laser itself
		gobj = getElementAt(laser[index].getX(), laser[index].getY() - 1); 
		
		//if there is no object on the top left tip of the laser, check the top right tip
		if(gobj == null)
		{
			//checks if any object is touching the right side of the laser
			gobj = getElementAt(laser[index].getX() + LASER_WIDTH, laser[index].getY() - 1);
			if(gobj == null)
			{
				return false;  //if the laser is not hitting anything, return false
			}
		}
			
		if(gobj == player) return false;  //ignore the laser while it is touching the paddle
		
		checkIfHitAlien();  //removes an alien if an alien was hit
		
		checkIfHitMotherShip();  //removes a mother ship if a mother ship was hit
		
		remove(laser[index]);  //removes the laser that hit the alien
		laser[index] = null;  //sets that laser to null
		increasePoints(gobj);  //increases the score based on which alien was hit
		
		return true;
	}
	
	private void checkIfHitMotherShip()
	{
		for(int i = 0; i < numberOfMotherShips; i++)
		{
			if(motherShips[i] == gobj)
			{
				remove(motherShips[i]);  //removes the mother ship
				motherShips[i] =  null;  //sets the mother ship to null
			}
		}
	}
	
	
	private void checkIfHitAlien()
	{
		for(int i = 0; i < N_ROWS; i++)
		{
			for(int j = 0; j < N_COLS; j++)
			{
				if(aliens[i][j] == gobj)
				{
					remove(aliens[i][j]);  //removes the alien
					aliens[i][j] =  null;  //sets the alien to null
				}
			}
		}
	}
	
	private void fireLazer()
	{
		createLazer();
	}
	
	private void createLazer()
	{
		for(int i = 0; i < MAX_LAZERS; i++)
		{
			if(laser[i] == null)
			{
				laser[i] = new GRect(player.getX() + PLAYER_WIDTH / 2 - LASER_WIDTH / 2, player.getY(), LASER_WIDTH, LASER_HEIGHT);
				laser[i].setFilled(true);
				add(laser[i]);
				return;
			}
		}
	}
	
	private void moveLeft()
	{
		if(player.getX() > PLAYER_SPEED)
		{	
			player.move(-PLAYER_SPEED, 0);
		}
		pause(10);	
	}
	
	private void moveRight()
	{
		if(player.getX() < BOARD_WIDTH - PLAYER_WIDTH - PLAYER_SPEED)
		player.move(PLAYER_SPEED, 0);
		
		pause(10);	
	}

	private void moveAliens()
	{
		//finds the outermost aliens
		leftMostAlien = findLeftMostAlien();
		rightMostAlien = findRightMostAlien();
	
		//sets the direction of the aliens so they go back and forth
		if(leftMostAlien.getX() <= 0 || (rightMostAlien.getX() >= BOARD_WIDTH - ALIEN_WIDTH))
		{
			xDirrection *= -1;
			moveAlienVertical();
		}
		
		//finds the outermost aliens
		leftMostMotherShip = findLeftMostMotherShip();
		rightMostMotherShip = findRightMostMotherShip();
	
		//sets the direction of the MotherShip so they go back and forth
		if(leftMostMotherShip.getX() <= 0 || (rightMostMotherShip.getX() >= BOARD_WIDTH - ALIEN_WIDTH))
		{
			xMotherShipDirrection *= -1;
			moveAlienVertical();
		}		
		
		moveAlienHorrizontal();
		motherShipFire();
		
	}
	
	private GRect findLeftMostAlien()
	{
		for(int j = 0; j < N_COLS; j++)
		{
			for(int i = 0; i < N_ROWS; i++)
			{
				if(aliens[i][j] != null)
					return aliens[i][j];
			}
		}
		
		return player;  //default GRect to return in case all of the aliens have been killed	
		 
	}
	
	private GRect findRightMostAlien()
	{
		for(int j = N_COLS - 1; j >= 0; j--)
		{
			for(int i = N_ROWS - 1; i >= 0; i--)
			{
				if(aliens[i][j] != null)
				return aliens[i][j];
			}
		}
		
		return player;  //default GRect to return in case all of the aliens have been killed	
		 
	}
	
	private GRect findLeftMostMotherShip()
	{
		for(int i = 0; i < numberOfMotherShips; i++)
		{
			if(motherShips[i] != null)
			{
				return motherShips[i];
			}
		}
		
		return player;  //default GRect to return in case all of the aliens have been killed	
		 
	}
	
	private GRect findRightMostMotherShip()
	{
		for(int i = numberOfMotherShips - 1; i >= 0; i--)
		{
			if(motherShips[i] != null)
			{
				return motherShips[i];
			}
		}
		
		return player;  //default GRect to return in case all of the aliens have been killed	
		 
	}
	
	/**moves each alien horizontally in the correct direction */
	private void moveAlienHorrizontal()
	{
		for(int i = 0; i < N_ROWS; i++)
		{
			for(int j = 0; j < N_COLS; j++)
			{
				if(aliens[i][j] != null)
				{
					// moves each alien horrizontally
					aliens[i][j].move(xDirrection * alien_speed , 0);
				}
			}
		}
		
		for(int i = 0; i < numberOfMotherShips; i++)
		{
			//checks if the mother ship is not null and then moves it if it is not null
			if(motherShips[i] != null)
			{
				// moves each mother ship horizontally
				motherShips[i].move(xMotherShipDirrection * motherShip_speed , 0);
			}
			
		}
	}
	
	/** moves the aliens down each time they hit one of the sides */
	private void moveAlienVertical()
	{
		for(int i = 0; i < N_ROWS; i++)
		{
			for(int j = 0; j < N_COLS; j++)
			{
				if(aliens[i][j] != null)
				{
					// moves each alien
					aliens[i][j].move(0, ALIEN_HEIGHT / 2);
				}
				
			}
		}
	}
	
	/** initializes the alien */
	private void initGame()
	{
		initAliens();
		
		initMotherShips();
		
		initPlayer();
	}
	
	private void initAliens()
	{
		for(int i = 0; i < N_ROWS; i++)
		{
			for(int j = 0; j < N_COLS; j++)
			{
				// creates each alien and positions them
				aliens[i][j] = new GRect((j + 0.5) * BOARD_WIDTH/ 1.1 / N_COLS, (i + 1) * BOARD_HEIGHT / N_ROWS / 3, ALIEN_WIDTH, ALIEN_HEIGHT);
				add(aliens[i][j]);
			}
		}
	}
	
	private void initMotherShips()
	{
		motherShips = new GRect[numberOfMotherShips];
		
		motherShipLaser = new GRect[numberOfMotherShips];
		
		for(int i = 0; i < numberOfMotherShips; i++)
		{
			//calculates how to center all of the mother ships
			motherShips[i] = new GRect((BOARD_WIDTH - MOTHERSHIPS_WIDTH * (numberOfMotherShips * 2 - 1)) / 2 + i * 2 * MOTHERSHIPS_WIDTH, 0, MOTHERSHIPS_WIDTH, MOTHERSHIPS_HEIGHT);
			motherShips[i].setFilled(true);
			add(motherShips[i]);
		}
	}
	
	/** initializes the user */
	private void initPlayer()
	{	
		player = new GRect((BOARD_WIDTH - PLAYER_WIDTH) / 2, BOARD_HEIGHT - 10 - PLAYER_HEIGHT, PLAYER_WIDTH, PLAYER_HEIGHT);
		add(player);
	}
	
	private void increaseDifficulty()
	{
		alien_speed += 0.5;
		numberOfMotherShips++;
		motherShip_speed++;
	}
	
	/** instance variables */
	
	/** array that will store the aliens */
	GRect[][] aliens = new GRect[N_ROWS][N_COLS];
	
	/** stores the leftmost alien that will base how far the swarm of aliens move */
	GRect leftMostAlien;
	
	/** stores the rightmost alien that will base how far the swarm of aliens will move */
	GRect rightMostAlien;
	
	/** stores the leftmost mother ship */
	GRect leftMostMotherShip;
	
	/** stores the rightmost mother ship */
	GRect rightMostMotherShip;
	
	/** object that stores the player */
	GRect player;
	
	/** object used to tell if an alien has been hit */
	GObject gobj;
	
	/** creates an array of laser blasts that the player will fire */
	GRect[] laser = new GRect[MAX_LAZERS];  //give more than enough lasers for the player to use
			
	/** creates an array of mother ships */
	GRect[] motherShips;
	
	/** creates an array of laser blasts the mother ship can fire */
	GRect[] motherShipLaser;
	
	/** random generator variable to determine if aliens should fire */
	RandomGenerator rgen = new RandomGenerator();
	
	/** the pause between each of the aliens movements */
	private int difficulty = 40;
	
	/** the xDirrection of the aliens */
	private int xDirrection = -1;
	
	/** stores the total points */
	private int points = 0;
	
	/** the speed of the aliens */
	private double alien_speed = 2;
	
	/** number of mother ships on the board */
	private int numberOfMotherShips = 2;
	
	/** stores the direction the mother ships should begin moving in */
	private int xMotherShipDirrection = -1; 
	
	/** the speed of the mother ships */
	private int motherShip_speed = 1;
	
	/** the points associated with the mother ships */
	private int motherShipPoints = 3;
	
	/** one out of this number is the chance that a mother ship will fire if they see the player */
	private int motherShipFiringRate = 20;
	
	/** variable that says whether the game is over or not */
	private boolean gameOver = false;
	
}



