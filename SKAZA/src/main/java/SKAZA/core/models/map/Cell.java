package SKAZA.core.models.map;

import SKAZA.core.models.unit.Unit;

public class Cell {
	public Unit unit;
	public Coordinates coordinates;
	public Cell[] neighbours;
	public Byte numberOfNeighbours;

}
