package SKAZA.genetic;

import java.util.List;
import java.util.Random;

import SKAZA.core.SimulationEngine;
import SKAZA.core.models.order.Order;
import SKAZA.core.models.unit.Nation;
import SKAZA.core.models.unitArchetype.UnitArchetype;

public class Speciman {

	public List<UnitArchetype> archetypeList;
	public SimulationEngine simulationEngine;
	
	public Double strengthRatio;
	public Double winRatio;
	public Double averageIterationsPerGame;
	
	public Double adaptation;
	
	public Speciman(){		
	}

	public Speciman(List<UnitArchetype> randomArchetypeList) {
		archetypeList = randomArchetypeList;
		simulationEngine = new SimulationEngine( archetypeList );
		
		strengthRatio = calculateStrengthRatio(); 
		
		winRatio = new Double(0.0);
		averageIterationsPerGame = new Double(0.0);
		
		adaptation = new Double(0.0);
	}

	private Double calculateStrengthRatio() {
		Double romePower = simulationEngine.getStatistics( Nation.ROME ).cavalryStrength + simulationEngine.getStatistics( Nation.ROME ).infantryStrength;
		Double carthagePower = simulationEngine.getStatistics( Nation.CARTHAGE ).cavalryStrength + simulationEngine.getStatistics( Nation.CARTHAGE ).infantryStrength;
		
		
		return romePower/( romePower + carthagePower );
	}

	public void startSelection() {
		double romeWins=0;
		double carthageWins=0;
		int iterationCounter=0;
		
		for(int i=0 ; i<10 ; i++){
			simulationEngine = new SimulationEngine( archetypeList );
			
			playout();
			
			if( simulationEngine.winner != null ){
				if( simulationEngine.winner == Nation.ROME )
					romeWins++;
				else
					carthageWins++;
			}
			else{
				romeWins += 0.5;
				carthageWins += 0.5;
			}
			
			iterationCounter += simulationEngine.iteration;
		}
		
		averageIterationsPerGame =  ( (double) iterationCounter/10.0);
		
		winRatio = romeWins / ( romeWins + carthageWins);
	}
	
	public void playout() {
		while( !simulationEngine.endingFlag && simulationEngine.iteration<1000 ){
			setRandomRomeOrder();
    		setRandomCarthageOrder();
    		
    		simulationEngine.iterate();
    		
    	}
    }	
	private void setRandomRomeOrder(){
    	if( simulationEngine.orderListForRome.size() > 0 ){
	    	Random generator = new Random();
	    	
	    	Order drawnOrder = simulationEngine.orderListForRome.get( generator.nextInt( simulationEngine.orderListForRome.size() ) );
	    	simulationEngine.orderOfRome.from = drawnOrder.from;
	    	simulationEngine.orderOfRome.to = drawnOrder.to;
	    	simulationEngine.orderOfRome.done = false;		
    	}
	}
    private void setRandomCarthageOrder() {
    	if( simulationEngine.orderListForCarthage.size() > 0 ){
	    	Random generator = new Random();
	    	
	    	Order drawnOrder = simulationEngine.orderListForCarthage.get( generator.nextInt( simulationEngine.orderListForCarthage.size() ) );
	    	simulationEngine.orderOfCarthage.from = drawnOrder.from;
	    	simulationEngine.orderOfCarthage.to = drawnOrder.to;
	    	simulationEngine.orderOfCarthage.done = false;		
    	}
    }

}
