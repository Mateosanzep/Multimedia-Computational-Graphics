package edu.up.cg.clock;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Main {
    public static void main(String[] args) {
        Double X = 800.0;
        Double Y = 600.0;
        Double radius = 200.0;
        Double tolerance = 750.0;
        Double rLinesHours = 170.0;
        double centerY = Y/2;
        double centerX = X/2;
        double rLineMin = 150.0;
        double rLineHour = 120.0;

        double minute = 15.0;
        double hour = 8.0 + (minute / 60.0);

        BufferedImage image = new BufferedImage(X.intValue(), Y.intValue(), BufferedImage.TYPE_INT_RGB);

        for (double x = 0; x < X; x++) {
            for (double y = 0; y < Y; y++) {
                if (Math.pow((x-(X/2)),2.0)+Math.pow((y-(Y/2)),2.0) <= Math.pow(radius,2.0)+tolerance &&
                    Math.pow((x-(X/2)),2.0)+Math.pow((y-(Y/2)),2.0) >= Math.pow(radius,2.0)-tolerance){
                    image.setRGB((int)x, (int)y, Color.WHITE.getRGB());
                } else {
                    image.setRGB((int)x, (int)y, Color.BLACK.getRGB());
                }
            }
        }

        for (int i = 0; i < 12; i++){
            for (double r = 0.0; r <= 20.0; r++) {
                image.setRGB((int)DistanceX(centerX, rLinesHours+r, 30.0*i), (int)DistanceY(centerY, rLinesHours+r, 30.0*i), Color.WHITE.getRGB());
            }
        }
        for (double i = 0.0; i < rLineMin; i++){
            for (double j = 0.0; j < 2; j++){
                image.setRGB((int)DistanceX(centerX, i+j, (minute*6-90.0)), (int)DistanceY(centerY, i+j, (minute*6-90.0)), Color.BLUE.getRGB());
            }
        }

        for (double i = 0.0; i < rLineHour; i++){
            for (double j = 0.0; j < 4; j++){
                image.setRGB((int)DistanceX(centerX, i+j, (hour*30-90.0)), (int)DistanceY(centerY, i, (hour*30-90.0)), Color.RED.getRGB());
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