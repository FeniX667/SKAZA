package SKAZA.genetic;

public class Criteria {
	public Integer iterationsPerGame;
	public Integer startingPopulation;
	public Double romeToCarthageStrengthRatio;
	public Double romeToCarthageWinRatio;
	
	public Criteria(Integer iterationsPerGame, Integer startingPopulation, Double strRatio, Double winRatio){
		this.iterationsPerGame = iterationsPerGame;
		this.startingPopulation = startingPopulation;
		this.romeToCarthageStrengthRatio = strRatio;
		this.romeToCarthageWinRatio = winRatio;
	}
}
