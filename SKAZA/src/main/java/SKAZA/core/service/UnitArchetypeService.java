package SKAZA.core.service;

import SKAZA.core.models.unitArchetype.UnitArchetype;

public class UnitArchetypeService {

	public static UnitArchetype createHalberdier(){
		UnitArchetype archetype = new UnitArchetype();
		
		archetype.setName( new String("Halabardier") );
		archetype.setAttack( new Integer(6) );
		archetype.setDefense( new Integer(5) );
		archetype.setDamage( new Integer(3) );
		archetype.setHealth(new Integer(10));
		archetype.setSpeed( new Integer(5));
		archetype.setEffectiveAmountOfFighters( new Integer(100) );

		return archetype;
	}	
	
	public static UnitArchetype create(String name, 
			Integer attack, Integer defense, 
			Integer damage, Integer health, 
			Integer speed, Integer effectiveAmountOfFighters){
		UnitArchetype archetype = new UnitArchetype();

		archetype.setName( new String(name));
		archetype.setAttack( new Integer(attack) );
		archetype.setDefense( new Integer(defense) );
		archetype.setDamage( new Integer(damage) );
		archetype.setHealth(new Integer(health));
		archetype.setSpeed( new Integer(speed));
		archetype.setEffectiveAmountOfFighters( new Integer(effectiveAmountOfFighters) );

		return archetype;
	}
	
	public static UnitArchetype create(String name, 
			String attack, String defense, 
			String damage, String health,
			String speed, String effectiveAmountOfFighters){
		UnitArchetype archetype = new UnitArchetype();
		
		archetype.setName( new String(name) );
		archetype.setAttack( new Integer(attack) );
		archetype.setDefense( new Integer(defense) );
		archetype.setDamage( new Integer(damage) );
		archetype.setHealth(new Integer(health) );
		archetype.setSpeed( new Integer(speed) );
		archetype.setEffectiveAmountOfFighters( new Integer(effectiveAmountOfFighters) );

		return archetype;
	}

	public static UnitArchetype edit(UnitArchetype archetype, String name,
			String attack, String defense,
			String damage, String health,
			String speed, String effectiveAmountOfFighters) {

		archetype.setName(new String (name) );
		archetype.setAttack( new Integer(attack) );
		archetype.setDefense( new Integer(defense) );
		archetype.setDamage( new Integer(damage) );
		archetype.setHealth(new Integer(health) );
		archetype.setSpeed( new Integer(speed) );
		archetype.setEffectiveAmountOfFighters( new Integer(effectiveAmountOfFighters) );
		
		return null;
	}
}