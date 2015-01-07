package SKAZA.core.service;

import java.io.File;
import java.util.ArrayList;

import SKAZA.core.math.constants.UnitConstants;
import SKAZA.core.models.unit.Nation;
import SKAZA.core.models.unit.Unit;
import SKAZA.core.models.unit.UnitState;
import SKAZA.core.models.unitArchetype.UnitArchetype;
import SKAZA.core.repository.UnitArchetypeRepository;

public class UnitService {
	
	public ArrayList<Unit> createArmy(Nation nation) {
		ArrayList<Unit> unitList = new ArrayList<Unit>();
		
		if( nation == Nation.ROME ){
			for(int i=0 ; i < UnitConstants.RomeArmySize ; i++){
				Unit unit = createUnit( nation, UnitArchetypeRepository.archetypeData.get(0), 500 );		
				unitList.add(unit);
			}
		}
		
		if( nation == Nation.CARTHAGE ){

			for(int i=0 ; i < UnitConstants.CarthageArmySize ; i++){
				Unit unit = createUnit( nation, UnitArchetypeRepository.archetypeData.get(0), 500 );		
				unitList.add(unit);
			}
		}
		
		return unitList;
	}
	
	public Unit createUnit(Nation nation, UnitArchetype archetype, Integer nrOfSoldiers){
		Unit unit = new Unit();
		
		setBasicStatistics(unit, nation, nrOfSoldiers);
		unit = setArchetype(unit, archetype);
		
		return unit;
	}

	private Unit setBasicStatistics(Unit unit, Nation nation, Integer nrOfSoldiers) {
		unit.setNrOfSoldiers( new Integer(nrOfSoldiers) );
		unit.setState( UnitState.IDLE );
		unit.setDistanceTravelled( new Integer(0) );
		unit.setMorale( new Integer(5) );
		unit.setNation(nation);
		
		return unit;
	}

	private Unit setArchetype(Unit unit, UnitArchetype archetype) {
		unit.setArchetype( archetype);
		
		return unit;		
	}
	
	public Unit copyUnit(Unit unit) {

		Unit newUnit = new Unit();

		newUnit = setArchetype(newUnit, unit.getArchetype());	
		newUnit.setNrOfSoldiers( new Integer( unit.getNrOfSoldiers() ) );
		newUnit.setState( unit.getState() );
		newUnit.setDistanceTravelled( new Integer( unit.getDistanceTravelled() ) );
		newUnit.setMorale( new Integer( unit.getMorale() ) );
		newUnit.setNation( unit.getNation() );
		
		return newUnit;
	}

	public Double calculateStrength(Unit unit) {		
		return new Double( ( unit.getArchetype().getAttack() + unit.getArchetype().getDefense() )* unit.getNrOfSoldiers()/100 );
	}
}
