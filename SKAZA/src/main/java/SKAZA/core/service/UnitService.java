package SKAZA.core.service;

import java.io.File;
import java.util.ArrayList;

import SKAZA.core.models.unit.Nation;
import SKAZA.core.models.unit.Unit;
import SKAZA.core.models.unit.UnitState;
import SKAZA.core.models.unitArchetype.UnitArchetype;
import SKAZA.core.repository.UnitArchetypeRepository;

public class UnitService {
	
	public static ArrayList<Unit> createArmy(Nation nation) {
		ArrayList<Unit> unitList = new ArrayList<Unit>();
		
		for(int i=0 ; i<12 ; i++){
			Unit unit = createUnit(nation, UnitArchetypeRepository.archetypeData.get(0) );		
			unitList.add(unit);
		}
		
		return unitList;
	}
	
	public static Unit createUnit(Nation nation, UnitArchetype archetype){
		Unit unit = new Unit();
		
		setBasicStatistics(unit, nation);
		unit = setArchetype(unit, archetype);
		
		return unit;
	}

	private static Unit setBasicStatistics(Unit unit, Nation nation) {
		unit.setNrOfSoldiers( new Integer(200) );
		unit.setState( UnitState.IDLE );
		unit.setDistanceTravelled( new Integer(0) );
		unit.setMorale( new Integer(5) );
		unit.setNation(nation);
		
		return unit;
	}

	private static Unit setArchetype(Unit unit, UnitArchetype archetype) {
		unit.setArchetype( archetype);
		
		return unit;		
	}
	
	public static Unit copyUnit(Unit unit) {

		Unit newUnit = new Unit();

		newUnit = setArchetype(newUnit, unit.getArchetype());	
		newUnit.setNrOfSoldiers( new Integer( unit.getNrOfSoldiers() ) );
		newUnit.setState( unit.getState() );
		newUnit.setDistanceTravelled( new Integer( unit.getDistanceTravelled() ) );
		newUnit.setMorale( new Integer( unit.getMorale() ) );
		newUnit.setNation( unit.getNation() );
		
		return newUnit;
	}
}
