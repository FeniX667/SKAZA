package SKAZA.core.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import SKAZA.core.SimulationEngine;
import SKAZA.core.SimulationStatistics;
import SKAZA.core.math.calculators.FightCalculator;
import SKAZA.core.math.constants.MapConstants;
import SKAZA.core.models.map.Cell;
import SKAZA.core.models.map.Map;
import SKAZA.core.models.order.Order;
import SKAZA.core.models.unit.Nation;
import SKAZA.core.models.unit.Unit;
import SKAZA.core.models.unit.UnitState;

public class SimulationEngineService {

	public static void initializeVariables(SimulationEngine simulationEngine) {
		simulationEngine.map = MapService.createMap();
		
		simulationEngine.orderListForRome =  Collections.synchronizedList( new LinkedList<Order>() );
		simulationEngine.orderListForCarthage = Collections.synchronizedList( new LinkedList<Order>() );
		simulationEngine.orderOfRome = new Order( CellService.createCell(0, 0, 0), CellService.createCell(0, 1, 1));
		simulationEngine.orderOfCarthage = new Order( CellService.createCell(1, 1, 0), CellService.createCell(0, 0, 0));
		
		simulationEngine.iteration = new Integer(0);
		simulationEngine.speedLimiter = new Long(0);
		
		simulationEngine.statistics = new SimulationStatistics();
		simulationEngine.endingFlag = new Boolean(false);	
	}
	
	public static void setArmiesOnMap(Map map) {
	
		List<Unit> unitsOfRome = UnitService.createArmy(Nation.ROME);
		List<Unit> unitsOfCarthage = UnitService.createArmy(Nation.CARTHAGE);
		
		for( int i=1 ; i < unitsOfRome.size() ; i++ ){
			map.matrix[i][4].unit = unitsOfRome.get(i);
		}
		
		for( int i=1 ; i < unitsOfCarthage.size() ; i++ ){
			map.matrix[i][14].unit = unitsOfCarthage.get(i);
		}
	}

	public static void prepareOrderLists(SimulationEngine simulationEngine) {	
		simulationEngine.orderListForRome.clear();
		simulationEngine.orderListForCarthage.clear();
		
		Runnable orderMakerForRome = () -> { generateOrderList(simulationEngine.map, simulationEngine.orderListForRome, Nation.ROME);};
		Runnable orderMakerForCarthage = () -> { generateOrderList(simulationEngine.map, simulationEngine.orderListForCarthage, Nation.CARTHAGE);};
		
		Thread r = new Thread(orderMakerForRome);
		Thread c = new Thread(orderMakerForCarthage);
		
		r.start();
		c.start(); 
		
		try {
			r.join();
			c.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	public static void generateOrderList(Map map, List<Order> orderListForRome , Nation nation) {
		for( int h=0 ; h < map.height ; h++ ){
			for( int w=0 ; w < map.width ; w++ ){
				if( isCellAbleToTakeOrder(map.matrix[h][w], nation) ){
					Cell orderTaker = map.matrix[h][w];

					for( int i=0 ; i < orderTaker.numberOfNeighbours ; i++){
						if( isNeighbourPossibleDestination(orderTaker.neighbours[i], nation) ){					
							orderListForRome.add( new Order(orderTaker, orderTaker.neighbours[i]) );
						}
					}
				}
			}
		}
	}
	
	private static boolean isCellAbleToTakeOrder(Cell cell, Nation nation) {
		if( cell.unit != null && cell.unit.getNation() == nation && cell.unit.getState()==UnitState.IDLE ){
			return true;
		}
		else
			return false;
	}

	private static boolean isNeighbourPossibleDestination(Cell cell, Nation nation) {
		if( cell.unit==null || ( cell.unit!=null && cell.unit.getNation()!=nation ) ){
			return true;
		}
		else
			return false;
	}
	
	public static void resolveFightsOnMap(Map map) {
		for( int h=0 ; h < map.height ; h++ ){
			for( int w=0 ; w < map.width ; w++ ){
				if( map.matrix[h][w].unit!=null ){
					checkFightInCell( map.matrix[h][w] );
				}
			}
		}
	}
	
	private static void checkFightInCell(Cell current) {
		Unit defender = current.unit;
		Unit attacker;
		
		for( int k=0 ; k < current.numberOfNeighbours ; k++ ){
			if( current.neighbours[k].unit != null ){
				attacker = current.neighbours[k].unit;							

				if( isUnitUnderAttack(attacker, defender) ){	
					applyDamage( defender, FightCalculator.calculateTotalDamage(attacker, defender) );
				}
			}
		}
		
		for( int k=0 ; k < current.numberOfNeighbours ; k++ ){
			if( current.neighbours[k].unit != null ){
				attacker = current.neighbours[k].unit;							

				if( isUnitUnderAttack(attacker, defender) ){	
					checkCellStatusAfterFight( current );
					checkNeighbourStatusAfterFight( current, current.neighbours[k] );
				}
			}
		}
	}
	
	private static boolean isUnitUnderAttack(Unit attacker, Unit defender) {
		if( attacker.getState() == UnitState.FIGTHING &&
				attacker.getDestination()!=null &&
				attacker.getDestination().unit!=null &&
				attacker.getDestination().unit.equals(defender) ){
			return true;
		}
		else
			return false;
	}
	
	private static void applyDamage(Unit defender, Integer damage) {
		int oldNrOfSoldiers = defender.getNrOfSoldiers();
		int newNrOfSoldiers = oldNrOfSoldiers - damage/defender.getArchetype().getHealth();
		defender.setNrOfSoldiers( newNrOfSoldiers );
	}
	
	private static void checkCellStatusAfterFight(Cell current) {
		Unit defender = current.unit;
		
		if( defender.getNrOfSoldiers() <= 0 ){
			defender.setState(UnitState.DEAD);
		}
		else if( defender.getMorale() <= 0 ){
			defender.setState(UnitState.FLEEING);
		}
	}

	private static void checkNeighbourStatusAfterFight(Cell current, Cell neighbour) {
		if( current.unit.getState() == UnitState.DEAD || current.unit.getState() == UnitState.FLEEING ){
			Unit attacker = neighbour.unit;
			current.unit=null;
			attacker.setState( UnitState.MOVING );
		}
	}



	public static void checkMorale() {
		// TODO Auto-generated method stub
		
	}



	public static void moveUnits(Map map) {
		for( int h=0 ; h < map.height ; h++ ){
			for( int w=0 ; w < map.width ; w++ ){
				Cell current = map.matrix[h][w];
				if( checkIfCellMayMove(current) ){
					Unit mover = current.unit;
					mover.setDistanceTravelled( mover.getDistanceTravelled() + mover.getArchetype().getSpeed() );
					Cell destination = mover.getDestination();
					
					if (destination == null){
						setMoverIdle(mover);
						break;
					}
					
					if( mover.getDistanceTravelled() >= MapConstants.distance ){
						if( destination.unit == null ){
							destination.unit = mover;
							current.unit=null;
							setMoverIdle(mover);
						}
						else if( destination.unit != null && destination.unit.getNation() != mover.getNation() ){
							Unit defender = mover.getDestination().unit;
							
							mover.setState( UnitState.FIGTHING );
							defender.setState( UnitState.FIGTHING );
							if( defender.getDestination()==null )
								defender.setDestination(current);
							
						}
						else if( destination.unit != null && destination.unit.getNation() == mover.getNation() ){
							setMoverIdle(mover);
						}						
					}
				}
			}
		}
	}

	private static boolean checkIfCellMayMove(Cell cell) {
		if( cell.unit!=null && (cell.unit.getState() == UnitState.MOVING || cell.unit.getState() == UnitState.FLEEING) ){
			return true;
		}
		return false;
	}

	private static void setMoverIdle(Unit mover) {
		mover.setState( UnitState.IDLE );
		mover.setDestination( null );
		mover.setDistanceTravelled( mover.getDistanceTravelled() - MapConstants.distance);
	}
	
	

	public static void applyNewOrders(Map map, Order orderOfRome, Order orderOfCarthage) {		
		if( orderOfRome != null && !orderOfRome.done  )
			setOrder(map, orderOfRome, Nation.ROME);
		
		if( orderOfCarthage != null && !orderOfCarthage.done )
			setOrder(map, orderOfCarthage, Nation.CARTHAGE);
	}

	private static void setOrder(Map map, Order order, Nation nation) {
		Cell current = map.matrix[order.from.x][order.from.y];
		Cell destination = map.matrix[order.to.x][order.to.y];
				
		if( current.unit!=null && current.unit.getNation()==nation && current.unit.getState()==UnitState.IDLE ){
			current.unit.setState(UnitState.MOVING);
			current.unit.setDestination(destination);
		}
		
		order.done=true;
	}
	
	

	public static boolean checkIfCompleted(Map map, Nation winner) {
		boolean isRomeDefeated = true;
		boolean isCarthageDefeated = true;
		
		for( int h=0 ; h < map.height; h++ ){
			for( int w=0 ; w < map.width ; w++ ){
				Cell current = map.matrix[h][w];
				if( current.unit!= null && current.unit.getState() != UnitState.FLEEING ){
					if( current.unit.getNation() == Nation.ROME ){
						isRomeDefeated = false;
					}
					if( current.unit.getNation() == Nation.CARTHAGE ){
						isCarthageDefeated = false;
					}
				}
			}
		}
		if(isRomeDefeated){
			winner = Nation.ROME;
			return isRomeDefeated;
		}
		if(isCarthageDefeated){
			winner = Nation.CARTHAGE;
			return isCarthageDefeated;
		}
		
		return false;		
	}
	
	
	
	public static void copyVariables(SimulationEngine copy, SimulationEngine simulationEngine) {
		copy.map = MapService.createMap();
		MapService.copyMap(copy.map, simulationEngine.map );
		
		copy.orderListForRome =  Collections.synchronizedList( new LinkedList<Order>() );
		copy.orderListForCarthage = Collections.synchronizedList( new LinkedList<Order>() );
		copy.orderOfRome = new Order( CellService.createCell(0, 0, 0), CellService.createCell(0, 1, 1));
		copy.orderOfCarthage = new Order( CellService.createCell(1, 1, 0), CellService.createCell(0, 0, 0));
		
		copy.iteration = new Integer( simulationEngine.iteration );
		copy.speedLimiter = new Long(0);
		
		copy.statistics = new SimulationStatistics();
		copy.endingFlag = new Boolean( simulationEngine.endingFlag );	
	}
	
}
