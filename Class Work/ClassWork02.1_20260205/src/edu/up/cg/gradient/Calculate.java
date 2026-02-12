package edu.up.cg.gradient;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Calculate {

    public static void main(String[] args) throws java.io.IOException {
        
        // Set image dimensions
        int width = 1000;
        int height = 1000;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Create points for triangle vertices
        Points pA = new Points(100.0, 900.0);
        Points pB = new Points(900.0, 900.0);
        Points pC = new Points(500.0, 100.0);

        // Create triangle object
        Triangle triangle = new Triangle(pA, pB, pC);

        System.out.println("Generating image");

        // Loop through all pixels
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                // Get barycentric coordinates
                double[] lambdas = triangle.getBarycentricCoordinates(x, y);
                double l1 = lambdas[0];
                double l2 = lambdas[1];
                double l3 = lambdas[2];

                // Check if pixel is inside triangle
                if (l1 >= 0 && l2 >= 0 && l3 >= 0) {
                    
                    // Mix colors based on coordinates
                    int red = (int) (l1 * 255 + l2 * 0 + l3 * 0);
                    int green = (int) (l1 * 0 + l2 * 255 + l3 * 0);
                    int blue = (int) (l1 * 0 + l2 * 0 + l3 * 255);

                    // Ensure values are within range
                    red = Math.min(255, Math.max(0, red));
                    green = Math.min(255, Math.max(0, green));
                    blue = Math.min(255, Math.max(0, blue));

                    // Set pixel color
                    Color color = new Color(red, green, blue);
                    image.setRGB(x, y, color.getRGB());
                } else {
                    // Set background color to black
                    image.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }

        // Save image to file
        File outputFile = new File("image.png");
        ImageIO.write(image, "png", outputFile);
    }
}
