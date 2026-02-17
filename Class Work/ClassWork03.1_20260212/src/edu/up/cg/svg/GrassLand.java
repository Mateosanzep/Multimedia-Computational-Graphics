package edu.up.cg.svg;

import java.io.FileWriter;
import java.io.IOException;

public class GrassLand {
    public static void main(String[] args) {
        // Define the dimensions of the SVG image
        int imgWidth = 800;
        int imgHeight = 600;

        // Sun Properties
        int sunX = 150;
        int sunY = 150;
        int sunRadius = 75;
        String sunColor = "#FFFF4D";
        String rayColor = "#A54D4D";

        // sun rays
        String rays = String.format(
            "  <line x1=\"20\" y1=\"150\" x2=\"280\" y2=\"150\" stroke=\"%s\" stroke-width=\"2\" />\n" +
            "  <line x1=\"150\" y1=\"20\" x2=\"150\" y2=\"280\" stroke=\"%s\" stroke-width=\"2\" />\n" +
            "  <line x1=\"57\" y1=\"57\" x2=\"242\" y2=\"242\" stroke=\"%s\" stroke-width=\"2\" />\n" +
            "  <line x1=\"57\" y1=\"242\" x2=\"242\" y2=\"57\" stroke=\"%s\" stroke-width=\"2\" />\n",
            rayColor, rayColor, rayColor, rayColor
        );

        // Grass Properties
        String grassColor = "#66FF4D";

        // Grass path
        String grassPath = "M 0 600 L 0 460 " +
            "Q 40 380, 80 460 " +
            "T 160 460 T 240 460 T 320 460 T 400 460 T 480 460 T 560 460 T 640 460 T 720 460 T 800 460 " +
            "L 800 600 Z";

        // Create the SVG content
        String svgContent = String.format(
            "<svg width=\"%d\" height=\"%d\" xmlns=\"http://www.w3.org/2000/svg\" style=\"background:white\">\n" +
            "  \n" +
            "%s" +
            "  \n" +
            "  <circle cx=\"%d\" cy=\"%d\" r=\"%d\" fill=\"%s\" />\n" +
            "  \n" +
            "  <path d=\"%s\" fill=\"%s\" />\n" +
            "</svg>",
            imgWidth, imgHeight, rays, sunX, sunY, sunRadius, sunColor, grassPath, grassColor
        );

        // Write the SVG content to a file
        try (FileWriter file = new FileWriter("grassland.svg")) {
            file.write(svgContent);
            System.out.println("SVG file grassland.svg created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}