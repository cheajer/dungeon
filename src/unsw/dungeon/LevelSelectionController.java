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

public class LevelSelectionController {

    @FXML
    private GridPane backgroundPane;

    @FXML
    private Button MazeButton;

    @FXML
    private Button BouldersButton;

    @FXML
    private Button AdvancedButton;

    @FXML
    private Button portalButton;

    @FXML
    private Button complexButton;

    @FXML
    private Button lakeButton;

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
    void handleAdvancedButton(ActionEvent event) {
        loadDungeon(event, "advanced.json");
    }

    @FXML
    void handleBouldersButton(ActionEvent event) {
        loadDungeon(event, "boulders.json");
    }

    @FXML
    void handleMazeButton(ActionEvent event) {
        loadDungeon(event, "maze.json");
    }

    @FXML
    void handleComplexButton(ActionEvent event) {
        loadDungeon(event, "complex.json");
    }

    @FXML
    void handleLakeButton(ActionEvent event) {
        loadDungeon(event, "lake.json");
    }

    @FXML
    void handlePortalButton(ActionEvent event) {
        loadDungeon(event, "portals.json");
    }

    private void loadDungeon(ActionEvent event, String dungeon) {
        try {
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.close();

            DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(dungeon);
            DungeonController controller = dungeonLoader.loadController();

            FXMLLoader dungeonViewLoader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
            dungeonViewLoader.setController(controller);
            Parent dungeonViewParent = dungeonViewLoader.load();
            Scene dungeonViewScene = new Scene(dungeonViewParent);

            dungeonViewParent.requestFocus();

            window.setHeight((controller.getDungeonHeight() + 3) * 32+6);
            window.setWidth(controller.getDungeonWidth() * 32+16);
            window.setScene(dungeonViewScene);
            window.show();
        }
        catch (IOException e) {
            System.out.println("IOException in loading dungeon selection");
        }
    }

}


