package SKAZA.core.math;

import org.jmock.Mockery;

import SKAZA.core.math.calculators.FightCalculator;
import SKAZA.core.math.constants.FightConstants;
import SKAZA.core.models.unit.Nation;
import SKAZA.core.models.unit.Unit;
import SKAZA.core.models.unit.UnitArchetype;
import SKAZA.core.service.UnitArchetypeService;
import SKAZA.core.service.UnitService;
import junit.framework.TestCase;

public class FightCalculatorTest extends TestCase {
	
    public void testCalculateTotalDamage(){
    	UnitArchetype halberdier = UnitArchetypeService.createHalberdier();
    	Unit attackingUnit = UnitService.createUnit(Nation.CARTHAGE, halberdier);
    	Unit defendingUnit = UnitService.createUnit(Nation.ROME, halberdier);
    	attackingUnit.setNrOfSoldiers(500);
    	defendingUnit.setNrOfSoldiers(1000);
    	
    	Integer totalDamage = FightCalculator.calculateTotalDamage(attackingUnit, defendingUnit);
    			
    	assertEquals( new Integer(1575), totalDamage );
		
    }
   
}
