package SKAZA.core;

import java.util.ArrayList;
import java.util.Set;

import javax.inject.Inject;

import SKAZA.core.models.map.Map;
import SKAZA.core.models.unit.Unit;
import SKAZA.core.models.unit.Nation;
import SKAZA.core.service.MapService;
import SKAZA.core.service.UnitService;

public class SimulationEngine {
	Map map;
	ArrayList<Unit> unitsOfRome;
	ArrayList<Unit> unitsOfCarthage;
	Integer iteration;
	SimulationStatistics statistics;
	Boolean endingFlag;

	public SimulationEngine(){
		map = new Map();
		unitsOfRome = new ArrayList<Unit>();
		unitsOfCarthage = new ArrayList<Unit>();
		statistics = new SimulationStatistics();
		endingFlag = new Boolean(false);
	}
	
	public void initialize() {
		unitsOfRome = UnitService.createArmy(Nation.ROME);
		unitsOfCarthage = UnitService.createArmy(Nation.CARTHAGE);
	}

	public void start() {
		while( !endingFlag ){
			iterate();
		}
	}

	private void iterate() {
		checkFights();
		checkMorale();
		moveUnits();
		executeNewOrdersIfPossible();
		generateOrderList();
	}
	
	private void checkFights() {
		// TODO Auto-generated method stub
		
	}

	private void checkMorale() {
		// TODO Auto-generated method stub
		
	}

	private void moveUnits() {
		// TODO Auto-generated method stub
		
	}

	private void executeNewOrdersIfPossible() {
		// TODO Auto-generated method stub
		
	}

	private void generateOrderList() {
		// TODO Auto-generated method stub
		
	}

	private void isCompleted(Nation nation) {
		if( nation == Nation.CARTHAGE)
			endingFlag = UnitService.isArmyDefeated( unitsOfCarthage );
		else
			endingFlag = UnitService.isArmyDefeated( unitsOfRome );
		
	}

	public SimulationStatistics getStatistics() {
		//TODO statystyki symulacji
		return statistics;
	}
}
