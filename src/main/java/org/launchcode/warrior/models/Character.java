package org.launchcode.warrior.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

// Character object constructor

@Entity
@Table(name = "vitals")
public class Character extends AbstractEntity {

	private String name;
	private String gender;
	private int strength;
	private int quickness;
	private int hardiness;
	private String weapon;
	private String armor;
	private String background;
	private int health;
	private int stamina;
	private int fatigue;
	private int initiative;
	private int moveRate;
	private User player;
	private Date created;
	private Date modified;
	
	public Character() {}
	
	public Character(String name, String gender, int strength, int quickness, int hardiness, String weapon, String armor, String background, User player) {

		super();

		this.name = name;
		this.gender = gender;
		this.strength = strength;
		this.quickness = quickness;
		this.hardiness = hardiness;
		this.weapon = weapon;
		this.armor = armor;
		this.background = background;
		this.health = hardiness * 3;
		this.stamina = hardiness * 10;
		this.fatigue = 0;		
		this.moveRate = 0;
		//this.attackRate = (quickness + movement) / weapon.speed
		this.player = player;
		this.created = new Date();
		this.updated();
		
		player.addCharacter (this);
		//commented this for testing.

	}
	
	@NotNull
    @Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.updated();
	}

	@NotNull
    @Column(name = "gender")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
		this.updated();
	}
	
	@NotNull
    @Column(name = "strength")
	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
		this.updated();
	}

	@NotNull
    @Column(name = "quickness")
	public int getQuickness() {
		return quickness;
	}

	public void setQuickness(int quickness) {
		this.quickness = quickness;
		this.updated();
	}
	
	@NotNull
    @Column(name = "hardiness")
	public int getHardiness() {
		return hardiness;
	}

	public void setHardiness(int hardiness) {
		this.hardiness = hardiness;
		this.updated();
	}

	@NotNull
    @Column(name = "weapon")
	public String getWeapon() {
		return weapon;
	}

	public void setWeapon(String weapon) {
		this.weapon = weapon;
		this.updated();
	}
	
	@NotNull
    @Column(name = "armor")
	public String getArmor() {
		return armor;
	}

	public void setArmor(String armor) {
		this.armor = armor;
		this.updated();
	}
	
	
	@NotNull
    @Column(name = "background")
	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
		this.updated();
	}

	public int getMoveRate() {
		return moveRate;
	}

	public void setMoveRate(int moveRate) {
		this.moveRate = moveRate;
	}

	@ManyToOne
	public User getPlayer() {
		return player;
	}
	
	@SuppressWarnings("unused")
	private void setPlayer(User player) {
		this.player = player;
	}
	
	@NotNull
	@OrderColumn
	@Column(name = "created")
	public Date getCreated() {
		return created;
	}
	
	@SuppressWarnings("unused")
	private void setCreated(Date created) {
		this.created = created;
	}
	
	@NotNull
	@Column(name = "modified")
	public Date getModified() {
		return modified;
	}
	
	@SuppressWarnings("unused")
	private void setModified(Date modified) {
		this.modified = modified;
	}
	
	private void updated() {
		this.modified = new Date();
	}

	
	public static int dice(int dice, int sides) {
		int tally = 0;
		for (int die = 0; die < dice; die++) {
			int roll = (int)(1 + Math.random() * sides);
			tally = tally + roll; 
		}
		return tally;
	}
	/*Add warrior actions
	 * Modifiers
	 * 
	 * MOVEMENT
	 *   Towards
	 *   -Approach
	 *   -Charge 
	 *   Away
	 *   -BackAway
	 *   -Flee
	 *   RePosition
	 *   -Flank
	 *   -Encircle
	 *   -Dodge
	 *   -SideStep
	 *
	 * DEFEND
	 *   -Dodge
	 *   -SideStep
	 *   -Parry
	 *   -Shield
	 *   -Guard
	 * 
	 * ATTACK
	 * 
	 *   Direct
	 *   -Hack
	 *   -Slash
	 *   -Stab
	 *   Maneuver
	 *   -Feint
	 *   -Parry
	 *   -Disarm
	 *   -SheildBash
	 * 
	 */
	
	//MOVEMENT
	public double calcMoveSpeed() {
		double fatMod = this.fatigue / this.stamina;
		double ftPerSec = 0;
		double encMod = this.strength/42;
		if (this.armor == "3") {
			encMod = encMod + (1/210);
		}
		else if (this.armor == "2") {
			encMod = encMod + (1/4);
		}
		else if (this.armor == "1") {
			encMod = encMod + (7/12);
		}
		else {
			encMod = 1;
		}
		if (this.moveRate==5) {
			ftPerSec = (this.strength * 2) + 10;
		}
		else if (this.moveRate==4) {
			ftPerSec = (this.strength * 9) / 7 + 8;
		}
		else if (this.moveRate==3) {
			ftPerSec = this.strength + 6;
		}
		else if (this.moveRate==2) {
			ftPerSec = ((this.strength * 4) + 9) / 7;
		}
		else if (this.moveRate==1) {
			ftPerSec = ((this.strength * 4.5) / 7) -.5;
		}
		else {
			ftPerSec = 0;
		}
		ftPerSec = ftPerSec * encMod;
		if (fatMod < 1/2) {
			ftPerSec = ftPerSec * 2/3;
		}
		else if (fatMod < 1/4) {
			ftPerSec = ftPerSec * 1/3;
		}
		return ftPerSec;
	}
	
	//ATTACK/SPEED
	public double calcSwipeSpeed() {
		double attSpd = 0;
		//double staMod = (this.fatigue / this.stamina) * 
		double quiMod = 5/(double)this.quickness;
		if (this.weapon == "1") {
			attSpd = 3 * quiMod;
		}
		else if (this.weapon == "2") {
			attSpd = 4 * quiMod;
		}
		else {
			attSpd = 7 * quiMod;
		}
		return attSpd;
	}
	
	public double calcHackSpeed() {
		double attSpd = 0;
		double quiMod = 5/(double)this.quickness;
		if (this.weapon == "1") {
			attSpd = 2 * quiMod;
		}
		else if (this.weapon == "2") {
			attSpd = 3 * quiMod;
		}
		else {
			attSpd = 6 * quiMod;
		}
		return attSpd;
	}
	
	public double calcStabSpeed() {
		double attSpd = 0;
		double quiMod = 5/(double)this.quickness;
		if (this.weapon == "1") {
			attSpd = quiMod;
		}
		else if (this.weapon == "2") {
			attSpd = 2 * quiMod;
		}
		else {
			attSpd = 5 * quiMod;
		}
		return attSpd;
	}
	
	//ATTACK/TOHIT
	public double calcSwipeToHit () {
		double fatMod = (this.fatigue / this.stamina) * 15;
		double toHitChance = 0;
		if (this.weapon == "1") {
			toHitChance = 65 + this.strength - fatMod;
			if (this.moveRate == 5) {
				toHitChance = toHitChance - 20;
			}
			else if (this.moveRate == 4) {
				toHitChance = toHitChance - 15;
			}
			else if (this.moveRate == 3) {
				toHitChance = toHitChance - 5;
			}
		}
		else if (this.weapon == "2") {
			toHitChance = 70 + this.strength - fatMod;
			if (this.moveRate == 5) {
				toHitChance = toHitChance - 20;
			}
			else if (this.moveRate == 4) {
				toHitChance = toHitChance - 15;
			}
			else if (this.moveRate == 3) {
				toHitChance = toHitChance - 5;
			}
		}
		else {
			toHitChance = 75 + this.strength - fatMod;
			if (this.moveRate == 5) {
				toHitChance = toHitChance - 20;
			}
			else if (this.moveRate == 4) {
				toHitChance = toHitChance - 15;
			}
			else if (this.moveRate == 3) {
				toHitChance = toHitChance - 5;
			}
		}
		return toHitChance;
	}
	
	public double calcHackToHit () {
		double fatMod = (this.fatigue / this.stamina) * 15;
		double toHitChance = 0;
		toHitChance = 55 + (this.strength * 1.5) - fatMod;
			if (this.moveRate == 5) {
				toHitChance = toHitChance - 20;
			}
			else if (this.moveRate == 4) {
				toHitChance = toHitChance - 15;
			}
			else if (this.moveRate == 3) {
				toHitChance = toHitChance - 5;
			}
		return toHitChance;
	}
	
	public double calcStabToHit () {
		double fatMod = (this.fatigue / this.stamina) * 15;
		double toHitChance = 0;
		if (this.weapon == "1") {
			toHitChance = 40 + this.strength - fatMod;
			if (this.moveRate == 5) {
				toHitChance = toHitChance - 20;
			}
			else if (this.moveRate == 4) {
				toHitChance = toHitChance - 15;
			}
			else if (this.moveRate == 3) {
				toHitChance = toHitChance - 5;
			}
		}
		else if (this.weapon == "2") {
			toHitChance = 35 + this.strength - fatMod;
			if (this.moveRate == 5) {
				toHitChance = toHitChance - 20;
			}
			else if (this.moveRate == 4) {
				toHitChance = toHitChance - 15;
			}
			else if (this.moveRate == 3) {
				toHitChance = toHitChance - 5;
			}
		}
		else {
			toHitChance = 30 + this.strength - fatMod;
			if (this.moveRate == 5) {
				toHitChance = toHitChance - 20;
			}
			else if (this.moveRate == 4) {
				toHitChance = toHitChance - 15;
			}
			else if (this.moveRate == 3) {
				toHitChance = toHitChance - 5;
			}
		}
		return toHitChance;
	}
	
	//ATTACK/DAMAGE
	public double calcSwipeDam () {
		double weaDam = 0;
		double damMod = ((double)this.strength / 2) - ((this.fatigue / this.stamina) * 5);
		//double spdMod = 0;
		if (this.weapon == "1") {
			weaDam = dice(1,3);
		}
		else if (this.weapon == "2") {
			weaDam = dice(1,6);
		}
		else {
			weaDam = dice(1,8);
		}
		weaDam = weaDam + damMod;
		//this spdMod should be used figured as moveRate compounded by distance run 
		if (this.moveRate == 3) {
			weaDam = weaDam * 1.2;
		}
		else if (this.moveRate == 4) {
			weaDam = weaDam * 1.4;	
		}
		else if (this.moveRate == 5) {
			weaDam = weaDam * 1.6;
		}
		return weaDam;
	}
	
	public double calcHackDam () {
		double weaDam = 0;
		double damMod = ((double)this.strength / 2) - ((this.fatigue / this.stamina) * 5);
		//double spdMod = 0;
		if (this.weapon == "1") {
			weaDam = (dice(1,4)) +1;
		}
		else if (this.weapon == "2") {
			weaDam = (dice(1,6)) + 1;
		}
		else {
			weaDam = (dice(1,8)) + 2;
		}
		weaDam = weaDam + damMod;
		//this spdMod should be used figured as moveRate compounded by distance run 
		if (this.moveRate == 3) {
			weaDam = weaDam * 1.2;
		}
		else if (this.moveRate == 4) {
			weaDam = weaDam * 1.4;	
		}
		else if (this.moveRate == 5) {
			weaDam = weaDam * 1.6;
		}
		return weaDam;
	}
	
	public double calcStabDam () {
		double weaDam = 0;
		double damMod = ((double)this.strength / 2) - ((this.fatigue / this.stamina) * 5);
		//double spdMod = 0;
		if (this.weapon == "1") {
			weaDam = (dice(1,4)) * 2;
		}
		else if (this.weapon == "2") {
			weaDam = (dice(1,6)) * 2;
		}
		else {
			weaDam = (dice(1,8)) * 2;
		}
		weaDam = weaDam + damMod;
		//this spdMod should be used figured as moveRate compounded by distance run 
		if (this.moveRate == 3) {
			weaDam = weaDam * 1.2;
		}
		else if (this.moveRate == 4) {
			weaDam = weaDam * 1.4;	
		}
		else if (this.moveRate == 5) {
			weaDam = weaDam * 1.6;
		}
		return weaDam;
	}
	
	//DEFENCE
	public double shield(String attType) {
		double defMod = (this.strength /2) + this.quickness;
		double fatMod = (this.fatigue / this.stamina) * 10;
		if (attType == "swipe") {
			defMod = defMod * 1.25;
		}
		else if (attType == "hack") {
			defMod = defMod * 1.5;
		}
		else {
			defMod = defMod * 2;
		}
		return defMod - fatMod;
	}
	
	public double dodge(String attType) {
		double defMod = (this.strength /2) + this.quickness;
		double fatMod = (this.fatigue / this.stamina) * 5;
		if (attType == "swipe") {
			defMod = defMod * 1.5;
		}
		else if (attType == "hack") {
			defMod = defMod * 2;
		}
		else {
			defMod = defMod * 3;
		}
		return defMod - fatMod;
	}
	
	public double parry(String attType) {
		double defMod = 0;
		double fatMod = (this.fatigue / this.stamina) * 10;
		if (attType == "swipe") {
			defMod = this.quickness + (this.strength * 3/2);
		}
		else if (attType == "hack") {
			defMod = this.quickness + this.strength;
		}
		else {
			defMod = (this.quickness * 3/2) + (this.strength * 1/3);
		}
		return defMod - fatMod;
	}
	
	public String toString() {
		return "Warrior:\n" + this.name + " / " + this.gender + "\n" +
				"Str: " + this.strength + " / Qui: " + this.quickness + " / Har: " + this.hardiness + "\n" +
				"Weapon: " + this.weapon + " / Armor: " + this.armor;
	}

	public static void main(String[] args) {
		System.out.println(dice(3,3));
		System.out.println(dice(3,3));
		User sample = new User ("chris", "pass");
		Character elb = new Character ("Elb", "Male", 7, 6, 8, "2", "1", "mad dwarf", sample);
		System.out.println(elb.toString());
		System.out.println(elb.calcHackToHit());
		System.out.println(elb.calcStabSpeed());
		System.out.println(elb.calcSwipeDam());
		Character warty = new Character ("Warty", "Male", 5, 10, 5, "1", "0", "slippery devil", sample);
		System.out.println(warty.toString());
		System.out.println(warty.calcHackToHit());
		System.out.println(warty.calcStabSpeed());
		System.out.println(warty.calcSwipeDam());

		
	}

}
