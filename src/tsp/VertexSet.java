package tsp;

import java.util.ArrayList;

/**
 * A VertexSet is used to store multiple
 * vertices in form of an ArrayList. Additionally
 * the VertexSet provides a map reference helping
 * to draw all vertices properly onto the GUI.
 * @author Roman Boegli
 *
 */
public class VertexSet {
	
	
	private long nID;
	private ArrayList<Vertex> Set = new ArrayList<Vertex>();
	public MapReference mp;	
	
	private static long nCount;
	
	
	
	/**
	 * constructor I, used to initiate a
	 * new empty VertexSet
	 */
	public VertexSet() {
		
		this.nID = ++nCount;
	}
	
	
	
	/**
	 * constructor II, used to initiate a
	 * new VertexSet with the vertices provided
	 * @param vertices multiple Vertex object
	 * 				    which should be stored
	 * 					 in the VertexSet
	 */

	public VertexSet(Vertex ... vertices) {
		
		this.nID = ++nCount;
		for(Vertex v : vertices) {
			this.Set.add(v);
		}
	}
	
	
	/**
	 * constructor III, used to initiate a
	 * new VertexSet with the vertices provided
	 * @param aList an ArrayList holding multiple
	 * 				 Vertex objects which should 
	 * 				 be stored in the VertexSet
	 */
	public VertexSet(ArrayList<Vertex> aList) {
		
		this.nID = ++nCount;
		this.Set = aList;
	}
	
	
	
	
	/**
	 * Appends the provided Vertex to the 
	 * end of the VertexSet.
	 * @param v Vertex being appended.
	 */
	public void addVertex(Vertex v) {
		this.Set.add(v);
	}
	
	
	/**
	 * Returns the vertex at the specified 
	 * position in this VertexSet.
	 * @param index Index of the element to return.
	 * @return Vertex stored at indicated index.
	 */
	public Vertex getVertex(int index) {
		return this.Set.get(index);
	}
	
	
	/**
	 * Returns the number of vertices stored
	 * in the VertexSet.
	 * @return Number of vertices stored in
	 * 		  in the VertexSet.
	 */
	public int getNumberOfVertices() {
		
		int nReturn = this.Set.size();
		
		return nReturn;
	}
	
	
	/**
	 * Method used to get detailed information about a 
	 * VertexSet in form of a multiline string. 
	 * @return Accumulates the info string of each
	 * 		  Vertex stored in this VertexSet 
	 * 		  retrieved by the Vertex-method
	 * 		  .getInfo().
	 * 		 
	 */
	public String getInfo() {
		
		String cReturn = "";
		
		for (int i = 0; i < Set.size() ; i++) {
			
			//accumulate info of each single vertex 
			cReturn += Set.get(i).getInfo() + "\n";
		}		
		
		return cReturn;
	}
	
	
	
	/**
	 * Returns the total distances generated
	 * when traveling trough all the vertices
	 * stored in this VertexSet according to the
	 * provided Path. Calculated the distances
	 * based on the WGS coordinate system
	 * (World Grid).
	 * @param p Path object indicating in what
	 * 			order the vertices are being
	 * 			perambulated
	 * @return the total distance as a double value
	 */
	public double getTotalDistanceWGS(Path p) {
		
				
		double nReturn=0;
		int nMax = p.getLength() - 1;
		int nFrom;
		int nTo;
		
		//sum up all distances between the vertices (edges)
		//according to the passed path (order)
		for(int i=0; i < nMax; i++){
			
			nFrom = p.get(i);
			nTo = p.get(i+1);
			
			nReturn += this.Set.get(nFrom).getDistanceToWGS(this.Set.get(nTo));
		}
		
	   //// last edge //!!!! this is not a closed path
		//nReturn += this.Set.get(nMax).getDistanceToWGS(this.Set.get(0));
		
		return nReturn;
	}
	
	

	/**
	 * Locates the corner points in order to
	 * draw all vertices properly
	 */
	public void defineMapReference() {
		
		//create new MapReference
		this.mp = new MapReference();
				
		//loop through vSet and create MapReference
		for (int i = 0; i < Set.size() ; i++) {
			
			Vertex v = Set.get(i);
			
			if(v.getX() < mp.getMinX()) {
				mp.setMinX(v.getX());
			}
			if(v.getX() > mp.getMaxX()) {
				mp.setMaxX(v.getX());
			}
			if(v.getY() < mp.getMinY()) {
				mp.setMinY(v.getY());
			}
			if(v.getY() > mp.getMaxY()) {
				mp.setMaxY(v.getY());
			}
			
			/*actually only needed when drawing
			 *3D models
			 */
			if(v.getZ() < mp.getMinZ()) {
				mp.setMinZ(v.getZ());
			}
			if(v.getZ() > mp.getMaxZ()) {
				mp.setMaxZ(v.getZ());
			}
		}
				
	}
	
	
	
}
