package team17.sheet02;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.naming.OperationNotSupportedException;
import java.io.StringReader;

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

    public String Process(String jsonRequest) {

        String status = null;
        int result = 0;
        String error = null;

        switch (state) {
            case INIT:
                status = Protocol.STATE_AUTHENTICATION;
                state = AUTHENTICATION;
                break;
            case AUTHENTICATION:
                status = Protocol.STATE_READY;
                state = READY;
                break;
            case READY:
                try {
                    status = Protocol.STATE_CALCULATION_SUCCESS;
                    result = calculate(jsonRequest);
                } catch (OperationNotSupportedException e) {
                    status = Protocol.STATE_CALCULATION_FAIL;
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

    private boolean isAuthorized(String username){
        return username.equals("Daniel");
    }
}
