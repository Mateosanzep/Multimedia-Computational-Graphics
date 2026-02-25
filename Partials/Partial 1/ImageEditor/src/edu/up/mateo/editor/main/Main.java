package edu.up.mateo.editor.main;

import edu.up.mateo.editor.gui.MainWindow;

public class Main {
    public static void main(String[] args) {
        
        // Initialize the GUI in the Event Dispatch Thread (EDT) for thread safety
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Starts the main window
                MainWindow ventana = new MainWindow();
                
                // Make the window visible
                ventana.setVisible(true);
            }
        });
        
    }
}