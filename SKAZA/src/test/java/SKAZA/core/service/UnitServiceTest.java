package SKAZA.core.service;

import SKAZA.core.math.constants.MapConstants;
import SKAZA.core.models.map.Cell;
import SKAZA.core.models.unit.Nation;
import SKAZA.core.models.unit.Unit;
import SKAZA.core.models.unitArchetype.UnitArchetype;
import junit.framework.TestCase;

public class UnitServiceTest extends TestCase{
	
	public void testCreateUnit(){
		Unit unit;
		
		UnitArchetype halberdier = UnitArchetypeService.createHalberdier();
		
		unit = UnitService.createUnit( Nation.CARTHAGE, halberdier );
		
		assertNotNull(unit);
		assertNotNull(unit.getArchetype());
		assertNotNull(unit.getMorale());
		assertNotNull(unit.getDistanceTravelled());
		assertNotNull(unit.getNation());
		assertNotNull(unit.getNrOfSoldiers());
		assertNotNull(unit.getOrientation());
		assertNotNull(unit.getState());
	}
}
