package SKAZA.core.service;

import SKAZA.core.math.constants.MapConstants;
import SKAZA.core.models.map.Cell;
import SKAZA.core.models.map.Map;

public class MapService {

	CellService cellService;
	UnitService unitService;
	
	public MapService(){
		cellService = new CellService();
		unitService = new UnitService();
	}
	
	public Map createMap(){
		Map map = new Map();
		map.height = MapConstants.gridHeight;
		map.width = MapConstants.gridWidth;
		map.lastRow = MapConstants.gridHeight-1;
		map.lastColumn = MapConstants.gridWidth-1;
		
		map.matrix = new Cell[map.height][map.width];		
		initializeMatrixCells(map);
		initializeNeighbours(map);
		
		return map;
	}
	
	private void initializeMatrixCells(Map map) {
		initializeCornerCells(map);
		initializeOuterBaseCells(map);
		initializeInnerBaseCells(map);
		initializeSideCells(map);
		initializeInnerCells(map);
	}	
	
	private void initializeCornerCells(Map map) {
		Cell[][] matrix = map.matrix;
		Integer lastColumn = map.lastColumn;
		Integer lastRow = map.lastRow;
		
		matrix[0][0] = cellService.createCell(MapConstants.numberOfUpperCornerNeighbours, 0, 0);
		matrix[0][lastColumn] = cellService.createCell( MapConstants.numberOfUpperCornerNeighbours, 0, lastColumn );
		matrix[lastRow][0] = cellService.createCell( MapConstants.numberOfLowerCornerNeighbours, lastRow, 0 );
		matrix[lastRow][lastColumn] = cellService.createCell( MapConstants.numberOfLowerCornerNeighbours, lastRow, lastColumn );
	}

	private void initializeOuterBaseCells(Map map) {
		Cell[][] matrix = map.matrix;
		Integer lastColumn = map.lastColumn;
		Integer lastRow = map.lastRow;
		
		for (int w = 2 ; w < lastColumn ; w+=2 ){
			matrix[0][w] = cellService.createCell( MapConstants.numberOfOuterBaseNeighbours, 0, w );
		}
		
		for (int w = 1 ; w < lastColumn ; w+=2 ){
			matrix[lastRow][w] = cellService.createCell( MapConstants.numberOfOuterBaseNeighbours, lastRow, w );
		}
	}

	private void initializeInnerBaseCells(Map map) {
		Cell[][] matrix = map.matrix;
		Integer lastColumn = map.lastColumn;
		Integer lastRow = map.lastRow;
		
		for (int w = 1 ; w < lastColumn ; w+=2 ){
			matrix[0][w] = cellService.createCell( MapConstants.numberOfInnerBaseNeighbours, 0, w );
		}		
		for (int w = 2 ; w < lastColumn ; w+=2 ){
			matrix[lastRow][w] = cellService.createCell( MapConstants.numberOfInnerBaseNeighbours, lastRow, w );
		}
	}

	private void initializeSideCells(Map map) {
		Cell[][] matrix = map.matrix;
		Integer lastColumn = map.lastColumn;
		Integer lastRow = map.lastRow;
		
		for (int h = 1 ; h < lastRow ; h++){			
			matrix[h][0] = cellService.createCell( MapConstants.numberOfSideNeighbours, h, 0 );
			matrix[h][lastColumn] = cellService.createCell( MapConstants.numberOfSideNeighbours, h, lastColumn );
		}
	}

	private void initializeInnerCells(Map map) {
		Cell[][] matrix = map.matrix;
		Integer lastColumn = map.lastColumn;
		Integer lastRow = map.lastRow;
		
		for (int h = 0 ; h < lastRow ; h++){
			for (int w = 0 ; w < lastColumn ; w++){
				if( matrix[h][w] == null )
					matrix[h][w] = cellService.createCell( MapConstants.numberOfNormalCellNeighbours, h, w );
			}
		}		
	}

	private void initializeNeighbours(Map map){
		initializeCornerNeighbours(map);
		initializeOuterBaseNeighbours(map);
		initializeSideNeighbours(map);
		initializeInnerBaseNeighbours(map);
		initializeInnerNeighbours(map);
	}

	private void initializeCornerNeighbours(Map map) {
		Cell[][] matrix = map.matrix;
		Integer lastColumn = map.lastColumn;
		Integer lastRow = map.lastRow;
		
		matrix[0][0].neighbours[0] = matrix[0][1];
		matrix[0][0].neighbours[1] = matrix[1][0];

		matrix[0][lastColumn].neighbours[0] = matrix[1][lastColumn];
		matrix[0][lastColumn].neighbours[1] = matrix[0][lastColumn-1];
		
		matrix[lastRow][0].neighbours[0] = matrix[lastRow-1][0];
		matrix[lastRow][0].neighbours[1] = matrix[lastRow-1][1];
		matrix[lastRow][0].neighbours[2] = matrix[lastRow][1];
		
		matrix[lastRow][lastColumn].neighbours[0] = matrix[lastRow-1][lastColumn];
		matrix[lastRow][lastColumn].neighbours[1] = matrix[lastRow][lastColumn-1];		
		matrix[lastRow][lastColumn].neighbours[2] = matrix[lastRow-1][lastColumn-1];
	}

	private void initializeOuterBaseNeighbours(Map map) {
		Cell[][] matrix = map.matrix;
		Integer lastColumn = map.lastColumn;
		Integer lastRow = map.lastRow;
		
		for (int w = 2 ; w < lastColumn ; w+=2 ){
			matrix[0][w].neighbours[0] = matrix[0][w+1];
			matrix[0][w].neighbours[1] = matrix[1][w];
			matrix[0][w].neighbours[2] = matrix[0][w-1];
		}
		
		for (int w = 1 ; w < lastColumn ; w+=2 ){
			matrix[lastRow][w].neighbours[0] = matrix[lastRow-1][w];
			matrix[lastRow][w].neighbours[1] = matrix[lastRow][w+1];
			matrix[lastRow][w].neighbours[2] = matrix[lastRow][w-1];
		}
	}

	private void initializeSideNeighbours(Map map) {
		Cell[][] matrix = map.matrix;
		Integer lastColumn = map.lastColumn;
		Integer lastRow = map.lastRow;
		
		for (int h = 1 ; h < lastRow ; h++){			
			matrix[h][0].neighbours[0] = matrix[h-1][0];
			matrix[h][0].neighbours[1] = matrix[h-1][1];
			matrix[h][0].neighbours[2] = matrix[h][1];
			matrix[h][0].neighbours[3] = matrix[h+1][0];
			
			matrix[h][lastColumn].neighbours[0] = matrix[h-1][lastColumn];
			matrix[h][lastColumn].neighbours[1] = matrix[h+1][lastColumn];
			matrix[h][lastColumn].neighbours[2] = matrix[h][lastColumn-1];
			matrix[h][lastColumn].neighbours[3] = matrix[h-1][lastColumn-1];
		}
	}

	private void initializeInnerBaseNeighbours(Map map) {
		Cell[][] matrix = map.matrix;
		Integer lastColumn = map.lastColumn;
		Integer lastRow = map.lastRow;
		
		for (int w = 1 ; w < lastColumn ; w+=2 ){
			Cell cell = matrix[0][w];
			cell.neighbours[0] = matrix[0][w+1];
			cell.neighbours[1] = matrix[1][w+1];
			cell.neighbours[2] = matrix[1][w];
			cell.neighbours[3] = matrix[1][w-1];
			cell.neighbours[4] = matrix[0][w-1];
		}		
		for (int w = 2 ; w < lastColumn ; w+=2 ){
			Cell cell = matrix[lastRow][w];
			cell.neighbours[0] = matrix[lastRow-1][w];
			cell.neighbours[1] = matrix[lastRow-1][w+1];
			cell.neighbours[2] = matrix[lastRow][w+1];
			cell.neighbours[3] = matrix[lastRow][w-1];
			cell.neighbours[4] = matrix[lastRow-1][w-1];
		}	
	}

	private void initializeInnerNeighbours(Map map) {
		Cell[][] matrix = map.matrix;
		Integer lastColumn = map.lastColumn;
		Integer lastRow = map.lastRow;
		
		for (int h = 1 ; h < lastRow ; h++){
			for (int w = 1 ; w < lastColumn ; w++){
				Cell cell = matrix[h][w];
				
				if ( w % 2 == 0){
					cell.neighbours[0] = matrix[h-1][w];
					cell.neighbours[1] = matrix[h-1][w+1];
					cell.neighbours[2] = matrix[h][w+1];
					cell.neighbours[3] = matrix[h+1][w];
					cell.neighbours[4] = matrix[h][w-1];
					cell.neighbours[5] = matrix[h-1][w-1];
				}
				else if ( w % 2 == 1){
					cell.neighbours[0] = matrix[h-1][w];
					cell.neighbours[1] = matrix[h][w+1];
					cell.neighbours[2] = matrix[h+1][w+1];
					cell.neighbours[3] = matrix[h+1][w];
					cell.neighbours[4] = matrix[h+1][w-1];
					cell.neighbours[5] = matrix[h][w-1];
				}
			}
		}
	}

	public void copyMap(Map copy, Map map) {
		for( int h=0 ; h < MapConstants.gridHeight ; h++){
			for( int w=0 ; w < MapConstants.gridWidth; w++){
				Cell copyCell = copy.matrix[h][w];
				Cell originalCell = map.matrix[h][w];
				
				if( originalCell.unit != null ){
					copyCell.unit = unitService.copyUnit( originalCell.unit );
				}
			}
		}
	}

}
