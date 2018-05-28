package tsp;


/**
 * A Path is used to store a specific travel
 * route through all vertices. This is done 
 * with help of an int array which holds the
 * index values a VertexSet. The order of 
 * how to travel the vertices can then simply
 * be retrieved by iterating through the array
 * from the beginning to the end. Additionally, 
 * each Path is able to store its fitness in 
 * form of a double value. The fitness is always
 * being calculated in relation to all the other
 * paths in the population.
 *  
 * @author Roman Boegli
 *
 */
public class Path implements Cloneable{

	private long nID;
	private int[] Order;
	private double nFitness;


	private static long nCount;
	
	
	/**
	 * constructor I, used to initiate paths
	 * @param nVertices indicates the length of 
	 * 					  the path
	 * @param lShuffle shuffles the order if set to true
	 * 					 intensively
	 */
	public Path(int nVertices, boolean lShuffle) {
		
		//set ID of Path
		this.nID = ++nCount;
		
		//initialize the array
		this.Order = new int[nVertices];
		
		//set initial order (path)
		for (int i = 0 ; i < nVertices ; i++ ) {
			this.Order[i] = i;
		}
		
		if(lShuffle) {
			//shuffle the elements randomly
			this.shuffle(nVertices);
		}
	}
	
		
	/**
	 * constructor II, used to create a copy
	 * of another path by means of the 
	 * .clone() method
	 * @param copyOf Path being copied
	 */
	private Path(Path copyOf) {
		this.Order = copyOf.Order.clone();
		this.nID = copyOf.nID;
	}
	
	
	/**
	 * constructor III, used to initiate a
	 * Path with a give order
	 * @param order int array providing the 
	 * 				 order
	 */
	public Path(int[] order) {
		//set ID of Path
		this.nID = ++nCount;
		this.Order = order.clone();
	}
	
	
	/**
	 * creates a clone of a Path with help
	 * of the constructor II
	 */
	public Path clone() {
		return new Path(this);
	}
		

	
	/**
	 * shuffles the order according to
	 * the intensity
	 * @param nIntensity number of swaps
	 * 						performed
	 */
	private void shuffle(int nIntensity) {

		int nMax = this.getLength();
		int nIndexA = -1; //arbitrary
		int nIndexB = -1; //arbitrary
		
		boolean lExit = false; 
		
		
		if(nMax <= 1) {
			//no need to swap
			lExit = true;
		}
		
		if(nMax == 2) {
			//swap once
			this.swap(0, 1);
			lExit = true;
		}
		
		
		if (!lExit) {
			//swap two elements randomly a certain number of times
			for ( int i = 0 ; i <= nIntensity ; i++ ) {
				
				//set indices equal
				nIndexA = nIndexB;
				
				//find two different indices
				while(nIndexA == nIndexB) {
					//choose two indices randomly
					nIndexA = (int)(Math.floor(nMax * Math.random())); 
					nIndexB = (int)(Math.floor(nMax * Math.random())); 
				}
				
				//swap those two
				this.swap(nIndexA, nIndexB);
			}
		}
	}
	
	
	
	/**
	 * makes two values of the int array
	 * Order changing place
	 * @param a one index of the array
	 * @param b another index of the array
	 */
	private void swap(int a, int b) {
		
		int temp = this.Order[a];
		this.Order[a] = this.Order[b];
		this.Order[b] = temp;
	}
	
	
		
	/**
	 * swaps values in the int array
	 * Order randomly according to the
	 * mutation rate
	 * @param nMutationRate Should be a value between
	 * 							0 and 1 indicating how much
	 * 							percent of the path may be 
	 * 							affected through mutation.
	 * 							Mutation will be performed
	 * 							by means of the .shuffle()
	 * 							method.
	 */
	public void mutate(double nMutationRate) {
		
		int nIntensity = 0;
			
		/*when mutation rate is 0.4, only 40% of the path 
		 * should be changed   */
		int len = this.getLength();
		double a = Math.floor(len * Math.abs(nMutationRate));
		nIntensity = (int)(a);
		
	   /*since the mutation will be simply execute by swapping
		  the elements we have to divide the intensity by
		  two as well (one swap ~~> two changes)   */
		nIntensity = nIntensity / 2;
		this.shuffle(nIntensity);
	}
	
	
	/**
	 * 
	 * @return length of the path
	 */
	public int getLength() {
		
		int nReturn = this.Order.length;
		
		return nReturn;
	}
	
	
	/**
	 * retrieves the value of a specific
	 * element in the path's Order array
	 * @param i index of the element being
	 * 			retrieved
	 * @return array value as an integer
	 */
	public int get(int i) {
		
		int nReturn = this.Order[i];
		
		return nReturn;
	}
	
	
	
	
	/**
	 * method used to get information about a 
	 * path in form of a one line string.
	 * @return the specific order of vertices,
	 * 		  indicated by their indices (index based),
	 * 		  and the current fitness as a one 
	 * 		  line info string
	 */
	public String getInfo() {
		
		String cReturn = "";
		
		for( int i = 0 ; i < this.getLength() ; i++ ) {
			cReturn = cReturn + "[" + this.get(i) + "] ";
		}
		
		//remove last character
		cReturn = cReturn.substring(0, cReturn.length() - 1);
		
		cReturn = cReturn + "  {" + this.nFitness + "}";
		
		//used for debugging reasons
		//cReturn = cReturn + "  {" + System.identityHashCode(this) + "}";
		
		return cReturn;
	}
	
	

	/**
	 * method used to get detailed information about a 
	 * path in form of a one line string.
	 * @param vs VertexSet on which the path applies
	 * @return the specific order of vertices,
	 * 		  indicated by their names (name based),
	 * 		  current fitness and total distance as
	 * 		  a one line info string
	 */
	public String getInfo(VertexSet vs) {
		
		int nIndex;
		Vertex v;
		String cReturn = "";
		
		for( int i = 0 ; i < this.getLength() ; i++ ) {
			nIndex = this.get(i);
			v = vs.getVertex(nIndex);
			cReturn = cReturn + "[" + v.getName() + "] -> ";
		}
		
		//remove last character
		cReturn = cReturn.substring(0, cReturn.length() - 4);
		
		cReturn = cReturn + "  {" + this.nFitness + "}";
		
		cReturn = cReturn + "  {" +  String.format("%.4f", vs.getTotalDistanceWGS(this)) + "}";
				
		//used for debugging reasons
		//cReturn = cReturn + "  {" + System.identityHashCode(this) + "}";
		//cReturn = cReturn + "  {" + System.identityHashCode(this.Order) + "}";
		
		return cReturn;
	}
	
	
	
	
	/**
	 * gets the path's fitness
	 * @return Fitness as a double value
	 * 		  between 0 and 1. The higher the 
	 * 		  value the better the path
	 */
	public double getFitness() {
		return nFitness;
	}


	
	/**
	 * sets the path's fitness
	 * @param nFitness value between 0 and 1,
	 * 		          whereas a higher value
	 * 					 is considered as better
	 */
	public void setFitness(double nFitness) {
		this.nFitness = nFitness;
	}


	
	/**
	 * used to check if a value is included in 
	 * an array
	 * @param array the array being searched
	 * @param v the value which is being checked
	 * @return true if value is included, false 
	 * 		  if not
	 */
	public static boolean contains(final int[] array, final int v) {

        boolean result = false;

        for(int i : array){
            if(i == v){
                result = true;
                break;
            }
        }

        return result;
    }
	
	
	
}
