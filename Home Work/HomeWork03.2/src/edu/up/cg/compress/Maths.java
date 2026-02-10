package edu.up.cg.compress;

import java.lang.Math;

public class Maths {
    // We work with squares of 8x8 pixels
    private static final int SIZE = 8; 

    // A helper formula for the math coefficient
    private static double getCoefficient(int k) {
        if (k == 0) {
            return 1.0 / Math.sqrt(2);
        } else {
            return 1.0;
        }
    }

    // Turn Pixels into Waves frequency
    // uses the Cosine Transform formula
    public static double[][] applyDCT(double[][] pixelMatrix) {
        double[][] waveMatrix = new double[SIZE][SIZE];

        for (int u = 0; u < SIZE; u++) {
            for (int v = 0; v < SIZE; v++) {
                double sum = 0.0;
                
                // Summing up all pixels in the block
                for (int x = 0; x < SIZE; x++) {
                    for (int y = 0; y < SIZE; y++) {
                        double pixelValue = pixelMatrix[x][y];
                        
                        // The complex maths
                        double cosX = Math.cos(((2 * x + 1) * u * Math.PI) / 16.0);
                        double cosY = Math.cos(((2 * y + 1) * v * Math.PI) / 16.0);
                        
                        sum += pixelValue * cosX * cosY;
                    }
                }
                waveMatrix[u][v] = 0.25 * getCoefficient(u) * getCoefficient(v) * sum;
            }
        }
        return waveMatrix;
    }

    // Turn Waves back into Pixels
    // This reverses the math above
    public static double[][] reverseDCT(double[][] waveMatrix) {
        double[][] pixelMatrix = new double[SIZE][SIZE];

        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                double sum = 0.0;
                
                for (int u = 0; u < SIZE; u++) {
                    for (int v = 0; v < SIZE; v++) {
                        double waveValue = waveMatrix[u][v];
                        
                        double cosX = Math.cos(((2 * x + 1) * u * Math.PI) / 16.0);
                        double cosY = Math.cos(((2 * y + 1) * v * Math.PI) / 16.0);
                        
                        sum += getCoefficient(u) * getCoefficient(v) * waveValue * cosX * cosY;
                    }
                }
                pixelMatrix[x][y] = 0.25 * sum;
            }
        }
        return pixelMatrix;
    }
}