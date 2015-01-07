package SKAZA.ai;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
				
				if( nation == Nation.ROME )
					currentOrderList = new LinkedList<Order> (currentSimEngine.orderListForRome);
				else if( nation == Nation.CARTHAGE )
					currentOrderList = new LinkedList<Order> (currentSimEngine.orderListForCarthage);
				
				if( currentOrderList.size()>0 ){
					mctsChoice();
					//randomChoice();
				}
			}
		}
	}
	
	private void mctsChoice(){
		
		TreeNode root = new TreeNode(null, currentSimEngine.getClone(), nation);
        ElapsedTimer timer = new ElapsedTimer();
            	
    	root.children = new TreeNode[currentOrderList.size()];
    	
        for( int i=0 ; i < currentOrderList.size() ; i++ ) {
        	SimulationEngine simEngineCopy = currentSimEngine.getClone();
        	root.children[i] = new TreeNode( currentOrderList.get(i), simEngineCopy, nation);
        }
    	
        while( timer.elapsed() < simEngineReference.speedLimiter ){
        	root.selectAction();
        }
        
		chooseBestMove(root);
	}
	
	private void chooseBestMove(TreeNode root) {
		Random generator = new Random();
		Integer randomIndex;
		List<Order> bestOrderList = new LinkedList<Order>();
		Order bestOrder;
		double bestValue = 0;
		
		for (TreeNode tn : root.children) {
			Double currentValue = tn.totValue/( tn.nVisits+1e-6 );
			if( currentValue > bestValue ) {
				bestOrderList.clear();
				bestOrderList.add( tn.orderInNode );
				bestValue = currentValue;
			}
			else if( currentValue == bestValue ){
				bestOrderList.add( tn.orderInNode );				
			}
		}
		
		System.out.println(nation+ " " +root.counter );
		
		randomIndex = generator.nextInt( bestOrderList.size() );
		bestOrder = bestOrderList.get( randomIndex );
		
		orderToGive.from = bestOrder.from;
		orderToGive.to = bestOrder.to;
		orderToGive.done = false;
	}
	
	
	private void randomChoice(){
		Random generator = new Random();
		Integer randomIndex = generator.nextInt( currentOrderList.size() );
		
		Order drawnOrder = currentOrderList.get( randomIndex );
		
		orderToGive.from = drawnOrder.from;
		orderToGive.to = drawnOrder.to;
		orderToGive.done = false;
	}
}
