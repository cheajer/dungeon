package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class DungeonApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Dungeon");

        FXMLLoader titleLoader = new FXMLLoader(getClass().getResource("TitleScreen.fxml"));
        Parent titleRoot = titleLoader.load();
        Scene titleScene = new Scene(titleRoot);
        primaryStage.setScene(titleScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
