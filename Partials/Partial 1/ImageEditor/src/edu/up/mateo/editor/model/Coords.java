package edu.up.mateo.editor.model;

public class Coords {
    private int x;
    private int y;
    private int width;
    private int height;

    public Coords(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Getters for the coordinates and dimensions
    public int getX() { 
        return x; 
    }
    public int getY() { 
        return y; 
    }
    public int getWidth() { 
        return width; 
    }
    public int getHeight() { 
        return height; 
    }
}