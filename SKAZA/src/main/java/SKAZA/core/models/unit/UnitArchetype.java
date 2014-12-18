package SKAZA.core.models.unit;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UnitArchetype {
	StringProperty name;	
	IntegerProperty attack;
	IntegerProperty defense;
	IntegerProperty damage;
	IntegerProperty health;
	IntegerProperty speed;
	IntegerProperty effectiveAmountOfFighters;
	
    public UnitArchetype() {
    	this.name = new SimpleStringProperty();
    	this.attack = new SimpleIntegerProperty();
    	this.defense = new SimpleIntegerProperty();
    	this.damage = new SimpleIntegerProperty();
    	this.health = new SimpleIntegerProperty();
    	this.speed = new SimpleIntegerProperty();
    	this.effectiveAmountOfFighters = new SimpleIntegerProperty();
    }

	public StringProperty nameProperty() {
		return this.name;
	}
	public String getName() {
		return this.nameProperty().get();
	}
	public void setName(String name) {
		this.nameProperty().set(name);
	}
	public IntegerProperty attackProperty() {
		return this.attack;
	}
	public int getAttack() {
		return this.attackProperty().get();
	}
	public void setAttack(int attack) {
		this.attackProperty().set(attack);
	}
	public IntegerProperty defenseProperty() {
		return this.defense;
	}
	public int getDefense() {
		return this.defenseProperty().get();
	}
	public void setDefense(int defense) {
		this.defenseProperty().set(defense);
	}
	public IntegerProperty damageProperty() {
		return this.damage;
	}
	public int getDamage() {
		return this.damageProperty().get();
	}
	public void setDamage(int damage) {
		this.damageProperty().set(damage);
	}
	public IntegerProperty healthProperty() {
		return this.health;
	}
	public int getHealth() {
		return this.healthProperty().get();
	}
	public void setHealth(int health) {
		this.healthProperty().set(health);
	}
	public IntegerProperty speedProperty() {
		return this.speed;
	}
	public int getSpeed() {
		return this.speedProperty().get();
	}
	public void setSpeed(int speed) {
		this.speedProperty().set(speed);
	}
	public IntegerProperty effectiveAmountOfFightersProperty() {
		return this.effectiveAmountOfFighters;
	}
	public int getEffectiveAmountOfFighters() {
		return this.effectiveAmountOfFightersProperty().get();
	}
	public void setEffectiveAmountOfFighters(int effectiveAmountOfFighters) {
		this.effectiveAmountOfFightersProperty().set(effectiveAmountOfFighters);
	}
	
	
}
