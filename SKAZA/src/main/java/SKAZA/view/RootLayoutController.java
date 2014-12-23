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
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        
        fileChooser.setInitialDirectory( new File( new String( System.getProperty("user.dir") + "/resources/datebase/archetypes"  ) ) );

        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            UnitArchetypeRepository.loadUnitArchetypeDataFromFile(file);
        }
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
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        fileChooser.setInitialDirectory( new File( new String( System.getProperty("user.dir") + "/resources/datebase/archetypes"  ) ) );

        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            UnitArchetypeRepository.saveUnitArchetypeDataToFile(file);
        }
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
