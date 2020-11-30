import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class simulates the motion of a robot
 */
public class Simulation extends Application {

    private Robot robot;
    private Pane root;
    private Avatar avatar;
    private double angularVel; // deg/sec
    private double acc;
    private double t;

    /**
     * Constructor for the simulation
     */
    public Simulation(){
        robot = new Robot(new double[]{300,100}, 40, 0, 0, 0);
        root = new Pane();
        avatar = new Avatar(robot, robot.getSize(), Color.AQUAMARINE);
        angularVel = 0;
        acc = 0;
        t=0;
    }

    private Pane createContent(){
        root.setPrefSize(600,600);
        root.setMinSize(600,600);
        root.setMaxSize(600,600);
        root.getChildren().addAll(avatar, avatar.getVel());
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
        avatar.move(acc, angularVel);
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
                    angularVel = -3;
                    break;
                case RIGHT:
                    angularVel = 3;
                    break;
                case UP:
                    acc = -0.4;
                    break;
                case DOWN:
                    acc = 0.4;
                    break;
            }
        });
        scene.setOnKeyReleased(e->{
            switch(e.getCode()){
                case LEFT:
                case RIGHT:
                    angularVel = 0;
                    break;
                case UP:
                case DOWN:
                    acc = 0;
                    break;
            }
        });
        primaryStage.setTitle("Transitions");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Main entry point
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Simulation t = new Simulation();
        launch(args);
    }
}
