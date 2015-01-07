package SKAZA.view.simulation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import SKAZA.MainApp;
import SKAZA.core.SimulationStatistics;
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
	private ProgressBar progressBar;

	@FXML
	private Label speedLabel;
	

	
	@FXML
	private Canvas romeCanvas;

	@FXML
	private Label romeTotalArmySize;

	@FXML
	private Label romeInfantrySize;
	
	@FXML
	private Label romeInfantryStrength;
	
	@FXML
	private Label romeCavalrySize;
	
	@FXML
	private Label romeCavalryStrength;
	
	@FXML
	private TextArea romeTextField;

	

	@FXML
	private Canvas carthageCanvas;
	
	@FXML
	private Label carthageTotalArmySize;

	@FXML
	private Label carthageInfantrySize;
	
	@FXML
	private Label carthageInfantryStrength;
	
	@FXML
	private Label carthageCavalrySize;
	
	@FXML
	private Label carthageCavalryStrength;
	
	@FXML
	private TextArea carthageTextField;
	
	
	
    private MainApp mainApp;
    
    private int size = MapConstants.rectangleSize;
    private int pictureSize = 30;
    
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
                    	if( startStopButton.isSelected() )
						printUnits();
						updateStatistics();
                    }
                });
            }
        });
		
		GraphicsContext romeGc = romeCanvas.getGraphicsContext2D();
		GraphicsContext cartGc = carthageCanvas.getGraphicsContext2D();

		cartGc.drawImage( new Image( "file:resources/images/Carthage.png" ), 0, 0, 140, 140);		
		romeGc.drawImage( new Image( "file:resources/images/Rome.png" ), 0, 0, 140, 140);
    }
    
	@FXML
    private void start() throws InterruptedException{
		mainApp.getSimulationEngine().speedLimiter = (long) Math.pow( speedSlider.getValue(), 2);
		
    	if( startStopButton.isSelected() ){
    		if( !mainApp.getMainSimulation().isAlive() ){
    			mainApp.getMainSimulation().start();
    			mainApp.getHannibalThread().start();
    			mainApp.getScipioThread().start();
    		}
			mainApp.getMainSimulation().resume();
			mainApp.getHannibalThread().resume();
			mainApp.getScipioThread().resume();

    	}
    	else{
	    	mainApp.getMainSimulation().suspend();
			mainApp.getHannibalThread().suspend();
			mainApp.getScipioThread().suspend();
    	}
    }
	
	@FXML
	private void cursorChanger(MouseEvent event){
		int x = (int) event.getX();
		int y = (int) event.getY();
		
		if( x > MapConstants.gridWidth * MapConstants.rectangleSize )
			x = MapConstants.gridWidth * MapConstants.rectangleSize;
		
		if( ( x / (MapConstants.rectangleSize) ) % 2 == 1 ){
			y -= MapConstants.rectangleSize/2;
		}
		
		if( y > MapConstants.gridHeight * MapConstants.rectangleSize )
			y = MapConstants.gridHeight * MapConstants.rectangleSize;
		
		x = ( x/MapConstants.rectangleSize ) % MapConstants.gridWidth;
		y = ( y/MapConstants.rectangleSize ) % MapConstants.gridHeight;
		
		if( mainApp.getSimulationEngine().map.matrix[y][x].unit != null ){		
			mapCanvas.cursorProperty().set( Cursor.HAND );	
		}
		else
			mapCanvas.cursorProperty().set( Cursor.DEFAULT );
	}
	
	@FXML
	private void handleReset(){
		startStopButton.setSelected( false );
		mainApp.reset();
	}
	
	@FXML
	private void mouseListener(MouseEvent event){
		int x = (int) event.getX();
		int y = (int) event.getY();
		
		if( x > MapConstants.gridWidth * MapConstants.rectangleSize )
			x = MapConstants.gridWidth * MapConstants.rectangleSize;
		
		if( ( x / (MapConstants.rectangleSize) ) % 2 == 1 ){
			y -= MapConstants.rectangleSize/2;
		}
		
		if( y > MapConstants.gridHeight * MapConstants.rectangleSize )
			y = MapConstants.gridHeight * MapConstants.rectangleSize;
		
		x = ( x/MapConstants.rectangleSize ) % MapConstants.gridWidth;
		y = ( y/MapConstants.rectangleSize ) % MapConstants.gridHeight;
		
		if( mainApp.getSimulationEngine().map.matrix[y][x].unit != null ){
			printOnTextArea( mainApp.getSimulationEngine().map.matrix[y][x].unit );
			System.out.println( mainApp.getSimulationEngine().map.matrix[y][x].unit );			
		}
	}
	
    private void printOnTextArea(Unit unit) {
		if( unit.getNation() == Nation.ROME ){
			romeTextField.setText(unit.toString());
		}
		else{
			carthageTextField.setText(unit.toString());
		}
		
	}

	private void drawShapes(GraphicsContext gc) {
    	for( int h=0 ; h < MapConstants.gridHeight ; h++ ){   		
    		for( int w=0 ; w < MapConstants.gridWidth ; w++ ){
    			if( w % 2 == 0){
    				gc.strokeRect(w*size, h*size, size, size);
    			}
    			else{
    				gc.strokeRect(w*size, h*size + size/2, size, size);	
    			} 				
    		}
    	}
    }
    
	private void updateStatistics() {
		SimulationStatistics romeStats = mainApp.getSimulationEngine().getStatistics( Nation.ROME );
		romeTotalArmySize.setText( romeStats.totalArmySize.toString() );
		romeInfantrySize.setText( romeStats.infantrySize.toString() );
		romeInfantryStrength.setText( romeStats.infantryStrength.toString() );
		romeCavalrySize.setText( romeStats.cavalrySize.toString() );
		romeCavalryStrength.setText( romeStats.cavalryStrength.toString() );
		

		SimulationStatistics carthageStats = mainApp.getSimulationEngine().getStatistics( Nation.CARTHAGE );
		carthageTotalArmySize.setText( carthageStats.totalArmySize.toString() );
		carthageInfantrySize.setText( carthageStats.infantrySize.toString() );
		carthageInfantryStrength.setText( carthageStats.infantryStrength.toString() );
		carthageCavalrySize.setText( carthageStats.cavalrySize.toString() );
		carthageCavalryStrength.setText( carthageStats.cavalryStrength.toString() );
		
		progressBar.progressProperty().set( romeStats.totalArmySize/( carthageStats.totalArmySize + romeStats.totalArmySize + 1e-6 ) );
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
    	
    	if( mainApp.getSimulationEngine().endingFlag ){
    		startStopButton.setDisable( true ); 
    	}
    }
    
    private void drawFootman(int w, int h, GraphicsContext gc, Unit unit){
		if( w % 2 == 0 ){
			if( unit.getNation() == Nation.ROME ){
				if( unit.getArchetype().getSpeed() >= 10){
					gc.drawImage( new Image( "file:resources/images/romanCavalry.jpg" ), w*size + 10, h*size + 10, pictureSize, pictureSize);
				}
				else{
					gc.drawImage( new Image( "file:resources/images/romanFootman.jpg" ), w*size + 10, h*size + 10, pictureSize, pictureSize);
				}
					
			}
			else{
				if( unit.getArchetype().getSpeed() >= 10)
					gc.drawImage( new Image( "file:resources/images/carthaginianCavalry.jpg" ), w*size + 10, h*size + 10, pictureSize, pictureSize);
				else
					gc.drawImage( new Image( "file:resources/images/carthaginianFootman.jpg" ), w*size + 10, h*size + 10, pictureSize, pictureSize);
			}
		}
		else{
			if( unit.getNation() == Nation.ROME ){
				if( unit.getArchetype().getSpeed() >= 10)
					gc.drawImage( new Image( "file:resources/images/romanCavalry.jpg" ), w*size + 10, h*size + 35, pictureSize, pictureSize);
				else
					gc.drawImage( new Image( "file:resources/images/romanFootman.jpg" ), w*size + 10, h*size + 35, pictureSize, pictureSize);
					
			}
			else{
				if( unit.getArchetype().getSpeed() >= 10)
					gc.drawImage( new Image( "file:resources/images/carthaginianCavalry.jpg" ), w*size + 10, h*size + 35, pictureSize, pictureSize);
				else
					gc.drawImage( new Image( "file:resources/images/carthaginianFootman.jpg" ), w*size + 10, h*size + 35, pictureSize, pictureSize);
			}
		}
    }
    private void clear(int w, int h, GraphicsContext gc){
		if( w % 2 == 0 ){
			gc.clearRect( w*size + 10, h*size + 10, pictureSize, pictureSize );    			    	
		}
		else{
			gc.clearRect( w*size + 10, h*size + 35, pictureSize, pictureSize );
		}
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
