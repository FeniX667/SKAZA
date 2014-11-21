package SKAZA.core.service;

import junit.framework.TestCase;
import SKAZA.core.math.constants.MapConstants;
import SKAZA.core.models.map.Cell;
import SKAZA.core.service.CellService;

public class CellServiceTest extends TestCase {
	
	public void testCreateCell(){
		Cell cell;
		
		cell = CellService.createCell( MapConstants.numberOfUpperCornerNeighbours );
		
		assertNotNull(cell);
		assertNotNull(cell.isUnitPresent);
		assertNotNull(cell.neighbours);
		assertEquals( new Byte("2"), cell.numberOfNeighbours );
		assertEquals( 2, cell.neighbours.length);
	}
}
