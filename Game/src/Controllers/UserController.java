package Controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class UserController implements Initializable {

	@FXML
	private TextField userTextField;

	@FXML
	public ImageView picIDImageView;

	@FXML
	private TextField hostTextField;

	@FXML
	private Button anulujButton;

	@FXML
	private Button okButton;

	public static UserController fxmlController;
	public String picID = "";

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		fxmlController = this;
		picID = "no-avatar.png";
		File file = new File("res/avatars/" + picID);
		Image image = new Image(file.toURI().toString());
		picIDImageView.setImage(image);
	}

	@FXML
	private void anulujButtonOnActrion() {
		Stage stage = (Stage) anulujButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void okButtonOnActrion() {

		// przypisanie pierwszego okna
		Stage primaryStage = (Stage) okButton.getScene().getWindow();

		try {

			application.ViewLoader<BorderPane, GameController> viewLoader = new application.ViewLoader<>("/FXML_Files/Game.fxml");
			if (!userTextField.getText().equals("") && !userTextField.getText().equals("Gracz1") && !userTextField.getText().equals("Gracz2")) {
				viewLoader.getController().setUserName(userTextField.getText());
			} else {
				viewLoader.getController().setUserName("Gość");
			}
			viewLoader.getController().setPicID(picID);
			viewLoader.getController().setHost(hostTextField.getText());
			viewLoader.getController().run();

			
			BorderPane borderPane = viewLoader.getLayout();
			Scene scene = new Scene(borderPane);

			
			//FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Files/FXMLCheckersBoard.fxml"));
			//Parent board = loader.load();
			GameController controller = viewLoader.getController();

			//borderPane.setCenter(board);
			
			// Initialize game board
			controller.ready(scene);
			

			Stage stage = new Stage();
			stage.setResizable(false);
			stage.setScene(scene);
			stage.setTitle("WARCABY");
			stage.setOnHiding(e -> {
				try {
					Stage_Hiding(e, viewLoader.getController());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
			stage.show();

			// ukrycie pierwszego okna
			primaryStage.hide();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void picID_MousePressed(MouseEvent event) {
		try {
			application.ViewLoader<AnchorPane, AvatarController> viewLoader = new application.ViewLoader<>("/FXML_Files/Avatar.fxml");
			AnchorPane anchorPane = viewLoader.getLayout();
			Stage stage = new Stage();
			Scene scene = new Scene(anchorPane);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Wybierz Avatar");
			stage.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Object Stage_Hiding(WindowEvent e, GameController controller) throws IOException {
		controller.closeSocket();
		return null;
	}

	@FXML
	private void userTextField_KeyPressed(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			okButtonOnActrion();
		}
	}

	@FXML
	private void hostTextField_KeyPressed(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			okButtonOnActrion();
		}
	}

}