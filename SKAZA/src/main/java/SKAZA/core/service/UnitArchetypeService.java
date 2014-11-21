package SKAZA.core.service;

import SKAZA.core.models.unit.UnitArchetype;

public class UnitArchetypeService {

	public static UnitArchetype createEmptyArchetype(){
		UnitArchetype archetype = new UnitArchetype();
		
		archetype.setAttack( new Byte("1") );
		archetype.setDamage( new Byte("1") );
		archetype.setDefense( new Byte("1") );
		archetype.setEffectiveAmountOfFighters( new Integer(1) );
		archetype.setHealth(new Byte("1"));
		archetype.setName( new String("1"));
		archetype.setSpeed( new Byte("1"));

		return archetype;
	}
	
	public static UnitArchetype createHalberdier(){
		UnitArchetype archetype = new UnitArchetype();

		archetype.setName( new String("Halabardier"));
		archetype.setAttack( new Byte("6") );
		archetype.setDefense( new Byte("5") );
		archetype.setDamage( new Byte("3") );
		archetype.setHealth(new Byte("10"));
		archetype.setSpeed( new Byte("5"));
		archetype.setEffectiveAmountOfFighters( new Integer(100) );

		return archetype;
	}
}
