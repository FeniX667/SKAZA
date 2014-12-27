package SKAZA.core.service;

import java.io.File;
import java.util.ArrayList;

import SKAZA.core.models.unit.Nation;
import SKAZA.core.models.unit.UnitOrientation;
import SKAZA.core.models.unit.Unit;
import SKAZA.core.models.unit.UnitState;
import SKAZA.core.models.unitArchetype.UnitArchetype;
import SKAZA.core.repository.UnitArchetypeRepository;

public class UnitService {
	
	public static ArrayList<Unit> createArmy(Nation nation) {
		ArrayList<Unit> unitList = new ArrayList<Unit>();
		
		Unit unit = createUnit(nation, UnitArchetypeRepository.archetypeData.get(0) );
		
		unitList.add(unit);
		
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
		unit.setOrientation( UnitOrientation.NORTH );
		unit.setDistanceTravelled( new Integer(0) );
		unit.setMorale( new Integer(5) );
		unit.setNation(nation);
		
		return unit;
	}

	private static Unit setArchetype(Unit unit, UnitArchetype archetype) {
		unit.setArchetype( archetype);
		
		return unit;		
	}

	public static boolean isArmyDefeated(ArrayList<Unit> unitList) {
		boolean isDefeated = true;
		
		for( Unit unit:unitList ){
			if( unit.getState() != UnitState.FLEEING && 
					unit.getState() != UnitState.DEAD )
				return false;
		}
		
		return isDefeated;
	}
}
