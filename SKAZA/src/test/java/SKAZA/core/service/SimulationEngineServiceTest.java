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
	
	@Before
	public void setBackground(){
		UnitArchetypeRepository.initialize();
	}
	
	@Test
	public void testConstructor(){
		SimulationEngine se = new SimulationEngine();
		
		assertEquals(new Boolean(false), se.endingFlag);
		assertNotNull(se.unitsOfCarthage);
		assertNotNull(se.unitsOfRome);
		assertTrue( se.unitsOfCarthage.size() > 0 );
		assertTrue( se.unitsOfRome.size() > 0 );
	}
	@Test
	public void testSetArmiesOnMap(){
		SimulationEngine se = new SimulationEngine();
		assertTrue( se.map.matrix[0][0].unit.equals(se.unitsOfRome.get(0)) );
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
		
		se.unitsOfRome.get(0).setDestination(se.map.matrix[1][0]);
		se.unitsOfRome.get(0).setState(UnitState.FIGTHING);
		
		SimulationEngineService.prepareOrderLists(se);
		
		assertEquals(0, se.orderListForRome.size() );
	}
	
	@Test
	public void testApplyNewOrders(){
		SimulationEngine se = new SimulationEngine();
		
		se.orderOfRome = new Order(se.map.matrix[0][0], se.map.matrix[0][1]);
		Unit roman = se.unitsOfRome.get(0);
		
		SimulationEngineService.applyNewOrders(se.map, se.orderOfRome, null);

		assertEquals( se.map.matrix[0][1], roman.getDestination() );
	}
	
	@Test
	public void testMoveUnits(){
		SimulationEngine se = new SimulationEngine();
		
		se.orderOfRome = new Order(se.map.matrix[0][0], se.map.matrix[0][1]);
		Unit roman = se.unitsOfRome.get(0);
		
		SimulationEngineService.applyNewOrders(se.map, se.orderOfRome, null);
		SimulationEngineService.moveUnits(se.map);

		assertEquals( roman.getArchetype().getSpeed(), roman.getDistanceTravelled() );
	}
	
	
	@Test
	public void testMoveUnits2(){
		SimulationEngine se = new SimulationEngine();
		
		se.orderOfRome = new Order(se.map.matrix[0][0], se.map.matrix[0][1]);
		Unit roman = se.unitsOfRome.get(0);
		
		SimulationEngineService.applyNewOrders(se.map, se.orderOfRome, null);
		roman.setDistanceTravelled(100);
		SimulationEngineService.moveUnits(se.map);

		assertEquals( UnitState.IDLE, roman.getState() );
		assertTrue( se.map.matrix[0][1].unit.equals(roman));
	}
	
	@Test
	public void testCheckFightsOnMap(){	
		SimulationEngine se = new SimulationEngine();
		
		Unit roman = se.unitsOfRome.get(0);
		Unit carthaginian = se.unitsOfCarthage.get(0);
		
		roman.setDestination( se.map.matrix[0][10] );
		roman.setState(UnitState.FIGTHING);
	
		carthaginian.setDestination( se.map.matrix[0][0] );
		carthaginian.setState(UnitState.FIGTHING);
		
		SimulationEngineService.resolveFightsOnMap(se.map);
		
		assertTrue( roman.getNrOfSoldiers() < 200);
		assertTrue( carthaginian.getNrOfSoldiers() < 200);
	}
}
