package edu.up.mateo.editor.gui;

import edu.up.mateo.editor.model.Coords;
import java.awt.image.BufferedImage;

public interface GuiEditor {
    // Update the image that is shown
    void updateImage(BufferedImage img);

    // Get the coords of the rectangle to edit
    Coords getSelection();

    // Clear the rectangle selected
    void clearSelection();

    // Error message
    void showError(String message);

    // Update the image dimensions on the status bar
    void updateImageInfo(int width, int height);
}