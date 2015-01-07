package SKAZA.core.service;

import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.TestCase;
import SKAZA.core.math.constants.MapConstants;
import SKAZA.core.models.map.Cell;
import SKAZA.core.repository.UnitArchetypeRepository;
import SKAZA.core.service.CellService;

public class CellServiceTest {
	
	@Test
	public void testCreateCell(){
		Cell cell;
		CellService cellService = new CellService();
		
		cell = cellService.createCell( MapConstants.numberOfUpperCornerNeighbours, 0, 0 );
		
		assertNotNull(cell);
		assertNotNull(cell.neighbours);
		assertNotNull(cell.coordinates);
		assertEquals(0, cell.coordinates.x);
		assertEquals(0, cell.coordinates.y);
		assertEquals( new Integer(2), cell.numberOfNeighbours );
		assertEquals( 2, cell.neighbours.length);
	}
}
