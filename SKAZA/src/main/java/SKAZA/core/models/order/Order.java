package SKAZA.core.models.order;

import SKAZA.core.models.map.Cell;
import SKAZA.core.models.map.Coordinates;

public class Order {
	public Coordinates from;
	public Coordinates to;
	
	public Order(Cell orderTaker, Cell cell) {
		this.from = orderTaker.coordinates;
		this.to = cell.coordinates;
	}
	
	@Override
	public String toString(){
		return new String( from.toString() +"-"+ to.toString() );
	}
}
