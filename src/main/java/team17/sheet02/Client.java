package team17.sheet02;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {

        String host = "127.0.0.1";
        String username = "Danielo";

        int param1, param2, result;
        ICalculator calculator = new RemoteCalculatorClient(host, username);

        param1 = 1;
        param2 = 1;

        try {
            result = calculator.Add(param1, param2);

            System.out.println(String.format("%d + %d = %d", param1, param2, result));
        } catch (CalculationException e){
            System.err.println(e.getMessage());
        }
    }
}