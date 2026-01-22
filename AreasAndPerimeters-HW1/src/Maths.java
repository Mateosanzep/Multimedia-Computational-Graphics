public class Maths {
    public static double[] Square(double side) {
        double area = side * side;
        double perimeter = 4 * side;
        return new double[]{area, perimeter};
    }

    public static double[] Rectangle(double base, double height) {
        double area = base * height;
        double perimeter = 2 * (base + height);
        return new double[]{area, perimeter};
    }

    public static double[] Triangle(double base, double side2, double side3) {
        double a = base;
        double b = side2;
        double c = side3;
        double perimeter = a + b + c;
        double s = perimeter / 2.0;
        double area = 0.0;
        double v = s * (s - a) * (s - b) * (s - c);
        if (v > 0) {
            area = Math.sqrt(v);
        } else {
            area = 0.0;
        }
        return new double[]{area, perimeter};
    }

    public static double[] Circle(double radius) {
        double area = Math.PI * radius * radius;
        double perimeter = 2 * Math.PI * radius;
        return new double[]{area, perimeter};
    }

    public static double[] RegularPentagon(double side) {
        double area = (1/4.0) * Math.sqrt(5 * (5 + 2 * Math.sqrt(5))) * side * side;
        double perimeter = 5 * side;
        return new double[]{area, perimeter};
    }
    public static double[] Pentagram(double side) {
        double b = side * Math.sqrt(2 * (1 - Math.cos(Math.toRadians(36))));
        double[] resTri = Triangle(b, side, side);
        double[] resPent = RegularPentagon(b);
        double area = resTri[0] * 5 + resPent[0];
        double perimeter = 10 * side;
        return new double[]{area, perimeter};
    }

    public static double[] SemiCircle(double radius) {
        double area = (Math.PI * radius * radius) / 2;
        double perimeter = Math.PI * radius + 2 * radius;
        return new double[]{area, perimeter};
    }
    
}