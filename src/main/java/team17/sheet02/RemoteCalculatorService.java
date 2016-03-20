package team17.sheet02;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.naming.OperationNotSupportedException;
import java.io.*;
import java.net.Socket;

public class RemoteCalculatorService {

    private static final int INIT = 0;
    private static final int AUTHENTICATION = 1;
    private static final int READY = 2;

    private int state;
    private ICalculator calculator;

    public RemoteCalculatorService(ICalculator calculator) {
        state = 0;
        this.calculator = calculator;
    }

    public void handleRequest(Socket server) {
        try (PrintWriter out = new PrintWriter(server.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        ) {
            RemoteCalculatorService srv = new RemoteCalculatorService(new Calculator());
            String request = null;
            String response = null;

            do {
                System.out.println(String.format("DEBUG: " + request));
                response = srv.Process(request);
                if (response == null) break;

                out.println(response);

            } while ((request = in.readLine()) != null);

            server.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String Process(String jsonRequest) {

        String response = null;

        switch (state) {
            case INIT:
                response = getResponseString(Protocol.STATE_AUTHENTICATION_REQUIRED, null, 0);
                state = AUTHENTICATION;
                break;
            case AUTHENTICATION:
                response = getAuthenticationResponse(jsonRequest);
                break;
            case READY:
                try {
                    response = getResponseString(Protocol.STATE_CALCULATION_SUCCESS, null, calculate(jsonRequest));
                } catch (OperationNotSupportedException e) {
                    response = getResponseString(Protocol.STATE_CALCULATION_FAIL, e.getMessage(), 0);
                }
                break;
            default:
                response = getResponseString(Protocol.STATE_CALCULATION_FAIL, "Unknown state", 0);
        }

        return response;
    }

    private int calculate(String jsonRequest) throws OperationNotSupportedException {

        String operation;
        int param1;
        int param2;
        int result;

        JsonObject request = Json.createReader(new StringReader(jsonRequest)).readObject();
        operation = request.getString("operation");
        param1 = request.getInt("param1");

        switch (operation) {
            case "+":
                param2 = request.getInt("param2");
                result = calculator.Add(param1, param2);
                break;
            case "-":
                param2 = request.getInt("param2");
                result = calculator.Subtract(param1, param2);
                break;
            case "*":
                param2 = request.getInt("param2");
                result = calculator.Multiply(param1, param2);
                break;
            case "lukas":
                result = calculator.Lukas(param1);
                break;
            default:
                throw new OperationNotSupportedException("Unknown operation " + operation);
        }

        return result;
    }

    private String getResponseString(String status, String error, int result) {

        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("status", status);
        builder.add("result", result);

        if (error == null) {
            builder.addNull("error");
        } else {
            builder.add("error", error);
        }

        return builder.build().toString();
    }

    private String getAuthenticationResponse(String jsonRequest) {
        JsonObject request = Json.createReader(new StringReader(jsonRequest)).readObject();
        String username = request.getString("name");

        if (!isAuthorized(username)) return getResponseString(Protocol.STATE_AUTHENTICATION_FAIL, null, 0);

        state = READY;
        return getResponseString(Protocol.STATE_AUTHENTICATION_SUCCESS, null, 0);
    }

    private boolean isAuthorized(String username) {
        return username.equals("Daniel");
    }
}
