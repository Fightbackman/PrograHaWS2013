package lufti.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author ubik
 */
public class SimpleWindow extends javax.swing.JFrame implements TurtleWindow {

	private Canvas canvas;
	private ArrayList<TurtleWindowListener> listener = new ArrayList<TurtleWindowListener>();

	public static void setLookAndFeel(String name) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if (name.equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception ex) {
			java.util.logging.Logger.getLogger(SimpleWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
	}

	/**
	 * Creates a new window with the specified parameters.
	 *
	 * @param width      The width of the window (not including window
	 *                      decorations)
	 * @param height     The height of the window (not including window
	 *                      decorations)
	 * @param fps        The number of redraws per second
	 * @param background The background color of the main canvas.
	 * @return A new window instance.
	 */
	public static SimpleWindow create(int width, int height, int fps, Color background) {

		final SimpleWindow win = new SimpleWindow(width, height, fps, background);

		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				win.setVisible(true);
			}
		});

		return win;
	}

	private SimpleWindow(int width, int height, int fps, Color background) {
		initComponents(width, height, fps, background);
	}

	@Override
	public Canvas getCanvas() {
		return canvas;
	}

	private void initComponents(int width, int height, int fps, Color background) {

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		BoxLayout layout = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
		getContentPane().setLayout(layout);

		canvas = new Canvas(fps, background);
		canvas.setSize(width, height);
		canvas.setPreferredSize(new Dimension(width, height));
		
		final JTextField inputStart = new JTextField("F+F+F+F");
		final JTextField inputRule = new JTextField("F+F-F-FF+F+F-F");
		final JSpinner spinnerIt = new JSpinner(new SpinnerNumberModel(3, 0, 15, 1));
		final JSpinner spinnerAngle = new JSpinner(new SpinnerNumberModel(0, 0, 360, 1));
		final JSpinner spinnerAngleInc = new JSpinner(new SpinnerNumberModel(90, 0, 360, 1));
		final JButton button = new JButton("Zeichne");	

		inputStart.setPreferredSize(new Dimension(200, 30));
		inputRule.setPreferredSize(new Dimension(200, 30));
		spinnerIt.setPreferredSize(new Dimension(200, 30));
		spinnerAngle.setPreferredSize(new Dimension(200, 30));
		spinnerAngleInc.setPreferredSize(new Dimension(200, 30));
		
		getContentPane().add(canvas);
		getContentPane().add(createLabeledComponent(inputStart, "Anfang:"), 0.0f);
		getContentPane().add(createLabeledComponent(inputRule, "Regel:"), 0.0f);
		getContentPane().add(createLabeledComponent(spinnerIt, "Iterationen:"), 0.0f);
		getContentPane().add(createLabeledComponent(spinnerAngle, "Startwinkel:"), 0.0f);
		getContentPane().add(createLabeledComponent(spinnerAngleInc, "Drehwinkel:"), 0.0f);
		getContentPane().add(createLabeledComponent(button, ""), 0.0f);
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				String start = inputStart.getText();
				String cmd = inputRule.getText();
				int n = (Integer) spinnerIt.getValue();
				int degAlpha = (Integer) spinnerAngle.getValue();
				int degDelta = (Integer) spinnerAngleInc.getValue();
				double alpha = degAlpha / 180f * Math.PI;
				double delta = degDelta / 180f * Math.PI;
				for (TurtleWindowListener l : listener) {
					try {
						l.drawTurtle(n, alpha, delta, 100.0, start, cmd);
					} catch (Exception ex) {
						String msg = ex.getMessage();
						JOptionPane.showMessageDialog(rootPane, msg);
					}
				}
			}
		});

		pack();

		// Center window
		setLocationRelativeTo(null);
	}
	
	private JComponent createLabeledComponent(JComponent comp, String caption) {
		JPanel group = new JPanel();
		group.setLayout(new FlowLayout(FlowLayout.LEFT));
		Label label = new Label(caption);
		label.setPreferredSize(new Dimension(120, 30));
				
		group.add(label);
		group.add(comp);
		
		return group;
	}

	@Override
	public JFrame getMainWindow() {
		return this;
	}

	@Override
	public void addListener(TurtleWindowListener l) {
		listener.add(l);
	}
}
