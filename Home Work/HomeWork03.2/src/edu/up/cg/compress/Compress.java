package edu.up.cg.compress;

public class Compress {
    // the standard JPEG table for brightness.
    private static final int[][] RULE_TABLE = {
        {16, 11, 10, 16, 24, 40, 51, 61},
        {12, 12, 14, 19, 26, 58, 60, 55},
        {14, 13, 16, 24, 40, 57, 69, 56},
        {14, 17, 22, 29, 51, 87, 80, 62},
        {18, 22, 37, 56, 68, 109, 103, 77},
        {24, 35, 55, 64, 81, 104, 113, 92},
        {49, 64, 78, 87, 103, 121, 120, 101},
        {72, 92, 95, 98, 112, 100, 103, 99}
    };

    private double compressionPower;

    public Compress(double power) {
        // power = 1.0 is normal. 
        // power = 5.0 is strong compression (low quality).
        this.compressionPower = power; 
    }

    // lose quality to save space.
    public int[][] compressBlock(double[][] inputMatrix) {
        int[][] result = new int[8][8];
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // Calculate the divider based on the table and our power setting
                double divider = RULE_TABLE[i][j] * compressionPower;
                
                // Divide and remove decimals
                result[i][j] = (int) Math.round(inputMatrix[i][j] / divider);
            }
        }
        return result;
    }

    // try to get the numbers back
    public double[][] expandBlock(int[][] compressedMatrix) {
        double[][] result = new double[8][8];
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                double multiplier = RULE_TABLE[i][j] * compressionPower;
                
                // Multiply to get the value back
                result[i][j] = compressedMatrix[i][j] * multiplier;
            }
        }
        return result;
    }
}
