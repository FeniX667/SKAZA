package SKAZA.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.inject.Inject;

import SKAZA.core.models.map.Map;
import SKAZA.core.models.order.Order;
import SKAZA.core.models.unit.Unit;
import SKAZA.core.models.unit.Nation;
import SKAZA.core.models.unit.UnitState;
import SKAZA.core.service.MapService;
import SKAZA.core.service.SimulationService;
import SKAZA.core.service.UnitService;

public class SimulationEngine {
	public Map map;
	public ArrayList<Unit> unitsOfRome;
	public ArrayList<Unit> unitsOfCarthage;
	public ArrayList<Order> ordersForRome;
	public ArrayList<Order> ordersForCarthage;
	public Integer iteration;
	public SimulationStatistics statistics;
	public Boolean endingFlag;

	public SimulationEngine(){	
		initializeVariables();
		SimulationService.setArmiesOnMap(map, unitsOfRome, unitsOfCarthage);	
		prepareOrderLists();
	}

	private void initializeVariables() {
		map = MapService.createMap();
		unitsOfRome = UnitService.createArmy(Nation.ROME);
		unitsOfCarthage = UnitService.createArmy(Nation.CARTHAGE);
		statistics = new SimulationStatistics();
		endingFlag = new Boolean(false);	
	}

	private void prepareOrderLists() {
		Runnable orderMakerForRome = () -> { ordersForRome = SimulationService.generateOrderList(map, Nation.ROME);};
		Runnable orderMakerForCarthage = () -> { ordersForCarthage = SimulationService.generateOrderList(map, Nation.CARTHAGE);};
		
		Thread r = new Thread(orderMakerForRome);
		Thread c= new Thread(orderMakerForCarthage);
		
		r.start();
		c.start();
	}

	public void start() {
		while( !endingFlag ){
			System.out.println("Dupa");
			iterate();
		}
	}

	private void iterate() {
		SimulationService.checkFightsOnMap(map);
		SimulationService.checkMorale(); //TODO może jako element walki
		SimulationService.moveUnits(); //MOVING -  po prostu dla jednostek dodajemy ruchy i je rozpatrujemy. Listę jednostek można sortować wg szybkości
		SimulationService.applyNewOrders();
		prepareOrderLists();
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
