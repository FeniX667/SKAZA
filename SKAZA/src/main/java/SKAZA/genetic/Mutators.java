package SKAZA.genetic;

public class Mutators {
	public boolean attack, defense, damage, health, speed, effectivity;
	public Integer mutationChance, mutationScope;
	
	public Mutators(boolean attack,
			boolean defense,
			boolean damage,
			boolean health,
			boolean speed,
			boolean effectivity,
			Integer mutationChance,
			Integer mutationScope){
		
		this.attack = attack;
		this.defense = defense;
		this.damage = damage;
		this.health = health;
		this.speed = speed;
		this.effectivity = effectivity;
		this.mutationChance = mutationChance;
		this.mutationScope = mutationScope;
	}
}
