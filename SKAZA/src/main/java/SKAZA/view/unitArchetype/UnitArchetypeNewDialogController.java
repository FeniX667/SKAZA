package SKAZA.view.unitArchetype;

import java.util.logging.ErrorManager;

import org.controlsfx.dialog.Dialogs;

import SKAZA.MainApp;
import SKAZA.core.models.unitArchetype.UnitArchetype;
import SKAZA.core.repository.UnitArchetypeRepository;
import SKAZA.core.service.UnitArchetypeService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@SuppressWarnings("deprecation")
public class UnitArchetypeNewDialogController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField attackField;
    @FXML
    private TextField defenseField;
    @FXML
    private TextField damageField;
    @FXML
    private TextField speedField;
    @FXML
    private TextField healthField;
    @FXML
    private TextField effectivityField;
    
    private Stage dialogStage;
    private MainApp mainApp;
    private UnitArchetype unitArchetype;
    
    @FXML
    private void initialize() {
    }
    
    @FXML
    private void handleOk() {
        if ( isInputValid() ) {       	
        	unitArchetype = UnitArchetypeService.create(
        			nameField.getText(), 
        			attackField.getText(), 
        			defenseField.getText(), 
        			damageField.getText(), 
        			healthField.getText(), 
        			speedField.getText(), 
        			effectivityField.getText());
            
    		UnitArchetypeRepository.archetypeData.add(unitArchetype);
            dialogStage.close();
        }
    }
   
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
	private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "No valid Name!\n"; 
        }
        errorMessage += parseIntegerStringAndReturnErrorCode( attackField, "Attack" );
        errorMessage += parseIntegerStringAndReturnErrorCode( defenseField, "Defence" );
        errorMessage += parseIntegerStringAndReturnErrorCode( damageField, "Damage" );
        errorMessage += parseIntegerStringAndReturnErrorCode( healthField, "Health" );
        errorMessage += parseIntegerStringAndReturnErrorCode( speedField, "Speed" );
        errorMessage += parseIntegerStringAndReturnErrorCode( effectivityField, "Effectivity" );

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
    

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
