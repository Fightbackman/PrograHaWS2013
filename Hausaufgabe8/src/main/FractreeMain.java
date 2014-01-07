package main;

import java.awt.Color;
import java.io.IOException;
import lufti.fractrees.FractreeApp;
import lufti.game.AbstractApp;
import lufti.ui.SimpleWindow;
import lufti.ui.TurtleWindow;

/**
 *
 * @author ubik
 */
public class FractreeMain {

	@SuppressWarnings("ResultOfObjectAllocationIgnored")
	public static void main(String args[]) throws IOException {
		int w = 1000;
		int h = 600;
		TurtleWindow win = SimpleWindow.create(w, h, 40, Color.darkGray);
		
		FractreeApp app = FractreeApp.create(w,h);
		AbstractApp.attach(app, win, 40);
		win.addListener(app);
	}
}
