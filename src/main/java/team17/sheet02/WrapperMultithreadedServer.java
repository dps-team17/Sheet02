package team17.sheet02;

import java.net.Socket;

public class WrapperMultithreadedServer implements Runnable {
	
	private Socket clientSocket;
	private ICalculator calculator;
	
	public WrapperMultithreadedServer (Socket client, ICalculator calculator){
		clientSocket = client;
		this.calculator = calculator;
	}

	@Override
	public void run() {
		new RemoteCalculatorService(calculator).handleRequest(clientSocket);
	}

}
