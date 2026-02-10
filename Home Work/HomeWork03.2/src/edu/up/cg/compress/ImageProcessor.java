package edu.up.cg.compress;

import java.awt.image.BufferedImage;

public class ImageProcessor {
    private Compress TheCompressor;

    public ImageProcessor(double quality) {
        this.TheCompressor = new Compress(quality);
    }

    // Main function to run the whole process
    public BufferedImage processImage(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        
        // Adjust size to be a multiple of 8
        int newWidth = (width / 8) * 8;
        int newHeight = (height / 8) * 8;
        
        // Create a blank image for the result
        BufferedImage finalImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

        // Loop through the image in steps of 8 pixels
        for (int y = 0; y < newHeight; y += 8) {
            for (int x = 0; x < newWidth; x += 8) {
                processOneSquare(originalImage, finalImage, x, y);
            }
        }
        return finalImage;
    }

    private void processOneSquare(BufferedImage input, BufferedImage output, int startX, int startY) {
        // We need 3 separate blocks for every color
        double[][] redBlock = new double[8][8];
        double[][] greenBlock = new double[8][8];
        double[][] blueBlock = new double[8][8];

        // READ THE PIXELS
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int color = input.getRGB(startX + j, startY + i);
                
                // Extract R, G, B using
                int r = (color >> 16) & 0xFF;
                int g = (color >> 8) & 0xFF;
                int b = color & 0xFF;

                // Subtract 128 to center around zero
                redBlock[i][j]   = r - 128.0;
                greenBlock[i][j] = g - 128.0;
                blueBlock[i][j]  = b - 128.0;
            }
        }

        // PROCESS EACH COLOR
        double[][] finalRed   = processChannel(redBlock);
        double[][] finalGreen = processChannel(greenBlock);
        double[][] finalBlue  = processChannel(blueBlock);

        // COMBINE AND DRAW
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // Add 128 back and make sure it stays between 0-255
                int r = clamp(finalRed[i][j] + 128.0);
                int g = clamp(finalGreen[i][j] + 128.0);
                int b = clamp(finalBlue[i][j] + 128.0);

                // Combine the 3 numbers back into one Java Color
                int finalColor = (r << 16) | (g << 8) | b;
                
                output.setRGB(startX + j, startY + i, finalColor);
            }
        }
    }

    // This runs the maths
    private double[][] processChannel(double[][] inputBlock) {
        // Turn to waves
        double[][] waves = Maths.applyDCT(inputBlock);
        
        // Compress
        int[][] compressed = TheCompressor.compressBlock(waves);
        
        // Decompress
        double[][] expanded = TheCompressor.expandBlock(compressed);
        
        // Turn back to pixels
        return Maths.reverseDCT(expanded);
    }

    // This makes sure a number is never less than 0 or more than 255
    private int clamp(double value) {
        return (int) Math.max(0, Math.min(255, value));
    }
}