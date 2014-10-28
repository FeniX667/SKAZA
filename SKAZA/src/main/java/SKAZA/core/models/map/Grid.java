package SKAZA.core.models.map;

import SKAZA.core.math.constants.MapConstants;

public class Grid {
	public final Cell[][] matrix; 
	public final Integer height;
	public final Integer width;
	public final Integer lastRow;
	public final Integer lastColumn;
	
	private MapConstants mapConstants = new MapConstants();
	
	public Grid(){
		height = new Integer (mapConstants.gridHeight);
		width = new Integer (mapConstants.gridWidth);
		lastRow = new Integer (height-1);
		lastColumn = new Integer (width-1);
		
		matrix = new Cell[height][width];		
		initializeMatrixCells();
		initializeNeighbours();
	}
	
	private void initializeMatrixCells() {
		initializeCornerCells();
		initializeOuterBaseCells();
		initializeInnerBaseCells();
		initializeSideCells();
		initializeInnerCells();
	}	
	
	private void initializeCornerCells() {
		matrix[0][0] = new Cell( mapConstants.numberOfUpperCornerNeighbours );
		matrix[0][lastColumn] = new Cell( mapConstants.numberOfUpperCornerNeighbours );
		matrix[lastRow][0] = new Cell( mapConstants.numberOfLowerCornerNeighbours );
		matrix[lastRow][lastColumn] = new Cell( mapConstants.numberOfLowerCornerNeighbours );
	}

	private void initializeOuterBaseCells() {
		for (int w = 2 ; w < lastColumn ; w+=2 ){
			matrix[0][w] = new Cell( mapConstants.numberOfOuterBaseNeighbours );
		}
		
		for (int w = 1 ; w < lastColumn ; w+=2 ){
			matrix[lastRow][w] = new Cell( mapConstants.numberOfOuterBaseNeighbours );
		}
	}

	private void initializeInnerBaseCells() {
		for (int w = 1 ; w < lastColumn ; w+=2 ){
			matrix[0][w] = new Cell( mapConstants.numberOfInnerBaseNeighbours );
		}		
		for (int w = 2 ; w < lastColumn ; w+=2 ){
			matrix[lastRow][w] = new Cell( mapConstants.numberOfInnerBaseNeighbours );
		}
	}

	private void initializeSideCells() {
		for (int h = 1 ; h < lastRow ; h++){			
			matrix[h][0] = new Cell( mapConstants.numberOfSideNeighbours );
			matrix[h][lastColumn] = new Cell( mapConstants.numberOfSideNeighbours );
		}
	}

	private void initializeInnerCells() {
		for (int h = 0 ; h < lastRow ; h++){
			for (int w = 0 ; w < lastColumn ; w++){
				if( matrix[h][w] == null )
					matrix[h][w] = new Cell( mapConstants.numberOfNormalCellNeighbours );
			}
		}		
	}

	private void initializeNeighbours(){
		initializeCornerNeighbours();
		initializeOuterBaseNeighbours();
		initializeSideNeighbours();
		initializeInnerBaseNeighbours();
		initializeInnerNeighbours();
	}

	private void initializeCornerNeighbours() {
		matrix[0][0].neighbours[0] = matrix[0][1];
		matrix[0][0].neighbours[1] = matrix[1][0];

		matrix[0][lastColumn].neighbours[0] = matrix[1][lastColumn];
		matrix[0][lastColumn].neighbours[1] = matrix[0][lastColumn-1];
		
		matrix[lastRow][0].neighbours[0] = matrix[lastColumn-1][0];
		matrix[lastRow][0].neighbours[1] = matrix[lastColumn-1][1];
		matrix[lastRow][0].neighbours[2] = matrix[lastRow][1];
		
		matrix[lastRow][lastColumn].neighbours[0] = matrix[lastColumn-1][lastColumn];
		matrix[lastRow][lastColumn].neighbours[1] = matrix[lastRow][lastColumn-1];		
		matrix[lastRow][lastColumn].neighbours[2] = matrix[lastColumn-1][lastColumn-1];
	}

	private void initializeOuterBaseNeighbours() {
		for (int w = 2 ; w < lastColumn ; w+=2 ){
			matrix[0][w].neighbours[0] = matrix[0][w+1];
			matrix[0][w].neighbours[1] = matrix[1][w];
			matrix[0][w].neighbours[2] = matrix[0][w-1];
		}
		
		for (int w = 1 ; w < lastColumn ; w+=2 ){
			matrix[lastRow][w].neighbours[0] = matrix[lastColumn-1][w];
			matrix[lastRow][w].neighbours[1] = matrix[lastRow][w+1];
			matrix[lastRow][w].neighbours[2] = matrix[lastRow][w-1];
		}
	}

	private void initializeSideNeighbours() {
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

	private void initializeInnerBaseNeighbours() {
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

	private void initializeInnerNeighbours() {
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

}