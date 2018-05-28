package app;


import java.io.File;
import java.text.DecimalFormat;
import abstrac.Controller;
import entry.ServiceLocator;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import tsp.MapReference;
import tsp.Path;
import tsp.Population;



/**
 * @author Roman Boegli
 */

public class AppController extends Controller<AppModel, AppView>{
	
	//---------------------------------------
	//GLOBAL VARIABLES
	//---------------------------------------	
	
	ServiceLocator serviceLocator;
	boolean lStopp;
	boolean lAlreadyStarted;
	
	
	MapReference mp;
	Population pop;
	Path bestPath;
	
	Thread GA;
	
	private final DecimalFormat df = new DecimalFormat("#,##0.0000"); 
	
	
	/**
 	 * public constructor, handling following events:
 	 *  - displaying value of slider after slider was moved
	 *  - starting process of adding new points to the painting
	 *  - terminating process of adding new points to the painting
	 *  - rearranging controls after window width was changed
	 *  - rearranging controls after window height was changed
	 *  
	 *  @param model object of type AppModel
	 *  @param view object of type AppView
	 *  
	 */
	public AppController(AppModel model, AppView view) {
		
		super(model, view);
		
		
	    //provide resources
		serviceLocator = ServiceLocator.getServiceLocator();  
	    
	    
	    //start the process
		view.cmdStart.setOnAction( (ActionEvent) -> { if(!lAlreadyStarted){runEvaluation();}  
													  serviceLocator.getLogger().info("start evaluation");   });
		
		 //stop the process
		view.cmdStop.setOnAction( (ActionEvent) -> { {breakEvaluation();}  
													  serviceLocator.getLogger().info("stopp evaluation");   });
		
		
		//file selector
		view.cmdSelectFile.setOnAction( (ActionEvent) -> { if(!lAlreadyStarted){ getImportFile();}	
														   serviceLocator.getLogger().info("file selected");   });
		
		
		
		
	    serviceLocator.getLogger().info("Application controller initialized");
    
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void runEvaluation() {
		
		boolean lExit = false;
			
		lAlreadyStarted = true;
		bestPath=null;
		
		//check if vertices should come from file
		File f = new File(view.txt_File.getText());
		if(f.exists() && !f.isDirectory()) { 
			if(model.correctStructure(f)) {
				//create vertices according to input file
				model.createVertices(f);
			} else {
				lExit = true;
			}
		} else {
			//create random vertices according number indicated on GUI
			model.createRandomVertices(Integer.parseInt(view.txt_Vertices.getText()));
		}
		
				
		//define map references
		model.vSet.defineMapReference();
	
    	//draw vertices
    	Platform.runLater(()-> { view.clear(view.painting);
    							 view.drawVertices(model.vSet, model.vSet.mp); });

    	//empty text file
		model.cleanPopBook();
		
		if(!lExit) {
			
			Task<Void> task1 = new Task<Void>() {
	
				@Override
				protected Void call() throws Exception {
			    	lStopp = false;
			    	while (!lStopp) {
			    		
			    		
			    		//initiate population
						int nSize = Integer.parseInt(view.txt_PopSize.getText());
						double nRate = Double.parseDouble(view.txt_MutationRate.getText());
						model.pop = new Population(model.vSet, nSize, nRate);
						
						if (bestPath==null) {
							//pick a random path as current best
							bestPath = model.pop.getRandomPath();
						}
						
						
						
						int i = Integer.parseInt(view.txt_PopEvolScope.getText());
						while(i>0 && !lStopp) {

							System.out.println("evolution: " + i);
							
							model.pop.evolve2();

							Path p = model.pop.getFittestPath();
														
							if( model.vSet.getTotalDistanceWGS(p) < model.vSet.getTotalDistanceWGS(bestPath)  ) {
								
								//dethrone
								bestPath = p;
								
								Double n = model.vSet.getTotalDistanceWGS(bestPath);
								
								//display total distance and path
						    	Platform.runLater(()-> {  view.lbl_minimalDistance.setText(df.format(n));
						    							        view.txt_BestPath.setText(bestPath.getInfo(model.vSet)); });

						    	//redraw network
						    	Platform.runLater(()-> { view.clear(view.painting);
						    							       view.drawPaths(model.vSet, model.vSet.mp, bestPath);
														       view.drawVertices(model.vSet, model.vSet.mp);   });
							}
							i--;	
						}
									
						
						
				    	String c = (model.pop.getInfo());
				    	System.out.println(c);
				    	
				    	model.writeToPopBook(c + "\n");
				    	model.writeToPopBook(model.pop.getDetailedInfo());
				  
				    	//wait some time
				    	Thread.sleep(Long.parseLong(view.txt_PauseTime.getText()));
				    	
			    	}
			    	
			    	//process was stopped
			    	lAlreadyStarted = false;
					return null;
				}
				
			};
	    	
	    	
	    	GA = new Thread(task1);
	    	GA.setDaemon(true);  //stops thread when closing the application
	    	GA.start();
		}
		
		
	}

	
	
	
	
	
	private void breakEvaluation() {
		
		lStopp = true;
    	System.out.println("");
    	System.out.println("Fittest path: " + bestPath.getInfo() );
    	System.out.println("============");		
	}
	
		
		
	private File getImportFile() {
		
		File aReturn = null;
		
		//Create a file chooser
		FileChooser  fc = new FileChooser ();
		
		// Set extension filter
      ExtensionFilter filter = new ExtensionFilter("Text files (*.txt)", "*.txt");
      fc.getExtensionFilters().add(filter);

      // Show open file dialog
      aReturn = fc.showOpenDialog(view.getStage());
      
      //show selected file
		if (aReturn != null) {
			view.txt_File.setText(aReturn.getAbsolutePath());
		}
        
      return aReturn;
        
	}
	
	
	
	
	
	
	
}
