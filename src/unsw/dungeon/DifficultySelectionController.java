package unsw.dungeon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class DifficultySelectionController {

    @FXML
    private GridPane backgroundPane;

    @FXML
    private Button normalButton;

    @FXML
    private Button hardButton;

    @FXML
    private void initialize() {
        Image wall = new Image((new File("images/dark_brick.png")).toURI().toString());
        for (int x = 0; x < 12; x++) {
            for (int y = 0; y < 12; y++) {
                this.backgroundPane.add(new ImageView(wall), x, y);
            }
        }
    }

    @FXML
    void handleHardDifficulty(ActionEvent event){
        DungeonController.setDifficulty((double) 0.175);
        this.loadLevel(event);
    }

    @FXML
    void handleNormalDifficulty(ActionEvent event){
        DungeonController.setDifficulty((double) 0.25);
        this.loadLevel(event);
    }

    private void loadLevel(ActionEvent event){
        try {
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.close();
            FXMLLoader controlLoader = new FXMLLoader(getClass().getResource("LevelSelection.fxml"));
            Parent controlParent = controlLoader.load();
            Scene controlScene = new Scene(controlParent);
            window.setScene(controlScene);
            window.show();
        }
        catch (IOException e) {
            System.out.println("IOException in loading difficulty selection");
        }
    }

}

