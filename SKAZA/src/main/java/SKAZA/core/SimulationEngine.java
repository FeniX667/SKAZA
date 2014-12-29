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
import SKAZA.core.service.SimulationEngineService;
import SKAZA.core.service.UnitService;

public class SimulationEngine {
	public Map map;
	
	public ArrayList<Unit> unitsOfRome;
	public ArrayList<Unit> unitsOfCarthage;
	public ArrayList<Order> orderListForRome;
	public ArrayList<Order> orderListForCarthage;
	public Order orderOfRome;
	public Order orderOfCarthage;
	
	public Integer iteration;
	public SimulationStatistics statistics;
	public Boolean endingFlag;

	public SimulationEngine(){	
		SimulationEngineService.initializeVariables(this);
		SimulationEngineService.setArmiesOnMap(map, unitsOfRome, unitsOfCarthage);	
		SimulationEngineService.prepareOrderLists(this);
	}

	public void start() {
		while( !endingFlag ){
			System.out.println("Koniec");
			iterate();
		}
	}

	private void iterate() {
		SimulationEngineService.resolveFightsOnMap(map);
		SimulationEngineService.moveUnits(map); //MOVING -  po prostu dla jednostek dodajemy ruchy i je rozpatrujemy. Listę jednostek można sortować wg szybkości
		SimulationEngineService.applyNewOrders(map, orderOfRome, orderOfCarthage);
		SimulationEngineService.prepareOrderLists(this);
		SimulationEngineService.checkIfCompleted(this);
	}

	public SimulationStatistics getStatistics() {
		//TODO statystyki symulacji
		return statistics;
	}
}
