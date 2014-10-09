package skaza.core.math.calculators;

public class FightCalculator {
	public Integer a;
	public Integer b;
	
	public Integer doKill(Integer a, Integer b){
		return a-b + this.a + this.b;
	}
}
