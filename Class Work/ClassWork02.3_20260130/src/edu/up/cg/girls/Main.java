package edu.up.cg.girls;
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
        int r = 255;
        int g;
        int b = 0;

        for (double x = 0; x < X; x++) {
            for (double y = 0; y < Y; y++) {
                g = (int) Math.round(255.0 - (y * 0.425));
                Color deg = new Color(r, g, b);
                image.setRGB((int)x, (int)y, deg.getRGB());
            }
        }
        for (int i = 0; i < 3; i++){
            Circle(image, 200.0*(i+1), 150.0, 40, 200*i, i+1);
            Circle(image, 200.0*(i+1)+40, 120.0, 20, 200*i, i+1);
            Rectangle(image, 100, 200*(i+1), 40, 16, i+1);
            Triangle(image, 300.0, 180.0, 120.0, i+1,true);
            Triangle(image, 370.0, 140.0, 100.0, i+1,false);
            for (int j = 0; j < 2; j++){
                int o[] = {-1,1};
                Rectangle(image, 400, (200*(i+1))+(20*o[j]),120,18,i+1);
            }
            
        }

        File outputImage = new File("Image.png");
        try {
            ImageIO.write(image, "png", outputImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void Circle(BufferedImage image, double xCenter, double yCenter, double rad, int xRange, int iter){
        for (int i = 100+xRange; i < 100+(200*iter); i++){
            for (int j = 0; j < 500; j++){
                if (Math.pow((i-(xCenter)),2.0)+Math.pow((j-(yCenter)),2.0) < Math.pow(rad,2.0)){
                    image.setRGB(i, j, Color.BLACK.getRGB());
                } 
            }
        }
    }

    public static void Rectangle(BufferedImage image, int yCenter, int xCenter, int height, int width, int iter){
        for (int i = (xCenter)-(width/2); i < (xCenter)+(width/2); i++){
            for (int j = 100+yCenter-(height/2); j < 100+yCenter+(height/2); j++){
                image.setRGB(i, j, Color.BLACK.getRGB());
            }
        }
    }

    public static void Triangle(BufferedImage image, double yCenter, double height, double width, int iter, boolean reflect){
        if (!reflect){
            double[] A = { (200.0 * iter) - (width / 2.0), yCenter + (height / 2.0) };
            double[] B = { (200.0 * iter) + (width / 2.0), yCenter + (height / 2.0) };
            double[] C = { (200.0 * iter), yCenter - (height / 2.0) };
            double totArea = Area(A, B, C);
            for (int i = (int)(A[0]); i <= (int)(B[0]); i++){
                for (int j = (int)(C[1]); j <= (int)(A[1]); j++){
                    double[] P = { (double)i, (double)j };
                    
                    double area1 = Area(P, B, C);
                    double area2 = Area(A, P, C);
                    double area3 = Area(A, B, P);

                    if (Math.abs(totArea - (area1 + area2 + area3)) < 0.1) {
                        if (i >= 0 && i < image.getWidth() && j >= 0 && j < image.getHeight()) {
                            image.setRGB(i, j, Color.BLACK.getRGB());
                        }
                    }
                }
            }
        } else {
            double[] A = { (200.0 * iter) - (width / 2.0), yCenter - (height / 2.0) };
            double[] B = { (200.0 * iter) + (width / 2.0), yCenter - (height / 2.0) };
            double[] C = { (200.0 * iter), yCenter + (height / 2.0) };
            double totArea = Area(A, B, C);
            for (int i = (int) (A[0]); i <= (int) (B[0]); i++) {
                for (int j = (int) (A[1]); j <= (int) (C[1]); j++) {
                    double[] P = { (double) i, (double) j };
                    double area1 = Area(P, B, C);
                    double area2 = Area(A, P, C);
                    double area3 = Area(A, B, P);
                    if (Math.abs(totArea - (area1 + area2 + area3)) < 0.1) {
                        if (i >= 0 && i < image.getWidth() && j >= 0 && j < image.getHeight()) {
                            image.setRGB(i, j, Color.BLACK.getRGB());
                        }
                    }
                }
            }
        }
    }

    public static double Area(double[] a, double[] b, double[] c){
        return Math.abs(0.5 * (a[0]*(b[1] - c[1]) + b[0]*(c[1] - a[1]) + c[0]*(a[1] - b[1])));
    }
}