package edu.up.mateo.editor.gui;

import edu.up.mateo.editor.model.Coords;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Canvas extends JPanel {
    private BufferedImage currentImage;

    // Selection color palette
    private static final Color SELECT_STROKE = new Color(50, 120, 220, 200);
    private static final Color SELECT_FILL = new Color(50, 120, 220, 30);
    private static final Color CHECKER_LIGHT = new Color(204, 204, 204);
    private static final Color CHECKER_DARK = new Color(255, 255, 255);
    private static final int CHECKER_SIZE = 10;

    // Coords of the selection
    private int startX, startY, endX, endY;

    public Canvas() {
        setBackground(new Color(45, 45, 48));

        // Mouse listener for selection
        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
                endX = startX;
                endY = startY;
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                endX = e.getX();
                endY = e.getY();
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                endX = e.getX();
                endY = e.getY();
                repaint();
            }
        };

        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    public void setImage(BufferedImage img) {
        this.currentImage = img;
        repaint();
    }

    // Set selection manually from the Controller
    public void setManualSelection(int x, int y, int width, int height) {
        this.startX = x;
        this.startY = y;
        this.endX = x + width;
        this.endY = y + height;
        repaint();
    }

    // Package the coords of the selection in a Coords object
    public Coords getSelectionCoords() {
        int x = Math.min(startX, endX);
        int y = Math.min(startY, endY);
        int width = Math.abs(startX - endX);
        int height = Math.abs(startY - endY);
        return new Coords(x, y, width, height);
    }

    public void clearSelection() {
        startX = startY = endX = endY = 0;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (currentImage != null) {
            // Draw checkerboard behind image for transparency
            drawCheckerboard(g2d, currentImage.getWidth(), currentImage.getHeight());

            // Draw the image
            g2d.drawImage(currentImage, 0, 0, this);

            // Draw pixel rulers
            drawRulers(g2d);
        }

        // Draw selected area rectangle
        if (startX != endX || startY != endY) {
            int x = Math.min(startX, endX);
            int y = Math.min(startY, endY);
            int w = Math.abs(startX - endX);
            int h = Math.abs(startY - endY);

            // Selection fill
            g2d.setColor(SELECT_FILL);
            g2d.fillRect(x, y, w, h);

            // Selection border
            g2d.setColor(SELECT_STROKE);
            float[] dash = { 6f, 4f };
            g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10f, dash, 0f));
            g2d.drawRect(x, y, w, h);

            // Size label
            String sizeText = w + " x " + h;
            g2d.setFont(new Font("SansSerif", Font.BOLD, 11));
            FontMetrics fm = g2d.getFontMetrics();
            int labelX = x + w / 2 - fm.stringWidth(sizeText) / 2;
            int labelY = y > 18 ? y - 6 : y + h + 14;

            g2d.setColor(new Color(0, 0, 0, 160));
            g2d.fillRoundRect(labelX - 4, labelY - fm.getAscent(), fm.stringWidth(sizeText) + 8, fm.getHeight() + 2, 6, 6);
            g2d.setColor(Color.WHITE);
            g2d.drawString(sizeText, labelX, labelY);
        }
    }

    // Draw checkerboard pattern behind image
    private void drawCheckerboard(Graphics2D g2d, int imgWidth, int imgHeight) {
        for (int y = 0; y < imgHeight; y += CHECKER_SIZE) {
            for (int x = 0; x < imgWidth; x += CHECKER_SIZE) {
                g2d.setColor(((x / CHECKER_SIZE + y / CHECKER_SIZE) % 2 == 0) ? CHECKER_LIGHT : CHECKER_DARK);
                g2d.fillRect(x, y, CHECKER_SIZE, CHECKER_SIZE);
            }
        }
    }

    // Draw measurement rulers on edges
    private void drawRulers(Graphics2D g2d) {
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
        Color rulerBg = new Color(0, 0, 0, 100);
        Color rulerFg = new Color(220, 220, 220);

        // Top ruler
        for (int x = 0; x < currentImage.getWidth(); x += 100) {
            g2d.setColor(rulerBg);
            g2d.fillRect(x, 0, 1, 8);
            g2d.setColor(rulerFg);
            g2d.drawString(String.valueOf(x), x + 3, 16);
        }

        // Left ruler
        for (int y = 0; y < currentImage.getHeight(); y += 100) {
            g2d.setColor(rulerBg);
            g2d.fillRect(0, y, 8, 1);
            if (y > 0) {
                g2d.setColor(rulerFg);
                g2d.drawString(String.valueOf(y), 10, y + 4);
            }
        }
    }
}