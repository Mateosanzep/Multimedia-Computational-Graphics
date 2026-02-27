package edu.up.mateo.editor.controller;

import edu.up.mateo.editor.gui.MainWindow;
import edu.up.mateo.editor.model.Coords;
import edu.up.mateo.editor.model.ImageEditor;
import edu.up.mateo.editor.model.actions.*;
import edu.up.mateo.editor.utils.FileManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

public class ControllerEditor {

    // Controller connections
    private MainWindow view;
    private ImageEditor model;
    private FileManager fileManager;

    // Inject dependencies
    public ControllerEditor(MainWindow view, ImageEditor model, FileManager fileManager) {
        this.view = view;
        this.model = model; 
        this.fileManager = fileManager;

        // Connect button listeners
        initListeners();
    }

    // Connect view actions with editor logic
    private void initListeners() {

        // Sync canvas selection to text fields
        view.getCanvas().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                updateCoordsTextFields();
            }
        });

        view.getCanvas().addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                updateCoordsTextFields();
            }
        });

        // Apply typed coordinates to canvas selection
        view.getBtnApplyCoords().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int x = Integer.parseInt(view.getTxtX().getText());
                    int y = Integer.parseInt(view.getTxtY().getText());
                    int w = Integer.parseInt(view.getTxtWidth().getText());
                    int h = Integer.parseInt(view.getTxtHeight().getText());
                    
                    view.getCanvas().setManualSelection(x, y, w, h);
                } catch (NumberFormatException ex) {
                    view.showError("Please enter valid numbers in the Coordinates fields.");
                }
            }
        });

        // Select whole image area
        view.getBtnSelectAll().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage actualImage = model.getImage();
                if (actualImage != null) {
                    view.getCanvas().setManualSelection(0, 0, actualImage.getWidth(), actualImage.getHeight());
                    updateCoordsTextFields();
                } else {
                    view.showError("Please load an image first.");
                }
            }
        });

        // Load image button
        view.getBtnLoad().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage newImage = fileManager.loadImage();
                if (newImage != null) {
                    updateImageAndUI(newImage);
                }
            }
        });

        // Save image button
        view.getBtnSave().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage actualImage = model.getImage();
                if (actualImage == null) {
                    view.showError("Please load an image first.");
                    return;
                }
                fileManager.saveImage(actualImage);
            }
        });

        // Invert colors button
        view.getBtnInvert().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage actualImage = model.getImage();
                if (actualImage == null) { view.showError("Please load an image first."); return; }
                
                Coords selection = view.getSelection();
                if (selection.getWidth() <= 0 || selection.getHeight() <= 0) { view.showError("Please select an area first."); return; }
                
                ActionEditor actionInvert = new Invert();
                BufferedImage modifiedImage = actionInvert.execute(actualImage, selection);
                updateImageAndUI(modifiedImage);
            }
        });

        // Crop button
        view.getBtnCrop().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage actualImage = model.getImage();
                if (actualImage == null) { view.showError("Please load an image first."); return; }
                
                Coords selection = view.getSelection();
                if (selection.getWidth() <= 0 || selection.getHeight() <= 0) { view.showError("Please select an area first."); return; }
                
                ActionEditor actionCrop = new Crop();
                BufferedImage modifiedImage = actionCrop.execute(actualImage, selection);
                updateImageAndUI(modifiedImage);
            }
        });

        // Rotate selection using typed angle
        view.getBtnRotate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage actualImage = model.getImage();
                if (actualImage == null) { view.showError("Please load an image first."); return; }
                
                Coords selection = view.getSelection();
                if (selection.getWidth() <= 0 || selection.getHeight() <= 0) { view.showError("Please select an area first."); return; }

                try {
                    // Read angle from text field
                    double userAngle = Double.parseDouble(view.getTxtAngle().getText());

                    ActionEditor actionRotate = new Rotate(userAngle);
                    BufferedImage modifiedImage = actionRotate.execute(actualImage, selection);
                    updateImageAndUI(modifiedImage);

                } catch (NumberFormatException ex) {
                    view.showError("Please enter a valid numeric angle.");
                }
            }
        });
    }

    // Keep text fields synced with selection
    private void updateCoordsTextFields() {
        Coords c = view.getCanvas().getSelectionCoords();
        view.getTxtX().setText(String.valueOf(c.getX()));
        view.getTxtY().setText(String.valueOf(c.getY()));
        view.getTxtWidth().setText(String.valueOf(c.getWidth()));
        view.getTxtHeight().setText(String.valueOf(c.getHeight()));
    }

    // Update model and view after an action
    private void updateImageAndUI(BufferedImage newImage) {
        model.setImage(newImage);
        view.updateImage(newImage);
        view.clearSelection();
        updateCoordsTextFields();
    }
}