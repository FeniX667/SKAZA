package SKAZA.core;

import java.util.concurrent.Callable;

public class SimulationEngineThread implements Runnable {
	
	public SimulationEngine simulationEngine;
	public Thread threadController;
	
	private SimulationStatistics simulationStatistics;
	
	public SimulationEngineThread(){
		simulationEngine = new SimulationEngine();
		simulationEngine.initialize();
		
		simulationStatistics = new SimulationStatistics();
		threadController = new Thread(this);
	}
	
	public void run() {
		simulationEngine.start();		
		simulationStatistics = simulationEngine.getStatistics();
	}
	
	
}
