package SKAZA.core.service;

import static org.junit.Assert.*;

import org.junit.Test;

import SKAZA.core.math.constants.MapConstants;
import SKAZA.core.models.map.Cell;
import SKAZA.core.models.unit.Nation;
import SKAZA.core.models.unit.Unit;
import SKAZA.core.models.unitArchetype.UnitArchetype;
import junit.framework.TestCase;

public class UnitServiceTest {
	
	@Test
	public void testCreateUnit(){
		Unit unit;
		UnitService unitService = new UnitService();
		
		UnitArchetype halberdier = UnitArchetypeService.createHalberdier();
		
		unit = unitService.createUnit( Nation.CARTHAGE, halberdier, 200 );
		
		assertNotNull(unit);
		assertNotNull(unit.getArchetype());
		assertNotNull(unit.getMorale());
		assertNotNull(unit.getDistanceTravelled());
		assertNotNull(unit.getNation());
		assertNotNull(unit.getNrOfSoldiers());
		assertNotNull(unit.getState());
	}
}
