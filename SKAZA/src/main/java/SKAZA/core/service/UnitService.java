package SKAZA.core.service;

import java.util.ArrayList;
import SKAZA.core.models.unit.Nation;
import SKAZA.core.models.unit.UnitOrientation;
import SKAZA.core.models.unit.Unit;
import SKAZA.core.models.unit.UnitState;
import SKAZA.core.models.unit.UnitArchetype;

public class UnitService {
	
	public static ArrayList<Unit> createArmy(Nation nation) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static Unit createUnit(Nation nation, UnitArchetype archetype){
		Unit unit = new Unit();
		
		unit = setBasicStatistics(unit, nation);
		unit = setArchetype(unit, archetype);
		
		return unit;
	}

	private static Unit setBasicStatistics(Unit unit, Nation nation) {
		unit.setNrOfSoldiers( new Integer(0) );
		unit.setState( UnitState.IDLE );
		unit.setOrientation( UnitOrientation.NORTH );
		unit.setDistanceTravelled( new Byte((byte) 0) );
		unit.setMorale( new Byte((byte) (0)) );
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
