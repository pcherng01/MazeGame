import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Dungeon master game, user has to navigate the map and wins the game
 * @author Pongsakorn Cherngchaosil 012107171
 *
 */
public class Main {

	/**
	 * Main method
	 * @param args - console input
	 * @throws FileNotFoundException - throws exception if file not found
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		Hero theHero = null;
		File objectFile = new File("hero.dat");
		if(objectFile.exists()){
			try{
				ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(objectFile));
				theHero = (Hero) objectInput.readObject();
				Level aLevel = new Level();
				aLevel.generateLevel(theHero.getLevel());
				startGame(theHero,aLevel,objectFile);
				objectInput.close();
			}catch(IOException e){
				System.out.println("Error processing file.");
			}catch(ClassNotFoundException e){
				System.out.println("Could not find class.");
			}
		}
		else{
			String heroName = getPlayerName();
			theHero = new Hero(heroName,"Aha!",new Point(3,0));
			Level aLevel = new Level();
			aLevel.generateLevel(theHero.getLevel());
			//EnemyGenerator anEnemyGenerator = new EnemyGenerator();
			
			startGame(theHero,aLevel,objectFile);
		}
	}
	/**
	 * Start the game
	 * @param pHero - The main hero
	 * @param pLevel - The level of the map
	 * @param pFile - The input file
	 * @throws FileNotFoundException - Throws exception when file is not found
	 */
	public static void startGame(Hero pHero, Level pLevel, File pFile) throws FileNotFoundException
	{
		Scanner sc = new Scanner(System.in);
		char room = 0;
		while(!pHero.dead() || !pLevel.atExitRoom(pHero)){
			pLevel.displayMap(pHero.getLocation());
			room = chooseDirection(pHero,pLevel);
			switch(room){
			// Found monster
			case 'm':
				EnemyGenerator anEnemyGenerator = new EnemyGenerator();
				Enemy theEnemy = anEnemyGenerator.generateEnemy(pHero.getLevel());
				room = foundMonster(pHero,theEnemy, pLevel, pFile);
				break;
			// Found item
			case 'i':
				ItemGenerator anItemGenerator = new ItemGenerator();
				Item randomItem = anItemGenerator.generateItem();
				foundItem(pHero,randomItem);
				break;
			// Found exit
			case 'f':
				pHero.increaseLevel();
				pHero.setLocation(pLevel.findStartLocation());
				System.out.println("You win this level!\nSaving progress....");
				try{
					ObjectOutputStream outputObject = new ObjectOutputStream(new FileOutputStream(pFile));
					outputObject.writeObject(pHero);
					outputObject.close();
				}catch(IOException e){
					System.out.println("Error processing file.");
				}
				break;
			// Found starting point
			case 's':
				System.out.println("You can sell any of your item:\nEnter 0 to exit.");
				for(int i = 1; i < pHero.getItems().size(); i++){
					System.out.println(i+". "+pHero.getItems().get(i).getName()+", "+pHero.getItems().get(i).getValue());
				}
				int option = sc.nextInt();
				switch(option){
					case 0:
						break;
					case 1:
						pHero.removeItem(1);
						break;
					case 2:
						pHero.removeItem(2);
						break;
					case 3:
						pHero.removeItem(3);
						break;
					case 4:
						pHero.removeItem(4);
						break;
					case 5:
						pHero.removeItem(5);
						break;
				}
				break;
			case 1:
				break;
		}
		}
		
	}
	/**
	 * Allow user to choose the valid direction of the Hero
	 * @param pHero - The Hero
	 * @param pLevel - The current Level
	 * @return - The room after the Hero has move ('m','i','f','s')
	 */
	public static char chooseDirection(Hero pHero, Level pLevel)
	{
		Scanner sc = new Scanner(System.in);
		boolean validInput = false;
		char room = 0;
		while(!validInput){
			System.out.println("Choose a Direction:\n"+ "1. North\n"
					+ "2. South\n"+ "3. East\n"+ "4. West");
			try{
				int input = sc.nextInt();
					if(input < 1 || input > 4)
						System.out.println("Invalid input, please try again.");
					else{
						switch(input){
							case 1:
								room = pHero.goNorth(pLevel);
								pLevel.displayMap(pHero.getLocation());
								validInput = (room=='n')?false:true;
								break;
							case 2:
								room = pHero.goSouth(pLevel);
								pLevel.displayMap(pHero.getLocation());
								validInput = (room=='n')?false:true;
								break;
							case 3:
								room = pHero.goEast(pLevel);
								pLevel.displayMap(pHero.getLocation());
								validInput = (room=='n')?false:true;
								break;
							case 4:
								room = pHero.goWest(pLevel);
								pLevel.displayMap(pHero.getLocation());
								validInput = (room=='n')?false:true;
								break;
						}
					}
			}catch(InputMismatchException e){
				sc.next();
				System.out.println("Invalid input, please try again.");
			}
		}
		return room;
	}
	/**
	 * Called when player found item
	 * @param pHero - The Hero
	 * @param pItem - The item in the room
	 */
	public static void foundItem(Hero pHero, Item pItem)
	{
		System.out.println(pHero.getName()+" found a"+pItem.getName()+".");
		System.out.println("What do you do?\n1. Keep it\n2. Sell it");
		Scanner sc = new Scanner(System.in);
		boolean invalidInput = true;
		int choice = 0;
		while(invalidInput){
			try{
				choice = sc.nextInt();

				if(choice < 0 || choice > 2)
					System.out.println("Invalid input, please try again.");
				invalidInput = false;
			}catch(InputMismatchException e){
				System.out.println("Invalid input, try again.");
			}
		}
		switch(choice){
			case 1:
				if( !pHero.pickUpItem(pItem)){
					System.out.println("Bag is full, can't pick it up. You have to sell it.");
					pHero.collectGold(pItem.getValue());
				}
			case 2:
				pHero.collectGold(pItem.getValue());
		}
	}
	
	/**
	 * Hero enters the room and encounters an Enemy
	 * @param pHero - The Hero
	 * @param pEnemy - The Enemy
	 * @param pLevel - Current level
	 * @return  - room after finding the monster
	 * @throws FileNotFoundException 
	 */
	public static char foundMonster(Hero pHero, Enemy pEnemy, Level pLevel, File pFile) throws FileNotFoundException
	{
		System.out.println(pHero.getName()+" has "+pHero.getHp()+" health.");
		System.out.println(pHero.getName()+" has encountered a "+pEnemy.getName());
		System.out.println("It has "+pEnemy.getHp()+" health.");
		boolean potionPresent = pHero.hasHealthPotion();

		if(potionPresent)
			System.out.println("What do you do?\n1. Run Away\n2.Attack\n3. Use potion");
		else
			System.out.println("What do you do?\n1. Run Away\n2.Attack");

		boolean invalidInput = true;
		int choice = 0;
		Scanner sc = new Scanner(System.in);
		while(invalidInput){
			try{
				choice = sc.nextInt();
				if(potionPresent){
					if(choice < 1 || choice > 3)
						System.out.println("Invalid input, please try again.");
					else
						invalidInput = false;
				}
				else{
					if(choice < 1 || choice > 2)
						System.out.println("Invalid input, please try again.");
					else 
						invalidInput = false;
				}
			}catch(InputMismatchException e){
				sc.next();
				System.out.println("Invalid input, try again.");
			}
		}
		switch(choice){
		case 1:
			return heroRunsAway(pHero, pLevel, pEnemy, pFile, sc);
		case 2:
			return heroAttackEnemy(pHero, pLevel, pEnemy, pFile);
		case 3:
			return heroUsesPotion(pHero,pEnemy,pLevel, pFile);
		}
		return 0;
	}
	/**
	 * Hero uses potion
	 * @param pHero - The Hero
	 * @param pEnemy - The enemy in the room
	 * @param pLevel - level of the map
	 * @return room - room after hero uses potion
	 * @throws FileNotFoundException 
	 */
	public static char heroUsesPotion(Hero pHero, Enemy pEnemy, Level pLevel, File pFile) throws FileNotFoundException
	{
		if(pHero.hasHealthPotion()){
			pHero.heal(pHero.getMaxHp());
			pEnemy.attack(pHero);
			pHero.useHealthPotion();
			return foundMonster(pHero, pEnemy, pLevel, pFile);
		}
		else
			return foundMonster(pHero, pEnemy, pLevel, pFile);
	}
	/**
	 * hero attack enemy
	 * @param pHero - The hero
	 * @param pLevel - Level of the map
	 * @param pEnemy - Current enemy
	 * @return room - room after attacking enemy
	 * @throws FileNotFoundException 
	 */
	public static char heroAttackEnemy(Hero pHero, Level pLevel, Enemy pEnemy, File pFile) throws FileNotFoundException
	{
		while(!pHero.dead() || !pEnemy.isDead()){
			pHero.attack(pEnemy);
			pEnemy.attack(pHero);
			if(pEnemy.isDead())
				return 1;
			foundMonster(pHero, pEnemy, pLevel, pFile);
		}
		return 1;
	}
	/**
	 * Hero runs away
	 * @param pHero - The Hero
	 * @param pLevel - Current Level
	 * @return - Return the room after the Hero has moved.('m','f','s','i')
	 * @throws FileNotFoundException 
	 */
	public static char heroRunsAway(Hero pHero, Level pLevel, Enemy pEnemy,File pFile,Scanner sc) throws FileNotFoundException
	{
		pEnemy.attack(pHero);
		Random randomGenerator = new Random();
		int randomDirection = randomGenerator.nextInt(4)+1;
		while(!validateUserMove(pHero,randomDirection,pLevel)){
			randomDirection = randomGenerator.nextInt(4)+1;
		}
		char room = 0;
		switch(randomDirection)
		{
			case 1:
				room = pHero.goNorth(pLevel);
				pLevel.displayMap(pHero.getLocation());
				afterHeroRunAway(room,pHero,pLevel,pFile,sc);
				return room;
			case 2:
				room = pHero.goSouth(pLevel);
				pLevel.displayMap(pHero.getLocation());
				afterHeroRunAway(room,pHero,pLevel,pFile,sc);
				return room;
			case 3:
				room = pHero.goEast(pLevel);
				pLevel.displayMap(pHero.getLocation());
				afterHeroRunAway(room,pHero,pLevel,pFile,sc);
				return room;
			case 4:
				room = pHero.goWest(pLevel);
				pLevel.displayMap(pHero.getLocation());
				afterHeroRunAway(room,pHero,pLevel,pFile,sc);
				return room;
				
		}
		return 0;
	}
	public static char afterHeroRunAway(char room,Hero pHero,Level pLevel, File pFile, Scanner sc) throws FileNotFoundException
	{
		switch(room){
		// Found monster
		case 'm':
			EnemyGenerator anEnemyGenerator = new EnemyGenerator();
			Enemy theEnemy = anEnemyGenerator.generateEnemy(pHero.getLevel());
			room = foundMonster(pHero,theEnemy, pLevel,pFile);
			return room;
		// Found item
		case 'i':
			ItemGenerator anItemGenerator = new ItemGenerator();
			Item randomItem = anItemGenerator.generateItem();
			foundItem(pHero,randomItem);
			return room;
		// Found exit
		case 'f':
			pHero.increaseLevel();
			pHero.setLocation(pLevel.findStartLocation());
			System.out.println("You win this level!\nSaving progress....");
			try{
				ObjectOutputStream outputObject = new ObjectOutputStream(new FileOutputStream(pFile));
				outputObject.writeObject(pHero);
				outputObject.close();
			}catch(IOException e){
				System.out.println("Error processing file.");
			}
			return room;
		// Found starting point
		case 's':
			System.out.println("You can sell any of your item:");
			for(int i = 1; i < pHero.getItems().size(); i++){
				System.out.println(i+". "+pHero.getItems().get(i).getName()+", "+pHero.getItems().get(i).getValue());
			}
			int option = sc.nextInt();
			switch(option){
				case 1:
					pHero.removeItem(1);
				case 2:
					pHero.removeItem(2);
				case 3:
					pHero.removeItem(3);
				case 4:
					pHero.removeItem(4);
				case 5:
					pHero.removeItem(5);
			}
			return room;
		case 1:
			return room;
		}
		return 1;
	}

	/**
	 * check if the move is valid
	 * @param pHero - the hero
	 * @param pDirection - User's input direction
	 * @param pLevel - the level of the map
	 * @return true if valid move, false otherwise
	 */
	public static boolean validateUserMove(Hero pHero,int pDirection, Level pLevel)
	{
		//char room = 0;
		int xLoc = (int)pHero.getLocation().getX();
		int yLoc = (int)pHero.getLocation().getY();
		switch(pDirection){
		case 1:
			return pHero.isValidMove(new Point(xLoc-1,yLoc), pLevel);
		case 2:
			return pHero.isValidMove(new Point(xLoc+1,yLoc), pLevel);
		case 3:
			return pHero.isValidMove(new Point(xLoc,yLoc+1), pLevel);
		case 4:
			return pHero.isValidMove(new Point(xLoc,yLoc-1), pLevel);
		}
		return false;
	}
	/**
	 * validate input
	 * @param minRange - Minimum range
	 * @param maxRange - Maximum range
	 * @param input - User's input
	 */
	public static void checkInput(int minRange, int maxRange, int input)
	{
		if(input < minRange || input > maxRange)
		{
			System.out.println("Invalid input.");
		}
	}
	/**
	 * Validate input 
	 * @param pMinRange - Minimum range
	 * @param pMaxRange - Maximum range
	 * @param pInput - user's input 
	 * @return yes if in range, false otherwise
	 */
	public static boolean validateInput(int pMinRange, int pMaxRange, int pInput)
	{
		if(pInput < pMinRange || pInput > pMaxRange)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * Get player's name
	 * @return - name of player
	 */
	public static String getPlayerName()
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("What is your name, traveler? ");
		String playerName = sc.nextLine();
		System.out.println(playerName + " enters the Dungeon of Despair.");
		return playerName;
	}
}

