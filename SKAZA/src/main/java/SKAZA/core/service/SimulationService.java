package SKAZA.core.service;

import java.util.ArrayList;

import SKAZA.core.SimulationEngine;
import SKAZA.core.math.calculators.FightCalculator;
import SKAZA.core.math.constants.MapConstants;
import SKAZA.core.models.map.Cell;
import SKAZA.core.models.map.Map;
import SKAZA.core.models.order.Order;
import SKAZA.core.models.unit.Nation;
import SKAZA.core.models.unit.Unit;
import SKAZA.core.models.unit.UnitState;

public class SimulationService {

	public static void setArmiesOnMap(Map map, ArrayList<Unit> unitsOfRome,
			ArrayList<Unit> unitsOfCarthage) {
		
		for( int i=0 ; i < unitsOfRome.size() ; i++ ){
			map.matrix[i][0].unit = unitsOfRome.get(i);
		}
		
		for( int i=0 ; i < unitsOfCarthage.size() ; i++ ){
			map.matrix[i][map.lastRow].unit = unitsOfCarthage.get(i);
		}
	}

	
	
	public static void checkFightsOnMap(Map map) {
		for( int h=0 ; h < map.height ; h++ ){
			for( int w=0 ; w < map.width ; w++ ){
				if( map.matrix[h][w].unit!=null ){
					checkFightInCell(map.matrix[h][w]);
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
					checkCellStatusAfterFight( current );
					checkNeighbourStatusAfterFight( current, current.neighbours[k] );
				}
			}
		}
	}
	
	private static boolean isUnitUnderAttack(Unit attacker, Unit defender) {
		if( attacker.getState() == UnitState.FIGTHING && attacker.getFightingWith().equals(defender) ){
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
		
		if( defender.getNrOfSoldiers()<0 ){
			defender.setState(UnitState.DEAD);
		}
		else if( defender.getMorale() <= 0 ){
			defender.setState(UnitState.FLEEING);
		}
	}

	private static void checkNeighbourStatusAfterFight(Cell current, Cell neighbour) {
		if( current.unit.getState() == UnitState.DEAD || current.unit.getState() == UnitState.FLEEING ){
			Unit attacker = neighbour.unit;
			attacker.setState( UnitState.IDLE );
			attacker.setFightingWith( null );
		}
	}



	public static void checkMorale() {
		// TODO Auto-generated method stub
		
	}



	public static void moveUnits() {
		// TODO Auto-generated method stub
		
	}



	public static void applyNewOrders() {
		// TODO Auto-generated method stub
		
	}



	public static ArrayList<Order> generateOrderList(Map map, Nation nation) {
		ArrayList<Order> orderList = new ArrayList<Order>();
		for( int h=0 ; h < map.height ; h++ ){
			for( int w=0 ; w < map.width ; w++ ){
				if( isCellAbleToTakeOrder(map.matrix[h][w], nation) ){
					Cell orderTaker = map.matrix[h][w];

					for( int i=0 ; i < orderTaker.numberOfNeighbours ; i++){
						if( isNeighbourPossibleDestination(orderTaker.neighbours[i], nation) ){					
							orderList.add( new Order(orderTaker, orderTaker.neighbours[i]) );
						}
					}
				}
			}
		}
		
		return orderList;
	}

	private static boolean isCellAbleToTakeOrder(Cell cell, Nation nation) {
		if( cell.unit != null && cell.unit.getNation() == nation ){
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

}
