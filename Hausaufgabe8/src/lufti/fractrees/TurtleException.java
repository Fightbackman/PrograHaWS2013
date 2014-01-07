package lufti.fractrees;

/**
 *
 * @author reidl
 */
public class TurtleException extends Exception {
	private Turtle turtle;
	
	public TurtleException(final Turtle turtle) {
		this.turtle = turtle;
	}

	public Turtle getTurtle() {
		return turtle;
	}
	
	public static class InvalidSymbolException extends TurtleException {
		private char symbol;
		public InvalidSymbolException(Turtle turtle, char symbol) {
			super(turtle);
			this.symbol = symbol;
		}
		
		public char getInvalidSymbol() {
			return symbol;
		}

		@Override
		public String getMessage() {
			return "Invalid Symbol: " + symbol;
		}
	}
	
	public static class EmptyStackException extends TurtleException {
		public EmptyStackException(Turtle turtle) {
			super(turtle);
		}
		

		@Override
		public String getMessage() {
			return "Turtle stack is empty. More ']' than '['s?";
		}		
	}
}
