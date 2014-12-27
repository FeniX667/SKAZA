package SKAZA.core.service;

import static org.junit.Assert.*;

import java.nio.channels.SeekableByteChannel;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import SKAZA.core.SimulationEngine;
import SKAZA.core.models.map.Map;
import SKAZA.core.models.unit.Nation;
import SKAZA.core.models.unit.Unit;
import SKAZA.core.models.unit.UnitState;
import SKAZA.core.repository.UnitArchetypeRepository;

public class SimulationEngineServiceTest {

	SimulationEngine se;
	
	@Before
	public void setBackground(){
		UnitArchetypeRepository.initialize();
		
		se = new SimulationEngine();
	}

	@Test
	public void testConstructor(){
		assertEquals(new Boolean(false), se.endingFlag);
		assertNotNull(se.unitsOfCarthage);
		assertNotNull(se.unitsOfRome);
		assertTrue( se.unitsOfCarthage.size() > 0 );
		assertTrue( se.unitsOfRome.size() > 0 );
	}

	@Test
	public void testSetArmiesOnMap(){
		
		assertTrue( se.map.matrix[0][0].unit.equals(se.unitsOfRome.get(0)) );
	}
	
	@Test
	public void testIsUnitUnderAttack(){	
		Unit roman = se.unitsOfRome.get(0);
		Unit carthaginian = se.unitsOfCarthage.get(0);
		
		roman.setFightingWith(carthaginian);
		roman.setState(UnitState.FIGTHING);
		
		assertTrue( SimulationService.isUnitUnderAttack(roman, carthaginian) );
		assertFalse( SimulationService.isUnitUnderAttack(carthaginian, roman) );

	}

	@Test
	public void testCheckFightsOnMap(){	
		Unit roman = se.unitsOfRome.get(0);
		Unit carthaginian = se.unitsOfCarthage.get(0);
		
		roman.setFightingWith(carthaginian);
		roman.setState(UnitState.FIGTHING);
	
		carthaginian.setFightingWith(roman);
		carthaginian.setState(UnitState.FIGTHING);
		
		SimulationService.checkFightsOnMap(se.map);
		
		assertTrue( roman.getNrOfSoldiers() < 200);
		assertTrue( carthaginian.getNrOfSoldiers() < 200);
		
		System.out.println( roman.getNrOfSoldiers() );
		System.out.println( carthaginian.getNrOfSoldiers() );
	}
}
