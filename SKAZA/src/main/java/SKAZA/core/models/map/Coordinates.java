package SKAZA.core.models.map;

public class Coordinates {
	public int x;
	public int y;
	
	public Coordinates(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString(){
		return new String( "("+ y +","+ x +")" );
	}
}
