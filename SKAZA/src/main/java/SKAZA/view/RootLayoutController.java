package SKAZA.view;

import java.io.File;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import org.controlsfx.dialog.Dialogs;

import SKAZA.MainApp;
import SKAZA.core.SimulationEngine;
import SKAZA.core.repository.UnitArchetypeRepository;


public class RootLayoutController {
	
    private MainApp mainApp;
    
    @FXML
    private void handleNew() {
        UnitArchetypeRepository.archetypeData.clear();
    }

    @FXML
    private void handleOpen() {

    }

    @FXML
    private void handleSave() {
        File unitArchetypeFile = UnitArchetypeRepository.getUnitArchetypeFilePath();
        if (unitArchetypeFile != null) {
            UnitArchetypeRepository.saveUnitArchetypeDataToFile(unitArchetypeFile);
        } else {
            handleSaveAs();
        }
    }

    @FXML
    private void handleSaveAs() {

    }

    @FXML
    private void handleAbout() {
        Dialogs.create()
            .title("AddressApp")
            .masthead("About")
            .message("Author: Marco Jakob\nWebsite: http://code.makery.ch")
            .showInformation();
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
