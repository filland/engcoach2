package comn;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    private Stage primaryStage;
    private Parent parent;

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Engcoach 2");

        URL resource = getClass().getClassLoader().getResource("mainpage.fxml");

        parent = FXMLLoader.load(resource);

        // Show the scene containing the root layout.
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch();

    }
}
