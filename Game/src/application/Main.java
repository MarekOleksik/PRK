package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {

			ViewLoader<AnchorPane, Controllers.UserController> viewLoader = new ViewLoader<>("/FXML_Files/User.fxml");
			Scene scene = new Scene(viewLoader.getLayout());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Nazwa użytkownika");
			primaryStage.setResizable(false);

			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

}