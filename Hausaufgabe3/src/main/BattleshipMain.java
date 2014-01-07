package main;

import java.awt.Color;
import java.io.IOException;
import lufti.battleship.BattleshipGame;
import lufti.ui.SimpleWindow;
import lufti.ui.Window;

/**
 *
 * @author ubik
 */
public class BattleshipMain {

	@SuppressWarnings("ResultOfObjectAllocationIgnored")
	public static void main(String args[]) throws IOException {
		Window win = SimpleWindow.create(1000, 600, 40, Color.darkGray);
		BattleshipGame.attach(BattleshipGame.create(), win, 40);
	}
}
