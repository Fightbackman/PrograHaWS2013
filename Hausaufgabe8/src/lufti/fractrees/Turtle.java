/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lufti.fractrees;

import java.util.ArrayList;

/**
 * An abstract turtle following commands given in a
 * command string.
 * @author reidl
 */
public abstract class Turtle {

	protected TurtleState currentState;
	private final double delta, len;
	private final ArrayList<TurtleState> stack;

	public Turtle(double angle, double delta, double len) {
		this.delta = delta;
		this.len = len;

		currentState = new TurtleState(0, 0, angle);
		stack = new ArrayList<TurtleState>();
	}

	/**
	 * Stores the current state of the turtle in the
	 * internal stack
	 */
	public void push() {
		// TODO
	}

	/**
	 * Removes the latest state from the stack and makes it
	 * the turtle's current state. Throws a TurtleException.EmptyStackException
	 * if the stack is empty.
	 * @throws TurtleException 
	 */
	public void pop() throws TurtleException {
		// TODO
	}

	public abstract void stepAndDraw() throws TurtleException;

	/**
	 * Advances the turtles position by 'len' many units into the
	 * direction given by the angle of the current state.
	 */
	public void step() {
		// TODO
	}

	/**
	 * Turns the turtle counter-clockwise by an amount of 'delta'.
	 * This method should ensure that the angle stored in the current
	 * state lies in the interval [0,2pi).
	 */
	public void turnLeft() {
		// TODO
	}

	/**
	 * Turns the turtle clockwise by an amount of 'delta'.
	 * This method should ensure that the angle stored in the current
	 * state lies in the interval [0,2pi).
	 */	
	public void turnRight() {
		// TODO
	}

	/**
	 * Executes the commands as outlined on the exercise sheet, where the
	 * first command is at the start of the string and the last at the end.
	 * If the string contains any symbol except F f + - [ ] this method
	 * throws a TurtleException.InvalidSymbolException containing the 
	 * offending symbol.
	 * @param command the command string
	 * @throws TurtleException 
	 */
	public final void execute(String command) throws TurtleException {
		// TODO
		String commandstr = command;
		char[] commandchar = command.toCharArray();
		char f = 'F';
		char fl = 'f';
		char p = '+';
		char m = '-';
		char l = '[';
		char r = ']';
		for(int i = 0; i < commandchar.length; i++){
			if(commandchar[i]!= f && commandchar[i]!= fl && commandchar[i]!= p && commandchar[i]!= m && commandchar[i]!= l && commandchar[i]!=r){
				char wrong = commandchar[i];
				throw new TurtleException.InvalidSymbolException(this, wrong);
			}
		}
	}

	protected static class TurtleState {
		protected double x, y, angle;

		public TurtleState(double x, double y, double angle) {
			this.x = x;
			this.y = y;
			this.angle = angle;
		}

		public TurtleState copy() {
			return new TurtleState(x, y, angle);
		}
	}
}
