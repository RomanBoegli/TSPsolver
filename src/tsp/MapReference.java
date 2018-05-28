package tsp;

/*
 * references borders of a map
 */


public class MapReference {
	
	double MinX;
	double MaxX;
	double MinY;
	double MaxY;
	double MinZ;
	double MaxZ;	
	
	
	//default constructor
	public MapReference() {
		
		//set maximas
		this.MinX = Double.MAX_VALUE;
		this.MaxX = Double.MIN_VALUE;
		this.MinY = Double.MAX_VALUE;
		this.MaxY = Double.MIN_VALUE;
		this.MinZ = Double.MAX_VALUE;
		this.MaxZ = Double.MIN_VALUE;
	}
	

	
	public double getMinX() {
		return MinX;
	}


	public void setMinX(double n) {
		this.MinX = n;
	}


	public double getMaxX() {
		return MaxX;
	}


	public void setMaxX(double n) {
		this.MaxX = n;
	}


	public double getMinY() {
		return MinY;
	}


	public void setMinY(double n) {
		this.MinY = n;
	}


	public double getMaxY() {
		return MaxY;
	}


	public void setMaxY(double n) {
		this.MaxY = n;
	}


	public double getMinZ() {
		return MinZ;
	}


	public void setMinZ(double n) {
		this.MinZ = n;
	}


	public double getMaxZ() {
		return MaxZ;
	}


	public void setMaxZ(double n) {
		this.MaxZ = n;
	}
	
	public double getMapWidth() {
		return this.MaxX - this.MinX;
	}

	public double getMapHeight() {
		return this.MaxY - this.MinY;
	}
	
	public String getInfo() {
		
		String cReturn = "";
		
		cReturn += "P_min(" + String.format("%.8f",this.getMinX()) + " , " + 
							  String.format("%.8f",this.getMinY()) + " , " + 
							  String.format("%.8f",this.getMinZ()) + " )" + "\n" ;
		
		cReturn += "P_max(" + String.format("%.8f",this.getMaxX()) + " , " + 
							  String.format("%.8f",this.getMaxY()) + " , " + 
							  String.format("%.8f",this.getMaxZ()) + " )" + "\n" ;
		
		
		return cReturn;
		
	}
	
	
	
	
}
