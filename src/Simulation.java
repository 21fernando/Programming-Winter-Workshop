import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class simulates the motion of a robot
 */
public class Simulation extends Application {

    private Robot robot;
    private Pane root;
    private Obj avatar;
    private double angularVel; // deg/sec
    private double vel;
    private double t;

    /**
     * Constructor for the simulation
     */
    public Simulation(){
        robot = new Robot(new double[]{300,100});
        root = new Pane();
        avatar = new Obj(robot, 40, Color.AQUAMARINE);
        angularVel = 0;
        vel = 0;
        t=0;
    }

    private Pane createContent(){
        root.setPrefSize(600,600);
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

    private List<Obj> robots(){
        return root.getChildren().stream().map(n->(Obj)n).collect(Collectors.toList());
    }

    private void update(){
        t+=0.016;
        avatar.move(vel, angularVel);
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
                    vel = -1;
                    break;
                case DOWN:
                    vel = 1;
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
                    vel = 0;
                    break;
            }
        });
        primaryStage.setTitle("Transitions");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static class Obj extends Rectangle{
        private Robot r;
        private int size;
        private Line vel;

        /**
         * Class for the graphics related to the robot graphic
         * @param r robot object associated with this graphics object
         * @param size size of the robot to be drawn
         * @param c color of the robot
         */
        public Obj(Robot r,int size, Color c){
            super(size, size, c);
            this.r = r;
            setX(r.getSpawnX()-(size/2.0));
            setY(r.getSpawnY()-(size/2.0));
            this.size = size;
            vel = new Line(this.r.getSpawnX(),this.r.getSpawnY(),this.r.getSpawnX(),this.r.getSpawnY());
        }

        /**
         * moves the robot and velocity vector
         * @param v acceleration applied to the robot
         * @param t angular velocity applied to the robot
         */
        public void move(double v, double t){
            r.rotate(t);
            Rotate rotate = new Rotate(r.getAngularVelocity(), getX()+size/2.0, getY()+size/2.0);
            rotate.setAngle(r.getAngularVelocity());
            getTransforms().addAll(rotate);
            r.move(v);
            setY(getY() + r.getVelocity());
            vel.setStartX(r.getXPos());
            vel.setStartY(r.getYPos());
            vel.setEndX(r.getXPos() + 10*Math.cos(Math.toRadians(r.getHeading())-(Math.PI/2)));
            vel.setEndY(r.getYPos() + 10*Math.sin(Math.toRadians(r.getHeading())-(Math.PI/2)));
            //System.out.println(r.getHeading() + " (" + r.getXPos() + ", " + r.getYPos() +")");
        }

        /**
         * Accessor method for robot's velocity vector
         * @return Robot's velocity vector Line object
         */
        public Line getVel(){
            return this.vel;
        }

    }

    private static double[] rotateVector(double[] in, double theta){
        return new double[]{in[0]*Math.cos(theta) - in[1]*Math.sin(theta),in[0]*Math.sin(theta) + in[1]*Math.cos(theta)};
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
