package team17.sheet02;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import java.io.StringReader;
import java.security.InvalidParameterException;

public class Protocol {

    private static final int INIT = 0;
    private static final int WAITING = 1;

    private int state;

    public Protocol() {
        state = 0;
    }

    public String processInput(String theInput) {
        String status = null;
        int result = 0;
        String error = null;


        switch (state) {
            case INIT:
                status = "Welcome. Waiting for requests...";
                state = WAITING;
                break;
            case WAITING:
                try {
                    status = "Calculation successful";
                    result = calculate(theInput);
                } catch (Exception e) {
                    error = e.getMessage();
                }
                break;
            default:
                error = "Unknown state";
        }


        final JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("status", status);
        objectBuilder.add("result", result);

        if (error == null) {
            objectBuilder.addNull("error");
        } else {
            objectBuilder.add("error", error);
        }

        return objectBuilder.build().toString();
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

    int getLucasNumber(int i){
        if(i < 0){
            throw new InvalidParameterException("Can not calculate lucas number of negative numbers");
        }
        else if( i == 0){
            return 2;
        }
        else if (i == 1){
            return 1;
        }
        else {
            return getLucasNumber(i-2) + getLucasNumber(i-1);
        }
    }
}