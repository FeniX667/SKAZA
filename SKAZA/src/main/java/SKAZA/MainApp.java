package SKAZA;

import java.io.IOException;

import SKAZA.core.SimulationEngineThread;
import SKAZA.core.models.unit.UnitArchetype;
import SKAZA.core.service.UnitArchetypeService;
import SKAZA.view.UnitArchetypeEditDialogController;
import SKAZA.view.UnitArchetypeNewDialogController;
import SKAZA.view.UnitArchetypeOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<UnitArchetype> archetypeData = FXCollections.observableArrayList();
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SKAZA/SCAR 0.1");

        initRootLayout();
        showUnitArchetypeOverview();
        
    }
    
    public MainApp() {

    	SimulationEngineThread mainSimulationEngineThread = new SimulationEngineThread();
    	   	
    	/*mainSimulationEngineThread.threadController.start();
		mainSimulationEngineThread.threadController.suspend();
        mainSimulationEngineThread.threadController.resume();*/
    	
    	archetypeData.add( UnitArchetypeService.createHalberdier() );
    	archetypeData.add( UnitArchetypeService.create(
        		"Goblin", 4, 2, 2, 5, 5, 100));
    }

	public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public void showMap(){
		
	}
	
    public void showUnitArchetypeOverview() {
        try {	
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/UnitArchetypeOverview.fxml"));
            AnchorPane unitArchetypeOverview = (AnchorPane) loader.load();

            rootLayout.setCenter(unitArchetypeOverview);
            
            UnitArchetypeOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showUnitArchetypeNewDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/UnitArchetypeNewDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New unit archetype");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Set the person into the controller.
            UnitArchetypeNewDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showUnitArchetypeEditDialog(UnitArchetype unitArchetype) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/UnitArchetypeEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit unit archetype");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Set the person into the controller.
            UnitArchetypeEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);
            controller.setUnitArchetype(unitArchetype);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }    
    
    public ObservableList<UnitArchetype> getArchetypeData() {
		return archetypeData;
	}

    public static void main(String[] args) {
        launch(args);
    }
}