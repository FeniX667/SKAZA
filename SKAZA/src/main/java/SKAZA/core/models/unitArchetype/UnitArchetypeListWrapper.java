package SKAZA.core.models.unitArchetype;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "unitArchetypes")
public class UnitArchetypeListWrapper {

    private List<UnitArchetype> unitArchetypes;

    @XmlElement(name = "unitArchetype")
    public List<UnitArchetype> getUnitArchetypes() {
        return unitArchetypes;
    }

    public void setUnitArchetypes(List<UnitArchetype> unitArchetypes) {
        this.unitArchetypes = unitArchetypes;
    }
}
