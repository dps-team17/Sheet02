package team17.sheet02.helpers;
/*
KeyIn class from http://www.java2s.com/Code/Java/Development-Class/Javaprogramtodemonstratemenuselection.htm

Java Programming for Engineers
Julio Sanchez
Maria P. Canton


ISBN: 0849308100
Publisher: CRC Press
*/

//**********************************************************
//**********************************************************
//Program: Keyin
//Reference: Session 20
//Topics:
// 1. Using the read() method of the InputStream class
//    in the java.io package
// 2. Developing a class for performing basic console
//    input of character and numeric types
//**********************************************************
//**********************************************************

public class KeyIn {

    //*******************************
    //   support methods
    //*******************************
    //Method to display the user's prompt string
    private static void printPrompt(String prompt) {
        System.out.print(prompt + " ");
        System.out.flush();
    }

    //Method to make sure no data is available in the
    //input stream
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void inputFlush() {

        try {
            while ((System.in.available()) != 0)
                System.in.read();
        } catch (java.io.IOException e) {
            System.out.println("Input error");
        }
    }

    //********************************
    //  data input methods for
    //string, int, char, and double
    //********************************
    public static String inString(String prompt) {
        inputFlush();
        printPrompt(prompt);
        return inString();
    }

    private static String inString() {
        int aChar;
        String s = "";
        boolean finished = false;

        while (!finished) {
            try {
                aChar = System.in.read();
                if (aChar < 0 || (char) aChar == '\n')
                    finished = true;
                else if ((char) aChar != '\r')
                    s = s + (char) aChar; // Enter into string
            } catch (java.io.IOException e) {
                System.out.println("Input error");
                finished = true;
            }
        }
        return s;
    }

    public static int inInt(String prompt) {
        while (true) {
            inputFlush();
            printPrompt(prompt);
            try {
                return Integer.valueOf(inString().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Not an integer");
            }
        }
    }

}