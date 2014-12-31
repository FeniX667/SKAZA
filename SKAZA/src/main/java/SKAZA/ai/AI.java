package SKAZA.ai;

import java.util.LinkedList;
import java.util.List;
import SKAZA.core.SimulationEngine;
import SKAZA.core.models.order.Order;
import SKAZA.core.models.unit.Nation;

public class AI {

	SimulationEngine simEngineReference;
	SimulationEngine currentSimEngine;
	List<Order> orderListReference;
	List<Order> currentOrderList;
	Nation nation;
	Order orderToGive;
	
	
	public AI(SimulationEngine simEngine, Nation nation) {
		this.nation = nation;
		this.simEngineReference = simEngine;
		
		if( nation == Nation.ROME){
			this.orderListReference = simEngine.orderListForRome;
			this.orderToGive = simEngine.orderOfRome;
		}
		else if( nation == Nation.CARTHAGE ){
			this.orderListReference = simEngine.orderListForCarthage;
			this.orderToGive = simEngine.orderOfCarthage;
		}
		
		
		orderToGive.done=true;
	}

	public void run(){
		while(true){			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {continue;	}					
			if( orderToGive.done && orderListReference.size()>0 ){
				currentSimEngine = simEngineReference.getClone();
				
				if( nation == Nation.ROME)
					currentOrderList = new LinkedList<Order> (currentSimEngine.orderListForRome);
				else if( nation == Nation.CARTHAGE)
					currentOrderList = new LinkedList<Order> (currentSimEngine.orderListForCarthage);
				
				if( currentOrderList.size()>0 ){
					mctsPlayout();
				}
			}
		}
	}
	
	private void mctsPlayout(){
        TreeNode treeNode = new TreeNode( new LinkedList<Order>(currentOrderList), null, currentSimEngine.getClone(), nation );
        
        ElapsedTimer timer = new ElapsedTimer();
    	int i=0;
        while( timer.elapsed() < 200 || i < currentOrderList.size() ){
        	treeNode.selectAction();
        }
		
        Order drawnOrder = treeNode.getBestOrder();
		
		orderToGive.from = drawnOrder.from;
		orderToGive.to = drawnOrder.to;
		orderToGive.done = false;
	}
}
