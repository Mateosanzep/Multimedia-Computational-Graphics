package edu.up.cg.images;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Main {
    public static void main(String[] args) {
        int X = 800;
        int Y = 600;
        BufferedImage image = new BufferedImage(X, Y, BufferedImage.TYPE_INT_RGB);

        double slope = (double) Y / (double) X;
        for (int x = 0; x < X; x++) {
            for (int y = 0; y < Y; y++) {
                double limit = slope * x;
                if (y > limit) {
                    image.setRGB(x, y, Color.BLUE.getRGB());
                } else {
                    image.setRGB(x, y, Color.RED.getRGB());
                }
            }
        }

        File outputImage = new File("Image.jpg");
        try {
            ImageIO.write(image, "jpg", outputImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}