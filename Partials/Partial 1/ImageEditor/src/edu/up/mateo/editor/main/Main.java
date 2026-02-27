package edu.up.mateo.editor.main;

import edu.up.mateo.editor.controller.ControllerEditor;
import edu.up.mateo.editor.gui.MainWindow;
import edu.up.mateo.editor.model.ImageEditor;
import edu.up.mateo.editor.utils.FileManager;

public class Main {
    public static void main(String[] args) {
        
        // Start Swing UI on the event dispatch thread
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                // Create the main window
                MainWindow view = new MainWindow();
                
                // Create the image model
                ImageEditor model = new ImageEditor();
                
                // Create file utility
                FileManager fileManager = new FileManager();
                
                // Connect view, model and file manager
                ControllerEditor controller = new ControllerEditor(view, model, fileManager);
                
                // Show window
                view.setVisible(true);
            }
        });
        
    }
}