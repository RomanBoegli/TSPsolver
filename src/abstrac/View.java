package abstrac;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public abstract class View<M extends Model> {
    protected Stage stage;
    protected Scene scene;
    protected M model;
    
    /**
     * Set any options for the stage in the subclass constructor
     * 
     * @param stage
     * @param model
     */
    protected View(Stage stage, M model) {
        this.stage = stage;
        this.model = model;
        
        scene = createGUI(); // Create all controls within "root"
        stage.setScene(scene);
    }

    protected abstract Scene createGUI();

    /**
     * Display the view
     * 
     * @param lResizable indicates if the stage should be
     *                   resizable (true) or not (false)
     */
    public void start(boolean lResizable) {
        stage.setResizable(lResizable);
    	stage.show();
    }
    
    /**
     * Hide the view
     */
    public void stop() {
        stage.hide();
    }
    
    /**
     * Getter for the stage, so that the controller can access window events
     * 
     * @return an object of type Stage 
     */
    public Stage getStage() {
        return stage;
    }
}
