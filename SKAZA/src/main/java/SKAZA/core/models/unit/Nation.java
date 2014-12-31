package SKAZA.core.models.unit;

public enum Nation {
	ROME, CARTHAGE;
	
	public String toCommander(Nation nation){
		if( nation==this.ROME )
			return "Scypion";
		else
			return "Hannibal";
					
	}
}
