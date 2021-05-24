package unsw.dungeon;

import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.io.IOException;


import java.io.File;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    @FXML
    private GridPane squares;

    private List<ImageView> initialEntities;

    private Player player;

    private Dungeon dungeon;

    private MediaPlayer mediaPlayer;

    private Timeline gameTimeLine, enemyTimeLine;

    static double difficulty;


    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.initialEntities = new ArrayList<>(initialEntities);
    }

    @FXML
    public void initialize() {
        Image ground = new Image((new File("images/dirt_0_new.png")).toURI().toString());
        Image inventory = new Image((new File("images/inventory.png")).toURI().toString());
        Image actionbar = new Image((new File("images/dark_brick.png")).toURI().toString()); 
        Image swordslot = new Image((new File("images/swordslot.png")).toURI().toString());
        Image potionslot = new Image((new File("images/potionslot.png")).toURI().toString());
        Image logo1 = new Image((new File("images/logo1.png")).toURI().toString());
        Image logo2 = new Image((new File("images/logo2.png")).toURI().toString());
        Image logo3 = new Image((new File("images/logo3.png")).toURI().toString());
        Image logo4 = new Image((new File("images/logo4.png")).toURI().toString());
        Image water = new Image((new File("images/waterold.png")).toURI().toString());
        this.prev = new ImageView(new Image((new File("images/lifering_player.png")).toURI().toString()));


        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }
        for (Entity ent : dungeon.getEntities()) {
            if (ent instanceof Water) {
                squares.add(new ImageView(water), ent.getX(), ent.getY());
            }
        }



        for (int x=0; x < dungeon.getWidth(); x++) {
            for (int y=dungeon.getHeight()+1; y < dungeon.getHeight()+3; y++) {
                squares.add(new ImageView(actionbar), x, y);
            }
        }

        if (dungeon.getWidth() < 10 ) {
            squares.add(new ImageView(logo1), 0, dungeon.getHeight()+1);
            squares.add(new ImageView(logo2), 1, dungeon.getHeight()+1);
            squares.add(new ImageView(logo3), 2, dungeon.getHeight()+1);
            squares.add(new ImageView(logo4), 3, dungeon.getHeight()+1);
        }

        for (int x=1; x < 4 && dungeon.getWidth() > 10; x++) {
            for (int y=dungeon.getHeight()+1; y < dungeon.getHeight()+3; y++) {
                squares.add(new ImageView(inventory), x, y);
            }
        }

        if (dungeon.getWidth() > 10) {
            double logoPos = dungeon.getWidth()/2;

            squares.add(new ImageView(swordslot), (int)Math.floor(logoPos)-2, dungeon.getHeight()+2);
            squares.add(new ImageView(potionslot), (int)Math.floor(logoPos), dungeon.getHeight()+2);
            squares.add(new ImageView(logo1), (int)Math.floor(logoPos)-2, dungeon.getHeight()+1);
            squares.add(new ImageView(logo2), (int)Math.floor(logoPos)-1, dungeon.getHeight()+1);
            squares.add(new ImageView(logo3), (int)Math.floor(logoPos), dungeon.getHeight()+1);
            squares.add(new ImageView(logo4), (int)Math.floor(logoPos)+1, dungeon.getHeight()+1);
            
        }
        placeObjectiveTile();

        for (ImageView entity : initialEntities)
            squares.getChildren().add(entity);

        String musicFile = "sounds/music.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        this.mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0.5);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
            }
        });


        this.gameTimeLine = new Timeline();
        this.enemyTimeLine = new Timeline();
        this.gameTimeLine.setCycleCount(Timeline.INDEFINITE);
        this.enemyTimeLine.setCycleCount(Timeline.INDEFINITE);

        KeyFrame gameTick = new KeyFrame(Duration.ONE, e -> this.tick());
        KeyFrame enemyTick = new KeyFrame(Duration.seconds(difficulty), e -> this.enemyTick());

        this.gameTimeLine.getKeyFrames().add(gameTick);
        this.enemyTimeLine.getKeyFrames().add(enemyTick);
        this.gameTimeLine.play();
        this.enemyTimeLine.play();
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
        case UP:
            player.moveUp();
            break;
        case DOWN:
            player.moveDown();
            break;
        case LEFT:
            player.moveLeft();
            break;
        case RIGHT:
            player.moveRight();
            break;
        case SEMICOLON:
            if (this.mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING){
                mediaPlayer.pause();
            }
            else{
                mediaPlayer.play();
            }
            break;
        case EQUALS:
            if (mediaPlayer.getVolume() < 1)
                mediaPlayer.setVolume(mediaPlayer.getVolume() + 0.1);
            break;
        case MINUS:
            if (mediaPlayer.getVolume() > 0.1)
                mediaPlayer.setVolume(mediaPlayer.getVolume() - 0.1);
            break;
        case F1:
            if (this.enemyTimeLine.getStatus() == Animation.Status.RUNNING)
                this.enemyTimeLine.stop();
            else
                this.enemyTimeLine.play();
            break;
        case F2:
            try {
                this.mediaPlayer.pause();
                this.mediaPlayer=null;
                System.gc();
                FXMLLoader titleLoader = new FXMLLoader(getClass().getResource("TitleScreen.fxml"));
                Parent titleRoot = titleLoader.load();
                Scene titleScene = new Scene(titleRoot);
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setHeight(300);
                window.setWidth(325);
                window.setScene(titleScene);
                window.show();
            }
            catch (IOException e) {}
            break;
        case F3:
            System.exit(0);
            break;
            
        default:
            break;
        }
    }

    public void placeObjectiveTile() {
        Image and = new Image((new File("images/objective_and.png")).toURI().toString());
        Image or = new Image((new File("images/objective_or.png")).toURI().toString());
        Image andor = new Image((new File("images/objective_andor.png")).toURI().toString());


        Image objective = new Image((new File("images/objective_tile.png")).toURI().toString());

        if (dungeon.getWidth() > 10) {
            squares.add(new ImageView(objective), dungeon.getWidth()-4, dungeon.getHeight()+1);
            squares.add(new ImageView(objective), dungeon.getWidth()-5, dungeon.getHeight()+1);
            squares.add(new ImageView(objective), dungeon.getWidth()-4, dungeon.getHeight()+2);
            squares.add(new ImageView(objective), dungeon.getWidth()-5, dungeon.getHeight()+2);
        }
        if (dungeon.getCondition().equals("AND")) {
            squares.add(new ImageView(and), dungeon.getWidth()-3, dungeon.getHeight()+1);
            for (Objective obj : dungeon.getObjectives()) {
                placeObjectiveImage(obj, false);
            }
        } else if (dungeon.getCondition().equals("OR")) {
            squares.add(new ImageView(or), dungeon.getWidth()-3, dungeon.getHeight()+1);
            for (Objective obj : dungeon.getObjectives()) {
                placeObjectiveImage(obj, false);
            }
        } else {
            squares.add(new ImageView(andor), dungeon.getWidth()-3, dungeon.getHeight()+1);
            Objective mainGoal = dungeon.getObjectives().get(0);
            placeObjectiveImage(mainGoal, true);
            

            List<Objective> subGoals = new ArrayList<>();



            for (int i=1; i < dungeon.getObjectives().size(); i++) {
                subGoals.add(dungeon.getObjectives().get(i));
            }
            for (Objective obj : subGoals) {
                placeObjectiveImage(obj, false);
            }

        }
    }

    public void placeObjectiveImage(Objective obj, boolean andor) {
        Image switchObj = new Image((new File("images/pressure_plate.png")).toURI().toString());
        Image treasureObj = new Image((new File("images/gold_pile.png")).toURI().toString());
        Image enemyObj = new Image((new File("images/deep_elf_master_archer.png")).toURI().toString());
        Image exitObj = new Image((new File("images/exit.png")).toURI().toString());
        if (andor == false) {
            if (obj instanceof EnemyObjective)
                squares.add(new ImageView(enemyObj), dungeon.getWidth()-4, dungeon.getHeight()+1);
            if (obj instanceof TreasureObjective)
                squares.add(new ImageView(treasureObj), dungeon.getWidth()-5, dungeon.getHeight()+1);
            if (obj instanceof ExitObjective)
                squares.add(new ImageView(exitObj), dungeon.getWidth()-4, dungeon.getHeight()+2);
            if (obj instanceof SwitchObjective)
                squares.add(new ImageView(switchObj), dungeon.getWidth()-5, dungeon.getHeight()+2);
        } else {
            if (obj instanceof EnemyObjective)
                squares.add(new ImageView(enemyObj), dungeon.getWidth()-2, dungeon.getHeight()+1);
            if (obj instanceof TreasureObjective)
                squares.add(new ImageView(treasureObj), dungeon.getWidth()-2, dungeon.getHeight()+1);
            if (obj instanceof ExitObjective)
                squares.add(new ImageView(exitObj), dungeon.getWidth()-2, dungeon.getHeight()+1);
            if (obj instanceof SwitchObjective)
                squares.add(new ImageView(switchObj), dungeon.getWidth()-2, dungeon.getHeight()+1);
        }


    }

    ImageView prev;

    public void tick() {
        dungeon.notifyObservers();

        if (Water.checkIfInWater(dungeon)) {
            squares.getChildren().remove(this.prev);
            squares.add(this.prev, dungeon.getPlayer().getX(), dungeon.getPlayer().getY());
        } else if (squares.getChildren().contains(this.prev)){
            squares.getChildren().remove(this.prev);
        }

        tickOffObjectives();

        if (!dungeon.getPlayer().getStatus()) {
            printYouDied();
        }

        if (dungeon.getObservers().get(0).checkCompletion()) {
            dungeon.setDungeonCompletion();
            printYouWin();
        }

    }

    public void enemyTick() {
        for (Enemy x : dungeon.getEnemies()) {
            x.move(player);
        }
    }

    public void tickOffObjectives() {
        ObjectiveObserver obs = (ObjectiveObserver)dungeon.getObservers().get(0);
        Image tick = new Image((new File("images/tick.png")).toURI().toString());
        if (dungeon.getCondition().equals("AND") || dungeon.getCondition().equals("OR")) {
            for (Objective obj : dungeon.getObjectives()) {
                if (obj instanceof EnemyObjective && obs.getEnemyCount() == 0 ) {
                    squares.add(new ImageView(tick), dungeon.getWidth()-4, dungeon.getHeight()+1);
                } else if (obj instanceof TreasureObjective && obs.getTreasureCount() == 0) {
                    squares.add(new ImageView(tick), dungeon.getWidth()-5, dungeon.getHeight()+1);
                } else if (obj instanceof SwitchObjective && obs.getSwitchCount() == 0) {
                    squares.add(new ImageView(tick), dungeon.getWidth()-4, dungeon.getHeight()+2);
                }
            }
        } else {
            Objective mainGoal = dungeon.getObjectives().get(0);
            if (mainGoal instanceof EnemyObjective && obs.getEnemyCount() == 0 ) {
                squares.add(new ImageView(tick), dungeon.getWidth()-2, dungeon.getHeight()+1);
            } else if (mainGoal instanceof TreasureObjective && obs.getTreasureCount() == 0) {
                squares.add(new ImageView(tick), dungeon.getWidth()-2, dungeon.getHeight()+1);
            } else if (mainGoal instanceof SwitchObjective && obs.getSwitchCount() == 0) {
                squares.add(new ImageView(tick), dungeon.getWidth()-2, dungeon.getHeight()+1);
            }
            List<Objective> subGoals = new ArrayList<>();
            for (int i=1; i < dungeon.getObjectives().size(); i++) {
                subGoals.add(dungeon.getObjectives().get(i));
            }
            for (Objective obj : subGoals) {
                if (obj instanceof EnemyObjective && obs.getEnemyCount() == 0 ) {
                    squares.add(new ImageView(tick), dungeon.getWidth()-4, dungeon.getHeight()+1);
                } else if (obj instanceof TreasureObjective && obs.getTreasureCount() == 0) {
                    squares.add(new ImageView(tick), dungeon.getWidth()-5, dungeon.getHeight()+1);
                } else if (obj instanceof SwitchObjective && obs.getSwitchCount() == 0) {
                    squares.add(new ImageView(tick), dungeon.getWidth()-4, dungeon.getHeight()+2);
                } 
            }
        }
    }
    
    public int getDungeonHeight(){
        return this.dungeon.getHeight();
    }

    public int getDungeonWidth(){
        return this.dungeon.getWidth();
    }

    public void printYouDied() {
        Image death1 = new Image((new File("images/you_died1.png")).toURI().toString());
        Image death2 = new Image((new File("images/you_died2.png")).toURI().toString());
        Image death3 = new Image((new File("images/you_died3.png")).toURI().toString());
        Image death4 = new Image((new File("images/you_died4.png")).toURI().toString());
        Image death5 = new Image((new File("images/you_died5.png")).toURI().toString());
        Image death6 = new Image((new File("images/you_died6.png")).toURI().toString());
        Image death7 = new Image((new File("images/you_died7.png")).toURI().toString());
        Image death8 = new Image((new File("images/you_died8.png")).toURI().toString());
        Image death9 = new Image((new File("images/you_died9.png")).toURI().toString());
        Image brick = new Image((new File("images/dark_brick.png")).toURI().toString());

        int x = (int)Math.floor(dungeon.getWidth()/2);
        int y = (int)Math.floor(dungeon.getHeight()/3);

        for (int i=x-3; i < x+2; i++) {
            for (int j=y-1; j < y+4; j++) {
                if(i%2!=0 || j%2!=0)
                    squares.add(new ImageView(brick), i, j);
            }
        }

        squares.add(new ImageView(death1), x-2, y);
        squares.add(new ImageView(death2), x-1, y);
        squares.add(new ImageView(death3),x, y);
        squares.add(new ImageView(death4), x-2, y+1);
        squares.add(new ImageView(death5), x-1, y+1);
        squares.add(new ImageView(death6), x, y+1);
        squares.add(new ImageView(death7), x-2, y+2);
        squares.add(new ImageView(death8), x-1, y+2);
        squares.add(new ImageView(death9), x, y+2);

        resetVariables();
    }
    public void printYouWin() {
        Image win1 = new Image((new File("images/you_win1.png")).toURI().toString());
        Image win2 = new Image((new File("images/you_win2.png")).toURI().toString());
        Image win3 = new Image((new File("images/you_win3.png")).toURI().toString());
        Image win4 = new Image((new File("images/you_died4.png")).toURI().toString());
        Image win5 = new Image((new File("images/you_died5.png")).toURI().toString());
        Image win6 = new Image((new File("images/you_died6.png")).toURI().toString());
        Image win7 = new Image((new File("images/you_died7.png")).toURI().toString());
        Image win8 = new Image((new File("images/you_died8.png")).toURI().toString());
        Image win9 = new Image((new File("images/you_died9.png")).toURI().toString());
        Image brick = new Image((new File("images/dark_brick.png")).toURI().toString());

        int x = (int)Math.floor(dungeon.getWidth()/2);
        int y = (int)Math.floor(dungeon.getHeight()/3);

        for (int i=x-3; i < x+2; i++) {
            for (int j=y-1; j < y+4; j++) {
                if(i%2!=0 || j%2!=0)
                    squares.add(new ImageView(brick), i, j);
            }
        }

        squares.add(new ImageView(win1), x-2, y);
        squares.add(new ImageView(win2), x-1, y);
        squares.add(new ImageView(win3),x, y);
        squares.add(new ImageView(win4), x-2, y+1);
        squares.add(new ImageView(win5), x-1, y+1);
        squares.add(new ImageView(win6), x, y+1);
        squares.add(new ImageView(win7), x-2, y+2);
        squares.add(new ImageView(win8), x-1, y+2);
        squares.add(new ImageView(win9), x, y+2);
        resetVariables();

    }

    static public void setDifficulty(double i){
        difficulty = i;
    }

    public void resetVariables() {
        this.gameTimeLine.stop();
        this.enemyTimeLine.stop();

        this.squares = null;
        this.initialEntities = null;
        this.player = null;
        this.gameTimeLine = null;
        this.enemyTimeLine = null;
        this.dungeon.resetDungeon();
        this.dungeon = null;

        this.initialEntities.clear();
        squares.getChildren().clear();
        System.gc();
    }


}

