package SKAZA.genetic;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import SKAZA.core.models.unitArchetype.UnitArchetype;
import SKAZA.core.repository.UnitArchetypeRepository;
import SKAZA.core.service.UnitArchetypeService;

public class GeneticAlgorithm {

	public Criteria c;
	public Mutators m;
	public List<Speciman> population;
	public List<Speciman> bestSpecimen;
	public Boolean endingFlag;
	public Integer iteration;
	
	public GeneticAlgorithm(Criteria c, Mutators m){
		this.c = c;
		this.m = m;
		endingFlag = false;
		iteration = new Integer(0);
		
		population = new LinkedList<Speciman>();
		for( int i=0 ; i < c.startingPopulation ; i++ ){
			population.add( randomSpeciman() );
		}

		bestSpecimen = new LinkedList<Speciman>();
	}
	
	private Speciman randomSpeciman() {
		List<UnitArchetype> randomArchetypeList = new LinkedList<UnitArchetype>();
		
		for( int i=0 ; i < UnitArchetypeRepository.archetypeData.size() ; i++){
			UnitArchetype ua = UnitArchetypeService.createRandomly( m, i );
			randomArchetypeList.add( ua );
		}
		
		return  new Speciman(randomArchetypeList);
	}

	public void run() {
		crossing();
		mutation();
		selection();
		iteration++;
	}

	private void crossing() {
		List<Speciman> tmpPopulation = new LinkedList<Speciman>();
		tmpPopulation.addAll( population );
		Random rndIndex = new Random();
		
		for(int i=0 ; i< (population.size()/2) ; i++ ){
			int index = rndIndex.nextInt( population.size() );
			Speciman first = population.get( index );
			population.remove( index );
			
			index = rndIndex.nextInt( population.size() );
			Speciman second = population.get( index );
			population.remove( index );
			
			Speciman firstChild = cross(first, second);
			Speciman secondChild = cross(second, first);
			
			tmpPopulation.add(firstChild);
			tmpPopulation.add(secondChild);
		}
		
		population.addAll( tmpPopulation );
	}

	private Speciman cross(Speciman first, Speciman second) {
		Speciman child = new Speciman( UnitArchetypeService.create(first.archetypeList) );
		Random rnd = new Random();
		
		int archetypeIndex = rnd.nextInt( child.archetypeList.size() );
		
		child.archetypeList.set( archetypeIndex, UnitArchetypeService.create(second.archetypeList.get( archetypeIndex )) );
		
		return child;
	}

	private void mutation() {
		Random rnd = new Random();
		
		for( Speciman s : population ){
			if( rnd.nextInt(100) < m.mutationChance ){
				mutate(s);
			}
		}
		
	}

	private void mutate(Speciman s) {
		Random rnd = new Random();
		
		UnitArchetype tmpUa = s.archetypeList.get( rnd.nextInt( s.archetypeList.size() ) );
		
		if( m.attack ){
			tmpUa.setAttack( tmpUa.getAttack() + rnd.nextInt( m.mutationScope ) - m.mutationScope/2 );
			if( tmpUa.getAttack()<1 )
				tmpUa.setAttack(1);
		}
		
		if( m.defense ){
			tmpUa.setDefense( tmpUa.getDefense() + rnd.nextInt( m.mutationScope ) - m.mutationScope/2 );
			if( tmpUa.getDefense()<1 )
				tmpUa.setDefense(1);
		}
		
		if( m.damage ){
			tmpUa.setDamage( tmpUa.getDamage() + rnd.nextInt( m.mutationScope ) - m.mutationScope/2 );
			if( tmpUa.getDamage()<1 )
				tmpUa.setDamage(1);
		}
		
		if( m.health ){
			tmpUa.setHealth( tmpUa.getHealth() + rnd.nextInt( m.mutationScope ) - m.mutationScope/2 );
			if( tmpUa.getHealth()<1 )
				tmpUa.setHealth(1);
		}
		
		if( m.speed ){
			tmpUa.setSpeed( tmpUa.getSpeed() + rnd.nextInt( m.mutationScope ) - m.mutationScope/2 );
			if( tmpUa.getSpeed()<1 )
				tmpUa.setSpeed(1);
		}
		
		if( m.effectivity ){
			tmpUa.setEffectiveAmountOfFighters( tmpUa.getEffectiveAmountOfFighters() + rnd.nextInt( m.mutationScope ) - m.mutationScope/2 );
			if( tmpUa.getEffectiveAmountOfFighters()<1 )
				tmpUa.setEffectiveAmountOfFighters(1);
		}
	}

	private void selection() {
		for( Speciman s : population ){
			s.startSelection();
			
			s.adaptation += Math.abs( c.iterationsPerGame - s.averageIterationsPerGame )/c.iterationsPerGame;
			s.adaptation += Math.abs( c.romeToCarthageStrengthRatio - s.strengthRatio )/c.romeToCarthageStrengthRatio;
			s.adaptation += Math.abs( c.romeToCarthageWinRatio - s.winRatio )/c.romeToCarthageWinRatio;
			
		}
		
	    Collections.sort(population, (Speciman s1, Speciman s2) -> s1.adaptation.compareTo(s2.adaptation));
	    
	    bestSpecimen.add( population.get(0) );
	    
	    int popSize = population.size();
	    for( int i=0 ; i< popSize/2 ; i++){
	    	population.remove( popSize -1 -i );
	    }
	    
	}
		
}
