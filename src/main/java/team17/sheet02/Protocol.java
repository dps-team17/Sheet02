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
    public static final String STATE_AUTHENTICATION_REQUIRED = "Authenticate";
    public static final String STATE_AUTHENTICATION_FAIL = "Access denied";
    public static final String STATE_AUTHENTICATION_SUCCESS = "Welcome";
    public static final String STATE_CALCULATION_SUCCESS = "Calculation successful";
    public static final String STATE_CALCULATION_FAIL = "Calculation failed";
}