package edu.up.mateo.editor.gui;

import edu.up.mateo.editor.model.Coords;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainWindow extends JFrame implements GuiEditor {

    // Color palette
    private static final Color BG_DARK = new Color(45, 45, 48);
    private static final Color BG_PANEL = new Color(60, 63, 65);
    private static final Color BG_FIELD = new Color(75, 78, 80);
    private static final Color TEXT_PRIMARY = new Color(220, 220, 220);
    private static final Color TEXT_SECONDARY = new Color(160, 160, 160);
    private static final Color ACCENT_BLUE = new Color(70, 130, 210);
    private static final Color ACCENT_GREEN = new Color(80, 175, 95);
    private static final Color ACCENT_ORANGE = new Color(220, 150, 50);
    private static final Color SEPARATOR_COLOR = new Color(90, 93, 95);

    private Canvas canvas;

    // Action buttons
    private JButton btnLoad, btnSave, btnSelectAll;
    private JButton btnCrop, btnInvert, btnRotate, btnApplyCoords;

    // Inputs for selection and rotation
    private JTextField txtX, txtY, txtWidth, txtHeight, txtAngle;

    // Status bar label
    private JLabel lblImageInfo;

    public MainWindow() {
        super("Image Editor");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(BG_DARK);

        // Main canvas area with dark scroll pane
        canvas = new Canvas();
        JScrollPane scrollPane = new JScrollPane(canvas);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(BG_DARK);
        add(scrollPane, BorderLayout.CENTER);

        // Top toolbar
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        topPanel.setBackground(BG_PANEL);
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, SEPARATOR_COLOR));

        btnLoad = createToolbarButton("\u2B73  Load Image", ACCENT_BLUE);
        btnSave = createToolbarButton("\uD83D\uDCBE  Save", ACCENT_GREEN);
        btnSelectAll = createToolbarButton("\u25A3  Select All", ACCENT_ORANGE);

        topPanel.add(btnLoad);
        topPanel.add(Box.createHorizontalStrut(4));
        topPanel.add(btnSave);
        topPanel.add(Box.createHorizontalStrut(12));
        JSeparator vSep = new JSeparator(SwingConstants.VERTICAL);
        vSep.setPreferredSize(new Dimension(1, 24));
        vSep.setForeground(SEPARATOR_COLOR);
        topPanel.add(vSep);
        topPanel.add(Box.createHorizontalStrut(12));
        topPanel.add(btnSelectAll);
        add(topPanel, BorderLayout.NORTH);

        // Right panel for tools and coordinates
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(BG_PANEL);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 1, 0, 0, SEPARATOR_COLOR),
            BorderFactory.createEmptyBorder(12, 14, 12, 14)
        ));
        rightPanel.setPreferredSize(new Dimension(220, 0));

        // Coordinates section title
        JLabel lblCoords = new JLabel("SELECTION");
        lblCoords.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblCoords.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblCoords.setForeground(TEXT_SECONDARY);
        rightPanel.add(Box.createVerticalStrut(8));
        rightPanel.add(lblCoords);
        rightPanel.add(Box.createVerticalStrut(12));

        // Grid with X, Y, width and height
        JPanel coordsGrid = new JPanel(new GridLayout(2, 4, 6, 6));
        coordsGrid.setMaximumSize(new Dimension(210, 60));
        coordsGrid.setOpaque(false);

        coordsGrid.add(createFieldLabel("X"));
        txtX = createStyledField("0");
        coordsGrid.add(txtX);
        coordsGrid.add(createFieldLabel("Y"));
        txtY = createStyledField("0");
        coordsGrid.add(txtY);

        coordsGrid.add(createFieldLabel("W"));
        txtWidth = createStyledField("0");
        coordsGrid.add(txtWidth);
        coordsGrid.add(createFieldLabel("H"));
        txtHeight = createStyledField("0");
        coordsGrid.add(txtHeight);

        rightPanel.add(coordsGrid);

        // Button to apply typed coordinates
        btnApplyCoords = createActionButton("Apply Coords", ACCENT_BLUE);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(btnApplyCoords);

        // Separator
        rightPanel.add(Box.createVerticalStrut(20));
        JSeparator sep = new JSeparator();
        sep.setForeground(SEPARATOR_COLOR);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        rightPanel.add(sep);
        rightPanel.add(Box.createVerticalStrut(20));

        // Tools section title
        JLabel lblTools = new JLabel("TOOLS");
        lblTools.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTools.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblTools.setForeground(TEXT_SECONDARY);
        rightPanel.add(lblTools);
        rightPanel.add(Box.createVerticalStrut(14));

        // Image action buttons
        btnCrop = createActionButton("\u2702  Crop", ACCENT_ORANGE);
        btnInvert = createActionButton("\u25D1  Invert", ACCENT_BLUE);
        btnRotate = createActionButton("\u21BB  Rotate", ACCENT_GREEN);

        rightPanel.add(btnCrop);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(btnInvert);
        rightPanel.add(Box.createVerticalStrut(10));

        // Rotation controls with angle input
        JPanel rotatePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        rotatePanel.setMaximumSize(new Dimension(210, 40));
        rotatePanel.setOpaque(false);
        rotatePanel.add(btnRotate);
        txtAngle = createStyledField("90");
        txtAngle.setColumns(3);
        JLabel degLabel = new JLabel("\u00B0");
        degLabel.setForeground(TEXT_SECONDARY);
        rotatePanel.add(txtAngle);
        rotatePanel.add(degLabel);
        rightPanel.add(rotatePanel);

        add(rightPanel, BorderLayout.EAST);

        // Bottom status bar
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        bottomPanel.setBackground(BG_PANEL);
        bottomPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, SEPARATOR_COLOR));
        lblImageInfo = new JLabel("No image loaded");
        lblImageInfo.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblImageInfo.setForeground(TEXT_SECONDARY);
        bottomPanel.add(lblImageInfo);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Create styled label for coordinate fields
    private JLabel createFieldLabel(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.RIGHT);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 11));
        lbl.setForeground(TEXT_PRIMARY);
        return lbl;
    }

    // Create styled text field
    private JTextField createStyledField(String defaultValue) {
        JTextField field = new JTextField(defaultValue);
        field.setBackground(BG_FIELD);
        field.setForeground(TEXT_PRIMARY);
        field.setCaretColor(TEXT_PRIMARY);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SEPARATOR_COLOR),
            BorderFactory.createEmptyBorder(2, 4, 2, 4)
        ));
        field.setFont(new Font("Monospaced", Font.PLAIN, 12));
        field.setHorizontalAlignment(JTextField.CENTER);
        return field;
    }

    // Create styled toolbar button
    private JButton createToolbarButton(String text, Color accentColor) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(accentColor);
        btn.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        return btn;
    }

    // Create styled side panel button
    private JButton createActionButton(String text, Color accentColor) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(160, 34));
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(accentColor);
        btn.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        return btn;
    }

    // Getters for the controller
    public Canvas getCanvas() { return canvas; }
    public JButton getBtnLoad() { return btnLoad; }
    public JButton getBtnSave() { return btnSave; }
    public JButton getBtnSelectAll() { return btnSelectAll; }
    public JButton getBtnApplyCoords() { return btnApplyCoords; }
    public JButton getBtnCrop() { return btnCrop; }
    public JButton getBtnInvert() { return btnInvert; }
    public JButton getBtnRotate() { return btnRotate; }
    
    public JTextField getTxtX() { return txtX; }
    public JTextField getTxtY() { return txtY; }
    public JTextField getTxtWidth() { return txtWidth; }
    public JTextField getTxtHeight() { return txtHeight; }
    public JTextField getTxtAngle() { return txtAngle; }

    // Methods from GuiEditor
    @Override
    public void updateImage(BufferedImage img) {
        canvas.setImage(img);
        if (img != null) {
            canvas.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
            canvas.revalidate(); 
            updateImageInfo(img.getWidth(), img.getHeight());
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

    @Override
    public void updateImageInfo(int width, int height) {
        lblImageInfo.setText("\u2B25  Image: " + width + " x " + height + " px");
    }
}