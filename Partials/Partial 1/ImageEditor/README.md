# Image Editor

A desktop image editor built with **Java Swing** as part of the Multimedia & Computational Graphics course (4th Semester) at Universidad Panamericana.

It lets you load an image, select a region with your mouse or by typing coordinates, and apply pixel-level transformations like inverting colors, cropping, and rotating.

---

## Project Structure

```
src/edu/up/mateo/editor/
├── main/
│   └── Main.java                 Entry point — boots Swing on the EDT and wires MVC
├── model/
│   ├── ImageEditor.java          Holds the current BufferedImage in memory
│   ├── Coords.java               Simple data class for selection (x, y, width, height)
│   └── actions/
│       ├── ActionEditor.java     Interface every image action implements
│       ├── Invert.java           Inverts RGB channels inside the selected area
│       ├── Crop.java             Extracts the selected region into a new image
│       └── Rotate.java           Rotates the selected area using inverse mapping
├── gui/
│   ├── GuiEditor.java            Interface the view must fulfill for the controller
│   ├── MainWindow.java           Main JFrame — toolbar, side panel, status bar
│   └── Canvas.java               Custom JPanel — draws the image, selection box, rulers
├── controller/
│   └── ControllerEditor.java     Connects buttons/mouse to model actions
└── utils/
    └── FileManager.java          Load/save images via JFileChooser + ImageIO
```

## How It Works

The project follows the **MVC (Model-View-Controller)** pattern:

### Model
- **ImageEditor** stores the current `BufferedImage`. The controller reads and writes through `getImage()` / `setImage()`.
- **Coords** packages a selection rectangle (x, y, width, height) so it can be passed cleanly between layers.
- **ActionEditor** is the interface that every image operation implements. Each action receives the current image and a `Coords` selection and returns a new `BufferedImage` — the original is never mutated.

### Actions (pixel manipulation)

| Action | What it does |
|--------|-------------|
| **Invert** | Loops through the selected pixels, extracts ARGB channels with bitwise ops, and flips each RGB value (`255 - channel`). |
| **Crop** | Creates a smaller `BufferedImage` with the dimensions of the selection and copies only the selected pixels into it. |
| **Rotate** | Calculates the diagonal of the selection to define a safe rotation zone, clears the original area, then uses **inverse mapping** with `cos`/`sin` to pull source pixels into their rotated positions. The rotation angle is read from a text field in the UI. |

### View
- **GuiEditor** is the interface the controller talks to — `updateImage()`, `getSelection()`, `clearSelection()`, `showError()`.
- **MainWindow** implements `GuiEditor`. It builds the dark-themed UI: a top toolbar with color-coded buttons, a right panel with coordinate inputs and tool buttons, a status bar, and a scrollable canvas in the center.
- **Canvas** is a custom `JPanel` that draws the image over a checkerboard background, renders pixel rulers on the edges, and paints the selection rectangle with a size label.

### Controller
- **ControllerEditor** receives the view, model, and file manager via constructor injection. It attaches `ActionListener`s to every button and keeps the coordinate text fields synced with the mouse selection on the canvas. Each listener validates the image and selection before running the corresponding action.

### Utils
- **FileManager** handles file I/O through `JFileChooser`. `loadImage()` returns a `BufferedImage` (or `null`), and `saveImage()` writes the image as PNG with automatic extension handling.

---

## How to Run

1. Open the project in VS Code (with the Java Extension Pack).
2. Run `Main.java` — the editor window will appear.
3. Click **Load Image** to open a JPG or PNG.
4. Draw a selection on the canvas (or type coordinates and click **Apply Coords**).
5. Use **Crop**, **Invert**, or **Rotate** to transform the selected area.
6. Click **Save** to export the result as PNG.

---

## Credits

All the code in this project was written by me, guided by AI assistance for learning and problem-solving.

The GUI was originally designed and created by me. Based on my design direction, the AI helped refine and polish the visual styling (dark theme, color-coded buttons, checkerboard canvas, selection labels) to improve the overall look and feel.

The image processing logic (invert, crop, rotate), the MVC architecture, the controller wiring, and the file management were all implemented by me with AI acting as a guide throughout the process.
