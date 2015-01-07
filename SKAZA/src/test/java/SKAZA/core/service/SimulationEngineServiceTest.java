package SKAZA.core.service;

import static org.junit.Assert.*;

import java.nio.channels.SeekableByteChannel;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import SKAZA.core.SimulationEngine;
import SKAZA.core.models.map.Map;
import SKAZA.core.models.order.Order;
import SKAZA.core.models.unit.Nation;
import SKAZA.core.models.unit.Unit;
import SKAZA.core.models.unit.UnitState;
import SKAZA.core.repository.UnitArchetypeRepository;

public class SimulationEngineServiceTest {
	
	SimulationEngineService simulationEngineService;
	
	@Before
	public void setBackground(){
		simulationEngineService = new SimulationEngineService();
		UnitArchetypeRepository.initialize();
	}
	
	@Test
	public void testConstructor(){
		SimulationEngine se = new SimulationEngine();
		
		assertEquals(new Boolean(false), se.endingFlag);
	}
	@Test
	public void testSetArmiesOnMap(){
		SimulationEngine se = new SimulationEngine();
	}

	@Test
	public void testPrepareOrderLists(){
		SimulationEngine se = new SimulationEngine();
		
		assertEquals(2, se.orderListForRome.size() );
		assertEquals(3, se.orderListForCarthage.size() );
	}
	 
	@Test
	public void testPrepareOrderLists2(){
		SimulationEngine se = new SimulationEngine();
		
		
		simulationEngineService.prepareOrderLists(se);
		
		assertEquals(0, se.orderListForRome.size() );
	}
	
	@Test
	public void testApplyNewOrders(){
		SimulationEngine se = new SimulationEngine();
		
		se.orderOfRome = new Order(se.map.matrix[0][0], se.map.matrix[0][1]);
		
		simulationEngineService.applyNewOrders(se.map, se.orderOfRome, null);

	}
	
	@Test
	public void testMoveUnits(){
		SimulationEngine se = new SimulationEngine();
		
		se.orderOfRome = new Order(se.map.matrix[0][0], se.map.matrix[0][1]);
		
		simulationEngineService.applyNewOrders(se.map, se.orderOfRome, null);
		simulationEngineService.moveUnits(se.map);

	}
	
	
	@Test
	public void testMoveUnits2(){
		SimulationEngine se = new SimulationEngine();
		
		se.orderOfRome = new Order(se.map.matrix[0][0], se.map.matrix[0][1]);
		
		simulationEngineService.applyNewOrders(se.map, se.orderOfRome, null);
	}
	
	@Test
	public void testCheckFightsOnMap(){	
		SimulationEngine se = new SimulationEngine();
		
		
	}
}
