package lufti.fractrees;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;
import lufti.game.AbstractApp;
import lufti.ui.Canvas.CanvasPainter;
import lufti.ui.TurtleWindow;

/**
 *
 * @author reidl
 */
public class FractreeApp extends AbstractApp implements TurtleWindow.TurtleWindowListener {

	private int width, height;
	private BufferedImage img;

	private FractreeApp(int width, int height) {
		this.width = width;
		this.height = height;
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	public static FractreeApp create(int width, int height) {
		return new FractreeApp(width, height);
	}

	@Override
	public void render(CanvasPainter pntr) {
		pntr.getGraphhics().drawImage(img, null, null);
	}
	
	@Override
	public void drawTurtle(int n, double alpha, double delta, double len, String start, String rule) throws Exception {
		// Prepare image, fill with grey
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gr = (Graphics2D) img.getGraphics();
		gr.setColor(Color.darkGray);
		gr.fillRect(0, 0, width, height);
		
		// Build command
		CommandBuilder commandBuilder = new CommandBuilder(start);	
		for (int i = 0; i < n; i++) {
			commandBuilder.replace(rule);
		}
		String command = commandBuilder.toString();

		// Use boundary turtle to determine area in which lines will be drawn
		BoundaryTurtle bndTurtle = new BoundaryTurtle(alpha, delta, len);
		bndTurtle.execute(command);
		Double boundary = bndTurtle.getBoundary();

		// Translate and scale graphics object accordingly
		double scale = width / boundary.getWidth();
		scale = Math.min(scale, height / boundary.getHeight());
		gr.scale(scale, scale);
		gr.translate(-boundary.x - .5 * (boundary.getWidth() - width / scale), -boundary.y - .5 * (boundary.getHeight() - height / scale));

		gr.setStroke(new BasicStroke((float) (1.0 / scale)));
		gr.setColor(Color.GRAY);
		gr.draw(boundary);

		// Use drawing turtle to draw the fractal given by the command
		DrawingTurtle turtle = new DrawingTurtle(alpha, delta, len);
		gr.setColor(Color.GREEN);
		turtle.setGraphics(gr);
		turtle.execute(command);
	}
}
