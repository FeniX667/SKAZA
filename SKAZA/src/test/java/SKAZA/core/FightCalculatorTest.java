package SKAZA.core;

import javax.inject.Inject;

import SKAZA.core.math.calculators.FightCalculator;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class FightCalculatorTest extends TestCase {

    public void testDoKill()
    {
    	//given
    	FightCalculator fightCalculator = new FightCalculator();
    	fightCalculator.a = 5;
    	fightCalculator.b = 3;
    	    	
    	//when
    	Integer result = fightCalculator.doKill(10, 5);
    	
    	//then  	
        assertTrue( result == 13 );
    }
}
