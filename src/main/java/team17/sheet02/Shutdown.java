
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Shutdown extends Thread {

	public void run() {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
			br.readLine();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		Server.stopServer();
	}
	
	public static void main(String[] args) {
	}

}
