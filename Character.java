import java.io.Serializable;

/**
 * Abstract class for all characters in the game
 * @author LeafCherngchaosil
 *
 */
public abstract class Character implements Serializable {
	/**
	 * Name of character
	 */
	private String mName;
	/**
	 * Catchphrase of character
	 */
	private String mQuip;
	/**
	 * Level of character
	 */
	private int mLevel;
	/**
	 * hitpoint of character
	 */
	private int mHp;
	/**
	 * gold of character
	 */
	private int mGold;
	/**
	 * Create a new character with name, quip, hp, level, and gold
	 * @param name - Name of character
	 * @param quip - Catchphrase of character
	 * @param hp - Hit point of character
	 * @param level - Level of character
	 * @param gold - Gold of character
	 */
	public Character(String name, String quip, int hp, int level, int gold)
	{
		mName = name;
		mQuip = quip;
		mLevel = level;
		mHp = hp;
		mGold = gold;
	}
	
	/**
	 * Returns name of character
	 * @return Character's name
	 */
	public String getName()
	{
		return mName;
	}
	
	/**
	 * Returns quip of character
	 * @return Character's quip
	 */
	public String getQuip()
	{
		return "\"" + mQuip + "\"";
	}
	
	/**
	 * Returns hit point of character
	 * @return Character's hit point
	 */
	public int getHp()
	{
		return mHp;
	}
	/**
	 * Returns level of character
	 * @return Character's level
	 */
	public int getLevel()
	{
		return mLevel;
	}
	
	/**
	 * Returns gold of character
	 * @return Character's gold
	 */
	public int getGold()
	{
		return mGold;
	}
	
	/**
	 * Increase level of character
	 */
	public void increaseLevel()
	{
		mLevel++;
		mHp += mHp;
	}
	
	/**
	 * Heals character by hp
	 * @param hp - amount to heal the character
	 */
	public void heal(int hp)
	{
		mHp += hp;
	}
	
	/**
	 * Decreases character's hitpoint
	 * @param hp - Amount of hp to decrease from character
	 */
	public void takeDamage(int hp)
	{
		mHp -= hp;
	}
	
	/**
	 * Add gold to character's gold
	 * @param gold - Gold to be added
	 */
	public void collectGold(int gold)
	{
		mGold += gold;
		System.out.println(getName()+" receives "+gold+" gold.");
	}
	/**
	 * Display name of character
	 */
	public void display()
	{
		System.out.println(this.getName());
	}
	/**
	 * Abstract method to attack other character
	 * @param c
	 */
	public abstract void attack(Character c);
	
}
