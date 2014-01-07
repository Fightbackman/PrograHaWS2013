package lufti.fractrees;

import java.awt.Graphics2D;

/**
 * A 
 * @author reidl
 */
public class DrawingTurtle extends Turtle {

	private Graphics2D target;

	public DrawingTurtle(double angle, double delta, double len) {
		super(angle, delta, len);
	}
	
	public void setGraphics(Graphics2D gr) {
		target = gr;
	}
	
	/**
	 * Draws a line on the internal graphics object 'target' using
	 * the method drawLine(...). 
	 * 
	 * Hint: the method step() implemented in the superclass can
	 * be used here.
	 * 
	 * If the graphics object has not yet been set, it throws a 
	 * TurtleExcpetion.
	 * @throws TurtleException 
	 */
	@Override
	public void stepAndDraw() throws TurtleException {
		// TODO
	}
}
