import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

// Server.java

public class Server {
	private static List<ClientHandler> clients = new ArrayList<>();

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(5000);
			System.out.println("Server started at address: " + serverSocket.getInetAddress());
			System.out.println("Waiting for clients...");
			try {
				while (true) {
					Socket clientSocket = serverSocket.accept();
					System.out.println("Client connected: " + clientSocket);

					// Create a new thread to handle the client connection
					ClientHandler clientThread = new ClientHandler(clientSocket);
					clients.add(clientThread);
					clientThread.start();
				}
			} finally {
				serverSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static class ClientHandler extends Thread {
		private Socket clientSocket;
		private PrintWriter out;

		public ClientHandler(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}

		@Override
		public void run() {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				out = new PrintWriter(clientSocket.getOutputStream(), true);

				String message;
				while ((message = in.readLine()) != null) {
					System.out.println("Received from client: " + message);
					broadcastMessage(message, this);
				}

				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private synchronized void broadcastMessage(String message, ClientHandler sender) {
			for (ClientHandler client : clients) {
				if (client != sender) {
					client.out.println(message);
				}
			}
		}
	}
}
