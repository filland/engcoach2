package comn;

import comn.model.eventbus.EvenBusProvider;
import comn.model.eventbus.EventBus;
import comn.model.eventbus.EventBusDispatcher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    public static Stage primaryStage;
    private BorderPane borderPane;

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Engcoach 2");

        URL resource = getClass().getClassLoader().getResource("mainpage.fxml");

        borderPane = FXMLLoader.load(resource);

        // Show the scene containing the root layout.
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {

        EventBusDispatcher.INSTANCE.registerService(EventBus.class, EvenBusProvider.class);

        launch();
    }
}
