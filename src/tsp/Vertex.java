package tsp;


/**
 * A Vertex represents a point which should get
 * perambulated.
 * @author Roman Boegli
 *
 */
public class Vertex {

	
	private long nID;
	private String cName;
	private double nX; //latitude (Breitengrad)
	private double nY; //longitude (Längengrad)
	private double nZ;
	
	private static long nCount;
	
	
	
	
	/**
	 * constructor I, used to create a
	 * random vertex with help of the
	 * .getRandom method
	 */
	private Vertex() {

	}
	
	
	
	/**
	 * constructor II, used to initiate vertices
	 * @param cName name of the vertex
	 * @param nX x coordinate (latitude)
	 * @param nY y coordinate (longitude)
	 */
	public Vertex(String cName, double nX, double nY) {
		
		//pass on to constructor III with 
		//default value 0 for height (nZ)
		this(cName, nX, nY, 0);
	}
	
	
	/**
	 * constructor III, used to initiate vertices
	 * @param cName name of the vertex
	 * @param nX x coordinate (latitude)
	 * @param nY y coordinate (longitude)
	 * @param nZ z coordinate (height)
	 */
	public Vertex(String cName, double nX, double nY, double nZ) {
		
		this.nID = ++nCount;
		
		this.cName = cName;
		this.nX = nX;
		this.nY = nY;
		this.nZ = nZ;
	}


	

	/**
	 * initiates a random vertex
	 * @param nXmin x coordinate's lower bound
	 * @param nXmax x coordinate's upper bound
	 * @param nYmin y coordinate's lower bound
	 * @param nYmax y coordinate's upper bound
	 * @param nZmin z coordinate's lower bound
	 * @param nZmax z coordinate's upper bound
	 * @return randomly generated Vertex
	 */
	public static Vertex getRandom(double nXmin, double nXmax, double nYmin, double nYmax, double nZmin, double nZmax) {
		
		Vertex aReturn = new Vertex(); //private constructor
		String cName;
		double nX;
		double nY;
		double nZ;
		
		//get hash code as name
		cName = String.valueOf(System.identityHashCode(aReturn));
		
		//get random coordinates within range
		nX = (nXmax - nXmin) * Math.random() + nXmin;
		nY = (nYmax - nYmin) * Math.random() + nYmin;
		nZ = (nZmax - nZmin) * Math.random() + nZmin;
		
		//set values
		aReturn.setName(cName);
		aReturn.setX(nX);
		aReturn.setY(nY);
		aReturn.setZ(nZ);
		
		//return the vertex
		return aReturn;
	}
	

	
	/*
	 *object attribute's getters and setters 
	 */
	
	public String getName() {
		return cName;
	}
	
	private void setName(String c) {
		this.cName = c;
	}


	public double getX() {
		return nX;
	}

	private void setX(double n) {
		this.nX = n;
	}

	public double getY() {
		return nY;
	}
	
	private void setY(double n) {
		this.nY = n;
	}
	
	public double getZ() {
		return nZ;
	}

	public void setZ(double n) {
		this.nZ = n;
	}
	
	
	
	/**
	 * retrieves the coordinates of the vertex
	 * and returns it as a Double array
	 * @return coordinates as a Double array
	 * 		  with three elements
	 */
	public Double[] getCoordinates() {
		
		Double aReturn[] = new Double[] {this.nX,this.nY, this.nZ};
		
		return aReturn;
	}
	
	
	/**
	 * method used to get detailed information about a 
	 * vertex in form of a one line string.
	 * @return name, x-, y- and z-coordinate as a one
	 * 		  line info string
	 */
	public String getInfo() {
		
		String cReturn = this.cName + ": " + this.nX + "," + this.nY + "," + this.nZ ;
		
		return cReturn;
	}
	
	
	
	/**
	 * Calculates the distance between two vertices 
	 * with help of the Pythagorean Theorem. Only
	 * applicable when working with the CH1903 or CH1903+
	 * coordinate system (Swiss Grid).
	 * @param v aimed vertex to which the distance
	 * 			should be calculated
	 * @return distance to Vertex 'v' as a double value
	 */
	public double getDistanceToCH1903(Vertex v) {
		
		double nReturn = 0;
		double a = 0; 
		double b = 0;
		
		a = v.getX() - this.getX();
		b = v.getY() - this.getY();
		
		a = Math.pow(a, 2);
		b = Math.pow(b, 2);
		
		nReturn = Math.sqrt((a+b));
		
		return nReturn;
		
	}
	
	
	/**
	 * Calculates the distance between two vertices 
	 * with help of the Pythagorean Theorem plus
	 * taking the height into account. Only
	 * applicable when working with the WGS
	 * coordinate system (World Grid).
	 * 
	 * source: https://goo.gl/24SHHA (David George)
	 * 
	 * @param v aimed vertex to which the distance
	 * 			should be calculated
	 * @return distance to Vertex 'v' as double
	 */
	public double getDistanceToWGS(Vertex v) {
		
		
		double nX_from = this.getX();
		double nY_from = this.getY();
		double nZ_from = this.getZ();
		
		double nX_to = v.getX();
		double nY_to = v.getY();
		double nZ_to = v.getZ();
		
	    final int R = 6371; // Radius of the earth in kilometer

	    double latDistance = Math.toRadians(nX_to - nX_from);
	    double lonDistance = Math.toRadians(nY_to - nY_from);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	               + Math.cos(Math.toRadians(nX_from)) * Math.cos(Math.toRadians(nX_to))
	               * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    double height = nZ_from - nZ_to;

	    distance = Math.pow(distance, 2) + Math.pow(height, 2);

	    return Math.sqrt(distance);
	}
	
	
	
}	
