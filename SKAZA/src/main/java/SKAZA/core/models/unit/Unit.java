package SKAZA.core.models.unit;


public class Unit {
	UnitStatistics unitStatistics;
	Integer nrOfSoldiers;
	UnitState state;
	UnitOrientation orientation;
	Byte distanceTravelled;
	Nation nation;
	
	public UnitStatistics getUnitStatistics() {
		return unitStatistics;
	}

	public void setUnitStatistics(UnitStatistics unitStatistics) {
		this.unitStatistics = unitStatistics;
	}

	public Integer getNrOfSoldiers() {
		return nrOfSoldiers;
	}

	public void setNrOfSoldiers(Integer nrOfSoldiers) {
		this.nrOfSoldiers = nrOfSoldiers;
	}

	public UnitState getUnitState() {
		return state;
	}

	public void setUnitState(UnitState unitState) {
		this.state = unitState;
	}

	public UnitOrientation getUnitOrientation() {
		return orientation;
	}

	public void setUnitOrientation(UnitOrientation unitOrientation) {
		this.orientation = orientation;
	}

	public Byte getDistanceTravelled() {
		return distanceTravelled;
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
