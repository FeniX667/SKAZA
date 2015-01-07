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

    Nation nation;
    SimulationEngine simEngine;
    
    Order orderInNode;
    Order enemyOrderInNode;
    Order allyOrderInNode;
    
    List<Order> allyOrderList;
    List<Order> enemyOrderList;
    int counter = 0;
    
    TreeNode[] children;
    double nVisits, totValue;

    public TreeNode(Order orderInNode, SimulationEngine simEngine, Nation nation) {
    	this.nation = nation;
    	
    	if( nation == Nation.ROME ){
        	this.orderInNode = orderInNode;
        	this.simEngine = simEngine;
        	this.allyOrderInNode = simEngine.orderOfRome;
        	this.enemyOrderInNode = simEngine.orderOfCarthage;
        	this.allyOrderList = simEngine.orderListForRome;
        	this.enemyOrderList = simEngine.orderListForCarthage;
    	}
    	if( nation == Nation.CARTHAGE){
        	this.orderInNode = orderInNode;
        	this.simEngine = simEngine;
        	this.allyOrderInNode = simEngine.orderOfCarthage;
        	this.enemyOrderInNode = simEngine.orderOfRome;
        	this.allyOrderList = simEngine.orderListForCarthage;  
        	this.enemyOrderList = simEngine.orderListForRome;  			
    	}
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
        
        double value = playout();
        
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
            if( uctValue > bestValue ) {
                selected = c;
                bestValue = uctValue;
            }
        }
        return selected;
           
    }
    
    public void expand() {    	
    	setAllyOrder();
    	setRandomEnemyOrder();
    	simEngine.iterate();

    	while( allyOrderList.size()==0 ){
    		setRandomEnemyOrder();
    		simEngine.iterate();
    	}
    	
    	children = new TreeNode[allyOrderList.size()];
    	
        for( int i=0 ; i < allyOrderList.size() ; i++ ) {
        	SimulationEngine simEngineCopy = simEngine.getClone();
        	children[i] = new TreeNode( allyOrderList.get(i), simEngineCopy, nation);
        }
    }

	public double playout() {
    	while( !simEngine.endingFlag && simEngine.iteration<1000 ){
    		setRandomAllyOrder();
    		setRandomEnemyOrder();
    		
    		simEngine.iterate();
    		
    	}
		counter++;
    	    	
    	if( simEngine.winner == nation )
    		return 1;
    	else if( simEngine.winner == null)
    		return 0.5;
    	else
    		return 0;
    }

    public void updateStats(double value) {
        nVisits++;
        totValue += value;
    }

	private void setAllyOrder() {
    	allyOrderInNode.from = orderInNode.from;
    	allyOrderInNode.to = orderInNode.to;
    	allyOrderInNode.done = false;
	}
	
	private void setRandomAllyOrder(){
    	if( allyOrderList.size() > 0 ){
	    	Random generator = new Random();
	    	
	    	Order drawnOrder = allyOrderList.get( generator.nextInt( allyOrderList.size() ) );
	    	allyOrderInNode.from = drawnOrder.from;
	    	allyOrderInNode.to = drawnOrder.to;
	    	allyOrderInNode.done = false;		
    	}
	}

    private void setRandomEnemyOrder() {
    	if( enemyOrderList.size() > 0 ){
	    	Random generator = new Random();
	    	
	    	Order drawnOrder = enemyOrderList.get( generator.nextInt( enemyOrderList.size() ) );
	    	enemyOrderInNode.from = drawnOrder.from;
	    	enemyOrderInNode.to = drawnOrder.to;
	    	enemyOrderInNode.done = false;		
    	}
	}
}