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

public class TitlescreenController {

    @FXML
    private Button playButton;

    @FXML
    private Button controlButton;

    @FXML
    private Button exitButton;

    @FXML
    private GridPane dungeonImageArea;


    @FXML
    private void initialize(){
        Image wall = new Image((new File("images/dark_brick.png")).toURI().toString());
        for (int x = 0; x < 12; x++){
            for (int y = 0; y < 12; y++){
                this.dungeonImageArea.add(new ImageView(wall), x, y);
            }
        }
        ImageView logo1 = new ImageView(new Image((new File("images/logo1.png")).toURI().toString()));
        ImageView logo2 = new ImageView(new Image((new File("images/logo2.png")).toURI().toString()));
        ImageView logo3 = new ImageView(new Image((new File("images/logo3.png")).toURI().toString()));
        ImageView logo4 = new ImageView(new Image((new File("images/logo4.png")).toURI().toString()));

        this.dungeonImageArea.add(logo1, 4, 3);
        this.dungeonImageArea.add(logo2, 5, 3);
        this.dungeonImageArea.add(logo3, 6, 3);
        this.dungeonImageArea.add(logo4, 7, 3);

        // String musicFile = "sounds/music.mp3";
        // Media sound = new Media(new File(musicFile).toURI().toString());
        // this.mediaPlayer = new MediaPlayer(sound);
        // mediaPlayer.setVolume(0.5);
        // mediaPlayer.play();
        // mediaPlayer.setOnEndOfMedia(new Runnable() {
        //     @Override
        //     public void run() {
        //         mediaPlayer.seek(Duration.ZERO);
        //         mediaPlayer.play();
        //     }
        // });

    }


    @FXML
    void handleControlButton(ActionEvent event) throws IOException{
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
        FXMLLoader controlLoader = new FXMLLoader(getClass().getResource("ControlScreen.fxml"));
        Parent controlParent = controlLoader.load();
        Scene controlScene = new Scene(controlParent);
        window.setScene(controlScene);
        window.show();
    }

    @FXML
    void handleExitButton(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void handlePlayButton(ActionEvent event) throws IOException {

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
        FXMLLoader controlLoader = new FXMLLoader(getClass().getResource("DifficultySelection.fxml"));
        Parent controlParent = controlLoader.load();
        Scene controlScene = new Scene(controlParent);
        window.setScene(controlScene);
        window.show();
        

    }
}