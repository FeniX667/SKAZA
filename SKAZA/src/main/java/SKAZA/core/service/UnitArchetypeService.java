package SKAZA.core.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import SKAZA.core.models.unitArchetype.UnitArchetype;
import SKAZA.core.repository.UnitArchetypeRepository;
import SKAZA.genetic.Mutators;
import SKAZA.genetic.Speciman;

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

	public static UnitArchetype edit(UnitArchetype archetype,
			String attack, String defense,
			String damage, String health,
			String speed, String effectiveAmountOfFighters) {

		archetype.setAttack( new Integer(attack) );
		archetype.setDefense( new Integer(defense) );
		archetype.setDamage( new Integer(damage) );
		archetype.setHealth(new Integer(health) );
		archetype.setSpeed( new Integer(speed) );
		archetype.setEffectiveAmountOfFighters( new Integer(effectiveAmountOfFighters) );
		
		return archetype;
	}

	public static UnitArchetype createRandomly(Mutators m, int i) {
		UnitArchetype archetype = new UnitArchetype();
		Random randomValue = new Random();
		
		archetype.setName( new String(UnitArchetypeRepository.archetypeData.get(i).getName()) );
		
		if( !m.attack )
			archetype.setAttack( UnitArchetypeRepository.archetypeData.get(i).getAttack() );
		else
			archetype.setAttack( randomValue.nextInt(20) +1 );
		
		if( !m.defense )
			archetype.setDefense( UnitArchetypeRepository.archetypeData.get(i).getDefense() );
		else
			archetype.setDefense( randomValue.nextInt(20) +1);
		
		if( !m.damage )
			archetype.setDamage( UnitArchetypeRepository.archetypeData.get(i).getDamage() );
		else
			archetype.setDamage( randomValue.nextInt(20) +1);
		
		if( !m.health )
			archetype.setHealth( UnitArchetypeRepository.archetypeData.get(i).getHealth() );
		else
			archetype.setHealth( randomValue.nextInt(20) +1);
		
		if( !m.speed )
			archetype.setSpeed( UnitArchetypeRepository.archetypeData.get(i).getSpeed() );
		else
			archetype.setSpeed( randomValue.nextInt(20) +1);
		
		if( !m.effectivity )
			archetype.setEffectiveAmountOfFighters( UnitArchetypeRepository.archetypeData.get(i).getEffectiveAmountOfFighters() );
		else
			archetype.setEffectiveAmountOfFighters( randomValue.nextInt(100) +100 );
		
		return archetype;
	}

	public static List<UnitArchetype> create(List<UnitArchetype> archetypeList) {
		List<UnitArchetype> newArchetypeList = new LinkedList<UnitArchetype>();
		
		for(UnitArchetype ua : archetypeList){
			newArchetypeList.add( create( ua ) );
		}
		return newArchetypeList;
	}

	public static UnitArchetype create(UnitArchetype ua) {	
		return create( ua.getName(), ua.getAttack(), ua.getDefense(), ua.getDamage(), ua.getHealth(), ua.getSpeed(), ua.getEffectiveAmountOfFighters() );
	}
}