
public interface space_invader_constants {
	/** width of board */
	public static final int BOARD_WIDTH = 500;
	
	/** height of board */
	public static final int BOARD_HEIGHT = 600;
	
	/** number of rows of monsters */
	public static final int N_ROWS = 7;
	
	/** number of columns of monsters */
	public static final int N_COLS = 7;
	
	/** the width of the monsters */
	public static final int ALIEN_WIDTH = 30;
	
	/** the height of the monsters */
	public static final int ALIEN_HEIGHT = 20;
	
	/** how many pixels the aliens move each time they move */
	public static final int ALIEN_SPEED = 1;
	
	/** the width of the motherShip */
	public static final int MOTHERSHIPS_WIDTH = 30;
	
	/** the height of the motherShips */
	public static final int MOTHERSHIPS_HEIGHT = 20;
	
	/** how many pixels the motherShip move each time they move */
	public static final int MOTHERSHIPS_SPEED = 1;
	
	/** the width of the mother ship laser */
	public static final int MOTHERSHIPS_LASER_LENGTH = 7;
	
	/** the speed of the mother ships lasers */
	public static final int MOTHERSHIPS_LASER_SPEED = 7;
	
	/** width of the player */
	public static final int PLAYER_WIDTH = BOARD_WIDTH / 8;
	
	/** height of the player */
	public static final int PLAYER_HEIGHT = BOARD_HEIGHT / 18;
	
	/** speed of the player */
	public static final int PLAYER_SPEED = 10;
	
	/** command for the player to go left */
	public static final char LEFT = 'a';
	
	/** command for the player to go right */
	public static final char RIGHT = 'd';
	
	/** command for the player to fire */
	public static final char FIRE =  ' ';
	
	/** maximum amount of lazers the player can fire at one time */
	public static final int MAX_LAZERS = 2; 
	
	/** width of the lazer blasts */
	public static final int LASER_WIDTH = 7;
	
	/** height of the lazer blasts */
	public static final int LASER_HEIGHT = LASER_WIDTH;
	
	/** speed of the lazer blastes */
	public static final int LAZER_SPEED = -10;
	
	/** x position of the displayed points total */
	public static final int POINTS_X = BOARD_WIDTH / 9;
	
	/** y position of the displayed points total */
	public static final int POINTS_Y = BOARD_HEIGHT - 60;
}
