package part2;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utilities.Avatar;
import utilities.Target;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class simulates the motion of a robot
 */
public class Simulation2 extends Application {

    private final Robot2 robot;
    private final Target target;
    private final Pane root;
    private final Avatar avatar;
    private final double angularVel; // deg/sec
    private final double acc;
    private double t;

    /**
     * Constructor for the simulation
     */
    public Simulation2(){
        robot = new Robot2(new double[]{300,100}, 40);
        root = new Pane();
        avatar = new Avatar(robot, robot.getSize(), Color.AQUAMARINE);
        target = new Target(300,300,0,20, Color.RED);
        angularVel = 0;
        acc = 0;
        t=0;
    }

    private Pane createContent(){
        root.setPrefSize(600,600);
        root.setMinSize(600,600);
        root.setMaxSize(600,600);
        root.getChildren().addAll(avatar, avatar.getVel(), target, target.getVel());
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
        return root;
    }

    private List<Avatar> robots(){
        return root.getChildren().stream().map(n->(Avatar)n).collect(Collectors.toList());
    }

    private void update(){
        t+=0.016;
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
        scene.setOnKeyPressed(e->{
            switch(e.getCode()){
                case LEFT:
                    robot.setAngularVelocity(-3);
                    break;
                case RIGHT:
                    robot.setAngularVelocity(3);
                    break;
                case UP:
                    robot.setAcceleration(-0.4);
                    break;
                case DOWN:
                    robot.setAcceleration(0.4);
                    break;
            }
        });
        scene.setOnKeyReleased(e->{
            switch(e.getCode()){
                case LEFT:
                case RIGHT:
                    robot.setAngularVelocity(0);
                    break;
                case UP:
                case DOWN:
                    robot.setAcceleration(0);
                    break;
            }
        });
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