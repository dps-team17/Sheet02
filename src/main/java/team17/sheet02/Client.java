package team17.sheet02;

import team17.sheet02.helpers.KeyIn;

import static team17.sheet02.helpers.KeyIn.inInt;

public class Client {

    private static ICalculator calculator;
    private static String host;
    private static String username;

    public static void main(String[] args) {

        host = "127.0.0.1";
        username = "Daniel";

        calculator = new RemoteCalculatorClient(host, username);
        run();
    }

    private static int getChoice() {

        // Display menu
        System.out.println("===========================");
        System.out.println("| Remote Calculation Demo |");
        System.out.println("===========================");
        System.out.println("| Methods:                |");
        System.out.println("| 1. Add numbers          |");
        System.out.println("| 2. Subtract numbers     |");
        System.out.println("| 3. Multiply numbers     |");
        System.out.println("| 4. Lucas number         |");
        System.out.println("|                         |");
        System.out.println("| Settings:               |");
        System.out.println("| 8. Change Username      |");
        System.out.println("| 9. Exit                 |");
        System.out.println("===========================");

        // Read choice
        return inInt(" Select option: ");
    }

    private static void run() {

        int choice;
        do {

            choice = getChoice();

            // Switch construct
            switch (choice) {
                case 1:
                    AddNumbers();
                    break;
                case 2:
                    SubtractNumbers();
                    break;
                case 3:
                    MultiplyNumbers();
                    break;
                case 4:
                    LucasNumber();
                    break;
                case 8:
                    ChangeUsername();
                    break;
                case 9:
                    System.out.println("Exit selected");
                    break;
                default:
                    System.out.println("Invalid selection");
            }

        } while (choice != 9);
    }

    private static void ChangeUsername() {
        username = KeyIn.inString("Username:");
        calculator = new RemoteCalculatorClient(host, username);
        System.out.println("\n");
    }

    private static void LucasNumber() {
        try {

            int a = inInt("Number:");

            int result = calculator.Lukas(a);

            System.out.format("Lucas number %d = %d\n\n", a, result);

        } catch (CalculationException e) {
            System.out.format("\nError during calculation:\n%s\n\n", e.getMessage());
        }
    }

    private static void MultiplyNumbers() {
        try {

            int a = inInt("First number:");
            int b = inInt("Second number:");

            int result = calculator.Multiply(a, b);

            System.out.format("%d * %d = %d\n\n", a, b, result);

        } catch (CalculationException e) {
            System.out.format("\nError during calculation:\n%s\n\n", e.getMessage());
        }
    }

    private static void SubtractNumbers() {
        try {

            int a = inInt("First number:");
            int b = inInt("Second number:");

            int result = calculator.Subtract(a, b);

            System.out.format("%d - %d = %d\n\n", a, b, result);

        } catch (CalculationException e) {
            System.out.format("\nError during calculation:\n%s\n\n", e.getMessage());
        }
    }

    private static void AddNumbers() {

        try {

            int a = inInt("First number:");
            int b = inInt("Second number:");

            int result = calculator.Add(a, b);

            System.out.format("%d + %d = %d\n\n", a, b, result);

        } catch (CalculationException e) {
            System.out.format("\nError during calculation:\n%s\n\n", e.getMessage());
        }
    }
}