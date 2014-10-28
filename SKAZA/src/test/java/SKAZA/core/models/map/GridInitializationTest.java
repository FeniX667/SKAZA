package SKAZA.core.models.map;

import junit.framework.TestCase;

public class GridInitializationTest extends TestCase {

	public void testNumberOfCellsNeighbours(){
		Grid grid = new Grid();
		
		thenCornerNeighbours(grid);
		thenSideNeighbours(grid);
		thenLogAllNeighbours(grid);
	}


	
	private void thenCornerNeighbours(Grid grid) {
		Integer lastRow = grid.height - 1;
		Integer lastColumn = grid.width - 1;
		
		assertEquals(new Integer(2), grid.matrix[0][0].numberOfNeighbours);
		assertEquals(new Integer(2), grid.matrix[0][lastColumn].numberOfNeighbours);
		assertEquals(new Integer(3), grid.matrix[lastRow][lastColumn].numberOfNeighbours);
		assertEquals(new Integer(3), grid.matrix[lastRow][0].numberOfNeighbours);
	}

	private void thenSideNeighbours(Grid grid) {
		Integer lastRow = grid.height - 1;
		
		assertEquals(new Integer(3), grid.matrix[0][2].numberOfNeighbours);
		assertEquals(new Integer(3), grid.matrix[0][4].numberOfNeighbours);
		assertEquals(new Integer(5), grid.matrix[0][1].numberOfNeighbours);
		assertEquals(new Integer(5), grid.matrix[lastRow][2].numberOfNeighbours);
	}
	
	private void thenLogAllNeighbours(Grid grid){
		
		for (int h=0 ; h < grid.height ; h++){
			for (int w=0 ; w < grid.width ; w++){
				System.out.print(grid.matrix[h][w].numberOfNeighbours);
			}
			System.out.println();
		}
	}
	
	public void testNeighboursInitialization(){
		Grid grid = new Grid();

		thenCornerNeighboursAreInitialized(grid);
		thenOuterBaseNeighboursAreInitialized(grid);
		thenSideNeighbroursAreInitialized(grid);
		thenInnerBaseNeighboursAreInitialized(grid);
		thenInnerNeighboursAreInitialized(grid);
	}
	
	private void thenCornerNeighboursAreInitialized(Grid grid) {
		Cell cell = grid.matrix[0][0];
		Integer lastRow = grid.height - 1;
		Integer lastColumn = grid.width - 1;
		
		assertEquals(new Integer(5), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer(4), cell.neighbours[1].numberOfNeighbours);

		cell = grid.matrix[0][lastColumn];
		
		assertEquals(new Integer(4), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer(5), cell.neighbours[1].numberOfNeighbours);

		cell = grid.matrix[lastRow][0];
		
		assertEquals(new Integer(4), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer(6), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer(3), cell.neighbours[2].numberOfNeighbours);

		cell = grid.matrix[lastRow][lastColumn];

		assertEquals(new Integer(4), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer(3), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer(6), cell.neighbours[2].numberOfNeighbours);
		
	}
	
	private void thenOuterBaseNeighboursAreInitialized(Grid grid) {
		Cell[][] matrix = grid.matrix;
		Integer lastRow = grid.height - 1;
		Integer lastColumn = grid.width - 1;
		
		for (int w = 2 ; w < lastColumn ; w+=2 ){
			Cell cell = matrix[0][w];
			assertEquals(new Integer(5), cell.neighbours[0].numberOfNeighbours);
			assertEquals(new Integer(6), cell.neighbours[1].numberOfNeighbours);
			assertEquals(new Integer(5), cell.neighbours[2].numberOfNeighbours);
		}
		
		for (int w = 3 ; w < lastColumn-1 ; w+=2 ){
			Cell cell = matrix[lastRow][w];
			
			assertEquals(new Integer(6), cell.neighbours[0].numberOfNeighbours);
			assertEquals(new Integer(5), cell.neighbours[1].numberOfNeighbours);
			assertEquals(new Integer(5), cell.neighbours[2].numberOfNeighbours);
		}
		
		Cell cell = matrix[lastRow][1];

		assertEquals(new Integer(6), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer(5), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer(3), cell.neighbours[2].numberOfNeighbours);
		
		cell = matrix[lastRow][lastColumn-1];

		assertEquals(new Integer(6), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer(3), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer(5), cell.neighbours[2].numberOfNeighbours);		
	}

	private void thenSideNeighbroursAreInitialized(Grid grid) {
		Cell[][] matrix = grid.matrix;
		Integer lastRow = grid.height - 1;
		Integer lastColumn = grid.width - 1;
		
		Cell cell = matrix[1][0];
		assertEquals(new Integer(2), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer(5), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer(6), cell.neighbours[2].numberOfNeighbours);
		assertEquals(new Integer(4), cell.neighbours[3].numberOfNeighbours);

		cell = matrix[1][lastColumn];
		assertEquals(new Integer(2), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer(4), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer(6), cell.neighbours[2].numberOfNeighbours);
		assertEquals(new Integer(5), cell.neighbours[3].numberOfNeighbours);

		cell = matrix[lastRow-1][0];
		assertEquals(new Integer(4), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer(6), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer(6), cell.neighbours[2].numberOfNeighbours);
		assertEquals(new Integer(3), cell.neighbours[3].numberOfNeighbours);

		cell = matrix[lastRow-1][lastColumn];
		assertEquals(new Integer(4), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer(3), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer(6), cell.neighbours[2].numberOfNeighbours);
		assertEquals(new Integer(6), cell.neighbours[3].numberOfNeighbours);
		
		for (int h = 1 ; h < lastRow ; h++){
		}
	}	
	
	private void thenInnerBaseNeighboursAreInitialized(Grid grid) {
		Cell[][] matrix = grid.matrix;
		Integer lastRow = grid.height - 1;
		Integer lastColumn = grid.width - 1;
		
		Cell cell = matrix[0][1];
		assertEquals(new Integer(3), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer(6), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer(6), cell.neighbours[2].numberOfNeighbours);
		assertEquals(new Integer(4), cell.neighbours[3].numberOfNeighbours);
		assertEquals(new Integer(2), cell.neighbours[4].numberOfNeighbours);

		cell = matrix[0][lastColumn-1];
		assertEquals(new Integer(2), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer(4), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer(6), cell.neighbours[2].numberOfNeighbours);
		assertEquals(new Integer(6), cell.neighbours[3].numberOfNeighbours);
		assertEquals(new Integer(3), cell.neighbours[4].numberOfNeighbours);
		
		for (int w = 3 ; w < lastColumn-2 ; w+=2 ){
			cell = matrix[0][w];
			assertEquals(new Integer(3), cell.neighbours[0].numberOfNeighbours);
			assertEquals(new Integer(6), cell.neighbours[1].numberOfNeighbours);
			assertEquals(new Integer(6), cell.neighbours[2].numberOfNeighbours);
			assertEquals(new Integer(6), cell.neighbours[3].numberOfNeighbours);
			assertEquals(new Integer(3), cell.neighbours[4].numberOfNeighbours);
		}		
		for (int w = 2 ; w < lastColumn ; w+=2 ){
			cell = matrix[lastRow][w];
			assertEquals(new Integer(6), cell.neighbours[0].numberOfNeighbours);
			assertEquals(new Integer(6), cell.neighbours[1].numberOfNeighbours);
			assertEquals(new Integer(3), cell.neighbours[2].numberOfNeighbours);
			assertEquals(new Integer(3), cell.neighbours[3].numberOfNeighbours);
			assertEquals(new Integer(6), cell.neighbours[4].numberOfNeighbours);
		}	
	}

	private void thenInnerNeighboursAreInitialized(Grid grid) {		
		checkCornerCells(grid);
		checkTopBorderCells(grid);
		checkSideBorderCells(grid);
		checkBottomBorderCells(grid);
		checkOtherCells(grid);
	}

	private void checkCornerCells(Grid grid) {
		Cell[][] matrix = grid.matrix;
		Integer lastRow = grid.height - 1;
		Integer lastColumn = grid.width - 1;
		
		Cell cell = matrix[1][1];
		assertEquals(new Integer(5), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer(6), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer(6), cell.neighbours[2].numberOfNeighbours);
		assertEquals(new Integer(6), cell.neighbours[3].numberOfNeighbours);
		assertEquals(new Integer(4), cell.neighbours[4].numberOfNeighbours);
		assertEquals(new Integer(4), cell.neighbours[5].numberOfNeighbours);

		cell = matrix [1][lastColumn-1];
		assertEquals(new Integer(5), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer(4), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer(4), cell.neighbours[2].numberOfNeighbours);
		assertEquals(new Integer(6), cell.neighbours[3].numberOfNeighbours);
		assertEquals(new Integer(6), cell.neighbours[4].numberOfNeighbours);
		assertEquals(new Integer(6), cell.neighbours[5].numberOfNeighbours);

		cell = matrix [lastRow-1][lastColumn-1];
		assertEquals(new Integer(6), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer(4), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer(3), cell.neighbours[2].numberOfNeighbours);
		assertEquals(new Integer(3), cell.neighbours[3].numberOfNeighbours);
		assertEquals(new Integer(5), cell.neighbours[4].numberOfNeighbours);
		assertEquals(new Integer(6), cell.neighbours[5].numberOfNeighbours);
		
		cell = matrix [lastRow-1][1];
		assertEquals(new Integer(6), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer(6), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer(5), cell.neighbours[2].numberOfNeighbours);
		assertEquals(new Integer(3), cell.neighbours[3].numberOfNeighbours);
		assertEquals(new Integer(3), cell.neighbours[4].numberOfNeighbours);
		assertEquals(new Integer(4), cell.neighbours[5].numberOfNeighbours);
	}

	private void checkTopBorderCells(Grid grid) {
		Cell[][] matrix = grid.matrix;
		Integer lastRow = grid.height - 1;
		Integer lastColumn = grid.width - 1;
		
		for (int w = 2 ; w < lastColumn-2 ; w+=2){
			Cell cell = matrix[1][w];
			
			assertEquals(new Integer(3), cell.neighbours[0].numberOfNeighbours);
			assertEquals(new Integer(5), cell.neighbours[1].numberOfNeighbours);
			assertEquals(new Integer(6), cell.neighbours[2].numberOfNeighbours);
			assertEquals(new Integer(6), cell.neighbours[3].numberOfNeighbours);
			assertEquals(new Integer(6), cell.neighbours[4].numberOfNeighbours);
			assertEquals(new Integer(5), cell.neighbours[5].numberOfNeighbours);
		}
		
		for (int w = 3 ; w < lastColumn-2 ; w+=2){
			Cell cell = matrix[1][w];
			
			assertEquals(new Integer(5), cell.neighbours[0].numberOfNeighbours);
			assertEquals(new Integer(6), cell.neighbours[1].numberOfNeighbours);
			assertEquals(new Integer(6), cell.neighbours[2].numberOfNeighbours);
			assertEquals(new Integer(6), cell.neighbours[3].numberOfNeighbours);
			assertEquals(new Integer(6), cell.neighbours[4].numberOfNeighbours);
			assertEquals(new Integer(6), cell.neighbours[5].numberOfNeighbours);
		}
	}

	private void checkSideBorderCells(Grid grid) {
		Cell[][] matrix = grid.matrix;
		Integer lastRow = grid.height - 1;
		Integer lastColumn = grid.width - 1;
		
		for (int h = 2 ; h < lastRow-2 ; h++){
			Cell cell = matrix[h][1];
			
			assertEquals(new Integer(6), cell.neighbours[0].numberOfNeighbours);
			assertEquals(new Integer(6), cell.neighbours[1].numberOfNeighbours);
			assertEquals(new Integer(6), cell.neighbours[2].numberOfNeighbours);
			assertEquals(new Integer(6), cell.neighbours[3].numberOfNeighbours);
			assertEquals(new Integer(4), cell.neighbours[4].numberOfNeighbours);
			assertEquals(new Integer(4), cell.neighbours[5].numberOfNeighbours);
		}
		
		for (int h = 2 ; h < lastRow-2 ; h++){
			Cell cell = matrix[h][lastColumn-1];
			
			assertEquals(new Integer(6), cell.neighbours[0].numberOfNeighbours);
			assertEquals(new Integer(4), cell.neighbours[1].numberOfNeighbours);
			assertEquals(new Integer(4), cell.neighbours[2].numberOfNeighbours);
			assertEquals(new Integer(6), cell.neighbours[3].numberOfNeighbours);
			assertEquals(new Integer(6), cell.neighbours[4].numberOfNeighbours);
			assertEquals(new Integer(6), cell.neighbours[5].numberOfNeighbours);
		}
	}
	
	private void checkBottomBorderCells(Grid grid) {
		Cell[][] matrix = grid.matrix;
		Integer lastRow = grid.height - 1;
		Integer lastColumn = grid.width - 1;
		
		for (int w = 2 ; w < lastColumn-2 ; w+=2){
			Cell cell = matrix[lastRow-1][w];
			
			assertEquals(new Integer(6), cell.neighbours[0].numberOfNeighbours);
			assertEquals(new Integer(6), cell.neighbours[1].numberOfNeighbours);
			assertEquals(new Integer(6), cell.neighbours[2].numberOfNeighbours);
			assertEquals(new Integer(5), cell.neighbours[3].numberOfNeighbours);
			assertEquals(new Integer(6), cell.neighbours[4].numberOfNeighbours);
			assertEquals(new Integer(6), cell.neighbours[5].numberOfNeighbours);
		}
		
		for (int w = 3 ; w < lastColumn-2 ; w+=2){
			Cell cell = matrix[lastRow-1][w];
			
			assertEquals(new Integer(6), cell.neighbours[0].numberOfNeighbours);
			assertEquals(new Integer(6), cell.neighbours[1].numberOfNeighbours);
			assertEquals(new Integer(5), cell.neighbours[2].numberOfNeighbours);
			assertEquals(new Integer(3), cell.neighbours[3].numberOfNeighbours);
			assertEquals(new Integer(5), cell.neighbours[4].numberOfNeighbours);
			assertEquals(new Integer(6), cell.neighbours[5].numberOfNeighbours);
		}
	}

	private void checkOtherCells(Grid grid) {
		Cell[][] matrix = grid.matrix;
		Integer lastRow = grid.height - 1;
		Integer lastColumn = grid.width - 1;
		
		for (int h = 2 ; h < lastRow-2 ; h++){
			for (int w = 2 ; w < lastColumn-2 ; w++){
				Cell cell = matrix[h][w];
				
				assertEquals(new Integer(6), cell.neighbours[0].numberOfNeighbours);
				assertEquals(new Integer(6), cell.neighbours[1].numberOfNeighbours);
				assertEquals(new Integer(6), cell.neighbours[2].numberOfNeighbours);
				assertEquals(new Integer(6), cell.neighbours[3].numberOfNeighbours);
				assertEquals(new Integer(6), cell.neighbours[4].numberOfNeighbours);
				assertEquals(new Integer(6), cell.neighbours[5].numberOfNeighbours);
			}
		}
	}
}
