package SKAZA.core.models.unit;

public class UnitStatistics {
	String name;
	Byte attack;
	Byte defense;
	Byte damage;
	Byte health;
	Byte speed;
	Byte morale;
	Byte stamina;
	Integer effectiveAmountOfFighters;
		
	public UnitStatistics() {
	}
	
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
	public Byte getMorale() {
		return morale;
	}
	public void setMorale(Byte morale) {
		this.morale = morale;
	}
	public Byte getStamina() {
		return stamina;
	}
	public void setStamina(Byte exhaustion) {
		this.stamina = exhaustion;
	}
	public Integer getEffectiveAmountOfFighters() {
		return effectiveAmountOfFighters;
	}
	public void setEffectiveAmountOfFighters(Integer effectiveAmountOfFighters) {
		this.effectiveAmountOfFighters = effectiveAmountOfFighters;
	}

	
}
