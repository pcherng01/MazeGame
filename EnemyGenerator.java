import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Randomy generate enemy
 * @author LeafCherngchaosil
 *
 */
public class EnemyGenerator{
	/**
	 * List of enemies
	 */
	private ArrayList<Enemy> enemyList;
	/**
	 * Construct a new EnemyGenerator
	 * @throws FileNotFoundException
	 */
	public EnemyGenerator() throws FileNotFoundException
	{
		enemyList = new ArrayList<Enemy>();
		ItemGenerator anItemGenerator = new ItemGenerator();
		File inputFile = new File("EnemyList.txt");
		Scanner sc = new Scanner(inputFile);
		while(sc.hasNext())
		{
			String inputLine = sc.nextLine();
			String[] nameQuipHitpoint = inputLine.split(",");
			String name = nameQuipHitpoint[0];
			String quip = nameQuipHitpoint[1];
			int hitPoints = Integer.parseInt(nameQuipHitpoint[2]);
			// Enemy(String name, String quip, int hitPoint, int level, int gold, Item item)
			Item enemyItem = anItemGenerator.generateItem();
			int enemyGold = enemyItem.getValue();
			Enemy theEnemy = new Enemy(name, quip, hitPoints, 1, enemyGold, enemyItem);
			enemyList.add(theEnemy);
		}
	}
	/**
	 * Generate enemy from the list
	 * @param level - Level of enemy
	 * @return - Enemy
	 */
	public Enemy generateEnemy(int level)
	{
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(enemyList.size());
		Enemy randomEnemy =  enemyList.get(randomInt);
		for(int i = 1; i < level; i++)
		{
			randomEnemy.increaseLevel();
		}
		return randomEnemy;
	}
}
