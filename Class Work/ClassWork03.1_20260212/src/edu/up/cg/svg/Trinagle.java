package edu.up.cg.svg;
import java.io.FileWriter;
import java.io.IOException;

public class Trinagle {
    public static void main(String[] args) {
        // Define the dimensions of the SVG image
        int imgWidth = 400;
        int imgHeight = 300;

        // Coords for the upper triangle
            int x1 = 0;
            int y1 = 0;
            int x2 = 400;
            int y2 = 0;
            int x3 = 400;
            int y3 = 300;

        // Coords for the lower triangle
            int z1 = 0;
            int w1 = 0;
            int z2 = 0;
            int w2 = 300;
            int z3 = 400;
            int w3 = 300;

        // Define colors for the triangles
            String upColor = "red";
            String downColor = "blue";

        // Create the SVG content
        String contenidoSvg = String.format(
            "<svg width=\"%d\" height=\"%d\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "  <polygon points=\"%d,%d %d,%d %d,%d\" fill=\"%s\" />\n" +
            "  <polygon points=\"%d,%d %d,%d %d,%d\" fill=\"%s\" />\n" +
            "</svg>",

            // Parameters for the SVG
            imgWidth, imgHeight, x1, y1, x2, y2, x3, y3, upColor, z1, w1, z2, w2, z3, w3, downColor
    );

        // Write the SVG content to a file
        try (FileWriter file = new FileWriter("triangle.svg")) {
            file.write(contenidoSvg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
