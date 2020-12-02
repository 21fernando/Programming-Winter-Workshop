package part2;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utilities.Avatar;
import utilities.Target;

/**
 * Class simulates the motion of a robot
 */
public class Simulation2 extends Application {

    private final Robot2 robot;
    private final Target target;
    private final Pane root;
    private final Avatar avatar;

    /**
     * Constructor for the simulation
     */
    public Simulation2(){
        target = new Target(300,300,0,20, Color.RED);
        robot = new Robot2(new double[]{300,100}, 40, target);
        root = new Pane();
        avatar = new Avatar(robot, robot.getSize(), Color.AQUAMARINE);
    }

    private Pane createContent(){
        root.setPrefSize(600,600);
        root.setMinSize(600,600);
        root.setMaxSize(600,600);
        root.getChildren().addAll(avatar, avatar.getVel(), target, target.getVel(), avatar.getDir());
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
        target.move();
    }

    /**
     * JavaFX start method
     * @param primaryStage primary stage for the simulation
     */
    @Override
    public void start(Stage primaryStage){
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Simulation 2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Main entry point
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Simulation2 t = new Simulation2();
        launch(args);
    }
}