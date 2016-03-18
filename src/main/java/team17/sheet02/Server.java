package team17.sheet02;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {

        Socket clientSocket;

        try (ServerSocket serverSocket = new ServerSocket(Protocol.PORT)) {

            while ((clientSocket = serverSocket.accept()) != null) {
                Protocol p = new Protocol();
                p.replay(clientSocket);
            }
        }


    }
}