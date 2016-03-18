package team17.sheet02;

class Calculator {

    int Add(int a, int b) {
        return a + b;
    }

    int Subtract(int a, int b) {
        return a - b;
    }

    int Multiply(int a, int b) {
        return a * b;
    }

    int Lukas(int a) {

        if (a == 0) {
            return 2;
        } else if (a == 1) {
            return 1;
        } else {
            return Lukas(a - 1) + Lukas(a - 2);
        }
    }
}
