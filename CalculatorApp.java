import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Calculator {
    double a,b;
    char operator;
    double result;
}

class real extends Calculator {
    double Result() {
        switch (operator){
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/':
                if (b != 0) return a / b;
                else {
                    System.out.println("Error: Division by zero");
                    return Double.NaN;
                }
            case '%': return a % b;
            default:
                System.out.println("Invalid operator");
                return Double.NaN;
        }
    }
}

public class CalculatorApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n--- Calculator App ---");
        System.out.println("Choose mode: 1 for Console, 2 for GUI");
        System.out.print("Enter choice: ");
        int mode = sc.nextInt();

        if (mode == 1) {
            real obj = new real();

            System.out.print("Enter first number: ");
            obj.a = sc.nextDouble();

            System.out.print("Enter second number: ");
            obj.b = sc.nextDouble();

            System.out.print("Enter operator (+, -, *, /, %): ");
            obj.operator = sc.next().charAt(0);

            double output = obj.Result();

            if (Double.isNaN(output)) {
                System.out.println("Calculation failed due to invalid input or operation.\n");
            } else {
                String opStr = String.valueOf(obj.operator);
                String resStr = formatNumber(output);
                System.out.printf("\nResult: %.4f %s %.4f = %s\n\n", obj.a, opStr, obj.b, resStr);
            }

        } else if (mode == 2) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                new CalculatorGUI().createAndShowGUI();
            });
        } else {
            System.out.println("Invalid mode selected.\n");
        }

        sc.close();
    }

    public static String formatNumber(double num) {
        if(num == (long) num)
            return String.format("%d", (long)num);
        else
            return String.format("%.4f", num);
    }
}

class CalculatorGUI {
    JFrame frame;
    JTextField fieldA, fieldB, fieldOp;
    JLabel resultLabel;

    void createAndShowGUI() {
        frame = new JFrame("Calculator GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 320);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        frame.add(new JLabel("First Number:"), gbc);
        gbc.gridx = 1;
        fieldA = new JTextField(10);
        frame.add(fieldA, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        frame.add(new JLabel("Second Number:"), gbc);
        gbc.gridx = 1;
        fieldB = new JTextField(10);
        frame.add(fieldB, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        frame.add(new JLabel("Operator (+, -, *, /, %):"), gbc);
        gbc.gridx = 1;
        fieldOp = new JTextField(10);
        frame.add(fieldOp, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        JButton calcButton = new JButton("Calculate");
        frame.add(calcButton, gbc);

        gbc.gridx = 1;
        resultLabel = new JLabel("Result: ");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        frame.add(resultLabel, gbc);

        // KeyListener for Enter key to move focus or calculate
        fieldA.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    fieldB.requestFocusInWindow();
                }
            }
        });
        fieldB.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    fieldOp.requestFocusInWindow();
                }
            }
        });
        fieldOp.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    calculate();
                }
            }
        });

        calcButton.addActionListener(e -> calculate());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    void calculate() {
        try {
            double a = Double.parseDouble(fieldA.getText().trim());
            double b = Double.parseDouble(fieldB.getText().trim());
            String opText = fieldOp.getText().trim();

            if (opText.length() == 0) {
                resultLabel.setText("Error: Operator required");
                return;
            }

            char operator = opText.charAt(0);

            real obj = new real();
            obj.a = a;
            obj.b = b;
            obj.operator = operator;

            double output = obj.Result();

            if (Double.isNaN(output)) {
                resultLabel.setText("Error: Invalid operation");
            } else {
                String resStr = CalculatorApp.formatNumber(output);
                resultLabel.setText(String.format("Result: %s %c %s = %s", 
                    CalculatorApp.formatNumber(a), operator, CalculatorApp.formatNumber(b), resStr));
            }
        } catch (NumberFormatException ex) {
            resultLabel.setText("Error: Invalid number");
        }
    }

}
