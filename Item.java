import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * This class is an Item class, has properties of name and 
 * gold value.
 * @author LeafCherngchaosil
 *
 */
public class Item implements Serializable {
	/**
	 * Name of the item
	 */
	private String mName;
	/**
	 * Value of the item
	 */
	private int mGoldValue;
	/**
	 * Create a new Item with name and value
	 * @param pName - Name of the item
	 * @param pValue - Gold value of the item
	 */
	public Item(String pName, int pValue)
	{
		mName = pName;
		mGoldValue = pValue;
	}
	
	/**
	 * Return name of the item
	 * @return Name of the item
	 */ 
	public String getName()
	{
		return mName;
	}
	
	/**
	 * Return gold value of the item
	 * @return Gold value of the item
	 */
	public int getValue()
	{
		return mGoldValue;
	}
}
