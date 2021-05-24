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

public class ControlController {

    @FXML
    private GridPane backgroundPane;

    @FXML
    private Button backButton;

    @FXML
    private void initialize(){
        Image controls = new Image((new File("images/controls.png")).toURI().toString());
        this.backgroundPane.add(new ImageView(controls), 0, 1);
    }



    @FXML
    void handleBackButton(ActionEvent event) throws IOException {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
        FXMLLoader titleLoader = new FXMLLoader(getClass().getResource("TitleScreen.fxml"));
        Parent titleRoot = titleLoader.load();
        Scene titleScene = new Scene(titleRoot);
        window.setScene(titleScene);
        window.show();
    }

}
