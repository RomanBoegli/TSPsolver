package entry;

import java.util.logging.Logger;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * The singleton instance of this class provide central storage for resources
 * used by the program. It also defines application-global constants, such as
 * the application name.
 * 
 * @author Brad Richards
 */

public class ServiceLocator {
    private static ServiceLocator serviceLocator; // singleton

    // Application-global constants
    final private String APPNAME = "TSPsolverBETA";

    // Resources
    private Logger logger;
    final private String PopBookDir = System.getProperty("user.dir") + "/PopBook/";
    final private String PopBookFile = PopBookDir + "populations.txt";	

    

    /**
     * Factory method for returning the singleton
     *
     * @return The singleton resource locator
     */
    public static ServiceLocator getServiceLocator() {
        if (serviceLocator == null)
            serviceLocator = new ServiceLocator();
        return serviceLocator;
    }

    
    
    /**
     * Private constructor, because this class is a singleton
     */
    private ServiceLocator() {
        // Currently nothing to do here. We must define this constructor anyway,
        // because the default constructor is public
    }

    
    
    public String getAPPNAME() {
        return APPNAME;
    }

    
    
    public void setLogger(Logger logger) {
        this.logger = logger;
    }
    
    public Logger getLogger() {
        return logger;
    }

    public String getPopBookFile() {
    	return PopBookFile;
    }
    public String getPopBookDir() {
    	return PopBookDir;
    }
    
    


    
}
