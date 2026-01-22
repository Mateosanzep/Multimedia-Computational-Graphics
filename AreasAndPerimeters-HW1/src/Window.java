import javax.swing.*;
import java.awt.event.*;
public class Window extends JFrame {
    public JPanel panel;
    private JComboBox<String> combo;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JTextField field1;
    private JTextField field2;
    private JTextField field3;

    public Window(){
        this.setSize(300, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Shape");
        this.setLocationRelativeTo(null);
        elements();
    }

    private void elements(){
        panels();
        title();   
        options();
        buttons();
    }

    private void panels(){
        panel = new JPanel();
        this.getContentPane().add(panel);
        panel.setLayout(null);
    }

    private void title(){
        JLabel tag = new JLabel();
        tag.setText("Select your shape:");
        tag.setBounds(10,10,150,50);
        panel.add(tag);
    }

    private void options(){
        combo = new JComboBox<>();

        label1 = new JLabel();
        field1 = new JTextField();
        label2 = new JLabel();
        field2 = new JTextField();
        label3 = new JLabel();
        field3 = new JTextField();

        label1.setVisible(false);
        field1.setVisible(false);
        label2.setVisible(false);
        field2.setVisible(false);
        label3.setVisible(false);
        field3.setVisible(false);

        combo.addItem("Select...");
        combo.addItem("Square");
        combo.addItem("Rectangle");
        combo.addItem("Triangle");
        combo.addItem("Circle");
        combo.addItem("Regular Pentagon");
        combo.addItem("Pentagram");
        combo.addItem("Semi-circle");
        combo.setSelectedIndex(0);
        combo.setBounds(50, 70, 170, 30);
        combo.setEnabled(true);
        combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selection = (String) combo.getSelectedItem();
                if (selection == "Square") {
                    label1.setText("Side:");
                    field1.setVisible(true);
                    label1.setVisible(true);
                    field2.setVisible(false);
                    label2.setVisible(false);
                    label3.setVisible(false);
                    field3.setVisible(false);
                } else if (selection == "Rectangle") {
                    label1.setText("Base:");
                    label2.setText("Height:");
                    field1.setVisible(true);
                    label1.setVisible(true);
                    field2.setVisible(true);
                    label2.setVisible(true);
                    label3.setVisible(false);
                    field3.setVisible(false);
                } else if (selection == "Circle" || selection == "Semi-circle") {
                    label1.setText("Radius:");
                    field1.setVisible(true);
                    label1.setVisible(true);
                    field2.setVisible(false);
                    label2.setVisible(false);
                    label3.setVisible(false);
                    field3.setVisible(false);
                } else if (selection == "Regular Pentagon") {
                    label1.setText("Side:");
                    field1.setVisible(true);
                    label1.setVisible(true);
                    field2.setVisible(false);
                    label2.setVisible(false);
                    label3.setVisible(false);
                    field3.setVisible(false);
                } else if(selection == "Pentagram"){
                    label1.setText("Side:");
                    field1.setVisible(true);
                    label1.setVisible(true);
                    field2.setVisible(false);
                    label2.setVisible(false);
                    label3.setVisible(false);
                    field3.setVisible(false);
                } else if(selection == "Triangle"){
                    label1.setText("Base:");
                    label2.setText("Side 2:");
                    label3.setText("Side 3:");
                    field1.setVisible(true);
                    label1.setVisible(true);
                    field2.setVisible(true);
                    label2.setVisible(true);
                    field3.setVisible(true);
                    label3.setVisible(true);
                }
    }});
        label1.setBounds(50, 100, 150, 30);
        field1.setBounds(50, 125, 150, 30);
        field1.setEditable(true);
        label2.setBounds(50, 150, 150, 30);
        field2.setBounds(50, 175, 150, 30);
        field2.setEditable(true);
        label3.setBounds(50, 200, 150, 30);
        field3.setBounds(50, 225, 150, 30);
        field3.setEditable(true);
        panel.add(combo);
        panel.add(field1);
        panel.add(field2);
        panel.add(label1);
        panel.add(label2);
        panel.add(label3);
        panel.add(field3);
    }
    
    private void buttons(){
        JButton button = new JButton();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                results();
            }
        });
        button.setText("Calculate");
        button.setBounds(50,260,100,50);
        button.setEnabled(true);
        button.setMnemonic('a');
        panel.add(button);
    }

    private void results(){
        String figure = (String) combo.getSelectedItem();
        double[] res = new double[]{0.0, 0.0};
        try {
            if("Rectangle".equals(figure))
                res = Maths.Rectangle(Double.parseDouble(field1.getText()), Double.parseDouble(field2.getText()));
            else if("Triangle".equals(figure))
                res = Maths.Triangle(Double.parseDouble(field1.getText()), Double.parseDouble(field2.getText()), Double.parseDouble(field3.getText()));
            else if("Circle".equals(figure))
                res = Maths.Circle(Double.parseDouble(field1.getText()));
            else if("Regular Pentagon".equals(figure))
                res = Maths.RegularPentagon(Double.parseDouble(field1.getText()));
            else if("Pentagram".equals(figure))
                res = Maths.Pentagram(Double.parseDouble(field1.getText()));
            else if("Semi-circle".equals(figure))
                res = Maths.SemiCircle(Double.parseDouble(field1.getText()));
            else if("Square".equals(figure))
                res = Maths.Square(Double.parseDouble(field1.getText()));
            else {
                JOptionPane.showMessageDialog(this, "Please select a shape.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values.", "Input error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ResultWindow resultWindow = new ResultWindow(figure, res);
        resultWindow.setVisible(true);
    }
    
    private static class ResultWindow extends JFrame {
        public ResultWindow(String figure, double[] res) {
            this.setSize(300,150);
            this.setTitle("Result");
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            JPanel p = new JPanel();
            p.setLayout(null);
            JLabel label = new JLabel("Selected: " + figure);
            label.setBounds(10,10,260,30);
            JLabel areaLabel = new JLabel("Area: " + res[0]);
            areaLabel.setBounds(10, 40, 260, 30);
            JLabel perimeterLabel = new JLabel("Perimeter: " + res[1]);
            perimeterLabel.setBounds(10, 70, 260, 30);
            p.add(label);
            p.add(areaLabel);
            p.add(perimeterLabel);
            this.getContentPane().add(p);
        }
    }
}
