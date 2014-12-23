package SKAZA.core.service;

import junit.framework.TestCase;
import SKAZA.core.math.constants.MapConstants;
import SKAZA.core.models.map.Cell;
import SKAZA.core.service.CellService;

public class CellServiceTest extends TestCase {
	
	public void testCreateCell(){
		Cell cell;
		
		cell = CellService.createCell( MapConstants.numberOfUpperCornerNeighbours, 0, 0 );
		
		assertNotNull(cell);
		assertNotNull(cell.neighbours);
		assertNotNull(cell.coordinates);
		assertEquals(0, cell.coordinates.x);
		assertEquals(0, cell.coordinates.y);
		assertEquals( new Byte("2"), cell.numberOfNeighbours );
		assertEquals( 2, cell.neighbours.length);
	}
}
