package SKAZA.core.service;

import SKAZA.core.models.map.Cell;
import SKAZA.core.models.map.Coordinates;

public class CellService {

	public Cell createCell(Integer numberOfNeighbours, int x, int y){
		Cell cell = new Cell();
		cell.numberOfNeighbours = numberOfNeighbours;
		cell.neighbours = new Cell[cell.numberOfNeighbours];
		cell.coordinates = new Coordinates(x, y);
		
		return cell;
	}
}
