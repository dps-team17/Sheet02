package team17.sheet02;

public class Calculator implements ICalculator {

    @Override
    public int Add(int a, int b) {
        return a + b;
    }

    @Override
    public int Subtract(int a, int b) {
        return a - b;
    }

    @Override
    public int Multiply(int a, int b) {
        return a * b;
    }

    @Override
    public int Lukas(int a) {

        if (a == 0) {
            return 2;
        } else if (a == 1) {
            return 1;
        } else {
            return Lukas(a - 1) + Lukas(a - 2);
        }
    }
}
