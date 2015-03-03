import java.io.Serializable;
import java.util.Random;


/**
 * The enemy class, has catchphrase(quip), name, hit point,
 * its job is to attack the main hero
 * @author Pongsakorn Cherngchaosil
 *
 */
public class Enemy extends Character {
	/**
	 * Item the enemy carries
	 */
	private Item mItem;
	/**
	 * Create an enemy with name, quip, hp, level, gold value, and item.
	 * @param name - Name of the enemy
	 * @param quip - Quip of the enemy
	 * @param hp - Hitpoint of the enemy
	 * @param level - Level of the enemy
	 * @param gold - Gold the enemy has
	 * @param item - Item the enemy possesses.
	 */
	public Enemy(String name, String quip, int hp, int level, int gold, Item item)
	{
		super(name, quip,hp,level, gold);
		mItem = item;
	}
	
	@Override
	/**
	 * Attack Character with damage base on level
	 */
	public void attack(Character c) {
		// TODO Auto-generated method stub
		if(!this.isDead())
		{
			Hero theHero = (Hero) c;
			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt(this.getHp());
			theHero.takeDamage(randomInt);
			System.out.println(getName()+" hits a "+c.getName()+" for "+randomInt+" damage.");
		}
	}
	/**
	 * Return item on corpse
	 * @return item on corpse
	 */
	public Item getItem()
	{
		return mItem;
	}
	/**
	 * No longer active after hp <= 0
	 * @return true if hp <= 0, false otherwise
	 */
	public boolean isDead()
	{
		if(getHp() <= 0)
		{
			return true;
		}
		return false;
	}
}
