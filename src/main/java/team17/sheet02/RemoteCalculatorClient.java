package team17.sheet02;

import sun.plugin.dom.exception.InvalidStateException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.naming.AuthenticationException;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class RemoteCalculatorClient implements ICalculator {

    private String host;
    private String username;
    private String calculationRequest;
    private int calculationResult;


    public RemoteCalculatorClient(String host, String username) {
        this.host = host;
        this.username = username;
    }

    @Override
    public int Add(int a, int b) throws CalculationException {
        CreateRequestString("+", a, b);
        CalculateRemote();
        return calculationResult;
    }

    @Override
    public int Subtract(int a, int b) throws CalculationException {
        CreateRequestString("-", a, b);
        CalculateRemote();
        return calculationResult;
    }

    @Override
    public int Multiply(int a, int b) throws CalculationException {
        CreateRequestString("*", a, b);
        CalculateRemote();
        return calculationResult;
    }

    @Override
    public int Lukas(int a) throws CalculationException {
        CreateRequestString("lukas", a);
        CalculateRemote();
        return calculationResult;
    }

    private void CreateRequestString(String operation, int... params) {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("operation", operation);
        for (int i = 0; i < params.length; i++) {
            builder.add("param" + (i + 1), params[i]);
        }

        calculationRequest = builder.build().toString();
    }

    private void CalculateRemote() throws CalculationException {

        try (
                Socket socket = new Socket(host, Protocol.SERVICE_PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {

            String jsonResponse;
            String nextRequest;

            while ((jsonResponse = in.readLine()) != null) {

                System.out.println("DEBUG: " + jsonResponse);
                nextRequest = ProcessResponse(jsonResponse);

                if (nextRequest == null) break;
                out.println(nextRequest);
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String ProcessResponse(String jsonResponse) throws CalculationException {

        JsonObject response = Json.createReader(new StringReader(jsonResponse)).readObject();

        String status = response.getString("status");
        String error = response.getString("error", null);

        String request = null;

        switch (status) {

            case Protocol.STATE_AUTHENTICATION_REQUIRED:
                request = getAuthenticationRequest();
                break;
            case Protocol.STATE_AUTHENTICATION_SUCCESS:
                request = calculationRequest;
                break;
            case Protocol.STATE_AUTHENTICATION_FAIL:
                throw new CalculationException("Access denied on RemoteCalculationService for username " + username);
            case Protocol.STATE_CALCULATION_SUCCESS:
                calculationResult = response.getInt("result");
                break;
            case Protocol.STATE_CALCULATION_FAIL:
                break;
            default:
                throw new InvalidStateException("There is no action for state " + status);
        }

        return request;
    }

    private String getAuthenticationRequest(){
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("status", "Authenticate");
        builder.add("name", username);

        return builder.build().toString();
    }

}
