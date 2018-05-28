//package tsp;
//
//import java.util.ArrayList;
//
///**
// * @author Roman Boegli
// */
//
//public class GA {
//	
//	
//	private static GA singleton = null;
//	public double MutationRate;
//	
//	
//		
//	
//	/*
//	 * constructor used
//	 * to maintain singleton
//	 */
//	private GA() {
//
//	}
//	
//	
//	
//	/**
//	 * Getter method for an object
//	 * instance of GA
//	 * 
//	 * @return object of a GA as a singleton
//	 */
//	public static GA getInstance() {
//		
//		if(singleton == null) {
//			singleton = new GA();
//		}
//		
//		return singleton;
//	}
//	
//	
//
//	
//	/**
//	 * sets the mutation rate
//	 * 
//	 * @param n rate of mutation being
//	 * 			used in the GA. The higher
//	 * 			this value, the more randomness
//	 * 			is taking part of the evaluation
//	 */
//	public void setMutationRate(double n) {
//		this.MutationRate = n;
//	}
//
//	
//	
//	public void createNextGeneration(Population p) {
//		
//	}
//	
//	
//	
//	
//	
//	
//	
//	private static Path cross(Path p1, Path p2) {
//		
//		Path aReturn = null;
//		
//		int nLength = p1.getLength();
//		int nSequenceLenght = (int) (nLength * Math.random());
//		
//		//set minimum length
//		if(nSequenceLenght==0) { 
//			nSequenceLenght=2;
//		}
//			
//		//decrease length
//		if(nSequenceLenght==nLength) { 
//			nSequenceLenght -= 2;
//		}
//		
//		
//		int nStartIndex = (int) ((nLength-nSequenceLenght) * Math.random());
//		
//		//define new order (child)
//		int[] order = new int[nLength];
//		
//		//put -1 into each slot (placeholder)
//		for (int i=0 ; i<nLength ; i++) {
//			order[i] = -1;
//		}
//		
//		
//		//fill in woman genome
//		for(int i=nStartIndex ; nSequenceLenght>0 ; i++) {
//			
//			order[i] = p1.get(i);
//			nSequenceLenght--;
//		}
//		
//		
//		//fill in man genome
//		int n=0;
//		for (int i=0 ; i<nLength ; i++) {
//			if (order[i]==-1) {
//				
//				//must be filled
//				boolean lExit=false;				
//				while(!lExit) {
//					//check if already used
//					if (Path.contains(order, p2.get(n))) {
//						n++;
//					} else {
//						lExit = true;
//					}
//				}
//				order[i] = p2.get(n);
//			}
//		}
//				
//		aReturn = new Path(order);
//				
////		System.out.println("");
////		System.out.println("crossing:");
////		System.out.println("p1: " + p1.getInfo());
////		System.out.println("p2: " + p2.getInfo());
////		System.out.println("p3: " + aReturn.getInfo());
//				
//		return aReturn;
//	}
//	
//	
//	
//	
//	//the aim is, to pick a path with a better fitness
//	//more likely than a path with a smaller fitness
//	private static Path pickOne(ArrayList<Path> list) {
//		
//		Path aReturn =null;
//		int index = 0; //assumption
//		double r = Math.random();
//		
//		
//		/**
//		 * imagine we would have two elements in the list.
//		 * The first element has a fitness of 0.9, the
//		 * second one of 0.1. Consider the fact, that the
//		 * fitness indicates (rather obvious) how well an 
//		 * element fits. Hence we misuse this value as the
//		 * probability of being picked. Now, when the random
//		 * the probability that will will oversee the first
//		 * well fitting element (0.9) resides by 10%, since
//		 * the random number must be between ~0.91 and 1.0.
//		 */
//		while (r > 0) {
//			r = r - list.get(index).getFitness();
//			index++;
//		}
//		
//		//take previous one (the one which caused the loop exit)
//		index--;
//		
//		//get the path
//		aReturn = list.get(index);
//				
//		return aReturn;
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//}
