package SKAZA.core.service;

import SKAZA.core.models.map.Cell;

public class CellService {

	public static Cell createCell(Byte numberOfNeighbours){
		Cell cell = new Cell();
		cell.numberOfNeighbours = numberOfNeighbours;
		cell.neighbours = new Cell[cell.numberOfNeighbours];
		cell.isUnitPresent=false;
		
		return cell;
	}
}
