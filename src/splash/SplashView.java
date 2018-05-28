package splash;

import abstrac.View;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * @author Roman Boegli
 */

public class SplashView extends View<SplashModel> {
    
	ProgressBar progress;
	Image appIcon; 
    
    
	public SplashView(Stage stage, SplashModel model) {
		super(stage, model);
		stage.initStyle(StageStyle.TRANSPARENT);
		
		//show application icon
        stage.getIcons().add(appIcon);
				
	}

	
	@Override
	protected Scene createGUI() {
		
		GridPane root = new GridPane();
		root.setId("splash");
		root.setVgap(30);
		
        progress = new ProgressBar();
        progress.setPrefWidth(300);
        
        appIcon = new Image(getClass().getResourceAsStream("../resources/appIcon.jpg"));
        ImageView background = new ImageView(appIcon);
        
        HBox bottomBox = new HBox();
        bottomBox.setId("progressbox");
        bottomBox.getChildren().add(progress);
        bottomBox.setAlignment(Pos.CENTER);
        root.add(background,0,0);
        root.add(bottomBox,0,1);
        
        Scene scene = new Scene(root, Color.TRANSPARENT);
        
        return scene;
		
	}
	
	
	

}
