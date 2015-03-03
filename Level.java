import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Scanner;

/**
 * This is the Level class, it reads the map from text file,
 * return starting location, display map
 * @author LeafCherngchaosil
 *
 */
public class Level {
	/**
	 * Map level, will be modified
	 */
	private char[][] mLevel;
	/**
	 * Unmodified map level
	 */
	private char[][]mOriginalLevel;
	/**
	 * Create a new level with 4x4 map
	 */
	public Level()
	{
		mLevel = new char[4][4];
		mOriginalLevel = new char[4][4];
	}
	
	/**
	 * Generate level based on the Level number
	 * @param levelNum - Level number
	 * @throws FileNotFoundException
	 */
	public void generateLevel(int levelNum) throws FileNotFoundException
	{
		switch(levelNum)
		{
			case 1:
				setUpMap("Level1.txt");
				break;
			case 2:
				setUpMap("Level2.txt");
				break;
			case 3:
				setUpMap("Level3.txt");
				break;
			default:
				System.out.println("You have finished the game. Congratuation!");
				break;
		}
	}
	
	/**
	 * Set up map according to level
	 * @param levelNum - level number
	 * @throws FileNotFoundException
	 */
	public void setUpMap(String levelNum) throws FileNotFoundException
	{
		int row = 0;
		File inputFile = new File(levelNum);
		Scanner sc = new Scanner(inputFile);
		while(sc.hasNext())
		{
			String inputLine = sc.nextLine();
			String inputLine2 = inputLine.replaceAll("\\s", "");
			for(int col = 0; col < inputLine2.length(); col++)
			{
				mLevel[row][col] = inputLine2.charAt(col);
				mOriginalLevel[row][col] = inputLine2.charAt(col);
			}
			row++;
		}
	}
	
	/**
	 * Return room as character based on Point argument
	 * @param pPoint - the location of hero
	 * @return Either 'm','f', or 's' based on Point argument
	 */
	public char getRoom(Point pPoint)
	{
		try
		{
			int xLoc = (int)pPoint.getX();
			int yLoc = (int)pPoint.getY();
			char theRoom = mLevel[xLoc][yLoc];
			//mLevel[xLoc][yLoc] = '*';
			return theRoom;
		}catch(IndexOutOfBoundsException e)
		{
			System.out.println("Cannot move that direction.");
			return 'n';
		}
	}
	
	/**
	 * Print out current map
	 * @param p - Location of hero
	 */
	public void displayMap(Point p)
	{
		Point heroLocation = p.getLocation();
		int xLoc = (int)heroLocation.getX();
		int yLoc = (int)heroLocation.getY();
		//mLevel[(int)heroLocation.getX()][(int)heroLocation.getY()] = '*';
		System.out.println("Dungeon Map:\n----------");
		for(int row = 0; row < mLevel.length; row++)
		{
			System.out.print("| ");
			for(int column = 0; column < mLevel[row].length; column++)
			{
				if(row == xLoc && column == yLoc)
				{
					System.out.print('*'+" ");
				}
				else
				{
					System.out.print(mOriginalLevel[row][column]+" ");
				}
			}
			System.out.println("|");
		}
		System.out.println("----------");
	}
	
	/**
	 * Returns starting location
	 * @return starting location of the level map
	 */
	public Point findStartLocation()
	{
		return (new Point(3,0));
	}
	
	/**
	 * Check if valid move
	 * @param pPoint - location of hero
	 * @return true if valid move, false otherwise
	 */
	public boolean validMove(Point pPoint)
	{
		try
		{
			int xLoc = (int)pPoint.getX();
			int yLoc = (int)pPoint.getY();
			mLevel[xLoc][yLoc] = '*';
			return true;
		}catch(IndexOutOfBoundsException e)
		{
			System.out.println("Cannot move that direction.");
			return false;
		}
	}
	
	/**
	 * Check if user is at Exit room
	 * @param pHero - The hero
	 * @return - true if Hero is at exit room, false otherwise
	 */
	public boolean atExitRoom(Hero pHero)
	{
		if(getRoom(pHero.getLocation()) == 'f')
			return true;
		return false;
	}
}
