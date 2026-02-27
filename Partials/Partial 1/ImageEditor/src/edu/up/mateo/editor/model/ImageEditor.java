package edu.up.mateo.editor.model;

import java.awt.image.BufferedImage;

public class ImageEditor {

    // Current image in memory
    private BufferedImage currentImage;

    // Start with no image loaded
    public ImageEditor() {
        this.currentImage = null;
    }

    // Save a new image in the model
    public void setImage(BufferedImage img) {
        this.currentImage = img;
    }

    // Get the current image
    public BufferedImage getImage() {
        return this.currentImage;
    }
    
    // Check if an image is loaded
    public boolean hasImage() {
        if (this.currentImage != null) {
            return true;
        } else {
            return false;
        }
    }
}