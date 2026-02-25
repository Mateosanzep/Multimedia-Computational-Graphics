package edu.up.mateo.editor.gui;

import edu.up.mateo.editor.model.Coords;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainWindow extends JFrame implements GuiEditor {
    private Canvas canvas;
    
    // Set the buttons for the actions
    private JButton btnLoad;
    private JButton btnSave;
    private JButton btnCrop;
    private JButton btnInvert;
    private JButton btnRotate;

    public MainWindow() {
        super("Image editor");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Starts and set the convas on the center
        canvas = new Canvas();
        // Add a scroll
        JScrollPane scrollPane = new JScrollPane(canvas);
        add(scrollPane, BorderLayout.CENTER);

        // Panel for the buttons
        JPanel buttonPanel = new JPanel();
        
        btnLoad = new JButton("Load Image");
        btnSave = new JButton("Save");
        btnCrop = new JButton("Crop");
        btnInvert = new JButton("Invert Colors");
        btnRotate = new JButton("Rotate");

        // add the buttons to the panel
        buttonPanel.add(btnLoad);
        buttonPanel.add(btnSave);
        buttonPanel.add(new JLabel(" | "));
        buttonPanel.add(btnCrop);
        buttonPanel.add(btnInvert);
        buttonPanel.add(btnRotate);

        add(buttonPanel, BorderLayout.NORTH);
    }

    // Getters for the buttons so the Controller can add listeners
    public JButton getBtnLoad() { 
        return btnLoad; 
    }
    public JButton getBtnSave() { 
        return btnSave; 
    }
    public JButton getBtnCrop() { 
        return btnCrop; }
    public JButton getBtnInvert() { return btnInvert; 

    }
    public JButton getBtnRotate() { 
        return btnRotate; 
    }

    // Methods from the GuiEditor interface

    @Override
    public void updateImage(BufferedImage img) {
        canvas.setImage(img);
        // adjust the window size to fit the new image
        if (img != null) {
            canvas.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
            canvas.revalidate(); 
        }
    }

    @Override
    public Coords getSelection() {
        return canvas.getSelectionCoords();
    }

    @Override
    public void clearSelection() {
        canvas.clearSelection();
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}