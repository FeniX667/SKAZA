package SKAZA.core.service;

import javafx.application.Application;

import javax.inject.Inject;

import SKAZA.core.math.constants.MapConstants;
import SKAZA.core.models.map.Cell;
import SKAZA.core.models.map.Map;

public class MapService {
	
	private static MapConstants mapConstants = new MapConstants();
	
	public static Map createMap(){
		Map map = new Map();
		map.height = new Byte (mapConstants.gridHeight);
		map.width = new Byte (mapConstants.gridWidth);
		map.lastRow = new Byte ((byte) (map.height-1));
		map.lastColumn = new Byte ((byte) (map.width-1));
		
		map.matrix = new Cell[map.height][map.width];		
		initializeMatrixCells(map);
		initializeNeighbours(map);
		
		return map;
	}
	
	private static void initializeMatrixCells(Map map) {
		initializeCornerCells(map);
		initializeOuterBaseCells(map);
		initializeInnerBaseCells(map);
		initializeSideCells(map);
		initializeInnerCells(map);
	}	
	
	private static void initializeCornerCells(Map map) {
		Cell[][] matrix = map.matrix;
		Byte lastColumn = map.lastColumn;
		Byte lastRow = map.lastRow;
		
		matrix[0][0] = CellService.createCell(mapConstants.numberOfUpperCornerNeighbours);
		matrix[0][lastColumn] = CellService.createCell( mapConstants.numberOfUpperCornerNeighbours );
		matrix[lastRow][0] = CellService.createCell( mapConstants.numberOfLowerCornerNeighbours );
		matrix[lastRow][lastColumn] = CellService.createCell( mapConstants.numberOfLowerCornerNeighbours );
	}

	private static void initializeOuterBaseCells(Map map) {
		Cell[][] matrix = map.matrix;
		Byte lastColumn = map.lastColumn;
		Byte lastRow = map.lastRow;
		
		for (int w = 2 ; w < lastColumn ; w+=2 ){
			matrix[0][w] = CellService.createCell( mapConstants.numberOfOuterBaseNeighbours );
		}
		
		for (int w = 1 ; w < lastColumn ; w+=2 ){
			matrix[lastRow][w] = CellService.createCell( mapConstants.numberOfOuterBaseNeighbours );
		}
	}

	private static void initializeInnerBaseCells(Map map) {
		Cell[][] matrix = map.matrix;
		Byte lastColumn = map.lastColumn;
		Byte lastRow = map.lastRow;
		
		for (int w = 1 ; w < lastColumn ; w+=2 ){
			matrix[0][w] = CellService.createCell( mapConstants.numberOfInnerBaseNeighbours );
		}		
		for (int w = 2 ; w < lastColumn ; w+=2 ){
			matrix[lastRow][w] = CellService.createCell( mapConstants.numberOfInnerBaseNeighbours );
		}
	}

	private static void initializeSideCells(Map map) {
		Cell[][] matrix = map.matrix;
		Byte lastColumn = map.lastColumn;
		Byte lastRow = map.lastRow;
		
		for (int h = 1 ; h < lastRow ; h++){			
			matrix[h][0] = CellService.createCell( mapConstants.numberOfSideNeighbours );
			matrix[h][lastColumn] = CellService.createCell( mapConstants.numberOfSideNeighbours );
		}
	}

	private static void initializeInnerCells(Map map) {
		Cell[][] matrix = map.matrix;
		Byte lastColumn = map.lastColumn;
		Byte lastRow = map.lastRow;
		
		for (int h = 0 ; h < lastRow ; h++){
			for (int w = 0 ; w < lastColumn ; w++){
				if( matrix[h][w] == null )
					matrix[h][w] = CellService.createCell( mapConstants.numberOfNormalCellNeighbours );
			}
		}		
	}

	private static void initializeNeighbours(Map map){
		initializeCornerNeighbours(map);
		initializeOuterBaseNeighbours(map);
		initializeSideNeighbours(map);
		initializeInnerBaseNeighbours(map);
		initializeInnerNeighbours(map);
	}

	private static void initializeCornerNeighbours(Map map) {
		Cell[][] matrix = map.matrix;
		Byte lastColumn = map.lastColumn;
		Byte lastRow = map.lastRow;
		
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

	private static void initializeOuterBaseNeighbours(Map map) {
		Cell[][] matrix = map.matrix;
		Byte lastColumn = map.lastColumn;
		Byte lastRow = map.lastRow;
		
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

	private static void initializeSideNeighbours(Map map) {
		Cell[][] matrix = map.matrix;
		Byte lastColumn = map.lastColumn;
		Byte lastRow = map.lastRow;
		
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

	private static void initializeInnerBaseNeighbours(Map map) {
		Cell[][] matrix = map.matrix;
		Byte lastColumn = map.lastColumn;
		Byte lastRow = map.lastRow;
		
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

	private static void initializeInnerNeighbours(Map map) {
		Cell[][] matrix = map.matrix;
		Byte lastColumn = map.lastColumn;
		Byte lastRow = map.lastRow;
		
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
