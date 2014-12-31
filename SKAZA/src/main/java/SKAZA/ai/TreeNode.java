package SKAZA.ai;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import SKAZA.core.SimulationEngine;
import SKAZA.core.models.order.Order;
import SKAZA.core.models.unit.Nation;

public class TreeNode {
    static Random r = new Random();
    static double epsilon = 1e-6;

    TreeNode[] children;
    Order order;
    SimulationEngine simEngine;
    List<Order> currentOrderList;
    Nation nation;
    double nVisits, totValue;

    public TreeNode(List<Order> currentOrderList, Order order, SimulationEngine simEngine, Nation nation) {
    	this.currentOrderList = currentOrderList;
    	this.order = order;
    	this.simEngine = simEngine;
    	this.nation = nation;
	}

	public void selectAction() {
        List<TreeNode> visited = new LinkedList<TreeNode>();
        TreeNode current = this;
        visited.add(this);
        
        while( !current.isLeaf() ) {
            current = current.select();
            visited.add(current);
        }
        
        current.expand();
        
        TreeNode newNode = current.select();
        visited.add(newNode);
        
        double value = playout(newNode);
        for (TreeNode node : visited) {
        	if( node!=null )
        		node.updateStats(value);
        }
    }

    public boolean isLeaf() {
        return children==null;
    }

    private TreeNode select() {
        TreeNode selected = null;
        double bestValue = Double.MIN_VALUE;
        for (TreeNode c : children) {
            double uctValue =
                    c.totValue / (c.nVisits + epsilon) +
                            Math.sqrt(Math.log(nVisits+1) / (c.nVisits + epsilon)) +
                            r.nextDouble() * epsilon;
            if (uctValue > bestValue) {
                selected = c;
                bestValue = uctValue;
            }
        }
        return selected;
    }
    
    public void expand() {
    	Random generator = new Random();
        children = new TreeNode[ currentOrderList.size() ];
        
        for( int i=0 ; i < currentOrderList.size() ; i++ ) {
        	SimulationEngine simEngineCopy = simEngine.getClone();
        	
        	if( nation == Nation.ROME ){
        		if( order!= null ){
            		simEngineCopy.orderOfRome.from = order.from;
            		simEngineCopy.orderOfRome.to = order.to;
            		simEngineCopy.orderOfRome.done = false;			
        		}
        		
        		if( simEngineCopy.orderListForCarthage.size()>0 ){
	        		Integer randomIndex = generator.nextInt( simEngineCopy.orderListForCarthage.size() );
	        		Order drawnOrder = simEngineCopy.orderListForCarthage.get( randomIndex );
	        		simEngineCopy.orderOfCarthage.from = drawnOrder.from;
	        		simEngineCopy.orderOfCarthage.to = drawnOrder.to;
	        		simEngineCopy.orderOfCarthage.done = false;
        		}
        		
        		simEngineCopy.iterate();
        		
            	while(simEngineCopy.orderListForRome.size() == 0){
            		simEngineCopy.iterate();
            		
                	if( simEngineCopy.orderListForCarthage.size() > 0){
                		Integer randomIndex = generator.nextInt( simEngineCopy.orderListForCarthage.size() );
                		Order drawnOrder = simEngineCopy.orderListForCarthage.get( randomIndex );
                		simEngineCopy.orderOfCarthage.from = drawnOrder.from;
                		simEngineCopy.orderOfCarthage.to = drawnOrder.to;
                		simEngineCopy.orderOfCarthage.done = false;
                	}                	
            	}
            	children[i] = new TreeNode(simEngineCopy.orderListForRome, currentOrderList.get(i), simEngineCopy, nation);
        	}       		
        	
        	if( nation == Nation.CARTHAGE ){
	        	if( order!= null ){
	        		simEngineCopy.orderOfCarthage.from = order.from;
	        		simEngineCopy.orderOfCarthage.to = order.to;
	        		simEngineCopy.orderOfCarthage.done = false;
        		}
        		
	        	if( simEngineCopy.orderListForRome.size()>0 ){
	        		Integer randomIndex = generator.nextInt( simEngineCopy.orderListForRome.size() );
	        		Order drawnOrder = simEngineCopy.orderListForRome.get( randomIndex );
	        		simEngineCopy.orderOfRome.from = drawnOrder.from;
	        		simEngineCopy.orderOfRome.to = drawnOrder.to;
	        		simEngineCopy.orderOfRome.done = false;
	        	}
        		
        		simEngineCopy.iterate();
        		
            	while(simEngineCopy.orderListForCarthage.size() == 0){
            		simEngineCopy.iterate();
            		
                	if( simEngineCopy.orderListForRome.size() > 0){
                		Integer randomIndex = generator.nextInt( simEngineCopy.orderListForRome.size() );
                		Order drawnOrder = simEngineCopy.orderListForRome.get( randomIndex );
                		simEngineCopy.orderOfRome.from = drawnOrder.from;
                		simEngineCopy.orderOfRome.to = drawnOrder.to;
                		simEngineCopy.orderOfRome.done = false;
                	}              	
            	}
            	children[i] = new TreeNode(simEngineCopy.orderListForCarthage, currentOrderList.get(i), simEngineCopy, nation);
        	}
        }
    }

    public double playout(TreeNode tn) {
    	Random generator = new Random();
    	System.out.println("Zaczynam playout..." + simEngine.endingFlag);
    	
    	while( !simEngine.endingFlag ){
        	simEngine.iterate();
        	
        	if( simEngine.orderListForRome.size() > 0){
        		Integer randomIndex = generator.nextInt( simEngine.orderListForRome.size() );
        		Order drawnOrder = simEngine.orderListForRome.get( randomIndex );
        		simEngine.orderOfRome.from = drawnOrder.from;
        		simEngine.orderOfRome.to = drawnOrder.to;
        		simEngine.orderOfRome.done = drawnOrder.done;
        	}
        	
        	if( simEngine.orderListForCarthage.size() > 0){
        		Integer randomIndex = generator.nextInt( simEngine.orderListForCarthage.size() );
        		Order drawnOrder = simEngine.orderListForCarthage.get( randomIndex );
        		simEngine.orderOfCarthage.from = drawnOrder.from;
        		simEngine.orderOfCarthage.to = drawnOrder.to;
        		simEngine.orderOfCarthage.done = drawnOrder.done;
        	}
        	
    	}
    	
    	System.out.println("Wynik " + simEngine.winner);
    	
    	if( simEngine.winner == nation )
    		return 1;
    	else
    		return 0;
    }

    public void updateStats(double value) {
        nVisits++;
        totValue += value;
    }

	public Order getBestOrder() {
		Order bestOrder = null;
		double bestValue = 0;
		for (TreeNode tn : children) {
			Double currentValue = tn.totValue/tn.nVisits;
			if (currentValue >= bestValue) {
				bestValue = currentValue;
				bestOrder = tn.order;
			}
	        System.out.println(currentValue+ "%");
		}
		return bestOrder;
	}
}