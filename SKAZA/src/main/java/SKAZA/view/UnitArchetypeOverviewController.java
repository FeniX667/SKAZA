package SKAZA.view;

import org.controlsfx.dialog.Dialogs;

import SKAZA.MainApp;
import SKAZA.core.models.unit.UnitArchetype;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

@SuppressWarnings("deprecation")
public class UnitArchetypeOverviewController {
	@FXML
    private TableView<UnitArchetype> unitArchetypeTable;
    @FXML
    private TableColumn<UnitArchetype, String> nameColumn;
    @FXML
    private TableColumn<UnitArchetype, Number> healthColumn;

    @FXML
    private Label nameLabel;
    @FXML
    private Label attackLabel;
    @FXML
    private Label defenseLabel;
    @FXML
    private Label damageLabel;
    @FXML
    private Label speedLabel;
    @FXML
    private Label healthLabel;
    @FXML
    private Label effectivityLabel;

    private MainApp mainApp;
    
    public UnitArchetypeOverviewController() {
    }
    
    @FXML
    private void initialize() {
    	nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    	healthColumn.setCellValueFactory(cellData -> cellData.getValue().healthProperty());
    	
    	showUnitArchetypeDetails(null);
    	
    	unitArchetypeTable.getSelectionModel().selectedItemProperty().addListener(
    			(observable, oldValue, newValue) -> showUnitArchetypeDetails(newValue));
    }
    
	 private void showUnitArchetypeDetails(UnitArchetype unitArchetype) {
	     if (unitArchetype != null) {
	     	nameLabel.setText( unitArchetype.getName() );
	     	attackLabel.setText( Integer.toString( unitArchetype.getAttack() ) );
	     	defenseLabel.setText(  Integer.toString( unitArchetype.getDefense() ) );
	     	damageLabel.setText(Integer.toString( unitArchetype.getDamage() ) );
	     	speedLabel.setText(  Integer.toString( unitArchetype.getSpeed() ) );
	     	healthLabel.setText(  Integer.toString( unitArchetype.getHealth() ) );
	     	effectivityLabel.setText(  Integer.toString( unitArchetype.getEffectiveAmountOfFighters() ) );
	     } else {
	     	nameLabel.setText("");
	     	attackLabel.setText("");
	     	defenseLabel.setText("");
	     	damageLabel.setText("");
	     	speedLabel.setText("");
	     	healthLabel.setText("");
	     	effectivityLabel.setText("");
	     }
	 }
    
    @FXML
    private void handleNewUnitArchetype() {
        mainApp.showUnitArchetypeNewDialog();
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
    private void handleDeleteUnitArchetype() {
        try {
			int selectedIndex = unitArchetypeTable.getSelectionModel().getSelectedIndex();
			
			unitArchetypeTable.getItems().remove(selectedIndex);
		} catch (Exception e) {
	        Dialogs.create()
            .title("No Selection")
            .masthead("No unit archetype selected")
            .message("Please select a unit archetype in the table.")
            .showWarning();
		}
    }
	

    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        unitArchetypeTable.setItems(mainApp.getArchetypeData());
    }
}
