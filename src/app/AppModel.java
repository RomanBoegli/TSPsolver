package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import abstrac.Model;
import entry.ServiceLocator;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import tsp.Population;
import tsp.Vertex;
import tsp.VertexSet;

/**
 * @author Roman Boegli
 */

public class AppModel extends Model{
		
	//---------------------------------------
	//GLOBAL VARIABLES
	//---------------------------------------
	
	ServiceLocator serviceLocator;
	
	public VertexSet vSet;
	public Population pop;
	
	
	
	
	
	/**
	 * Constructor
	 */
	public AppModel() {
        
        serviceLocator = ServiceLocator.getServiceLocator();        
        serviceLocator.getLogger().info("Application model initialized");
    }
	
	
	
	
	/**
	 * Method use to initiate n vertices
	 * randomly. Generated vertices are automatically
	 * added to the VertexSet
	 * @param n number of vertices to initiate
	 */
	public void createRandomVertices(int n) {
		
		vSet = new VertexSet();

		for (int i = 0 ; i < n ; i++) {
			Vertex v = Vertex.getRandom(0, 100, 0, 100, 0, 0); 
			vSet.addVertex(v);
		}
		
	}
	
	
	/**
	 * Method use to initiate vertices accordingly
	 * the text file provided. Generated vertices 
	 * are automatically added to the VertexSet
	 * @param f legal file with expected structure
	 */
	public void createVertices(File f) {
			
		vSet = new VertexSet();
		
		try {
			
			FileInputStream fstream = new FileInputStream(f);
			InputStreamReader rd = new InputStreamReader(fstream,"iso-8859-1");
			BufferedReader br =  new BufferedReader(rd);
			String cLine;
			
			while ((cLine = br.readLine()) != null) {
				
				String aValues[] = cLine.split("\t");
				
				//create vertex
				Vertex v = new Vertex(aValues[0], 
									  Double.parseDouble(aValues[1]), 
									  Double.parseDouble(aValues[2]));
				
				//set Z coordinate if provided
				if (aValues.length == 4) {
					v.setZ(Double.parseDouble(aValues[3])); 
				}
				
				vSet.addVertex(v);
			}
		
			br.close();
		
		} catch(Exception e) {
			e.printStackTrace();
		}
				
	}
	
	

	/**
	 * Appends the provided string to the text file stored in 
	 * «current project path + "\PopBook\populations.txt"».
	 * @param c a string, ideally ending with a line break (CRLF)
	 * 
	 */
	public void writeToPopBook(String c) {
		
		try {
			BufferedWriter fw = new BufferedWriter(new FileWriter(serviceLocator.getPopBookFile(),true));		
			fw.write(c);
			fw.flush();
			fw.close();
			fw =null;
		} catch (IOException e) { 
			e.printStackTrace();
		}
	
	}
	
	
	/**
	 * cleans the content of the the text file stored in 
	 * «current project path + "\PopBook\populations.txt"».
	 */
	public void cleanPopBook() {
		
		try {
			BufferedWriter fw = new BufferedWriter(new FileWriter(serviceLocator.getPopBookFile(),false));		
			fw.write("");
			fw.flush();
			fw.close();
			fw =null;
		} catch (IOException e) { 
			e.printStackTrace();
		}
		
		
	}

	
	//checks if a file is valid
	/**
	 * Method use to verify if the provided file
	 * has a valid structure in order to initiate
	 * the vertices accordingly.
	 * @param f file with expected structure
	 * @return true if the file has a legal
	 * 		  structure, false if not
	 */
	public boolean correctStructure(File f) {
		
		boolean lReturn = false;
		String cLine = "";
		String cName = "";
		String cX = "0.0";
		String cY = "0.0";
		String cZ = "0.0";
		boolean lExit = false;
		
		FileInputStream fstream = null;
		BufferedReader br = null;
		
		if(!lExit) {

			try {
				// open the file
				fstream = new FileInputStream(f);
			} catch (FileNotFoundException e) {
				lExit = true;
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("File cannot be open");
				alert.showAndWait();
				e.printStackTrace();
			}
		}
			
		
		if(!lExit) {
			
			try {
				InputStreamReader rd = new InputStreamReader(fstream,"iso-8859-1");
				br = new BufferedReader(rd ,1000 * 8192);
			} catch (UnsupportedEncodingException e) {
				lExit = true;
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("File cannot be read");
				alert.showAndWait();
				e.printStackTrace();
			}
		}
		
		
		if(!lExit) {
			
			//read file line by line
			try {
				while ((cLine = br.readLine()) != null && !lExit)   {
					
					//correct structure:
					//[name] + \t  + [x] + \t + [y] + \t + [z]
					
					String aValues[] = cLine.split("\t");
										
					cName = aValues[0];
					cX = aValues[1];
					cY = aValues[2];
					if (aValues.length == 4) {
						cZ = aValues[3];
					}
					
					//check if name exists
					if(cName.length()==0) {
						lExit = true;
					}
					
					//check number validity
					try {
					  Double.parseDouble(cX);
					  Double.parseDouble(cY);
					  Double.parseDouble(cZ);
					} catch(NumberFormatException e) {
					  lExit = true;
					}

					System.out.println(cName + "|" + cX + "|" + cY + "|" + cZ);
					
				}
			} catch (IOException e) {
				lExit = true;
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("File has not appropriate structure");
				alert.showAndWait();
				e.printStackTrace();
			}
					
		}
				
		//clean up
		try { br.close(); } catch (IOException e) {e.printStackTrace();}
		
		//define return value
		if(!lExit) {
			lReturn = true;
		} else {
			lReturn = false;
		}

		return lReturn;
	}
	
	
	
}
