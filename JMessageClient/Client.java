import java.io.*;
import java.net.*;

// Client.java

public class Client {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 5000);
			System.out.println("Connected to server.");

			BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			// Create a separate thread to continuously read messages from the server
			Thread receiveThread = new Thread(() -> {
				try {
					String serverMessage;
					while ((serverMessage = in.readLine()) != null) {
						System.out.println("Received: " + serverMessage);
						System.out.print("> ");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			receiveThread.start();

			String message;
			System.out.println("Enter message to send to server (type 'exit' to quit): ");
			while (true) {
				System.out.print("> ");
				message = userInput.readLine();
				if ("exit".equalsIgnoreCase(message)) {
					break;
				}
				out.println(message);
			}

			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
