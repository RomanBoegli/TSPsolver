package app;

import abstrac.View;
import entry.ServiceLocator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tsp.MapReference;
import tsp.Path;
import tsp.Vertex;
import tsp.VertexSet;

/**
 * @author Roman Boegli
 */

public class AppView extends View<AppModel> {
	
	//---------------------------------------
	//GLOBAL VARIABLES
	//---------------------------------------
	
	ServiceLocator serviceLocator;
	
	BorderPane root;
	MediaPlayer mediaPlayer;
	Media media;
	
	
	//top
	Label lbl_minimalDistance;
	TextField txt_Vertices;
	TextField txt_PopSize;
	TextField txt_PopEvolScope;
	TextField txt_MutationRate;
	TextField txt_File;
	Button cmdSelectFile;
	TextField txt_PauseTime;
	Button cmdStart;
	Button cmdStop;
	
	//left
	TextArea txt_BestPath;
	
	//center
	Painting painting;
	
	
	
	//resources
	private double nVertexRadius = 5.0;
	
	
	
	
	
	
	//Constructor
	public AppView(Stage stage, AppModel model) {
		
		super(stage, model);
		
		//show application icon
		Image appIcon = new Image(getClass().getResourceAsStream("../resources/appIcon.jpg"));
        stage.getIcons().add(appIcon);
        
        stage.setTitle("TSPsolverBETA");
        
        serviceLocator = ServiceLocator.getServiceLocator();        
        serviceLocator.getLogger().info("Application view initialized");
        
	}
	
	
	
	
	
	
	
	
	//---------------------------------------
	//BUILDING THE BORDERPANE
	//---------------------------------------
	
	@Override
	protected Scene createGUI() {
					
		root = new BorderPane();
		root.setId("app");
		
		root.setTop(getTop());
		root.setLeft(getLeft());
		root.setCenter(getCenter());	

		Scene scene = new Scene(root,1200,1050);

		return scene;
		
	}
	
	
	
	
	
	
	
	
	//---------------------------------------
	//BUILDING EACH SECTION OF A BOARDERPANE
	//---------------------------------------
	
	private Pane getTop() {
		
		HBox holder = new HBox();
		holder.setId("holder_top");
		
		
		//construct left side
		Label lbl_lbl_minimalDistance = new Label("Best Distance im meters");
		lbl_minimalDistance = new Label("9999.99");
		lbl_minimalDistance.setFont(Font.font("Consolas", 80));
		VBox holder_01 = new VBox(lbl_lbl_minimalDistance, lbl_minimalDistance);
		
		
		//construct right side
		Label lbl_txt_Vertices = new Label("Vertices:");
		txt_Vertices = new TextField("27");
		HBox holder_02_01 = new HBox(lbl_txt_Vertices, txt_Vertices);
		
		Label lbl_txt_PopSize = new Label("Population size:");
		txt_PopSize = new TextField("75");
		HBox holder_02_02 = new HBox(lbl_txt_PopSize, txt_PopSize);
		
		Label lbl_txt_PopEvolScope = new Label("Generation max.:");
		txt_PopEvolScope = new TextField("100");
		HBox holder_02_03 = new HBox(lbl_txt_PopEvolScope, txt_PopEvolScope);	
		
		Label lbl_txt_MutationRate = new Label("Mutation rate:");
		txt_MutationRate = new TextField("0.01");
		HBox holder_02_04 = new HBox(lbl_txt_MutationRate, txt_MutationRate);
		
		Label lbl_txt_File = new Label("File:");
		txt_File = new TextField("{txt-file}")    ;
		cmdSelectFile = new Button("...");
		HBox holder_02_05 = new HBox(lbl_txt_File, txt_File, cmdSelectFile);
		
		Label lbl_txt_PauseTime = new Label("Pause time (ms):");
		txt_PauseTime = new TextField("0");
		HBox holder_02_06 = new HBox(lbl_txt_PauseTime, txt_PauseTime);
		
		cmdStart = new Button("Start");
		cmdStop = new Button("Stop");
		HBox holder_02_07 = new HBox(cmdStart, cmdStop);
		
		VBox holder_02 = new VBox(holder_02_01, holder_02_02, holder_02_03, holder_02_04, holder_02_05, holder_02_06, holder_02_07);
		
		
		//do the alignment
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

		
		//put sub holders in holder
		holder.getChildren().addAll(holder_01, region1, holder_02);
		
		return holder;
	}
	
	
	private Pane getLeft() {
		
		
	    
		//main-container
		HBox holder = new HBox();
		holder.setId("holder_left");
		holder.setAlignment(Pos.TOP_LEFT);	
		
		Label lbl_txt_BestPath = new Label("Best Path:");
		txt_BestPath = new TextArea("a -> b -> ...");
		txt_BestPath.setPrefWidth(300);
		txt_BestPath.setPrefHeight(750);
		txt_BestPath.setWrapText(true);
		VBox holder_01 = new VBox(lbl_txt_BestPath, txt_BestPath);
		
		
		//put sub holders in holder
		holder.getChildren().add(holder_01);
		
		
		return holder;
	}
	
	
	private Pane getCenter() {
		
		
		//main-container
		StackPane holder = new StackPane();
		holder.setId("holder_center");
		holder.setAlignment(Pos.TOP_LEFT);
		
		//painting
		painting = getPainting();
				
		//put sub holders in holder
		holder.getChildren().add(painting);
		
		return holder;
	}
	
	
	
	
	
	
	
	
	
	
	
	//---------------------------------------
	//SUPPORTING THE SECTION BUILDING PROCESS
	//---------------------------------------
	

	public Stage getStage() {
		return this.stage;
	}
	
	
	
	public Painting getPainting() {	
		
        /*create painting if not already existing
		 *as the view need only one painting.
		 */
		if (painting == null) {
			
			double nWidth = 800;
			double nHeight = 800;
			
			painting = new Painting(nWidth,nHeight);
			clear(painting);
			
		}
		
		return painting;
	}
	
	
	public void clear(Painting p) {
		
		
		GraphicsContext gc = painting.getGraphicsContext2D();
		gc.clearRect(0, 0, p.getWidth(), p.getHeight());
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, p.getWidth(), p.getHeight());
	}
	
	
	
	public void drawVertices(VertexSet vs, MapReference mp) {
		
		double nRatioX; 
		double nRatioY;
		
		GraphicsContext gc = painting.getGraphicsContext2D();
		
		for (int i=0 ; i<vs.getNumberOfVertices() ; i++) {
			
			Vertex v = vs.getVertex(i);
			
			//get proportions
			nRatioX = getRatioX(v,mp);
			nRatioY = getRatioY(v,mp);
			
			//draw vertex proportionally on the painting
			gc.setFill(Color.WHITE);
			gc.fillOval((painting.getWidth()-nVertexRadius) * nRatioX, (painting.getHeight()-nVertexRadius) * nRatioY, nVertexRadius, nVertexRadius);
			gc.setStroke(Color.RED);
			gc.strokeOval((painting.getWidth()-nVertexRadius) * nRatioX, (painting.getHeight()-nVertexRadius) * nRatioY, nVertexRadius, nVertexRadius);
			
		}
		
		
	}
	
	
	
	private double getRatioX(Vertex v, MapReference mp) {
		return (v.getX() - mp.getMinX()) / mp.getMapWidth();
	}
	
	
	private double getRatioY(Vertex v, MapReference mp) {
		return (v.getY() - mp.getMinY()) / mp.getMapHeight();
	}
	
	
	
	
	
	public void drawPaths(VertexSet vs, MapReference mp, Path p) {
		
		int nMax = p.getLength() - 1;
		double nXfrom;
		double nYfrom;
		double nXto;
		double nYto;
		int nFrom;
		int nTo;
		
		
		GraphicsContext gc = painting.getGraphicsContext2D();
		gc.setFill(Color.RED);
		
		for(int i=0; i < nMax; i++){
			
			nFrom = p.get(i);
			nTo = p.get(i+1);
			
			nXfrom = (painting.getWidth()-nVertexRadius) * getRatioX(model.vSet.getVertex(nFrom), mp);
			nYfrom = (painting.getHeight()-nVertexRadius) * getRatioY(model.vSet.getVertex(nFrom), mp);
			
			nXto = (painting.getWidth()-nVertexRadius) * getRatioX(model.vSet.getVertex(nTo), mp);
			nYto = (painting.getHeight()-nVertexRadius) * getRatioY(model.vSet.getVertex(nTo), mp);
			
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(5);
            gc.strokeLine(nXfrom, nYfrom, nXto, nYto);
			
//			nReturn += this.Set.get(nFrom).getDistanceToWGS(this.Set.get(nTo));
		}
		
		// last edge
//		nReturn += this.Set.get(nMax).getDistanceToWGS(this.Set.get(0));
		
		
	}
	
	
}
