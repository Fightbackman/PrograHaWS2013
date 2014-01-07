package lufti.fractrees;

import java.awt.geom.Rectangle2D;

/**
 * A turtle that determines the bounding rectangle of
 * the path it travels.
 * 
 * @author reidl
 */
public class BoundaryTurtle extends Turtle {

	private double left, right, top, bottom;
	
	public BoundaryTurtle(double angle, double delta, double len) {
		super(angle,delta,len);
		
		left = right = 0;
		top = bottom = 0;
	}
	
	public Rectangle2D.Double getBoundary() {
		return new Rectangle2D.Double(left, top, right-left, bottom-top);
	}

	
	@Override
	public void step() {
		/*
		 * Execute step and update the values left, right, top and bottom
		 */
		
		
		// TODO
	}

	@Override
	public void stepAndDraw() throws TurtleException {
		/*
		 * Execute step and update the values left, right, top and bottom
		 */		
		
		// TODO
	}
}
