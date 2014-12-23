package SKAZA.core.models.order;

import SKAZA.core.models.map.Cell;
import SKAZA.core.models.map.Coordinates;

public class Order {
	Coordinates from;
	Coordinates to;
	
	public Order(Cell orderTaker, Cell cell) {
		this.from = orderTaker.coordinates;
		this.to = cell.coordinates;
	}
}
