package part1;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utilities.Avatar;

/**
 * Class simulates the motion of a robot
 */
public class Simulation1 extends Application {

    private final Robot1 robot;
    private final Pane root;
    private final Avatar avatar;

    /**
     * Constructor for the simulation
     */
    public Simulation1(){
        robot = new Robot1(new double[]{300,100}, 40);
        root = new Pane();
        avatar = new Avatar(robot, robot.getSize(), Color.AQUAMARINE);
    }

    private Pane createContent(){
        root.setPrefSize(600,600);
        root.setMinSize(600,600);
        root.setMaxSize(600,600);
        root.getChildren().addAll(avatar, avatar.getVel(), avatar.getDir());
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
        return root;
    }

    private void update(){
        avatar.move();
    }

    /**
     * JavaFX start method
     * @param primaryStage primary stage for the simulation
     */
    @Override
    public void start(Stage primaryStage){
        Scene scene = new Scene(createContent());
        scene.setOnKeyPressed(e->{
            switch(e.getCode()){
                //TODO: Add actions for key presses
            }
        });
        scene.setOnKeyReleased(e->{
            switch(e.getCode()){
                //TODO: add actions for key releases
            }
        });
        primaryStage.setTitle("Simulation 1");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Main entry point
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Simulation1 t = new Simulation1();
        launch(args);
    }
}