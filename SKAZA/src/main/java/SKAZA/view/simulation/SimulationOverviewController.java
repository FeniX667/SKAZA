package SKAZA.view.simulation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import SKAZA.MainApp;
import SKAZA.core.math.constants.MapConstants;
import SKAZA.core.models.map.Map;
import SKAZA.core.models.unit.Nation;
import SKAZA.core.models.unit.Unit;

@SuppressWarnings("deprecation")
public class SimulationOverviewController {

	@FXML
	private Canvas mapCanvas;

	@FXML
	private ToggleButton startStopButton;
	
	@FXML
	private Slider speedSlider;

	@FXML
	private Label speedLabel;
	
    private MainApp mainApp;
    
    public Timer timer;
    
    public SimulationOverviewController() {
    }
    
    @FXML
    private void initialize() {
		GraphicsContext gc = mapCanvas.getGraphicsContext2D();
		drawShapes(gc);
		
		speedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
		    mainApp.getSimulationEngine().speedLimiter = (long) Math.pow( speedSlider.getValue(), 2);
		    speedLabel.setText("Delay: "+ mainApp.getSimulationEngine().speedLimiter +"ms" );
		});
		
		timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Platform.runLater(new Runnable() {
                    public void run() {
						printUnits();
                    }
                });
            }
        });
    }
    
	@FXML
    private void start(){
    	if( startStopButton.isSelected() ){
    		if( !mainApp.getMainSimulation().isAlive() ){
    			mainApp.getMainSimulation().start();
    		}
			mainApp.getMainSimulation().resume();
			
    		if( !mainApp.getHannibalThread().isAlive() ){
    			mainApp.getHannibalThread().start();
    		}
			mainApp.getHannibalThread().resume();
			
    		if( !mainApp.getScipioThread().isAlive() ){
    			mainApp.getScipioThread().start();
    		}
			mainApp.getScipioThread().resume();

    	}
    	else{
	    	mainApp.getMainSimulation().suspend();
			mainApp.getHannibalThread().suspend();
			mainApp.getScipioThread().suspend();
    	}
    }
   
    private void drawShapes(GraphicsContext gc) {
    	for( int h=0 ; h < MapConstants.gridHeight ; h++ ){   		
    		for( int w=0 ; w < MapConstants.gridWidth ; w++ ){
    			if( w % 2 == 0){
    				gc.strokeRect(w*40, h*40, 40, 40);
    			}
    			else{
    				gc.strokeRect(w*40, h*40 +20, 40, 40);	
    			} 				
    		}
    	}
    }
    
    public void printUnits(){
		if( mainApp==null )
			return;
		
		GraphicsContext gc = mapCanvas.getGraphicsContext2D();		
		Map map = mainApp.getSimulationEngine().map;
		
		
    	for( int h=0 ; h < map.height ; h++ ){
    		for( int w=0 ; w < map.width ; w++ ){
    			if( map.matrix[h][w].unit != null ){
    				drawFootman(w, h, gc, map.matrix[h][w].unit);
    			}
    			else{
    				clear(w, h, gc);
    			}   				
    		}
    	}
    }
    
    private void drawFootman(int w, int h, GraphicsContext gc, Unit unit){
		if( w % 2 == 0 ){
			if( unit.getNation() == Nation.ROME )
				gc.drawImage( new Image( "file:resources/images/romanFootman.jpg" ), w*40 + 10, h*40 + 10, 20, 20);
			else
				gc.drawImage( new Image( "file:resources/images/carthaginianFootman.jpg" ), w*40 + 10, h*40 + 10, 20, 20);				
		}
		else{
			if( unit.getNation() == Nation.ROME )
				gc.drawImage( new Image( "file:resources/images/romanFootman.jpg" ), w*40 + 10, h*40 + 30, 20, 20);
			else
				gc.drawImage( new Image( "file:resources/images/carthaginianFootman.jpg" ), w*40 + 10, h*40 + 30, 20, 20);
		}
    }
    private void clear(int w, int h, GraphicsContext gc){
		if( w % 2 == 0 ){
			gc.clearRect( w*40 + 10, h*40 + 10, 20, 20 );    			    	
		}
		else{
			gc.clearRect( w*40 + 10, h*40 + 30, 20, 20 );
		}
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
