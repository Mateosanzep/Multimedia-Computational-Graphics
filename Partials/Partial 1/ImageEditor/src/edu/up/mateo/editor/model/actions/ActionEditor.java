package edu.up.mateo.editor.model.actions;

import edu.up.mateo.editor.model.Coords;
import java.awt.image.BufferedImage;

public interface ActionEditor {

    // Apply an action to the current image and selected area
    BufferedImage execute(BufferedImage currentImage, Coords selection);

}