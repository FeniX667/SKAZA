package SKAZA.core.models.order;

import SKAZA.core.models.map.Cell;
import SKAZA.core.models.map.Coordinates;

public class Order {
	public Coordinates from;
	public Coordinates to;
	public boolean done;
	
	public Order(Cell orderTaker, Cell destination) {
		this.from = orderTaker.coordinates;
		this.to = destination.coordinates;
		done = false;
	}
	
	@Override
	public String toString(){
		return new String( from.toString()+ "->" +to.toString()+ ":" +done );
	}
}
