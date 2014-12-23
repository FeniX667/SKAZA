package SKAZA.core;

import junit.framework.TestCase;

public class SimulationEngineTest extends TestCase {

	public void testInitialization(){
		SimulationEngine se = new SimulationEngine();
		
		assertEquals(new Boolean(false), se.endingFlag);
		assertNotNull(se.unitsOfCarthage);
		assertNotNull(se.unitsOfRome);
		assertTrue( se.unitsOfCarthage.size() > 0 );
		assertTrue( se.unitsOfRome.size() > 0 );
		
		
	}
}
