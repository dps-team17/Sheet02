package team17.sheet02;

import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorTest {

    @Test
    public void Add_ValidParameters_ReturnsValidResult(){
        Calculator sut = new Calculator();

        int a = 1;
        int b = 2;

        int expected = a + b;
        int actual = sut.Add(a, b);

        assertEquals(expected, actual);
    }

    @Test
    public void Subtract_ValidParameters_ReturnsValidResult(){
        Calculator sut = new Calculator();

        int a = 1;
        int b = 2;

        int expected = a - b;
        int actual = sut.Subtract(a, b);

        assertEquals(expected, actual);
    }

    @Test
    public void Multiply_ValidParameters_ReturnsValidResult(){
        Calculator sut = new Calculator();

        int a = 1;
        int b = 2;

        int expected = a * b;
        int actual = sut.Multiply(a, b);

        assertEquals(expected, actual);
    }

    @Test
    public void Lukas_OfZero_IsTwo(){
        Calculator sut = new Calculator();

        int a = 0;

        int expected = 2;
        int actual = sut.Lukas(a);

        assertEquals(expected, actual);
    }

    @Test
    public void Lukas_OfOne_IsOne(){
        Calculator sut = new Calculator();

        int a = 1;

        int expected = 1;
        int actual = sut.Lukas(a);

        assertEquals(expected, actual);
    }

    @Test
    public void Lukas_OfTen_Is123(){
        Calculator sut = new Calculator();

        int a = 10;

        int expected = 123;
        int actual = sut.Lukas(a);

        assertEquals(expected, actual);
    }
}