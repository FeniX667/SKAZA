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
import SKAZA.core.models.unitArchetype.UnitArchetype;
import SKAZA.core.repository.UnitArchetypeRepository;

public class SimulationEngineService {

	MapService mapService;
	CellService cellService;
	UnitService unitService;
	List<UnitArchetype> archetypeList;
	
	public SimulationEngineService(){
		mapService = new MapService();
		cellService = new CellService();
		unitService = new UnitService();
		archetypeList = new ArrayList<UnitArchetype>( UnitArchetypeRepository.archetypeData );
	}
	
	public SimulationEngineService(List<UnitArchetype> generatedArchetypes) {
		mapService = new MapService();
		cellService = new CellService();
		unitService = new UnitService();
		archetypeList = generatedArchetypes;
	}

	public void initializeVariables(SimulationEngine simulationEngine) {
		simulationEngine.map = mapService.createMap();
		
		simulationEngine.orderListForRome = Collections.synchronizedList( new LinkedList<Order>() );
		simulationEngine.orderListForCarthage = Collections.synchronizedList( new LinkedList<Order>() );
		simulationEngine.orderOfRome = new Order( cellService.createCell(0, 0, 0), cellService.createCell(0, 1, 1));
		simulationEngine.orderOfCarthage = new Order( cellService.createCell(1, 1, 0), cellService.createCell(0, 0, 0));
		
		simulationEngine.iteration = new Integer(0);
		simulationEngine.speedLimiter = new Long(0);
		
		simulationEngine.statisticsForCarthage = new SimulationStatistics(Nation.CARTHAGE);
		simulationEngine.statisticsForRome = new SimulationStatistics(Nation.ROME);
		
		simulationEngine.endingFlag = new Boolean(false);	
	}
	
	public void setArmiesOnMapDefault(Map map) {
	
		List<Unit> unitsOfRome = unitService.createArmy(Nation.ROME);
		List<Unit> unitsOfCarthage = unitService.createArmy(Nation.CARTHAGE);
		
		for( int i=1 ; i < unitsOfRome.size() ; i++ ){
			map.matrix[i][4].unit = unitsOfRome.get(i);
		}
		
		for( int i=1 ; i < unitsOfCarthage.size() ; i++ ){
			map.matrix[i][7].unit = unitsOfCarthage.get(i);
		}
		
	}	
	
	public void setArmiesOnMap(Map map) {
	
		//Prawe Skrzydło
		map.matrix[2][1].unit = unitService.createUnit( Nation.ROME, archetypeList.get(3), 1200 );
		map.matrix[2][0].unit = unitService.createUnit( Nation.ROME, archetypeList.get(3), 1200 );

		//Lewe Skrzydło
		map.matrix[2][11].unit = unitService.createUnit( Nation.ROME, archetypeList.get(0), 1200 );
		map.matrix[2][12].unit = unitService.createUnit( Nation.ROME, archetypeList.get(0), 1200 );
		map.matrix[1][11].unit = unitService.createUnit( Nation.ROME, archetypeList.get(0), 1200 );
		map.matrix[1][12].unit = unitService.createUnit( Nation.ROME, archetypeList.get(0), 1200 );
		
		
		//Centrum w 3 rzutach
		map.matrix[0][5].unit = unitService.createUnit( Nation.ROME, archetypeList.get(5), 6000 );
		map.matrix[1][6].unit = unitService.createUnit( Nation.ROME, archetypeList.get(5), 6000 );
		map.matrix[0][7].unit = unitService.createUnit( Nation.ROME, archetypeList.get(5), 6000 );

		map.matrix[1][4].unit = unitService.createUnit( Nation.ROME, archetypeList.get(5), 6000 );
		map.matrix[1][5].unit = unitService.createUnit( Nation.ROME, archetypeList.get(5), 6000 );
		map.matrix[2][6].unit = unitService.createUnit( Nation.ROME, archetypeList.get(5), 6000 );
		map.matrix[1][7].unit = unitService.createUnit( Nation.ROME, archetypeList.get(5), 6000 );
		map.matrix[1][8].unit = unitService.createUnit( Nation.ROME, archetypeList.get(5), 6000 );
		
		map.matrix[2][4].unit = unitService.createUnit( Nation.ROME, archetypeList.get(5), 6000 );
		map.matrix[2][5].unit = unitService.createUnit( Nation.ROME, archetypeList.get(5), 6000 );
		map.matrix[3][6].unit = unitService.createUnit( Nation.ROME, archetypeList.get(5), 6000 );
		map.matrix[2][7].unit = unitService.createUnit( Nation.ROME, archetypeList.get(5), 6000 );
		map.matrix[2][8].unit = unitService.createUnit( Nation.ROME, archetypeList.get(5), 6000 );
		
		
		//Lewe Skrzydło
		map.matrix[6][0].unit = unitService.createUnit( Nation.CARTHAGE, archetypeList.get(1), 1500 );
		map.matrix[7][0].unit = unitService.createUnit( Nation.CARTHAGE, archetypeList.get(1), 1500 );
		map.matrix[7][1].unit = unitService.createUnit( Nation.CARTHAGE, archetypeList.get(1), 1500 );
		map.matrix[6][1].unit = unitService.createUnit( Nation.CARTHAGE, archetypeList.get(1), 1500 );

		//Prawo Skrzydło
		map.matrix[7][11].unit = unitService.createUnit( Nation.CARTHAGE, archetypeList.get(3), 1000 );
		map.matrix[7][12].unit = unitService.createUnit( Nation.CARTHAGE, archetypeList.get(3), 1000 );
		map.matrix[6][12].unit = unitService.createUnit( Nation.CARTHAGE, archetypeList.get(3), 1000 );
		map.matrix[6][11].unit = unitService.createUnit( Nation.CARTHAGE, archetypeList.get(3), 1000 );

		//Centrum
		map.matrix[7][4].unit = unitService.createUnit( Nation.CARTHAGE, archetypeList.get(7), 6000 );
		map.matrix[7][6].unit = unitService.createUnit( Nation.CARTHAGE, archetypeList.get(7), 6000 );
		map.matrix[7][8].unit = unitService.createUnit( Nation.CARTHAGE, archetypeList.get(7), 6000 );
		
		map.matrix[7][5].unit = unitService.createUnit( Nation.CARTHAGE, archetypeList.get(6), 6000 );
		map.matrix[8][6].unit = unitService.createUnit( Nation.CARTHAGE, archetypeList.get(6), 6000 );
		map.matrix[7][7].unit = unitService.createUnit( Nation.CARTHAGE, archetypeList.get(6), 6000 );
	}

	public void prepareOrderLists(SimulationEngine simulationEngine) {	
		simulationEngine.orderListForRome.clear();
		simulationEngine.orderListForCarthage.clear();
		
		Map map = simulationEngine.map;
		
		for( int h=0 ; h < map.height ; h++ ){
			for( int w=0 ; w < map.width ; w++ ){
				if( isCellAbleToTakeOrder(map.matrix[h][w]) ){
					Cell orderTaker = map.matrix[h][w];

					for( int i=0 ; i < orderTaker.numberOfNeighbours ; i++){
						if( isNeighbourPossibleDestination(orderTaker.neighbours[i], orderTaker.unit.getNation()) ){							
							if( orderTaker.unit.getNation() == Nation.ROME )							
								simulationEngine.orderListForRome.add( new Order(orderTaker, orderTaker.neighbours[i]) );
							if( orderTaker.unit.getNation() == Nation.CARTHAGE )							
								simulationEngine.orderListForCarthage.add( new Order(orderTaker, orderTaker.neighbours[i]) );							
						}
					}
				}
			}
		}	
	}
	
	private boolean isCellAbleToTakeOrder(Cell cell) {
		if( cell.unit != null && cell.unit.getState()==UnitState.IDLE ){
			return true;
		}
		else
			return false;
	}

	private boolean isNeighbourPossibleDestination(Cell cell, Nation nation) {
		if( cell.unit==null || ( cell.unit!=null && cell.unit.getNation()!=nation ) ){
			return true;
		}
		else
			return false;
	}
	
	public void resolveFightsOnMap(Map map) {
		for( int h=0 ; h < map.height ; h++ ){
			for( int w=0 ; w < map.width ; w++ ){
				if( map.matrix[h][w].unit!=null ){
					checkFightInCell( map.matrix[h][w] );
				}
			}
		}
	}
	
	private void checkFightInCell(Cell current) {
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
	
	private boolean isUnitUnderAttack(Unit attacker, Unit defender) {
		if( attacker.getState() == UnitState.FIGTHING &&
				attacker.getDestination()!=null &&
				attacker.getDestination().unit!=null &&
				attacker.getDestination().unit.equals(defender) ){
			return true;
		}
		else
			return false;
	}
	
	private void applyDamage(Unit defender, Integer damage) {
		int oldNrOfSoldiers = defender.getNrOfSoldiers();
		int newNrOfSoldiers = oldNrOfSoldiers - damage/defender.getArchetype().getHealth();
		defender.setNrOfSoldiers( newNrOfSoldiers );
	}
	
	private void checkCellStatusAfterFight(Cell current) {
		Unit defender = current.unit;
		
		if( defender.getNrOfSoldiers() <= 0 ){
			defender.setState(UnitState.DEAD);
		}
		else if( defender.getMorale() <= 0 ){
			defender.setState(UnitState.FLEEING);
		}
	}

	private void checkNeighbourStatusAfterFight(Cell current, Cell neighbour) {
		if( current.unit.getState() == UnitState.DEAD || current.unit.getState() == UnitState.FLEEING ){
			Unit attacker = neighbour.unit;
			current.unit=null;
			attacker.setState( UnitState.MOVING );
		}
	}



	public void checkMorale() {
		// TODO Auto-generated method stub
		
	}



	public void moveUnits(Map map) {
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

	private boolean checkIfCellMayMove(Cell cell) {
		if( cell.unit!=null && (cell.unit.getState() == UnitState.MOVING || cell.unit.getState() == UnitState.FLEEING) ){
			return true;
		}
		return false;
	}

	private void setMoverIdle(Unit mover) {
		mover.setState( UnitState.IDLE );
		mover.setDestination( null );
		mover.setDistanceTravelled( mover.getDistanceTravelled() - MapConstants.distance);
	}
	
	

	public void applyNewOrders(Map map, Order orderOfRome, Order orderOfCarthage) {		
		if( orderOfRome != null && !orderOfRome.done )
			setOrder(map, orderOfRome, Nation.ROME);
		
		if( orderOfCarthage != null && !orderOfCarthage.done )
			setOrder(map, orderOfCarthage, Nation.CARTHAGE);
	}

	private void setOrder(Map map, Order order, Nation nation) {
		Cell current = map.matrix[order.from.x][order.from.y];
		Cell destination = map.matrix[order.to.x][order.to.y];
				
		if( current.unit!=null && current.unit.getNation()==nation && current.unit.getState()==UnitState.IDLE ){
			current.unit.setState(UnitState.MOVING);
			current.unit.setDestination(destination);
		}
		
		order.done=true;
	}
	
	

	public boolean checkIfCompleted(SimulationEngine simulationEngine) {
		Map map = simulationEngine.map;
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
			simulationEngine.winner = Nation.CARTHAGE;
			return isRomeDefeated;
		}
		if(isCarthageDefeated){
			simulationEngine.winner = Nation.ROME;
			return isCarthageDefeated;
		}
		
		
		return false;		
	}
	
	
	
	public void copyVariables(SimulationEngine copy, SimulationEngine simulationEngine) {
		copy.map = mapService.createMap();
		mapService.copyMap(copy.map, simulationEngine.map );
		
		copy.orderListForRome = Collections.synchronizedList( new LinkedList<Order>() );
		copy.orderListForCarthage = Collections.synchronizedList( new LinkedList<Order>() );
		copy.orderOfRome = new Order( cellService.createCell(0, 0, 0), cellService.createCell(0, 1, 1));
		copy.orderOfCarthage = new Order( cellService.createCell(1, 1, 0), cellService.createCell(0, 0, 0));
		
		copy.iteration = new Integer( simulationEngine.iteration );
		copy.speedLimiter = new Long(0);
		
		copy.statisticsForCarthage = new SimulationStatistics( Nation.CARTHAGE );
		copy.statisticsForRome = new SimulationStatistics( Nation.ROME );
		copy.endingFlag = new Boolean( simulationEngine.endingFlag );	
	}

	public SimulationStatistics generateStatisticsFor(SimulationEngine simulationEngine, Nation nation) {
		Map map = simulationEngine.map;
		SimulationStatistics ss = new SimulationStatistics( nation );
		
		for( int h=0 ; h < map.height ; h++ ){
			for( int w=0 ; w < map.width ; w++ ){
				Cell cell = map.matrix[h][w];
				if( cell.unit!=null &&  cell.unit.getNation()==nation ){
					ss.totalArmySize += cell.unit.getNrOfSoldiers();
					
					if( cell.unit.getArchetype().getSpeed() >= 10){
						ss.cavalrySize += cell.unit.getNrOfSoldiers();
						ss.cavalryStrength += unitService.calculateStrength( cell.unit ); 
					}
					else{
						ss.infantrySize += cell.unit.getNrOfSoldiers();
						ss.infantryStrength += unitService.calculateStrength( cell.unit ); 
					}
				}
			}
		}
		
		return ss;	
	}
}
