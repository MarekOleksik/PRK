package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.Random;

public class SocketServer {

	private static final int PORT = 9001;
	private static ArrayList<String> users = new ArrayList<String>();
	private static String player1 = "";
	private static String player2 = "";
	private static String board = "";
	private static ConcurrentHashMap<Integer, PrintWriter> clients = new ConcurrentHashMap<Integer, PrintWriter>();

	public static void main(String[] args) throws Exception {

		// adres IP w sieci lokalnej
		System.out.println("IP: " + InetAddress.getLocalHost().getHostAddress());

		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			System.out.println("The server is running.");
			while (true) {
				Socket socket = serverSocket.accept();
				RequestHandler requestHandler = new RequestHandler(socket);
				requestHandler.start();
			}
		}

	}

	private static class RequestHandler extends Thread {
		private int id;
		private String name;
		private String picID;
		private Socket socket;
		private BufferedReader inputBufferedReader;
		private PrintWriter outputPrintWriter;

		public RequestHandler(Socket socket) {
			this.socket = socket;
			id = new Random().nextInt(Integer.MAX_VALUE);
		}

		@Override
		public void run() {
			try {
				inputBufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				outputPrintWriter = new PrintWriter(socket.getOutputStream(), true);
				outputPrintWriter.println("RDY");

				name = inputBufferedReader.readLine();
				users.add(id + "\t" + name);
				picID = inputBufferedReader.readLine();
				clients.putIfAbsent(id, outputPrintWriter);
				System.out.println("Name: " + name + "; Pic: " + picID);

				outputPrintWriter.println("UID" + String.valueOf(id));
				System.out.println("UID" + String.valueOf(id));

				sendUsers();
				outputPrintWriter.println(player1);
				outputPrintWriter.println(player2);
				if (!board.equals("")){
				outputPrintWriter.println(board);
				}
				// Wysłanie kto przyszedł
				sendToAll("MSG" + 000000000 + "\t" + "System" + "\t" + "bot.png" + "\t" + "Przychodzi: " + name);

				while (true) {
					String clientMsg = inputBufferedReader.readLine();
					if (clientMsg == null) {
						return;
					}

					if (clientMsg.startsWith("MSG")) {
						clientMsg = clientMsg.substring(3);
						// Wysłanie wiadomości (MSG)
						sendToAll("MSG" + id + "\t" + name + "\t" + picID + "\t" + clientMsg);
					} else if (clientMsg.startsWith("SIT1")) {
						sendToAll("SIT" + id + "\t" + name + "\t" + picID + "\t" + clientMsg);
						player1 = "SIT" + id + "\t" + name + "\t" + picID + "\t" + clientMsg;
					} else if (clientMsg.startsWith("SIT2")) {
						sendToAll("SIT" + id + "\t" + name + "\t" + picID + "\t" + clientMsg);
						player2 = "SIT" + id + "\t" + name + "\t" + picID + "\t" + clientMsg;
					} else if (clientMsg.startsWith("STAND1")) {
						sendToAll("STAND" + id + "\t" + name + "\t" + picID + "\t" + clientMsg);
						player1 = "";
					} else if (clientMsg.startsWith("STAND2")) {
						sendToAll("STAND" + id + "\t" + name + "\t" + picID + "\t" + clientMsg);
						player2 = "";
					} else if (clientMsg.startsWith("BRD")) {
						sendToAll(clientMsg);
						board=clientMsg;
					}

				}
			} catch (IOException e) {
				System.out.println("Client reset connection");
			} finally {
				// Czy gracz siedział przy stole? (jak tak to zwalnia miejsce).
				ifPlayerWasSitting();
				// Usunięcie z listy obecnych
				users.remove(id + "\t" + name);
				// Wysłanie aktualnej listy obecnych
				sendUsers();
				// Wysłanie kto odszedł
				sendToAll("MSG" + 000000000 + "\t" + "System" + "\t" + "bot.png" + "\t" + "Odchodzi: " + name);
				// Usunięcie ID
				clients.remove(id);
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		private void ifPlayerWasSitting() {
			if (!player1.equals("")) {
				String temp = player1.substring(3);
				String[] param = temp.split("\t");
				if (param[0].equals(String.valueOf(id))) {
					sendToAll("STAND" + id + "\t" + name + "\t" + picID + "\t" + "STAND1");
					player1 = "";
					System.out.println("aaa " + player1);
				}

			}
			if (!player2.equals("")) {
				String temp = player2.substring(3);
				String[] param = temp.split("\t");
				if (param[0].equals(String.valueOf(id))) {
					sendToAll("STAND" + id + "\t" + name + "\t" + picID + "\t" + "STAND2");
					player2 = "";
				}

			}
		}

		private void sendUsers() {
			for (ConcurrentHashMap.Entry<Integer, PrintWriter> entry : clients.entrySet()) {
				PrintWriter printWriter = entry.getValue();
				printWriter.println(users);
				System.out.println(users); // do skasowania
			}
		}

		private void sendToAll(String serverMsg) {
			for (ConcurrentHashMap.Entry<Integer, PrintWriter> entry : clients.entrySet()) {
				PrintWriter printWriter = entry.getValue();
				printWriter.println(serverMsg); // wysyłanie komunikatu do
												// klienta
			}
		}
	}
}