package SKAZA;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.controlsfx.dialog.Dialogs;

import SKAZA.ai.AI;
import SKAZA.ai.ElapsedTimer;
import SKAZA.core.SimulationEngine;
import SKAZA.core.models.order.Order;
import SKAZA.core.models.unit.Nation;
import SKAZA.core.models.unitArchetype.UnitArchetype;
import SKAZA.core.models.unitArchetype.UnitArchetypeListWrapper;
import SKAZA.core.repository.UnitArchetypeRepository;
import SKAZA.core.service.UnitArchetypeService;
import SKAZA.genetic.Criteria;
import SKAZA.genetic.GeneticAlgorithm;
import SKAZA.genetic.Mutators;
import SKAZA.view.RootLayoutController;
import SKAZA.view.simulation.SimulationOverviewController;
import SKAZA.view.unitArchetype.UnitArchetypeEditDialogController;
import SKAZA.view.unitArchetype.UnitArchetypeOverviewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private TabPane rootLayout;
    
    private Thread mainSimulation;		
	private SimulationEngine simulationEngine;
	
	private AI scipio;
	private AI hannibal;
	private Thread scipioThread;
	private Thread hannibalThread;
	
	private SimulationOverviewController mapController;

	public MainApp() {
    }
	
    @Override
    public void start(Stage primaryStage) {

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("SKAZA/SCAR 0.1");
		//this.primaryStage.getIcons().add(new Image("file:resources/images/SkazaIcon1.png"));
		this.primaryStage.resizableProperty().set( false );
				
		initRepositories();
		initSimulation();
		initAI();
		initRootLayout();	
		
		mapController.timer.start();
    }

	private void initRepositories() {
        UnitArchetypeRepository.initialize();
	}

	private void initSimulation() {	
		simulationEngine = new SimulationEngine();

		Runnable simulationIteration = () -> { simulationEngine.run(); };		
		mainSimulation = new Thread(simulationIteration);
	}
	
	private void initAI() {
		scipio = new AI( simulationEngine, Nation.ROME );
		hannibal = new AI( simulationEngine,  Nation.CARTHAGE );

		Runnable scipioThinking = () -> { scipio.run(); };		
		Runnable hannibalThinking = () -> { hannibal.run(); };		
		scipioThread = new Thread(scipioThinking);
		hannibalThread = new Thread(hannibalThinking);
		
	}

	public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (TabPane) loader.load();
            
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace(); 
        }
       
		showSimulationOverview();
		showUnitArchetypeOverview();
    }
 
    private void showSimulationOverview() {
        try {	
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/simulation/SimulationOverview.fxml"));
            HBox simulationOverview = (HBox) loader.load();

            rootLayout.getTabs().get(0).setContent(simulationOverview);
            
            mapController = loader.getController();
            mapController.setMainApp(this);
            mapController.printUnits();
                       
        } catch (IOException e) {
            e.printStackTrace();
        }
        
	}
    
    public void showUnitArchetypeOverview() {
        try {	
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/unitArchetype/UnitArchetypeOverview.fxml"));
            AnchorPane unitArchetypeOverview = (AnchorPane) loader.load();

            rootLayout.getTabs().get(1).setContent(unitArchetypeOverview);
            
            UnitArchetypeOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showUnitArchetypeEditDialog(UnitArchetype unitArchetype) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/unitArchetype/UnitArchetypeEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit unit archetype");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Set the unitArchetype into the controller.
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
    
    public void reset(){
		initSimulation();
		initAI();
		initRootLayout();
		
		mapController.timer.start();
    }

    public void repaint(){
    	mapController.printUnits();
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }    
    
    
	public Thread getMainSimulation() {
		return mainSimulation;
	}

	public SimulationEngine getSimulationEngine() {
		return simulationEngine;
	}

	public AI getScipio() {
		return scipio;
	}

	public AI getHannibal() {
		return hannibal;
	}

	public Thread getScipioThread() {
		return scipioThread;
	}

	public Thread getHannibalThread() {
		return hannibalThread;
	}
	public static void main(String[] args) {
        launch(args);
        System.exit(0);
    }
}