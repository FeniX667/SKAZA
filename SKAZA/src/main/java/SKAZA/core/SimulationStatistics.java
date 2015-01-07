package SKAZA.core;

import SKAZA.core.models.unit.Nation;

public class SimulationStatistics {
	
	public Nation nation;
	public Integer totalArmySize;
	public Integer infantrySize;
	public Double infantryStrength;
	public Integer cavalrySize;
	public Double cavalryStrength;
	
	public SimulationStatistics(Nation nation) {
		nation = nation;	
		totalArmySize = new Integer(0);
		infantrySize = new Integer(0);
		infantryStrength = new Double(0.0);
		cavalrySize = new Integer(0);
		cavalryStrength = new Double(0.0);
	}
	
}
