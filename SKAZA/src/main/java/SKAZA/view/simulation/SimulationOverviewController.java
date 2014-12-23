package SKAZA.view.simulation;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
import SKAZA.MainApp;
import SKAZA.core.math.constants.MapConstants;
import SKAZA.core.models.map.Map;
import SKAZA.core.models.unit.Unit;
public class SimulationOverviewController {

	@FXML
	private Canvas mapCanvas;
	
    private MainApp mainApp;
    
    public SimulationOverviewController() {
    }
    
    @FXML
    private void initialize() {
		GraphicsContext gc = mapCanvas.getGraphicsContext2D();
		drawShapes(gc);
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
		GraphicsContext gc = mapCanvas.getGraphicsContext2D();
		Map map = mainApp.getMainSimulationEngineThread().simulationEngine.map;
		
    	for( int h=0 ; h < map.height ; h++ ){
    		for( int w=0 ; w < map.width ; w++ ){
    			if( map.matrix[h][w].unit != null ){
    				if( h % 2 == 0 ){
    	        		gc.drawImage( new Image( "file:resources/images/footman.jpg" ), h*40 + 10, w*40 + 10, 20, 20);
    				}
    				else{
    	        		gc.drawImage( new Image( "file:resources/images/footman.jpg" ), h*40 + 10, w*40 + 10, 20, 20);
    				}
    			}
    				
    		}
    	}
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
