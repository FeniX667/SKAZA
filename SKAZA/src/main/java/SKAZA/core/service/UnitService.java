package SKAZA.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import SKAZA.core.models.map.Map;
import SKAZA.core.models.unit.Nation;
import SKAZA.core.models.unit.UnitOrientation;
import SKAZA.core.models.unit.Unit;
import SKAZA.core.models.unit.UnitState;
import SKAZA.core.models.unit.UnitStatistics;

public class UnitService {
	
	public static ArrayList<Unit> createArmy(Nation nation) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static Unit createUnit(UnitStatistics _unitStatistics){
		Unit unit = new Unit();
		unit.setUnitStatistics( new UnitStatistics() ) ;
		unit.setUnitState( UnitState.IDLE );
		unit.setUnitOrientation( UnitOrientation.NORTH );
		unit.setDistanceTravelled( new Byte((byte) 0) );
		
		return unit;
	}

	public static boolean isArmyDefeated(ArrayList<Unit> unitList) {
		boolean isDefeated = true;
		
		for( Unit unit : unitList ){
			if( unit.getUnitState() != UnitState.FLEEING )
				return false;
		}
		
		return isDefeated;
	}
}
