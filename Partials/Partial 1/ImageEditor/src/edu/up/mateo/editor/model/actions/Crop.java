package edu.up.mateo.editor.model.actions;

import edu.up.mateo.editor.model.Coords;
import java.awt.image.BufferedImage;

public class Crop implements ActionEditor{
    @Override
    public BufferedImage execute(BufferedImage currentImage, Coords selection) {

        // Keep original image if selection is invalid
        if (selection.getWidth() <= 0 || selection.getHeight() <= 0) {
            return currentImage;
        }

        int startX = selection.getX();
        int startY = selection.getY();
        int width = selection.getWidth();
        int height = selection.getHeight();

        // Create output image with selection size
        BufferedImage newImage = new BufferedImage(
                width,
                height,
                currentImage.getType()
        );

        // Copy selected pixels into the new image
        for (int x = startX; x < startX + width; x++) {
            for (int y = startY; y < startY + height; y++) {
                int pixelColor = currentImage.getRGB(x, y);

            // Map original coords to cropped coords
                int newX = x - startX;
                int newY = y - startY;

            // Write pixel to output image
                newImage.setRGB(newX, newY, pixelColor);
            }
        }

        return newImage;
    }
}