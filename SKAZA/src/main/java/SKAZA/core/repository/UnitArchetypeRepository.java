package SKAZA.core.repository;

import java.io.File;
import java.util.List;
import java.util.prefs.Preferences;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.controlsfx.dialog.Dialogs;

import SKAZA.MainApp;
import SKAZA.core.models.unitArchetype.UnitArchetype;
import SKAZA.core.models.unitArchetype.UnitArchetypeListWrapper;

public class UnitArchetypeRepository {
    
	public static ObservableList<UnitArchetype> archetypeData = FXCollections.observableArrayList();
	
	public static void initialize(){
        File file = UnitArchetypeRepository.getUnitArchetypeFilePath();
        if (file != null) {
        	UnitArchetypeRepository.loadUnitArchetypeDataFromFile(file);
        }
	}
	
    public static void loadUnitArchetypeDataFromFile(File file) {
    	try {
            JAXBContext context = JAXBContext
                    .newInstance(UnitArchetypeListWrapper.class);
    		Unmarshaller um = context.createUnmarshaller();
    		um.unmarshal(file);
    	} catch (Exception e) { // catches ANY exception
            Dialogs.create()
            .title("Error")
            .masthead("Could not load data from file:\n" + file.getPath() + "\nChanging to original file.");
    		file = new File( new String( System.getProperty("user.dir") + "/resources/datebase/archetypes/original.xml" ) );
    	}
    	
    	
        try {       	
            JAXBContext context = JAXBContext
                    .newInstance(UnitArchetypeListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            UnitArchetypeListWrapper wrapper = (UnitArchetypeListWrapper) um.unmarshal(file);

            archetypeData.clear();
            archetypeData.addAll(wrapper.getUnitArchetypes());

            // Save the file path to the registry.
            setUnitArchetypeFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Dialogs.create()
                    .title("Error")
                    .masthead("Could not load data from file:\n" + file.getPath())
                    .showException(e);
            System.exit(1);
        }
    }
    
    public static void saveUnitArchetypeDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(UnitArchetypeListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our unitArchetype data.
            UnitArchetypeListWrapper wrapper = new UnitArchetypeListWrapper();
            wrapper.setUnitArchetypes(archetypeData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setUnitArchetypeFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Dialogs.create().title("Error")
                    .masthead("Could not save data to file:\n" + file.getPath())
                    .showException(e);
        }
    }
    
    public static void saveUnitArchetypeDataToFile(File file, List<UnitArchetype> archetypeList){
        try {
            JAXBContext context = JAXBContext
                    .newInstance(UnitArchetypeListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our unitArchetype data.
            UnitArchetypeListWrapper wrapper = new UnitArchetypeListWrapper();
            wrapper.setUnitArchetypes(archetypeList);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setUnitArchetypeFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Dialogs.create().title("Error")
                    .masthead("Could not save data to file:\n" + file.getPath())
                    .showException(e);
        }
    }
    

    public static File getUnitArchetypeFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File( filePath );
        } else {
            return null;
        }
    }
    
    public static void setUnitArchetypeFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());
        } else {
            prefs.remove("filePath");
        }
    }
}
