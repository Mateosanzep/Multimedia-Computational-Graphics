package edu.up.cg.compress;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) {
        try {
            // Look for the file
            File myFile = new File("input.jpg"); 
            
            if (!myFile.exists()) {
                System.out.println("Error: cannot find 'input.jpg'.");
                return;
            }

            // Read the image
            BufferedImage originalImage = ImageIO.read(myFile);

            // Set compression level
            // 1.0 = Normal Quality
            // 5.0 = Low Quality
            double compressionLevel = 5.0; 

            // Run the processor
            ImageProcessor processor = new ImageProcessor(compressionLevel);
            
            BufferedImage resultImage = processor.processImage(originalImage);

            // Save the new image
            File savedFile = new File("output.jpg");
            ImageIO.write(resultImage, "jpg", savedFile);
            

        } catch (IOException e) {
            System.out.println("Something went wrong reading the file.");
            e.printStackTrace();
        }
    }
}
