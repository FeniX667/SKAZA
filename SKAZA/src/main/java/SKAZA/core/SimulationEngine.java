package SKAZA.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import SKAZA.core.models.map.Map;
import SKAZA.core.models.order.Order;
import SKAZA.core.models.unit.Nation;
import SKAZA.core.models.unit.Unit;
import SKAZA.core.service.SimulationEngineService;

public class SimulationEngine {
	public Map map;
	
	public List<Order> orderListForRome;
	public List<Order> orderListForCarthage;
	public Order orderOfRome;
	public Order orderOfCarthage;
	
	public Integer iteration;
	public Long speedLimiter;
	
	public SimulationStatistics statistics;
	public Nation winner;
	public Boolean endingFlag;

	public SimulationEngine(){	
		SimulationEngineService.initializeVariables(this);
		SimulationEngineService.setArmiesOnMap(map);	
		SimulationEngineService.prepareOrderLists(this);
	}

	public SimulationEngine(SimulationEngine simulationEngine) {
		SimulationEngineService.copyVariables(this, simulationEngine);
		SimulationEngineService.prepareOrderLists(this);
	}

	public void run() {
		while( !endingFlag ){
			iterate();
			/*
			System.out.println(orderOfRome);
			System.out.println(orderListForRome);
			System.out.println(orderOfCarthage);
			System.out.println(orderListForCarthage);
			*/
			if( speedLimiter > 0){
				try {
					Thread.sleep(speedLimiter);
				} catch (InterruptedException e) {continue;	}				
			}
		}
	}

	public void iterate() {
		SimulationEngineService.resolveFightsOnMap(map);
		SimulationEngineService.moveUnits(map);		
		SimulationEngineService.applyNewOrders(map, orderOfRome, orderOfCarthage);
		SimulationEngineService.prepareOrderLists(this);
		endingFlag = SimulationEngineService.checkIfCompleted(map, winner);
		iteration++;
	}

	public SimulationStatistics getStatistics() {
		//TODO statystyki symulacji
		return statistics;
	}
	
	public SimulationEngine getClone(){
		return new SimulationEngine(this);
	}
}
