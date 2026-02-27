package edu.up.mateo.editor.model.actions;

import edu.up.mateo.editor.model.Coords;
import java.awt.image.BufferedImage;

public class Rotate implements ActionEditor {
    // Rotation angle in degrees
    private double degrees;

    // Constructor to set rotation angle
    public Rotate(double degrees) {
        this.degrees = degrees;
    }

    @Override
    public BufferedImage execute(BufferedImage currentImage, Coords selection) {
        // Keep original image if selection is invalid
        if (selection.getWidth() <= 0 || selection.getHeight() <= 0) {
            return currentImage;
        }

        // Selection bounds
        int startX = selection.getX();
        int startY = selection.getY();
        int width = selection.getWidth();
        int height = selection.getHeight();

        // Create output image with transparency support
        BufferedImage newImage = new BufferedImage(
            currentImage.getWidth(),
            currentImage.getHeight(),
            BufferedImage.TYPE_INT_ARGB
        );
        
        // Copy original pixels to output image
        for (int x = 0; x < currentImage.getWidth(); x++) {
            for (int y = 0; y < currentImage.getHeight(); y++) {
                int pixelColor = currentImage.getRGB(x, y);
                newImage.setRGB(x, y, pixelColor);
            }
        }
        
        // Compute selection center
        int cx = startX + (width / 2);
        int cy = startY + (height / 2);
        
        // Use diagonal as safe affected area for rotation
        int diagonal = (int) Math.sqrt((width * width) + (height * height));

        // Affected area limits
        int startAffectedX = cx - (diagonal / 2);
        int startAffectedY = cy - (diagonal / 2);
        int endAffectedX = cx + (diagonal / 2);
        int endAffectedY = cy + (diagonal / 2);

        // Clear the original selected area
        for (int x = startX; x < startX + width; x++) {
            for (int y = startY; y < startY + height; y++) {
                newImage.setRGB(x, y, 0x00FFFFFF);
            }
        }

        // Rotation values for 90 degrees
        double angle = Math.toRadians(this.degrees % 360); 
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        // Traverse affected area and map pixels back to original selection
        for (int x = startAffectedX; x < endAffectedX; x++) {
            for (int y = startAffectedY; y < endAffectedY; y++) {

                // Keep writes inside image bounds
                if (x >= 0 && x < currentImage.getWidth() && y >= 0 && y < currentImage.getHeight()) {

                    // Distance from center
                    int dx = x - cx;
                    int dy = y - cy;

                    // Inverse mapping to find source pixel
                    int oldX = (int) Math.round(dx * cos + dy * sin) + cx;
                    int oldY = (int) Math.round(-dx * sin + dy * cos) + cy;

                    // Paint only pixels from original selection
                    if (oldX >= startX && oldX < startX + width && oldY >= startY && oldY < startY + height) {
                        int pixelColor = currentImage.getRGB(oldX, oldY);
                        newImage.setRGB(x, y, pixelColor);
                    }
                }
            }
        }

        // Return rotated result
        return newImage;
    }
}