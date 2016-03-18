package team17.sheet02;

import sun.plugin.dom.exception.InvalidStateException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import java.io.*;
import java.net.Socket;
import java.security.InvalidParameterException;

public class Protocol {
    public static final int SERVICE_PORT = 12345;
    public static final String STATE_AUTHENTICATION = "Authenticate";
    public static final String STATE_READY = "Welcome";
    public static final String STATE_CALCULATION_SUCCESS = "Calculation successful";
    public static final String STATE_CALCULATION_FAIL = "Calculation failed";


    public void request(Socket client, String operation, int... args) throws Exception {
        try (PrintWriter out =
                     new PrintWriter(client.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(client.getInputStream()));) {

            String responseString = null;

            // Open connection
            responseString = in.readLine();
            if (!responseString.equals("Waiting")) throw new Exception("Protocol wurde nicht eingehalten");

            //Send request
            final JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("operation", operation);
            for (int i = 0; i < args.length; i++) {
                builder.add(String.format("param%d", i + 1), args[i]);
            }
            out.println(builder.build().toString());

            // Wait for responseString
            responseString = in.readLine();
            if (responseString == null) throw new Exception("Response is null");

            final JsonReader reader = Json.createReader(new StringReader(responseString));
            final JsonObject response = reader.readObject();

            String error = response.getString("error");
            if (error != null) throw new Exception(error);

            int result = response.getInt("result");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void replay(Socket server) {
        try (PrintWriter out = new PrintWriter(server.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        ) {
            RemoteCalculatorService srv = new RemoteCalculatorService(new Calculator());
            String request = null;
            String response = null;

            do {
                response = srv.Process(request);
                if(response == null) break;

                out.println(response);

            } while ((request = in.readLine()) != null);

            server.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String ClientProcessInput(String jsonResponse){

        JsonObject response = Json.createReader(new StringReader(jsonResponse)).readObject();

        String status = response.getString("status");
        String error = response.getString("error", null);
        int result;

        JsonObjectBuilder request = Json.createObjectBuilder();

        switch (status){

            case Protocol.STATE_AUTHENTICATION:
                request.add("status", "Authenticate");
                request.add("name", "Daniel");
                break;
            case Protocol.STATE_READY:
                request.add("operation", "lukas");
                request.add("param1", 10);
                request.add("param2", 2);
                break;
            case STATE_CALCULATION_SUCCESS:
                break;
            case STATE_CALCULATION_FAIL:
                break;
            default:
                throw new InvalidStateException("There is no action for state " + status);
        }

        return request.build().toString();
    }

    private int calculate(String jsonString) {

        int result = 0;
        int a1, a2;
        final JsonReader reader = Json.createReader(new StringReader(jsonString));
        final JsonObject jsonObject = reader.readObject();

        String operation = jsonObject.getString("operation");

        switch (operation) {
            case "+":
                a1 = jsonObject.getInt("param1");
                a2 = jsonObject.getInt("param2");
                result = a1 + a2;
                break;
            case "-":
                a1 = jsonObject.getInt("param1");
                a2 = jsonObject.getInt("param2");
                result = a1 - a2;
                break;
            case "*":
                a1 = jsonObject.getInt("param1");
                a2 = jsonObject.getInt("param2");
                result = a1 * a2;
                break;
            case "lukas":
                a1 = jsonObject.getInt("param1");
                result = getLucasNumber(a1);
                break;
            default:
                throw new InvalidParameterException(String.format("Operation %s not supported", operation));

        }
        return result;
    }

    int getLucasNumber(int i) {
        if (i < 0) {
            throw new InvalidParameterException("Can not calculate lucas number of negative numbers");
        } else if (i == 0) {
            return 2;
        } else if (i == 1) {
            return 1;
        } else {
            return getLucasNumber(i - 2) + getLucasNumber(i - 1);
        }
    }
}