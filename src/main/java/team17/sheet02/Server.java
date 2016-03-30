package team17.sheet02;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server {
	private static final int NTHREADS = 4;
	private static final ExecutorService exec = Executors.newFixedThreadPool(NTHREADS);
	public static volatile ServerSocket serverSocket = null;
	
    public static void main(String[] args) throws InterruptedException {

        Socket clientSocket;
        ICalculator calculator = new Calculator();
        
        // attachShutdownHook();

        try {
        	serverSocket = new ServerSocket(Protocol.SERVICE_PORT);
            System.out.println("SERVER started. Listening on port " + Protocol.SERVICE_PORT);
            
            new Thread(new Shutdown()).start();

            while (true) {
            	clientSocket = serverSocket.accept();
            	exec.execute(new WrapperMultithreadedServer(clientSocket, calculator));
            }
            /*
            while((clientSocket = serverSocket.accept()) != null) {
            	exec.execute(new WrapperMultithreadedServer(clientSocket, calculator));
            }
            */
        } catch (IOException e) {
        	System.out.println("in IO exception");
            exec.shutdown();
            exec.awaitTermination(100L, TimeUnit.SECONDS);
            
            // e.printStackTrace();
        }
    }
    
    public static void stopServer() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Error in server shutdown");
            e.printStackTrace();
        }
    }
    
    private static void attachShutdownHook() {
    	Runtime.getRuntime().addShutdownHook(new Thread() {
    		public void run() {
				System.out.println("Shutdown Hook executed!");
				Server.stopServer();
				// serverSocket.close();
				
    			serverSocket = null;
    		}
    	});
    	System.out.println("Shutdown Hook attached!");
    }
}