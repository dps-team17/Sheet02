package team17.sheet02;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {

        Socket clientSocket;

        try (ServerSocket serverSocket = new ServerSocket(Protocol.SERVICE_PORT)) {

            System.out.println("Server started. Listening on port " + Protocol.SERVICE_PORT);

            while ((clientSocket = serverSocket.accept()) != null) {

                new Protocol().replay(clientSocket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}