package skaza.core.models.map;

import javax.inject.Inject;

import skaza.core.math.calculators.MapConstants;
import skaza.core.models.unit.Unit;

public class Cell {
	public Unit unit;
	public Cell[] neighbours;
	public Integer numberOfNeighbours;
	
	@Inject
	MapConstants mapConstants;
	
	public Cell(Integer _numberOfNeighbours){
		unit = new Unit();
		numberOfNeighbours = _numberOfNeighbours;
		neighbours = new Cell[numberOfNeighbours];
	}
}
