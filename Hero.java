import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Hero class, the main character
 * @author Pongsakorn Cherngchaosil
 *
 */
public class Hero extends Character implements Serializable {
	/**
	 * List of item the hero has
	 */
	private ArrayList<Item> mItems;
	/**
	 * Current location of hero
	 */
	private Point mLocation;
	/**
	 * Max hitpoint of character
	 */
	private int mMaxHp;
	/**
	 * Construct a new Hero
	 * @param pName - name of hero
	 * @param pQuip - quip of hero
	 * @param pStart - location of hero
	 */
	public Hero(String pName, String pQuip, Point pStart)
	{
		super(pName,pQuip,10,1,0);
		mMaxHp = 10;
		mLocation = pStart;
		mItems = new ArrayList<Item>();
	}
	/**
	 * get list of item
	 * @return List of items
	 */
	public ArrayList<Item>getItems()
	{
		return mItems;
	}
	/**
	 * Pick up item
	 * @param i - item
	 * @return - true if success, false otherwise
	 */
	public boolean pickUpItem(Item i)
	{
		if(getItems().size() > 5)
		{
			return false;
		}
		else
		{
			mItems.add(i);
			System.out.println(getName()+" receives a "+i.getName());
			return true;
		}
	}
	/**
	 * Remove item from list
	 * @param i - item to be removed
	 */
	public void removeItem(Item i)
	{
		for(int ii = 0; ii < mItems.size(); ii++)
		{
			if(mItems.get(ii).getName().equals(i.getName()))
			{
				removeItem(ii);
				collectGold(i.getValue());
				return;
			}
		}
	}
	/**
	 * Remove item base on index
	 * @param index - index of item
	 */
	public void removeItem(int index)
	{
		Item anItem = mItems.get(index-1);
		collectGold(anItem.getValue());
		mItems.remove(index-1);
	}
	/**
	 * Get Hero location
	 * @return - location of hero
	 */
	public Point getLocation()
	{
		return mLocation;
	}
	/**
	 * Set location of hero
	 * @param pPoint - location oh hero
	 */
	public void setLocation(Point pPoint)
	{
		mLocation = pPoint;
	}
	/**
	 * Go north
	 * @param l - level
	 * @return room
	 */
	public char goNorth(Level l)
	{
		Point newPoint = new Point((int)mLocation.getX()-1, (int)mLocation.getY());
		char room = l.getRoom(newPoint);
		if(room == 'n')
		{
			return 'n';
		}
		setLocation(newPoint);
		return room;
	}
	
	/**
	 * Check if the move is valid
	 * @param pPoint - Location of hero
	 * @param mLevel - Level of hero
	 * @return - true if valid move, false otherwise
	 */
	public boolean isValidMove(Point pPoint, Level mLevel)
	{
		char room = mLevel.getRoom(pPoint);
		if(room == 'n')
		{
			return false;
		}
		return true;
	}
	/**
	 * Go south
	 * @param l - level of the map
	 * @return destination room
	 */
	public char goSouth(Level l)
	{
		Point newPoint = new Point((int)mLocation.getX()+1, (int)mLocation.getY());
		char room = l.getRoom(newPoint);
		if(room == 'n')
		{
			return 'n';
		}
		setLocation(newPoint);
		return room;
	}
	/**
	 * Go east
	 * @param l - level of the map
	 * @return destination room
	 */
	public char goEast(Level l)
	{
		Point newPoint = new Point((int)mLocation.getX(), (int)mLocation.getY()+1);
		char room = l.getRoom(newPoint);
		if(room == 'n')
		{
			return 'n';
		}
		setLocation(newPoint);
		return room;
	}
	/**
	 * Go west
	 * @param l - level of the map
	 * @return destination room
	 */
	public char goWest(Level l)
	{
		Point newPoint = new Point((int)mLocation.getX(), (int)mLocation.getY()-1);
		char room = l.getRoom(newPoint);
		if(room == 'n')
		{
			return 'n';
		}
		setLocation(newPoint);
		return room;
	}
	/**
	 * check if dead
	 * @return true if dead, false otherwise
	 */
	public boolean dead()
	{
		if(this.getHp() <= 0)
		{
			System.out.println(getName()+" has died.");
			return true;
		}
		return false;
	}
	public void useHealthPotion()
	{
		for(int ii = 0; ii < mItems.size(); ii++)
		{
			if(mItems.get(ii).getName().equals("Health Potion"))
			{
				removeItem(ii+1);
				return;
			}
		}
	}
	/**
	 * Check if has health potion
	 * @return yes if has health potion, false otherwise
	 */
	public boolean hasHealthPotion()
	{
		for(int i = 0; i < mItems.size();i++)
		{
			if(mItems.get(i).getName().equals("Health Potion"))
			{
				return true;
			}
		}
		return false;
	}
	/**
	 * return hero max hp
	 * @return map hp as an int
	 */
	public int getMaxHp()
	{
		return mMaxHp;
	}
	@Override
	/**
	 * Attack an enemy
	 */
	public void attack(Character c) {
		// TODO Auto-generated method stub
		Enemy theEnemy = (Enemy) c;
		if(!theEnemy.isDead())
		{
			Random randomGenerator = new Random();
			//int randomInt = randomGenerator.nextInt(this.getHp());
			int randomInt = 100;
			theEnemy.takeDamage(randomInt);
			System.out.println(getName()+" hits a "+c.getName()+" for "+randomInt+" damage.");
			if(theEnemy.isDead())
			{
				System.out.println(getName()+" killed a "+theEnemy.getName()+".");
				System.out.println(getName()+" says "+getQuip());
				pickUpItem(theEnemy.getItem());
				collectGold(theEnemy.getGold());
			}
		}
	}
}
