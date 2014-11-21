package SKAZA.core.math.calculators;

import SKAZA.core.math.constants.FightConstants;
import SKAZA.core.models.unit.Unit;
import SKAZA.core.models.unit.UnitArchetype;

public class FightCalculator {

	public static Integer calculateTotalDamage(Unit attacker, Unit defender){
		Integer baseDamage = calculateBaseDamage(attacker);
		Integer ADD = calculateAttackDefenseDifference(attacker.getArchetype(), defender.getArchetype());
		Double fightParameter = chooseFightParameter(ADD);
		
		return new Integer( (int) (baseDamage * (1 + fightParameter * ADD)) );
	}

	private static Integer calculateBaseDamage(Unit attacker) {
		return new Integer( attacker.getNrOfSoldiers() * attacker.getArchetype().getDamage() );
	}
	
	private static Integer calculateAttackDefenseDifference(UnitArchetype attackerArchetype,
			UnitArchetype defenderArchetype) {
		Integer ADD = new Integer(0);
		ADD = attackerArchetype.getAttack() - defenderArchetype.getDefense();
		roundADD( ADD );
		
		return ADD;
	}
	
	private static void roundADD(Integer aDD) {
		if( aDD > FightConstants.maxADD )
			aDD = FightConstants.maxADD;
		else if( aDD < FightConstants.minADD )
			aDD = FightConstants.minADD;
	}
	
	private static Double chooseFightParameter(Integer aDD) {
		if( aDD >= 0 )
			return new Double( FightConstants.attackParameter );
		else
			return new Double( FightConstants.defenseParameter );
	}
	
}
