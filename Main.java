import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Change "AdminDashboard.fxml" to "WaiterDashboard.fxml" to test the other screen
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("WaiterDashboard.fxml")));

        primaryStage.setTitle("EL7ATY Restaurant System");
        primaryStage.setScene(new Scene(root, 1000, 700));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}