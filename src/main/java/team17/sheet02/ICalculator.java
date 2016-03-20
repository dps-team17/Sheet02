package team17.sheet02;


import javax.naming.AuthenticationException;

public interface ICalculator {
    int Add(int a, int b) throws CalculationException;

    int Subtract(int a, int b) throws CalculationException;

    int Multiply(int a, int b) throws CalculationException;

    int Lukas(int a) throws CalculationException;
}
