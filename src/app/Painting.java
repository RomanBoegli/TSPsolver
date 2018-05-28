package app;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author Roman Boegli
 */

public class Painting extends Canvas{
	
	private static long nCount;
	public long nID;
	public double nWidth;
	public double nHeight;
	
	
	
	
	
	
	/**
	 * Constructor I
	 * @param nWidth width in pixel
	 * @param nHeight height in pixel
	 */
	public Painting(double nWidth, double nHeight) {
		
        super(nWidth, nHeight);
				
        this.nID = ++nCount;
		this.nWidth = getWidth();
		this.nHeight = getHeight();
				
	}
	
	
		
	
	
	public double getPaintingWidth() {
		return this.nWidth;
	}
	
	public double getPaintingHeight() {
		return this.nHeight;
	}
	
	
}
