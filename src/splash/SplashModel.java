package splash;

import java.io.File;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import abstrac.Model;
import entry.ServiceLocator;
import javafx.concurrent.Task;

/**
 * @author Roman Boegli
 */

public class SplashModel extends Model{
	
	ServiceLocator serviceLocator;

	public SplashModel() {
		super();
	}
	
	final Task<Void> initializer = new Task<Void>() {
        
		@Override
        protected Void call() throws Exception {

            // First, take some time, update progress
			Integer i = 0;
            for (; i < 100000000; i++) {
                if ((i % 1000) == 0)
                    this.updateProgress(i, 100000000);
            }
            System.out.println("**");
                       
            // Create the service locator to hold our resources
            serviceLocator = ServiceLocator.getServiceLocator();
            
            
            //make sure PopBook exists
            createDir(serviceLocator.getPopBookDir());
      
            
            
            // Initialize the resources in the service locator
            Logger x = getCustomLogger();
            serviceLocator.setLogger(x);
                      
            return null;
        }
    };

    
    public void initialize() {
        new Thread(initializer).start();
    }
	
    
    
    /**
	 * Constructs the Logger, consisting of two handlers. 
	 * Handler_1 outputs into the console. Handler_2 outputs into
	 * a text file, located at the passed path cPath.
	 * 
	 * @param cPath path (directory & filename) for the log file
	 * @return an object of type Logger
	 * 
	 */
    private static Logger getCustomLogger () {
    	
    	//Add first logger
		Logger rootLogger = Logger.getLogger("");
		rootLogger.setLevel(Level.FINEST);
		
		//define handler
		Handler[] defaultHandlers = rootLogger.getHandlers();
		defaultHandlers[0].setLevel(Level.INFO);
		    	
    	return rootLogger;
    }
      
    
    

    /**
 	 * Creates directory a directory. Deletes it, when
 	 * already existing and (re)creates it. Finally, you'll have 
 	 * a fresh created directory.
 	 * 
 	 * @param cPath directory with an "\" at the end
	 *
 	 */
	public void createDir(String cPath) {
	    	
		File file = new File(cPath);
		boolean isDirectoryCreated = file.mkdir();
		
		if (isDirectoryCreated) {
			System.out.println("directory successfully created: " + cPath);
		} else {
			System.out.println("directory already existing: " + cPath);
			deleteDir(file);   // <~~ invokes recursive method
			System.out.println("directory deleted: " + cPath);
		    createDir(cPath);  // <~~ recursive call
		}
		
	}
    
		
    
    /**
     * Recursive method deleting a directory with all the directories
     * in it.
     * 
     * @param dir an Object of type File
     */
    private void deleteDir(File dir) {
    	
        File[] files = dir.listFiles();

        for (File myFile: files) {
            if (myFile.isDirectory()) {  
                deleteDir(myFile);
            }
            myFile.delete();
        }
        dir.delete();
    } 
    
    
}
