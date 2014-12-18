package SKAZA.core.models.unit;


public class Unit{
	UnitArchetype archetype;
	Integer nrOfSoldiers;
	UnitState state;
	UnitOrientation orientation;
	Byte distanceTravelled;
	Byte morale;
	Nation nation;

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

	public Integer getNrOfSoldiers() {
		return nrOfSoldiers;
	}

	public void setNrOfSoldiers(Integer nrOfSoldiers) {
		this.nrOfSoldiers = nrOfSoldiers;
	}
	public Byte getDistanceTravelled() {
		return distanceTravelled;
	}
	public Byte getMorale() {
		return morale;
	}
	public void setMorale(Byte morale) {
		this.morale = morale;
	}
	public void setDistanceTravelled(Byte distanceTravelled) {
		this.distanceTravelled = distanceTravelled;
	}

	public Nation getNation() {
		return nation;
	}

	public void setNation(Nation nation) {
		this.nation = nation;
	}
	
}
