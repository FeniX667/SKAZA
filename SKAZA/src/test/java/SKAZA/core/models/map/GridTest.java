package skaza.core.models.map;

import junit.framework.TestCase;

public class GridTest extends TestCase {

	public void testNumberOfCellsNeighbours(){
		Grid grid = new Grid();
		
		
		thenCornerNeighbours(grid);
		thenSideNeighbours(grid);
	}

	private void thenCornerNeighbours(Grid grid) {
		Integer height = grid.height;
		Integer width = grid.width;
		assertEquals(new Integer(2), grid.matrix[0][0].numberOfNeighbours);
		assertEquals(new Integer(2), grid.matrix[0][width-1].numberOfNeighbours);
		assertEquals(new Integer(3), grid.matrix[height-1][width-1].numberOfNeighbours);
		assertEquals(new Integer(3), grid.matrix[height-1][0].numberOfNeighbours);
	}

	private void thenSideNeighbours(Grid grid) {
		Integer height = grid.height;
		Integer width = grid.width;
		assertEquals(new Integer(3), grid.matrix[0][2].numberOfNeighbours);
		assertEquals(new Integer(3), grid.matrix[0][4].numberOfNeighbours);
		assertEquals(new Integer(5), grid.matrix[0][1].numberOfNeighbours);
		assertEquals(new Integer(5), grid.matrix[height-1][2].numberOfNeighbours);
	}
}
