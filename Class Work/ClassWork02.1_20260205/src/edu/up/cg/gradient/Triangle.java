package edu.up.cg.gradient;

public class Triangle {

    // Vertices of the triangle
    private Points a;
    private Points b;
    private Points c;

    // Constructor to set vertices
    public Triangle(Points a, Points b, Points c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    // Calculate barycentric coordinates for a point
    public double[] getBarycentricCoordinates(double px, double py) {
        
        // Get coordinates from points
        double xA = a.getX(); double yA = a.getY();
        double xB = b.getX(); double yB = b.getY();
        double xC = c.getX(); double yC = c.getY();

        // Calculate common denominator
        double denominator = ((yB - yC) * (xA - xC) + (xC - xB) * (yA - yC));

        // Calculate first coordinate
        double lambda1 = ((yB - yC) * (px - xC) + (xC - xB) * (py - yC)) / denominator;

        // Calculate second coordinate
        double lambda2 = ((yC - yA) * (px - xC) + (xA - xC) * (py - yC)) / denominator;

        // Calculate third coordinate
        double lambda3 = 1.0 - lambda1 - lambda2;

        // Return all three values
        return new double[]{lambda1, lambda2, lambda3};
    }
}