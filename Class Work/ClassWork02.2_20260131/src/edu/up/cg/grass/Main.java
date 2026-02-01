package edu.up.cg.grass;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Main {
    public static void main(String[] args) {
        Double X = 800.0;
        Double Y = 600.0;

        BufferedImage image = new BufferedImage(X.intValue(), Y.intValue(), BufferedImage.TYPE_INT_RGB);

        for (double x = 0; x < X; x++) {
            for (double y = 0; y < Y; y++) {
                if (500 + (15*Math.cos(x*0.15)) < y){
                    image.setRGB((int)x, (int)y, Color.GREEN.getRGB());
                } else {
                    image.setRGB((int)x, (int)y, Color.WHITE.getRGB());
                }
            }
        }

        for (int i = 0; i < 85; i++){
            for (int j = 0; j < 8; j++){
                for (int k = 0; k < 4; k++){
                    image.setRGB((int)DistanceX(150.0, i+j, k*90), (int)DistanceY(150.0, i+j, k*90), Color.ORANGE.getRGB());
                }
            }
        }

        for (int i = 0; i < 75; i++){
            for (int j = 0; j < 8; j++){
                for (int k = 0; k < 4; k++){
                    image.setRGB((int)DistanceX(150.0, i+j, 45+k*90), (int)DistanceY(150.0, i+j, 45+k*90), Color.ORANGE.getRGB());
                }
            }
        }
        for (int i = 0; i < 250; i++){
            for (int j = 0; j < 250; j++){
                if (Math.pow((i-(150)),2.0)+Math.pow((j-(150)),2.0) < Math.pow(60,2.0)){
                    image.setRGB((int)i, (int)j, Color.YELLOW.getRGB());
                } 
            }
        }

        File outputImage = new File("Image.png");
        try {
            ImageIO.write(image, "png", outputImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static double DistanceY(double centerY, double rLine, double degree){
        double Ydistance = centerY + Math.round(rLine * Math.sin(Math.toRadians(degree)));
            return Ydistance;
        }
    public static double DistanceX(double centerX, double rLine, double degree){
        double Xdistance = centerX + Math.round(rLine * Math.cos(Math.toRadians(degree)));
            return Xdistance;
        }
}
