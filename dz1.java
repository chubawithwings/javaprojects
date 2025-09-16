pupublic class Main {
    public static void main(String[] args) {
        Calculator calc = new Calculator();

        double r1 = calc.calculate(5, 3, '+');
        System.out.println("5 + 3 = " + r1);

        double r2 = calc.calculate(2.5, 1.2);
        System.out.println("2.5 + 1.2 = " + r2);

        int r3 = calc.calculate(1, 2, 3, 4);
        System.out.println("Сумма чисел 1, 2, 3, 4 = " + r3);

        Student s1 = new Student("Иванов", "Иван");
        Student s2 = new Student("Петрова", "Анна");

        System.out.println("Сравнение студентов Иванов и Петрова (по фамилии): " + s1.compare(s2));
        System.out.println("Сравнение фамилии Иванов со строкой \"Петрова\": " + s1.compare("Петрова"));
    }
}

class Calculator {
    public double calculate(int a, int b, char op) {
        if (op == '+') return a + b;
        if (op == '-') return a - b;
        if (op == '*') return a * b;
        if (op == '/') {
            if (b == 0) return Double.NaN;
            return (double) a / b;
        }
        return Double.NaN;
    }

    public double calculate(double a, double b) {
        return a + b;
    }

    public int calculate(int... values) {
        int sum = 0;
        for (int v : values) {
            sum += v;
        }
        return sum;
    }
}

class Student {
    String lastName;
    String firstName;

    public Student(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    // Сравнение студентов по фамилии
    public int compare(Student other) {
        return this.lastName.compareTo(other.lastName);
    }

    // Сравнение с произвольной фамилией (строкой)
    public int compare(String lastName) {
        return this.lastName.compareTo(lastName);
    }
}
