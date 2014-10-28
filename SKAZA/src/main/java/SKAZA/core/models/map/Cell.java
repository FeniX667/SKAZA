package SKAZA.core.models.map;

import SKAZA.core.models.unit.Unit;

public class Cell {
	public Unit unit;
	public Boolean isUnitPresent;
	public final Cell[] neighbours;
	public final Integer numberOfNeighbours;

	public Cell(Integer _numberOfNeighbours){
		numberOfNeighbours = _numberOfNeighbours;
		neighbours = new Cell[numberOfNeighbours];
		isUnitPresent=false;
	}
	
}
