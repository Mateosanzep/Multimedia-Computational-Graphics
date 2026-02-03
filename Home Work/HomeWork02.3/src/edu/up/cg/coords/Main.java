package edu.up.cg.coords;

import javax.swing.*;
import java.awt.*;

public class Main {

	public static void main(String[] args) throws Exception {
		SwingUtilities.invokeLater(() -> new Main().build());
	}

	void build() {
		JFrame f = new JFrame("Coords Converter & Polar Rose");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel root = new JPanel(new BorderLayout());

		JPanel controls = new JPanel();
		controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));

		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JTextField rField = new JTextField("100", 6);
		JTextField phiField = new JTextField("45", 6);
		JButton p2cBtn = new JButton("Polar→Cartesian");
		JTextField xOut = new JTextField(8);
		JTextField yOut = new JTextField(8);
		p1.add(new JLabel("r"));
		p1.add(rField);
		p1.add(new JLabel("φ°"));
		p1.add(phiField);
		p1.add(p2cBtn);
		p1.add(new JLabel("x"));
		p1.add(xOut);
		p1.add(new JLabel("y"));
		p1.add(yOut);

		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JTextField xField = new JTextField("50", 6);
		JTextField yField = new JTextField("50", 6);
		JButton c2pBtn = new JButton("Cartesian→Polar");
		JTextField rOut = new JTextField(8);
		JTextField phiOut = new JTextField(8);
		p2.add(new JLabel("x"));
		p2.add(xField);
		p2.add(new JLabel("y"));
		p2.add(yField);
		p2.add(c2pBtn);
		p2.add(new JLabel("r"));
		p2.add(rOut);
		p2.add(new JLabel("φ°"));
		p2.add(phiOut);

		controls.add(p1);
		controls.add(p2);

		JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JTextField aField = new JTextField("100", 4);
		JSpinner kSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 50, 1));
		JTextField y0Field = new JTextField("0", 4);
		JButton plotBtn = new JButton("Plot Rose");
		p3.add(new JLabel("a"));
		p3.add(aField);
		p3.add(new JLabel("k"));
		p3.add(kSpinner);
		p3.add(new JLabel("y0°"));
		p3.add(y0Field);
		p3.add(plotBtn);
		controls.add(p3);

		root.add(controls, BorderLayout.NORTH);

		PlotPanel plot = new PlotPanel();
		root.add(plot, BorderLayout.CENTER);

		f.setContentPane(root);
		f.setSize(800, 600);
		f.setLocationRelativeTo(null);
		f.setVisible(true);

		p2cBtn.addActionListener(e -> {
			try {
				double r = Double.parseDouble(rField.getText());
				double deg = Double.parseDouble(phiField.getText());
				double rad = Math.toRadians(deg);
				double x = r * Math.cos(rad);
				double y = r * Math.sin(rad);
				xOut.setText(String.format("%.4f", x));
				yOut.setText(String.format("%.4f", y));
			} catch (Exception ex) {
			}
		});

		c2pBtn.addActionListener(e -> {
			try {
				double x = Double.parseDouble(xField.getText());
				double y = Double.parseDouble(yField.getText());
				double r = Math.hypot(x, y);
				double deg = Math.toDegrees(Math.atan2(y, x));
				rOut.setText(String.format("%.4f", r));
				phiOut.setText(String.format("%.4f", deg));
			} catch (Exception ex) {
			}
		});

		plotBtn.addActionListener(e -> {
			try {
				double a = Double.parseDouble(aField.getText());
				int k = (Integer) kSpinner.getValue();
				double y0deg = Double.parseDouble(y0Field.getText());
				plot.setRose(a, k, Math.toRadians(y0deg));
			} catch (Exception ex) {
			}
		});
	}

	class PlotPanel extends JPanel {
		double[] xs, ys;

		void setRose(double a, int k, double y0) {
			int steps = 2000;
			xs = new double[steps];
			ys = new double[steps];
			for (int i = 0; i < steps; i++) {
				double phi = (i / (double) (steps - 1)) * Math.PI * 2;
				double r = a * Math.cos(k * phi + y0);
				xs[i] = r * Math.cos(phi);
				ys[i] = r * Math.sin(phi);
			}
			repaint();
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			int w = getWidth(), h = getHeight();
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.WHITE);
			g2.fillRect(0, 0, w, h);
			g2.setColor(Color.LIGHT_GRAY);
			g2.drawLine(w / 2, 0, w / 2, h);
			g2.drawLine(0, h / 2, w, h / 2);
			if (xs == null) return;
			double max = 0;
			for (double v : xs) if (Math.abs(v) > max) max = Math.abs(v);
			for (double v : ys) if (Math.abs(v) > max) max = Math.abs(v);
			double scale = Math.min(w, h) / 2.0 / (max == 0 ? 1 : max);
			g2.setColor(Color.RED);
			for (int i = 0; i < xs.length - 1; i++) {
				int x1 = (int) Math.round(w / 2 + xs[i] * scale);
				int y1 = (int) Math.round(h / 2 - ys[i] * scale);
				int x2 = (int) Math.round(w / 2 + xs[i + 1] * scale);
				int y2 = (int) Math.round(h / 2 - ys[i + 1] * scale);
				g2.drawLine(x1, y1, x2, y2);
			}
		}
	}

}
