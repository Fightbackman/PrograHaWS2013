package lufti.game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;
import lufti.ui.Canvas;
import lufti.ui.TurtleWindow;

/**
 * An abstract game class providing asynchronous render() and update() methods.
 *
 * The game must be attached to a window via AbstractGame.attach(...) to run,
 * the rendering takes place in the Canvas supplied by the Window via
 * getCanvas().
 *
 * @author ubik
 */
public abstract class AbstractApp {

	public AbstractApp() {
	}

	/**
	 * Attaches the game to a window and executes it.
	 *
	 * @param target The game to run
	 * @param window A window to attach to
	 * @param ups    The number of updates per second.
	 */
	public static void attach(final AbstractApp target, TurtleWindow window, int ups) {
		window.getCanvas().addRenderCallback(new Canvas.RenderCallback() {
			@Override
			public void render(Canvas.CanvasPainter pntr) {
				synchronized (target) {
					target.render(pntr);
				}
			}
		});
	}

	/**
	 * This function should paint all the elements of the game appropiately on
	 * the provided canvas.
	 *
	 * @param pntr The canvas on which to paint.
	 */
	public abstract void render(Canvas.CanvasPainter pntr);
}
