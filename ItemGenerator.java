import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * This class reads items from text file, store them
 * in ArrayList<Item>. Randomly generates item from ArrayList
 * @author Pongsakorn Cherngchaosil
 *
 */
public class ItemGenerator{
	/**
	 * List of item
	 */
	private ArrayList<Item> mItemList;
	/**
	 * Reads each item from a file and store them in ArrayList
	 * @throws FileNotFoundException
	 */
	public ItemGenerator() throws FileNotFoundException
	{
		mItemList = new ArrayList<Item>();
		File inputFile = new File("ItemList.txt");
		Scanner sc = new Scanner(inputFile);
		while(sc.hasNext())
		{
			String inputLine = sc.nextLine();
			String[] nameAndGold = inputLine.split(",");
			Item anItem = new Item(nameAndGold[0], Integer.parseInt(nameAndGold[1]));
			mItemList.add(anItem);
		}
	}
	/**
	 * Randomly generates item from ArrayList
	 * @return random Item
	 */
	public Item generateItem()
	{
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(mItemList.size());
		return mItemList.get(randomInt);
	}
}
