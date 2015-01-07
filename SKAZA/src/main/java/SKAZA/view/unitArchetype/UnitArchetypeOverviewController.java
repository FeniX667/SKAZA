package SKAZA.view.unitArchetype;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Timer;

import org.controlsfx.dialog.Dialogs;

import SKAZA.MainApp;
import SKAZA.core.models.unitArchetype.UnitArchetype;
import SKAZA.core.repository.UnitArchetypeRepository;
import SKAZA.genetic.Criteria;
import SKAZA.genetic.GeneticAlgorithm;
import SKAZA.genetic.Mutators;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.FileChooser;

@SuppressWarnings("deprecation")
public class UnitArchetypeOverviewController {
	
	@FXML
    private TableView<UnitArchetype> unitArchetypeTable;
    @FXML
    private TableColumn<UnitArchetype, String> nameColumn;
    @FXML
    private TableColumn<UnitArchetype, Number> attackColumn;
    @FXML
    private TableColumn<UnitArchetype, Number> defenseColumn;
    @FXML
    private TableColumn<UnitArchetype, Number> damageColumn;
    @FXML
    private TableColumn<UnitArchetype, Number> healthColumn;
    @FXML
    private TableColumn<UnitArchetype, Number> speedColumn;
    @FXML
    private TableColumn<UnitArchetype, Number> effectivityColumn;

    @FXML
    private TextField iterationsPerGame;
    @FXML
    private TextField strengthRatio;
    @FXML
    private TextField winRatio;
    @FXML
    private TextField population;
    
    @FXML
    private ToggleButton startStopButton;
    @FXML
    private LineChart<String, Double> lineChart;
    
    @FXML
    private CheckBox attackCheckBox;
    @FXML
    private CheckBox defenseCheckBox;
    @FXML
    private CheckBox damageCheckBox;
    @FXML
    private CheckBox speedCheckBox;
    @FXML
    private CheckBox healthCheckBox;
    @FXML
    private CheckBox effectivityCheckBox;
    @FXML
    private Slider mutationChanceSlider;
    @FXML
    private Label mutationChanceLabel;
    @FXML
    private Slider mutationScopeSlider;
    @FXML
    private Label mutationScopeLabel;

    private MainApp mainApp;
    
    private GeneticAlgorithm geneticAlgorithm;
    
    private Thread geneticThread;
    
    public Timer timer;
    
    private boolean timerLock;
    
    public UnitArchetypeOverviewController() {
    }
    
    @FXML
    private void initialize() {
    	nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    	attackColumn.setCellValueFactory(cellData -> cellData.getValue().attackProperty());
    	defenseColumn.setCellValueFactory(cellData -> cellData.getValue().defenseProperty());
    	damageColumn.setCellValueFactory(cellData -> cellData.getValue().damageProperty());
    	healthColumn.setCellValueFactory(cellData -> cellData.getValue().healthProperty());
    	speedColumn.setCellValueFactory(cellData -> cellData.getValue().speedProperty());
    	effectivityColumn.setCellValueFactory(cellData -> cellData.getValue().effectiveAmountOfFightersProperty());
    	timerLock = false;
    			
    	mutationChanceSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
    		mutationChanceLabel.setText( new String( (int) mutationChanceSlider.getValue()+ "%" ) );
		});
				
    	mutationScopeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
        	mutationScopeLabel.setText( new String( (int) mutationScopeSlider.getValue()+"" ) );
		});
    	
		timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Platform.runLater(new Runnable() {
                    public void run() {
                    	if( timerLock ){
            				XYChart.Series<String, Double> series = new XYChart.Series<>();
            				series.getData().add( new XYChart.Data<>( geneticAlgorithm.iteration.toString(), geneticAlgorithm.population.get(0).adaptation ) );
            				lineChart.getData().add( series );
            				timerLock = false;
            				
            				UnitArchetypeRepository.saveUnitArchetypeDataToFile( 
            						new File( new String( System.getProperty("user.dir") + "/resources/datebase/archetypes/generated/" +geneticAlgorithm.iteration+ ".xml" ) ),
            						geneticAlgorithm.population.get( geneticAlgorithm.population.size()-1 ).archetypeList);
                    	}                   	
                    }
                });
            }
        });
		
		timer.start();
    }
    
    @FXML
    private void handleOpenUnitArchetype() {
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
    private void handleEditUnitArchetype() {
        UnitArchetype selectedUnitArchetype = unitArchetypeTable.getSelectionModel().getSelectedItem();
        if (selectedUnitArchetype != null) {
            mainApp.showUnitArchetypeEditDialog(selectedUnitArchetype);

        } else {
            // Nothing selected.
            Dialogs.create()
                .title("No Selection")
                .masthead("No UnitArchetype Selected")
                .message("Please select a person in the table.")
                .showWarning();
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
    private void handleStart() {
		if( geneticThread == null ){
			if( isInputValid() ){
				createGeneticThread();
				geneticThread.start();
			}
		}
		else if( startStopButton.isSelected() ){
			geneticThread.resume();
		}else if( !startStopButton.isSelected() ){
			geneticThread.suspend();
		}
		

    	Criteria c = new Criteria( Integer.parseInt(iterationsPerGame.getText()), Integer.parseInt(population.getText()), 
    			Double.parseDouble(strengthRatio.getText()), Double.parseDouble(winRatio.getText()) );
    	
    	Mutators m = new Mutators( attackCheckBox.isSelected(), defenseCheckBox.isSelected(), damageCheckBox.isSelected(),
    			healthCheckBox.isSelected(), speedCheckBox.isSelected(), effectivityCheckBox.isSelected(),
    			(int) mutationChanceSlider.getValue(), (int) mutationScopeSlider.getValue());

    	geneticAlgorithm.c = c;
    	geneticAlgorithm.m = m;
    }
	
	private boolean isInputValid() {
        String errorMessage = "";
        
        errorMessage += parseIntegerStringAndReturnErrorCode( iterationsPerGame, "Iterations" );
        errorMessage += parseDoubleStringAndReturnErrorCode( strengthRatio, "Strength ratio" );
        errorMessage += parseDoubleStringAndReturnErrorCode( winRatio, "Win ratio" );
        errorMessage += parseIntegerStringAndReturnErrorCode( population, "Population" );

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Dialogs.create()
                .title("Invalid Fields")
                .masthead("Please correct invalid fields")
                .message(errorMessage)
                .showError();
            return false;
        }
    }
   
	private String parseDoubleStringAndReturnErrorCode(TextField numberField, String valueName) {
        if (numberField.getText() == null || numberField.getText().length() == 0) {
            return "No valid " + valueName +" field!\n"; 
        }else {
            try {
                Double.parseDouble(numberField.getText());
            } catch (NumberFormatException e) {
            	return valueName + " must be an double value!"; 
            }
        }
    	return "";   
	}

	private String parseIntegerStringAndReturnErrorCode( TextField numberField, String valueName ){
        if (numberField.getText() == null || numberField.getText().length() == 0) {
            return "No valid " + valueName +" field!\n"; 
        }else {
            try {
                Integer.parseInt(numberField.getText());
            } catch (NumberFormatException e) {
            	return valueName + " must be an integer value!"; 
            }
        }
    	return "";    	
    }
	
	private void createGeneticThread() {
    	Criteria c = new Criteria( Integer.parseInt(iterationsPerGame.getText()), Integer.parseInt(population.getText()), 
    			Double.parseDouble(strengthRatio.getText()), Double.parseDouble(winRatio.getText()) );
    	
    	Mutators m = new Mutators( attackCheckBox.isSelected(), defenseCheckBox.isSelected(), damageCheckBox.isSelected(),
    			healthCheckBox.isSelected(), speedCheckBox.isSelected(), effectivityCheckBox.isSelected(),
    			(int) mutationChanceSlider.getValue(), (int) mutationScopeSlider.getValue());

    	geneticAlgorithm = new GeneticAlgorithm(c, m);
    	
		Runnable geneticRun = () -> { 
			while( true ){
				if( startStopButton.isSelected() ){
					geneticAlgorithm.run();
					timerLock = true;
				}
			}
		};
		geneticThread = new Thread(geneticRun);
	}
  
	@FXML
    private void handleReset() {
		startStopButton.setSelected( false );
		lineChart.getData().clear();
		if( geneticThread != null)
			geneticThread.stop(); 
		geneticThread = null; 
		geneticAlgorithm = null ;
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        unitArchetypeTable.setItems(UnitArchetypeRepository.archetypeData);
    }
}
