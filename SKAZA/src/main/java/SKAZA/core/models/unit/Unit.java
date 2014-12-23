package SKAZA.core.models.unit;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import SKAZA.core.models.unitArchetype.UnitArchetype;


public class Unit{
	private UnitArchetype archetype;
	private IntegerProperty nrOfSoldiers;
	private IntegerProperty distanceTravelled;
	private IntegerProperty morale;
	private Unit fightingWith;
	UnitState state;
	UnitOrientation orientation;
	Nation nation;
	
	public Unit(){
		nrOfSoldiers = new SimpleIntegerProperty();
		distanceTravelled = new SimpleIntegerProperty();
		morale = new SimpleIntegerProperty();
	}
	
	public UnitArchetype getArchetype() {
		return archetype;
	}
	public void setArchetype(UnitArchetype archetype) {
		this.archetype = archetype;
	}
	public UnitState getState() {
		return state;
	}
	public void setState(UnitState state) {
		this.state = state;
	}
	public UnitOrientation getOrientation() {
		return orientation;
	}
	public void setOrientation(UnitOrientation orientation) {
		this.orientation = orientation;
	}
	public Nation getNation() {
		return nation;
	}
	public void setNation(Nation nation) {
		this.nation = nation;
	}
	public IntegerProperty nrOfSoldiersProperty() {
		return this.nrOfSoldiers;
	}
	public int getNrOfSoldiers() {
		return this.nrOfSoldiersProperty().get();
	}
	public void setNrOfSoldiers(int nrOfSoldiers) {
		this.nrOfSoldiersProperty().set(nrOfSoldiers);
	}
	public IntegerProperty distanceTravelledProperty() {
		return this.distanceTravelled;
	}
	public int getDistanceTravelled() {
		return this.distanceTravelledProperty().get();
	}
	public void setDistanceTravelled(int distanceTravelled) {
		this.distanceTravelledProperty().set(distanceTravelled);
	}
	public IntegerProperty moraleProperty() {
		return this.morale;
	}
	public int getMorale() {
		return this.moraleProperty().get();
	}
	public void setMorale(int morale) {
		this.moraleProperty().set(morale);
	}

	public Unit getFightingWith() {
		return fightingWith;
	}

	public void setFightingWith(Unit fightingWith) {
		this.fightingWith = fightingWith;
	}
	
	
}
