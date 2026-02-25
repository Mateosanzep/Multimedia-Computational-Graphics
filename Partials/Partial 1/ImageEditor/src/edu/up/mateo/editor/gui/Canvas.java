package edu.up.mateo.editor.gui;

import edu.up.mateo.editor.model.Coords;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Canvas extends JPanel {
    private BufferedImage currentImage;
    
    // save the coords of the selection
    private int startX, startY, endX, endY;

    public Canvas() {
        // The listener for the mouse
        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // save the first coords on the click
                startX = e.getX();
                startY = e.getY();
                endX = startX;
                endY = startY;
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // the drag of the mouse
                endX = e.getX();
                endY = e.getY();
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // The mouse realesed
                endX = e.getX();
                endY = e.getY();
                repaint();
            }
        };

        // Add the listener of the mouse to the panel
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    public void setImage(BufferedImage img) {
        this.currentImage = img;
        repaint();
    }

    // package the coords of the selection in a Coords object
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
        
        // Paint the image
        if (currentImage != null) {
            g.drawImage(currentImage, 0, 0, this);
        }

        // Paint the selection rectangle if there is one
        if (startX != endX || startY != endY) {
            Graphics2D g2d = (Graphics2D) g;
            
            // Set the color and transparency for the selection rectangle
            g2d.setColor(new Color(255, 0, 0, 150)); 
            
            // dasshed stroke for the selection rectangle
            float[] dash = { 5f, 5f };
            g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, dash, 0f));
            
            int x = Math.min(startX, endX);
            int y = Math.min(startY, endY);
            int width = Math.abs(startX - endX);
            int height = Math.abs(startY - endY);
            
            g2d.drawRect(x, y, width, height);
        }
    }
}
