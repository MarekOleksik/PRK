package Controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import application.CheckersBoard;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebView;

public class GameController {
	@FXML
	TextField messageTextField;
	@FXML
	Label welcomeLabel;
	@FXML
	WebView webViewMessages;
	@FXML
	Circle circleImage;
	@FXML
	ImageView sendImageView;
	@FXML
	ListView<String> userListView;
	@FXML
	Circle player1Image;
	@FXML
	Circle player2Image;
	@FXML
	CheckBox player1CheckBox;
	@FXML
	CheckBox player2CheckBox;
	@FXML
	Button testButton;
	@FXML
	Button testButton1;
	@FXML
	Button testButton2;

	@FXML
	private AnchorPane anchorPane;

	private double boardHeight;
	private double boardWidth;

	private String picID = "";
	// nazwa obrazka wybrana przez użytkownika
	private String userName = "";
	// nazwa wybrana przez użytkownika
	private int UID;
	// identyfikator użytkownika nadany przez serwer
	private String senderName;
	// nazwa nadawcy wiadomości
	private int senderUID;
	// identyfikator nadawcy
	private String senderPicID;
	// nazwa obrazka nadawcy
	private String host;
	// adres serwera
	private final int PORT = 9001;
	// numer portu
	private Socket socket;
	// obiekt gniazda
	private BufferedReader inputBufferedReader;
	// bufor wejściowy (dane odebrane z serwera)
	public static PrintWriter outputPrintWriter;
	// bufor wyjściowy (dane do wysłania)

	Document messagesLayout;

	@FXML
	private void initialize() {

		Image myImage = new Image(new File("res/avatars/bot.png").toURI().toString());
		ImagePattern pattern = new ImagePattern(myImage);
		player1Image.setFill(pattern);
		player2Image.setFill(pattern);

		String welcome = "Witaj w grze. Wybierz wolne miejsce.";
		messagesLayout = Jsoup.parse("<html><head><meta charset='UTF-8'>"
				+ "</head><body><ul><li class=\"welcome\"><div class=\"message\"><div class=\"content\">" + welcome
				+ "</div></div></li></ul></body></html>", "UTF-16", Parser.xmlParser());
		webViewMessages.getEngine().loadContent(messagesLayout.html());
		webViewMessages.getEngine()
				.setUserStyleSheetLocation(getClass().getResource("/application/application.css").toString());
	}

	public void setUserName(String userName) {
		this.userName = userName;
		welcomeLabel.setText("Witaj " + this.userName + "!");
	}

	public void setPicID(String picID) {
		this.picID = picID;
		Image myImage = new Image(new File("res/avatars/" + this.picID).toURI().toString());
		ImagePattern pattern = new ImagePattern(myImage);
		circleImage.setFill(pattern);
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUserName() {
		return userName;
	}

	public void run() throws UnknownHostException, IOException {
		socket = new Socket(host, PORT);
		inputBufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		outputPrintWriter = new PrintWriter(socket.getOutputStream(), true);
		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() throws IOException {
				while (true) {
					String msg = inputBufferedReader.readLine();
					System.out.println(msg);
					if (msg.startsWith("RDY")) {
						outputPrintWriter.println(userName);
						outputPrintWriter.println(picID);
					} else if (msg.startsWith("UID")) {
						UID = Integer.parseInt(msg.substring(3));
					} else if (msg.startsWith("MSG")) {
						addMessage(toHTML(decodeUID(msg)));
					} else if (msg.startsWith("[")) {
						updateUserList(msg);
					} else if (msg.startsWith("SIT")) {
						updateUserSit(msg);
					} else if (msg.startsWith("STAND")) {
						updateUserStand(msg);
					} else if (msg.startsWith("BRD")) {
						setBoardString(msg);
					}
				}
			}
		};
		new Thread(task).start();
	}

	public void closeSocket() throws IOException {
		this.socket.close();

	}

	private void setBoardString(String msg) {
		// CheckersBoard.boardString.clear();
		msg = msg.substring(3);
		String[] board = msg.split(",");
		int i = 0;
		for (int col = 0; col < 8; col++) {
			for (int row = 0; row < 8; row++) {

				CheckersBoard.boardString[row][col] = board[i];
				i++;

			}
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				boardWidth = 600;
				boardHeight = 600;

				// Generate new gameboard
				CheckersBoard checkerboard = new CheckersBoard(boardWidth, boardHeight);
				AnchorPane gameboard = checkerboard.fill();
			anchorPane.getChildren().clear();
				anchorPane.getChildren().addAll(gameboard);
			}
		});
	}

	private void updateUserList(String msg) {
		msg = msg.substring(1);
		msg = msg.substring(0, msg.length() - 1);
		String[] user = msg.split(", ");

		// Aby nie wywoływać Not on FX
		// application thread;
		// currentThread=Thread-5
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				userListView.getItems().clear();
				for (int i = 0; i < user.length; i++) {
					String[] param = user[i].split("\t");
					userListView.getItems().add(param[1]);
				}
			}
		});

	}

	private void updateUserSit(String msg) {
		msg = msg.substring(3);
		String[] param = msg.split("\t");

		if (param[3].equals("SIT1") && !param[0].equals(String.valueOf(UID))) {

			Platform.runLater(new Runnable() { // Aby nie wywoływać Not on FX
												// application thread;
												// currentThread=Thread-5
				@Override
				public void run() {
					player1CheckBox.setText(param[1]);
					player1CheckBox.setDisable(true);
				}
			});
			Image myImage = new Image(new File("res/avatars/" + param[2]).toURI().toString());
			ImagePattern pattern = new ImagePattern(myImage);
			player1Image.setFill(pattern);
		}
		if (param[3].equals("SIT2") && !param[0].equals(String.valueOf(UID))) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					player2CheckBox.setText(param[1]);
					player2CheckBox.setDisable(true);
				}
			});

			Image myImage = new Image(new File("res/avatars/" + param[2]).toURI().toString());
			ImagePattern pattern = new ImagePattern(myImage);
			player2Image.setFill(pattern);
		}

	}

	private void updateUserStand(String msg) {
		msg = msg.substring(5);
		String[] param = msg.split("\t");

		if (param[3].equals("STAND1") && !param[0].equals(String.valueOf(UID))) {

			Platform.runLater(new Runnable() { // Aby nie wywoływać Not on FX
												// application thread;
												// currentThread=Thread-5
				@Override
				public void run() {
					player1CheckBox.setText("Gracz1");
					if (!player2CheckBox.getText().equals(userName)) { // Czy
																		// gracz
																		// nie
																		// siedzi
																		// na
																		// drugim
																		// miejscu
						player1CheckBox.setDisable(false);
					}
				}
			});
			Image myImage = new Image(new File("res/avatars/bot.png").toURI().toString());
			ImagePattern pattern = new ImagePattern(myImage);
			player1Image.setFill(pattern);
		}
		if (param[3].equals("STAND2") && !param[0].equals(String.valueOf(UID))) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					player2CheckBox.setText("Gracz2");
					if (!player1CheckBox.getText().equals(userName)) { // Czy
																		// gracz
																		// nie
																		// siedzi
																		// na
																		// drugim
																		// miejscu
						player2CheckBox.setDisable(false);
					}
				}
			});

			Image myImage = new Image(new File("res/avatars/bot.png").toURI().toString());
			ImagePattern pattern = new ImagePattern(myImage);
			player2Image.setFill(pattern);
		}

	}

	@FXML
	private void sendImageView_MouseReleased() {
		if (messageTextField.getLength() == 0)
			return;
		outputPrintWriter.println("MSG" + messageTextField.getText());
		messageTextField.clear();
	}

	@FXML
	private void messageTextField_KeyPressed(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			sendImageView_MouseReleased();
		}
	}

	private void addMessage(Element message) {
		Element wrapper = messagesLayout.getElementsByTag("ul").first();
		wrapper.appendChild(message);
		Platform.runLater(new Runnable() {
			public void run() {
				webViewMessages.getEngine().loadContent(messagesLayout.html());
			}
		});
	}

	private Element toHTML(String message) {
		System.out.println("toHTML:" + message);
		String msgClass = (UID == senderUID) ? "request" : "response";
		Element wrapper = new Element("li").attr("class", msgClass);
		Element image = new Element("img").attr("class", "avatar").attr("src",
				new File("res/avatars/" + picID).toURI().toString());
		if (UID != senderUID) {
			image.attr("src", new File("res/avatars/" + senderPicID).toURI().toString());
			new Element("span").attr("class", "author").append(senderName).appendTo(wrapper);
		}
		image.appendTo(wrapper);
		Element message_div = new Element("div").attr("class", "message").appendTo(wrapper);
		new Element("div").attr("class", "content").append(message).appendTo(message_div);
		return wrapper;
	}

	private String decodeUID(String msg) {
		msg = msg.substring(3);
		String[] param = msg.split("\t");
		this.senderUID = Integer.parseInt(param[0]);
		this.senderName = param[1];
		this.senderPicID = param[2];
		return msg.substring(param[0].length() + param[1].length() + param[2].length() + 3);
	}

	@FXML
	void player1CheckBox_OnActrion(ActionEvent event) {
		if (player1CheckBox.isSelected()) {
			player1CheckBox.setText(userName);
			player2CheckBox.setDisable(true);
			outputPrintWriter.println("SIT" + 1);
			Image myImage = new Image(new File("res/avatars/" + picID).toURI().toString());
			ImagePattern pattern = new ImagePattern(myImage);
			player1Image.setFill(pattern);
		} else {
			player1CheckBox.setText("Gracz1");
			if (player2CheckBox.getText().equals("Gracz2")) { // Czy drugie
																// miejsce jest
																// puste
				player2CheckBox.setDisable(false);
			}
			outputPrintWriter.println("STAND" + 1);
			Image myImage = new Image(new File("res/avatars/bot.png").toURI().toString());
			ImagePattern pattern = new ImagePattern(myImage);
			player1Image.setFill(pattern);
		}

	}

	@FXML
	void player2CheckBox_OnActrion(ActionEvent event) {
		if (player2CheckBox.isSelected()) {
			player2CheckBox.setText(userName);
			player1CheckBox.setDisable(true);
			outputPrintWriter.println("SIT" + 2);
			Image myImage = new Image(new File("res/avatars/" + picID).toURI().toString());
			ImagePattern pattern = new ImagePattern(myImage);
			player2Image.setFill(pattern);
		} else {
			player2CheckBox.setText("Gracz2");
			if (player1CheckBox.getText().equals("Gracz1")) { // Czy drugie
																// miejsce jest
																// puste
				player1CheckBox.setDisable(false);
			}
			outputPrintWriter.println("STAND" + 2);
			Image myImage = new Image(new File("res/avatars/bot.png").toURI().toString());
			ImagePattern pattern = new ImagePattern(myImage);
			player2Image.setFill(pattern);
		}
	}

	@FXML
	void testButton_Click(ActionEvent event) {
		anchorPane.setDisable(false);
		setGameBoard();
	}

	@FXML
	void testButton2_Click(ActionEvent event) {

	}

	@FXML
	void testButton3_Click(ActionEvent event) {

	}

	public static String convertBoardStringToString() {
		String temp = "";
		for (int col = 0; col < 8; col++) {
			for (int row = 0; row < 8; row++) {
				temp = temp + CheckersBoard.boardString[row][col] + ",";
			}
		}
		temp = temp + "END";
		return temp;
	}

	public void ready(Scene scene) {
		// Create change listener for width/height
		ChangeListener<Number> sizeChangeListener = (ObservableValue<? extends Number> observable, Number oldValue,
				final Number newValue) -> {
			setGameBoard();
		};

		// Add change listeners to scene
		scene.widthProperty().addListener(sizeChangeListener);
		scene.heightProperty().addListener(sizeChangeListener);

		setGameBoard();
	}

	public void setGameBoard() {
		boardWidth = 600;
		boardHeight = 600;

		// Generate new gameboard
		CheckersBoard checkerboard = new CheckersBoard(boardWidth, boardHeight);
		AnchorPane gameboard = checkerboard.build();

		// Clear previous gameboard
		anchorPane.getChildren().clear();

		// Set new gameboard configuration
		anchorPane.getChildren().addAll(gameboard);
	}

}
