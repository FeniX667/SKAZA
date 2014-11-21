package SKAZA.core.models.unit;

public class UnitArchetype {
	String name;
	Byte attack;
	Byte defense;
	Byte damage;
	Byte health;
	Byte speed;
	Integer effectiveAmountOfFighters;
		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Byte getAttack() {
		return attack;
	}
	public void setAttack(Byte attack) {
		this.attack = attack;
	}
	public Byte getDefense() {
		return defense;
	}
	public void setDefense(Byte defense) {
		this.defense = defense;
	}
	public Byte getDamage() {
		return damage;
	}
	public void setDamage(Byte damage) {
		this.damage = damage;
	}
	public Byte getHealth() {
		return health;
	}
	public void setHealth(Byte health) {
		this.health = health;
	}
	public Byte getSpeed() {
		return speed;
	}
	public void setSpeed(Byte speed) {
		this.speed = speed;
	}
	public Integer getEffectiveAmountOfFighters() {
		return effectiveAmountOfFighters;
	}
	public void setEffectiveAmountOfFighters(Integer effectiveAmountOfFighters) {
		this.effectiveAmountOfFighters = effectiveAmountOfFighters;
	}

	
}
