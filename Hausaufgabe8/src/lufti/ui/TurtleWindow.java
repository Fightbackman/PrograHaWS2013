package lufti.ui;

import javax.swing.JFrame;

public interface TurtleWindow {
    public Canvas getCanvas();
    public JFrame getMainWindow();
	public void addListener(TurtleWindowListener l);
	
	public interface TurtleWindowListener {
		public void drawTurtle(int n, double alpha, double delta, double len, String start, String command) throws Exception;
	}
}
