package edu.up.mateo.editor.model.actions;

import edu.up.mateo.editor.model.Coords;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Invert implements ActionEditor {

    @Override
    public BufferedImage execute(BufferedImage currentImage, Coords selection) {
        // Create a copy to avoid editing the original image
        BufferedImage newImage = new BufferedImage(
                currentImage.getWidth(), 
                currentImage.getHeight(), 
                currentImage.getType()
        );
        
        // Copy original pixels into the new image
        Graphics gra = newImage.getGraphics();
        gra.drawImage(currentImage, 0, 0, null);
        gra.dispose();

        // Selected area limits
        int startX = selection.getX();
        int startY = selection.getY();
        int endX = startX + selection.getWidth();
        int endY = startY + selection.getHeight();
        
        // Invert colors only inside selected area
        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                // Read pixel channels
                int pixel = newImage.getRGB(x, y);
                int a = (pixel >> 24) & 0xff;
                int r = (pixel >> 16) & 0xff;
                int g = (pixel >> 8) & 0xff;
                int b = pixel & 0xff;

                // Invert RGB values
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;

                // Build and save new pixel
                int newPixel = (a << 24) | (r << 16) | (g << 8) | b;
                newImage.setRGB(x, y, newPixel);
            }
        }
        return newImage;
    }
}