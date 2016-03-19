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
    public static final int SERVICE_PORT = 12346;
    public static final String STATE_AUTHENTICATION = "Authenticate";
    public static final String STATE_READY = "Welcome";
    public static final String STATE_CALCULATION_SUCCESS = "Calculation successful";
    public static final String STATE_CALCULATION_FAIL = "Calculation failed";


    public int request(Socket client, String username, String request) throws Exception {
        try (PrintWriter out =
                     new PrintWriter(client.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(client.getInputStream()));) {

            String responseString = null;

            // Open connection
            responseString = in.readLine();
            if (!responseString.equals("Waiting")) throw new Exception("Protocol wurde nicht eingehalten");

            //Send request
//            final JsonObjectBuilder builder = Json.createObjectBuilder();
//            builder.add("operation", operation);
//            for (int i = 0; i < args.length; i++) {
//                builder.add(String.format("param%d", i + 1), args[i]);
//            }
//            out.println(builder.build().toString());

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

        return 0;
    }

    public void replay(Socket server) {
        try (PrintWriter out = new PrintWriter(server.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        ) {
            RemoteCalculatorService srv = new RemoteCalculatorService(new Calculator());
            String request = null;
            String response = null;

            do {

                System.out.println(String.format("DEBUG: "+ request));
                response = srv.Process(request);
                if(response == null) break;

                out.println(response);

            } while ((request = in.readLine()) != null);

            server.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}