package edu.up.cg.aspectRatio;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(Main::createAndShowGUI);
	}

	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Aspect Ratio Calculator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(380, 180);
		frame.setLocationRelativeTo(null);

		JPanel p = new JPanel(new GridLayout(0, 2, 6, 6));

		p.add(new JLabel("Width:"));
		JTextField widthField = new JTextField();
		p.add(widthField);

		p.add(new JLabel("Height:"));
		JTextField heightField = new JTextField();
		p.add(heightField);

		JButton openFile = new JButton("Open File");
		p.add(openFile);
		JButton compute = new JButton("Compute Aspect Ratio");
		p.add(compute);

		p.add(new JLabel("Result:"));
		JLabel result = new JLabel(" ");
		p.add(result);

		frame.getContentPane().add(p);

		openFile.addActionListener(e -> {
			JFileChooser chooser = new JFileChooser();
			if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
				File f = chooser.getSelectedFile();
				try {
					BufferedImage img = ImageIO.read(f);
					if (img != null) {
						widthField.setText(String.valueOf(img.getWidth()));
						heightField.setText(String.valueOf(img.getHeight()));
						result.setText("Loaded: " + f.getName());
						return;
					}
				} catch (Exception ignored) {}
				try (Scanner sc = new Scanner(f)) {
					if (sc.hasNextInt()) {
						int w = sc.nextInt();
						if (sc.hasNextInt()) {
							int h = sc.nextInt();
							widthField.setText(String.valueOf(w));
							heightField.setText(String.valueOf(h));
							result.setText("Loaded: " + f.getName());
							return;
						}
					}
					result.setText("Could not read dimensions");
				} catch (Exception ex) {
					result.setText("Error: " + ex.getMessage());
				}
			}
		});

		compute.addActionListener(e -> {
			String ws = widthField.getText().trim();
			String hs = heightField.getText().trim();
			if (ws.isEmpty() || hs.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Enter width and height or open a file.");
				return;
			}
			try {
				int w = Integer.parseInt(ws);
				int h = Integer.parseInt(hs);
				if (w <= 0 || h <= 0) {
					JOptionPane.showMessageDialog(frame, "Values must be positive.");
					return;
				}
				int g = gcd(w, h);
				result.setText(String.format("%d:%d  (reduced %d:%d)", w, h, w / g, h / g));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(frame, "Width and height must be integers.");
			}
		});

		frame.setVisible(true);
	}

	private static int gcd(int a, int b) {
		a = Math.abs(a); b = Math.abs(b);
		while (b != 0) {
			int t = a % b;
			a = b; b = t;
		}
		return a == 0 ? 1 : a;
	}

}
