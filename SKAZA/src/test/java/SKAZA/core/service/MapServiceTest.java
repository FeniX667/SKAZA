package SKAZA.core.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import SKAZA.core.math.constants.MapConstants;
import SKAZA.core.models.map.Cell;
import SKAZA.core.models.map.Map;
import SKAZA.core.service.MapService;
import junit.framework.TestCase;

public class MapServiceTest {
	
	MapService mapService;
	
	@Before
	public void init(){
		mapService = new MapService();
	}

	@Test
	public void testCreateUnit(){
		Map map;
		
		map = mapService.createMap();
		
		assertNotNull(map);
		assertNotNull(map.height);
		assertNotNull(map.width);
		assertNotNull(map.lastRow);
		assertNotNull(map.lastColumn);
		assertNotNull(map.matrix);
		assertEquals( MapConstants.gridHeight, map.height );
	}
	
	@Test
	public void testNumberOfCellsNeighbours(){
		Map map = mapService.createMap();
		
		thenCornerNeighbours(map);
		thenSideNeighbours(map);
		thenLogAllNeighbours(map);
	}
	
	private void thenCornerNeighbours(Map map) {
		Integer lastRow = (map.height - 1);
		Integer lastColumn = (map.width - 1);
		
		assertEquals(new Integer("2"), map.matrix[0][0].numberOfNeighbours);
		assertEquals(new Integer("2"), map.matrix[0][lastColumn].numberOfNeighbours);
		assertEquals(new Integer("3"), map.matrix[lastRow][lastColumn].numberOfNeighbours);
		assertEquals(new Integer("3"), map.matrix[lastRow][0].numberOfNeighbours);
	}

	private void thenSideNeighbours(Map map) {
		Integer lastRow = (map.height - 1);
		
		assertEquals(new Integer("3"), map.matrix[0][2].numberOfNeighbours);
		assertEquals(new Integer("3"), map.matrix[0][4].numberOfNeighbours);
		assertEquals(new Integer("5"), map.matrix[0][1].numberOfNeighbours);
		assertEquals(new Integer("5"), map.matrix[lastRow][2].numberOfNeighbours);
	}
	
	private void thenLogAllNeighbours(Map map){
		
		for (int h=0 ; h < map.height ; h++){
			for (int w=0 ; w < map.width ; w++){
				System.out.print(map.matrix[h][w].numberOfNeighbours);
			}
			System.out.println();
		}
	}
	
	@Test
	public void testNeighboursInitialization(){
		Map map = mapService.createMap();

		thenCornerNeighboursAreInitialized(map);
		thenOuterBaseNeighboursAreInitialized(map);
		thenSideNeighbroursAreInitialized(map);
		thenInnerBaseNeighboursAreInitialized(map);
		thenInnerNeighboursAreInitialized(map);
	}
	
	private void thenCornerNeighboursAreInitialized(Map map) {
		Cell cell = map.matrix[0][0];
		Integer lastRow =  (map.height - 1);
		Integer lastColumn =  (map.width - 1);
		
		assertEquals(new Integer(5), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer("4"), cell.neighbours[1].numberOfNeighbours);

		cell = map.matrix[0][lastColumn];
		
		assertEquals(new Integer("4"), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer("5"), cell.neighbours[1].numberOfNeighbours); 	

		cell = map.matrix[lastRow][0];
		
		assertEquals(new Integer("4"), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer("6"), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer("3"), cell.neighbours[2].numberOfNeighbours);

		cell = map.matrix[lastRow][lastColumn];

		assertEquals(new Integer("4"), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer("3"), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer("6"), cell.neighbours[2].numberOfNeighbours);
		
	}
	
	private void thenOuterBaseNeighboursAreInitialized(Map map) {
		Cell[][] matrix = map.matrix;
		Integer lastRow = (map.height - 1);
		Integer lastColumn = (map.width - 1);
		
		for (int w = 2 ; w < lastColumn ; w+=2 ){
			Cell cell = matrix[0][w];
			assertEquals(new Integer("5"), cell.neighbours[0].numberOfNeighbours);
			assertEquals(new Integer("6"), cell.neighbours[1].numberOfNeighbours);
			assertEquals(new Integer("5"), cell.neighbours[2].numberOfNeighbours);
		}
		
		for (int w = 3 ; w < lastColumn-1 ; w+=2 ){
			Cell cell = matrix[lastRow][w];
			
			assertEquals(new Integer("6"), cell.neighbours[0].numberOfNeighbours);
			assertEquals(new Integer("5"), cell.neighbours[1].numberOfNeighbours);
			assertEquals(new Integer("5"), cell.neighbours[2].numberOfNeighbours);
		}
		
		Cell cell = matrix[lastRow][1];

		assertEquals(new Integer("6"), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer("5"), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer("3"), cell.neighbours[2].numberOfNeighbours);
		
		cell = matrix[lastRow][lastColumn-1];

		assertEquals(new Integer("6"), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer("3"), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer("5"), cell.neighbours[2].numberOfNeighbours);		
	}

	private void thenSideNeighbroursAreInitialized(Map map) {
		Cell[][] matrix = map.matrix;
		Integer lastRow = (map.height - 1);
		Integer lastColumn = (map.width - 1);
		
		Cell cell = matrix[1][0];
		assertEquals(new Integer("2"), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer("5"), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer("6"), cell.neighbours[2].numberOfNeighbours);
		assertEquals(new Integer("4"), cell.neighbours[3].numberOfNeighbours);

		cell = matrix[1][lastColumn];
		assertEquals(new Integer("2"), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer("4"), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer("6"), cell.neighbours[2].numberOfNeighbours);
		assertEquals(new Integer("5"), cell.neighbours[3].numberOfNeighbours);

		cell = matrix[lastRow-1][0];
		assertEquals(new Integer("4"), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer("6"), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer("6"), cell.neighbours[2].numberOfNeighbours);
		assertEquals(new Integer("3"), cell.neighbours[3].numberOfNeighbours);

		cell = matrix[lastRow-1][lastColumn];
		assertEquals(new Integer("4"), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer("3"), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer("6"), cell.neighbours[2].numberOfNeighbours);
		assertEquals(new Integer("6"), cell.neighbours[3].numberOfNeighbours);
		
		for (int h = 1 ; h < lastRow ; h++){
		}
	}	
	
	private void thenInnerBaseNeighboursAreInitialized(Map map) {
		Cell[][] matrix = map.matrix;
		Integer lastRow =  (map.height - 1);
		Integer lastColumn =  (map.width - 1);
		
		Cell cell = matrix[0][1];
		assertEquals(new Integer("3"), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer("6"), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer("6"), cell.neighbours[2].numberOfNeighbours);
		assertEquals(new Integer("4"), cell.neighbours[3].numberOfNeighbours);
		assertEquals(new Integer("2"), cell.neighbours[4].numberOfNeighbours);

		cell = matrix[0][lastColumn-1];
		assertEquals(new Integer("2"), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer("4"), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer("6"), cell.neighbours[2].numberOfNeighbours);
		assertEquals(new Integer("6"), cell.neighbours[3].numberOfNeighbours);
		assertEquals(new Integer("3"), cell.neighbours[4].numberOfNeighbours);
		
		for (int w = 3 ; w < lastColumn-2 ; w+=2 ){
			cell = matrix[0][w];
			assertEquals(new Integer("3"), cell.neighbours[0].numberOfNeighbours);
			assertEquals(new Integer("6"), cell.neighbours[1].numberOfNeighbours);
			assertEquals(new Integer("6"), cell.neighbours[2].numberOfNeighbours);
			assertEquals(new Integer("6"), cell.neighbours[3].numberOfNeighbours);
			assertEquals(new Integer("3"), cell.neighbours[4].numberOfNeighbours);
		}		
		for (int w = 2 ; w < lastColumn ; w+=2 ){
			cell = matrix[lastRow][w];
			assertEquals(new Integer("6"), cell.neighbours[0].numberOfNeighbours);
			assertEquals(new Integer("6"), cell.neighbours[1].numberOfNeighbours);
			assertEquals(new Integer("3"), cell.neighbours[2].numberOfNeighbours);
			assertEquals(new Integer("3"), cell.neighbours[3].numberOfNeighbours);
			assertEquals(new Integer("6"), cell.neighbours[4].numberOfNeighbours);
		}	
	}

	private void thenInnerNeighboursAreInitialized(Map map) {		
		checkCornerCells(map);
		checkTopBorderCells(map);
		checkSideBorderCells(map);
		checkBottomBorderCells(map);
		checkOtherCells(map);
	}

	private void checkCornerCells(Map map) {
		Cell[][] matrix = map.matrix;
		Integer lastRow =  (map.height - 1);
		Integer lastColumn = (map.width - 1);
		
		Cell cell = matrix[1][1];
		assertEquals(new Integer("5"), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer("6"), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer("6"), cell.neighbours[2].numberOfNeighbours);
		assertEquals(new Integer("6"), cell.neighbours[3].numberOfNeighbours);
		assertEquals(new Integer("4"), cell.neighbours[4].numberOfNeighbours);
		assertEquals(new Integer("4"), cell.neighbours[5].numberOfNeighbours);

		cell = matrix [1][lastColumn-1];
		assertEquals(new Integer("5"), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer("4"), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer("4"), cell.neighbours[2].numberOfNeighbours);
		assertEquals(new Integer("6"), cell.neighbours[3].numberOfNeighbours);
		assertEquals(new Integer("6"), cell.neighbours[4].numberOfNeighbours);
		assertEquals(new Integer("6"), cell.neighbours[5].numberOfNeighbours);

		cell = matrix [lastRow-1][lastColumn-1];
		assertEquals(new Integer("6"), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer("4"), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer("3"), cell.neighbours[2].numberOfNeighbours);
		assertEquals(new Integer("3"), cell.neighbours[3].numberOfNeighbours);
		assertEquals(new Integer("5"), cell.neighbours[4].numberOfNeighbours);
		assertEquals(new Integer("6"), cell.neighbours[5].numberOfNeighbours);
		
		cell = matrix [lastRow-1][1];
		assertEquals(new Integer("6"), cell.neighbours[0].numberOfNeighbours);
		assertEquals(new Integer("6"), cell.neighbours[1].numberOfNeighbours);
		assertEquals(new Integer("5"), cell.neighbours[2].numberOfNeighbours);
		assertEquals(new Integer("3"), cell.neighbours[3].numberOfNeighbours);
		assertEquals(new Integer("3"), cell.neighbours[4].numberOfNeighbours);
		assertEquals(new Integer("4"), cell.neighbours[5].numberOfNeighbours);
	}

	private void checkTopBorderCells(Map map) {
		Cell[][] matrix = map.matrix;
		Integer lastRow = (map.height - 1);
		Integer lastColumn = (map.width - 1);
		
		for (int w = 2 ; w < lastColumn-2 ; w+=2){
			Cell cell = matrix[1][w];
			
			assertEquals(new Integer("3"), cell.neighbours[0].numberOfNeighbours);
			assertEquals(new Integer("5"), cell.neighbours[1].numberOfNeighbours);
			assertEquals(new Integer("6"), cell.neighbours[2].numberOfNeighbours);
			assertEquals(new Integer("6"), cell.neighbours[3].numberOfNeighbours);
			assertEquals(new Integer("6"), cell.neighbours[4].numberOfNeighbours);
			assertEquals(new Integer("5"), cell.neighbours[5].numberOfNeighbours);
		}
		
		for (int w = 3 ; w < lastColumn-2 ; w+=2){
			Cell cell = matrix[1][w];
			
			assertEquals(new Integer("5"), cell.neighbours[0].numberOfNeighbours);
			assertEquals(new Integer("6"), cell.neighbours[1].numberOfNeighbours);
			assertEquals(new Integer("6"), cell.neighbours[2].numberOfNeighbours);
			assertEquals(new Integer("6"), cell.neighbours[3].numberOfNeighbours);
			assertEquals(new Integer("6"), cell.neighbours[4].numberOfNeighbours);
			assertEquals(new Integer("6"), cell.neighbours[5].numberOfNeighbours);
		}
	}

	private void checkSideBorderCells(Map map) {
		Cell[][] matrix = map.matrix;
		Integer lastRow =  (map.height - 1);
		Integer lastColumn = (map.width - 1);
		
		for (int h = 2 ; h < lastRow-2 ; h++){
			Cell cell = matrix[h][1];
			
			assertEquals(new Integer("6"), cell.neighbours[0].numberOfNeighbours);
			assertEquals(new Integer("6"), cell.neighbours[1].numberOfNeighbours);
			assertEquals(new Integer("6"), cell.neighbours[2].numberOfNeighbours);
			assertEquals(new Integer("6"), cell.neighbours[3].numberOfNeighbours);
			assertEquals(new Integer("4"), cell.neighbours[4].numberOfNeighbours);
			assertEquals(new Integer("4"), cell.neighbours[5].numberOfNeighbours);
		}
		
		for (int h = 2 ; h < lastRow-2 ; h++){
			Cell cell = matrix[h][lastColumn-1];
			
			assertEquals(new Integer("6"), cell.neighbours[0].numberOfNeighbours);
			assertEquals(new Integer("4"), cell.neighbours[1].numberOfNeighbours);
			assertEquals(new Integer("4"), cell.neighbours[2].numberOfNeighbours);
			assertEquals(new Integer("6"), cell.neighbours[3].numberOfNeighbours);
			assertEquals(new Integer("6"), cell.neighbours[4].numberOfNeighbours);
			assertEquals(new Integer("6"), cell.neighbours[5].numberOfNeighbours);
		}
	}
	
	private void checkBottomBorderCells(Map map) {
		Cell[][] matrix = map.matrix;
		Integer lastRow =  (map.height - 1);
		Integer lastColumn =  (map.width - 1);
		
		for (int w = 2 ; w < lastColumn-2 ; w+=2){
			Cell cell = matrix[lastRow-1][w];
			
			assertEquals(new Integer("6"), cell.neighbours[0].numberOfNeighbours);
			assertEquals(new Integer("6"), cell.neighbours[1].numberOfNeighbours);
			assertEquals(new Integer("6"), cell.neighbours[2].numberOfNeighbours);
			assertEquals(new Integer("5"), cell.neighbours[3].numberOfNeighbours);
			assertEquals(new Integer("6"), cell.neighbours[4].numberOfNeighbours);
			assertEquals(new Integer("6"), cell.neighbours[5].numberOfNeighbours);
		}
		
		for (int w = 3 ; w < lastColumn-2 ; w+=2){
			Cell cell = matrix[lastRow-1][w];
			
			assertEquals(new Integer("6"), cell.neighbours[0].numberOfNeighbours);
			assertEquals(new Integer("6"), cell.neighbours[1].numberOfNeighbours);
			assertEquals(new Integer("5"), cell.neighbours[2].numberOfNeighbours);
			assertEquals(new Integer("3"), cell.neighbours[3].numberOfNeighbours);
			assertEquals(new Integer("5"), cell.neighbours[4].numberOfNeighbours);
			assertEquals(new Integer("6"), cell.neighbours[5].numberOfNeighbours);
		}
	}

	private void checkOtherCells(Map map) {
		Cell[][] matrix = map.matrix;
		Integer lastRow =  (map.height - 1);
		Integer lastColumn =  (map.width - 1);
		
		for (int h = 2 ; h < lastRow-2 ; h++){
			for (int w = 2 ; w < lastColumn-2 ; w++){
				Cell cell = matrix[h][w];
				
				assertEquals(new Integer("6"), cell.neighbours[0].numberOfNeighbours);
				assertEquals(new Integer("6"), cell.neighbours[1].numberOfNeighbours);
				assertEquals(new Integer("6"), cell.neighbours[2].numberOfNeighbours);
				assertEquals(new Integer("6"), cell.neighbours[3].numberOfNeighbours);
				assertEquals(new Integer("6"), cell.neighbours[4].numberOfNeighbours);
				assertEquals(new Integer("6"), cell.neighbours[5].numberOfNeighbours);
			}
		}
	}
}
