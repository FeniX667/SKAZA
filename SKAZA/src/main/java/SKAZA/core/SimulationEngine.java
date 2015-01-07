package SKAZA.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import SKAZA.core.models.map.Map;
import SKAZA.core.models.order.Order;
import SKAZA.core.models.unit.Nation;
import SKAZA.core.models.unit.Unit;
import SKAZA.core.models.unitArchetype.UnitArchetype;
import SKAZA.core.service.SimulationEngineService;

public class SimulationEngine {
	public Map map;
	public SimulationEngineService simulationEngineService;
	
	public List<Order> orderListForRome;
	public List<Order> orderListForCarthage;
	public Order orderOfRome;
	public Order orderOfCarthage;
	
	public Integer iteration;
	public Long speedLimiter;
	
	public SimulationStatistics statisticsForRome;
	public SimulationStatistics statisticsForCarthage;
	public Nation winner;
	public Boolean endingFlag;

	public SimulationEngine(){
		simulationEngineService = new SimulationEngineService();
		simulationEngineService.initializeVariables(this);
		simulationEngineService.setArmiesOnMap(map);	
		simulationEngineService.prepareOrderLists(this);
	}
	
	public SimulationEngine( List<UnitArchetype> generatedArchetypes ){
		simulationEngineService = new SimulationEngineService(generatedArchetypes);
		simulationEngineService.initializeVariables(this);
		simulationEngineService.setArmiesOnMap(map);	
		simulationEngineService.prepareOrderLists(this);
	}

	public SimulationEngine(SimulationEngine simulationEngine) {
		simulationEngineService = new SimulationEngineService();
		simulationEngineService.copyVariables(this, simulationEngine);
		simulationEngineService.prepareOrderLists(this);
	}

	public void run() {
		while( !endingFlag ){
			iterate();
			if( speedLimiter > 0){
				try {
					Thread.sleep(speedLimiter);
				} catch (InterruptedException e) {continue;	}				
			}
		}
		
		System.out.println(winner);
	}

	public void iterate() {
		simulationEngineService.resolveFightsOnMap(map);
		simulationEngineService.moveUnits(map);		
		simulationEngineService.applyNewOrders(map, orderOfRome, orderOfCarthage);
		simulationEngineService.prepareOrderLists(this);
		endingFlag = simulationEngineService.checkIfCompleted(this);
		iteration++;
	}

	public SimulationStatistics getStatistics(Nation nation) {
		if( nation ==  Nation.ROME ){			
			return statisticsForRome = simulationEngineService.generateStatisticsFor(this, nation);
		}
		else if( nation == Nation.CARTHAGE ){
			return statisticsForCarthage = simulationEngineService.generateStatisticsFor(this, nation);
		}
		return null;
	}
	
	public SimulationEngine getClone(){
		return new SimulationEngine(this);
	}
}
