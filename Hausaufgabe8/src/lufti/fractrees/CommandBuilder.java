package lufti.fractrees;

/**
 * Builds a command string 
 * @author reidl
 */
public class CommandBuilder {
	
	private String current;
	
	public CommandBuilder(String start) {
		this.current = ""+start;
	}
	
	/**
	 * Replaces every occurrence of 'F' in the string 'current'
	 * by the string 'rule'.
	 * @param rule 
	 */
	public void replace(String rule) {
		// TODO
		current.replaceAll("F", rule);
	}
	
	@Override
	public String toString() {
		return ""+current;
	}
	
}
