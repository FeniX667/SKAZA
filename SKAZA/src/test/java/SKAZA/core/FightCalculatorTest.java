package skaza.core;

import javax.inject.Inject;

import skaza.core.math.calculators.FightCalculator;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class FightCalculatorTest extends TestCase {

    public FightCalculatorTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( FightCalculatorTest.class );
    }

    public void testDoKill()
    {
    	//given
    	FightCalculator fightCalculator = new FightCalculator();
    	fightCalculator.a = 5;
    	fightCalculator.b = 3;
    	    	
    	//then
    	Integer result = fightCalculator.doKill(10, 5);
    	
    	//then  	
        assertTrue( result == 1 );
    }
}
