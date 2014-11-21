package SKAZA.core.models.map;

import SKAZA.core.models.unit.Unit;

public class Cell {
	public Unit unit;
	public Boolean isUnitPresent;
	public Cell[] neighbours;
	public Byte numberOfNeighbours;

}
