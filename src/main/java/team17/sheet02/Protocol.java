package team17.sheet02;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import java.io.StringReader;

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

//        final JsonReader reader = Json.createReader(new StringReader(theInput));
//        final JsonObject jsonObject = reader.readObject();


        switch (state){
            case INIT:
                status = "Welcome. Waiting for requests...";
                break;
            case WAITING:
                break;
            default:
                error = "Unknown state";
        }


        final JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("status", status);
        objectBuilder.add("result", result);

        if(error == null){
            objectBuilder.addNull("error");
        }
        else {
            objectBuilder.add("error", error);
        }

        return objectBuilder.build().toString();
    }
}