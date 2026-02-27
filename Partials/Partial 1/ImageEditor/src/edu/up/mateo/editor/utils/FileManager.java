package edu.up.mateo.editor.utils;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileManager {
    // Open a window to choose an image file and load it
    public BufferedImage loadImage() {
        // Create the chooser and allow image formats only
        JFileChooser imageFile = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Only images", "jpg", "jpeg", "png");
        imageFile.setFileFilter(filter);

        // Show the open dialog
        int openResponse = imageFile.showOpenDialog(null);
        
        if (openResponse == JFileChooser.APPROVE_OPTION) {
            File selectedFile = imageFile.getSelectedFile();
            try {
                return ImageIO.read(selectedFile);
            } catch (IOException e) {
                // Return null if there is an error while reading
                return null;
            }
        } 
        // Return null when the user cancels
        return null;
    }

    // Open a window to choose where to save the edited image
    public boolean saveImage(BufferedImage img) {
        // Check that there is an image to save
        if (img == null) {
            return false;
        }

        // Create the chooser for PNG files
        JFileChooser imageFile = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG image", "png");
        imageFile.setFileFilter(filter);

        // Show the save dialog
        int saveResponse = imageFile.showSaveDialog(null);

        if (saveResponse == JFileChooser.APPROVE_OPTION) {
            File selectedFile = imageFile.getSelectedFile();

            // Add .png extension if missing
            if (!selectedFile.getName().toLowerCase().endsWith(".png")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".png");
            }

            try {
                // Returns true when the file is written correctly
                return ImageIO.write(img, "png", selectedFile);
            } catch (IOException e) {
                // Return false when writing fails
                return false; 
            }
        }
        
        // Return false when the user cancels
        return false;
    }
}