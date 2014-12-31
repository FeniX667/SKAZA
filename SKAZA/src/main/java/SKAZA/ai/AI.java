package SKAZA.ai;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import SKAZA.core.models.order.Order;
import SKAZA.core.models.unit.Nation;

public class AI {
	
	List<Order> orderListReference;
	List<Order> currentOrderList;
	Nation nation;
	Order orderToGive;
	
	Random generator = new Random();
	
	public AI(List<Order> orderListForRome, Order orderToGive, Nation nation) {
		this.orderListReference = orderListForRome;
		this.orderToGive = orderToGive;
		this.nation = nation;
		
		orderToGive.done=true;
	}

	public void run(){
		try {
		while(true){

			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {continue;	}		
			
/*			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {continue;	}	*/
			//System.out.println(orderToGive);
			//System.out.println(orderListReference);
			if( orderToGive.done && orderListReference.size()>0 ){
				currentOrderList = new LinkedList<Order> (orderListReference);
				
				if( currentOrderList.size()>0 ){
					Integer randomIndex = generator.nextInt( currentOrderList.size() );
					
					Order drawnOrder = currentOrderList.get( randomIndex );
	
					//Order drawnOrder = orderListReference.get( generator.nextInt( orderListReference.size() ) );
					
					orderToGive.from = drawnOrder.from;
					orderToGive.to = drawnOrder.to;
					orderToGive.done = false;
	
					//System.out.println(nation+ ": Wybieram rozkaz! " +orderToGive);
				}
			}
		}
		} catch(Exception e){
			System.out.println(e.getStackTrace());
            e.printStackTrace();
		}
	};
}
