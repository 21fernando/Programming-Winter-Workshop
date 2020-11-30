package utilities;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

/**
 * Class defines the behavior for the target that the robots will track
 */
public class Target extends Rectangle{

    private double xPos;
    private double yPos;
    private final int size;
    private double velocity = 0;
    private final double terminalVelocity = 8;
    private double acceleration = 0;
    private double netAcceleration = 0;
    private double angularVelocity = 0;
    private final double terminalAngularVelocity = 10;
    private final double[] absoluteVelocity = new double[]{0.0,0.0};
    private double heading = 0;
    private final double friction = 0.3;
    private final Arrow vel;

    /**
     * Parameterized constructor for the Target class
     * @param x starting x position
     * @param y starting y position
     * @param t starting heading
     * @param s size of the square
     * @param c color of the square
     */
    public Target(double x, double y, double t, int s, Color c) {
        super(s, s, c);
        this.xPos = x;
        this.yPos = y;
        this.heading = t;
        this.size = s;
        setX(xPos-(size/2.0));
        setY(yPos-(size/2.0));
        vel = new Arrow();
        vel.setStartX(xPos);
        vel.setStartY(yPos);
        vel.setEndX(xPos);
        vel.setEndY(yPos);
    }

    /**
     * sets robots heading based on angular velocity
     */
    private void rotate(){
        if(angularVelocity>terminalAngularVelocity){
            angularVelocity = terminalAngularVelocity;
        }
        if(angularVelocity<(-1*terminalAngularVelocity)){
            angularVelocity = -1*terminalAngularVelocity;
        }
        heading += angularVelocity;
        if(heading>=360){
            heading-=360;
        }else if(heading<0){
            heading+=360;
        }
    }

    /**
     * moves robot linearly based on velocity and acceleration
     */
    private void linearMove(){
        if(canMove(acceleration)){
            netAcceleration = acceleration + calculateFriction();
            velocity += netAcceleration;
            if(velocity>terminalVelocity){
                velocity = terminalVelocity;
            }
            if(velocity<(-1*terminalVelocity)){
                velocity = -1*terminalVelocity;
            }
            absoluteVelocity[0] = velocity* Math.cos(Math.toRadians(heading) - Math.PI /2);
            absoluteVelocity[1] = velocity* Math.sin(Math.toRadians(heading) - Math.PI /2);
            xPos -= absoluteVelocity[0];
            yPos -= absoluteVelocity[1];

        }
        else{
            velocity = 0.0;
        }
    }

    /**
     * moves and rotates the robot and the Rectangle
     */
    public void move(){
        rotate();
        linearMove();
        Rotate rotate = new Rotate(angularVelocity, getX()+size/2.0, getY()+size/2.0);
        rotate.setAngle(angularVelocity);
        getTransforms().addAll(rotate);
        setY(getY() + velocity);
        vel.setStartX(xPos);
        vel.setStartY(yPos);
        vel.setEndX(xPos + -10*velocity*Math.cos(Math.toRadians(heading)-(Math.PI/2)));
        vel.setEndY(yPos + -10*velocity*Math.sin(Math.toRadians(heading)-(Math.PI/2)));
    }

    //Calculates frictional acceleration that the target experiences
    private double calculateFriction (){
        if(Math.abs(velocity)-friction<0){
            return -1.0*velocity ;
        }
        if(velocity>0.1){
            return -1.0*friction;
        }
        if(velocity<-0.1){
            return friction;
        }
        else{
            return -1*velocity;
        }
    }

    //Determines whether the target can move or not
    private boolean canMove(double acc){
        //If it is in bounds, go ahead and move
        if((xPos+(size/2.0) < 600) && (xPos-(size/2.0)>0) && (yPos+(size/2.0) < 600) && (yPos-(size/2.0)>0)){
            return true;
        }
        //calculate the absolute x and y velocities
        double vel = velocity + acc + calculateFriction();
        if(vel>terminalVelocity){
            vel = terminalVelocity;
        }
        if(vel<(-1*terminalVelocity)){
            vel = -1*terminalVelocity;
        }
        double xVel = vel* Math.cos(Math.toRadians(heading) - Math.PI/2);
        double yVel = vel* Math.sin(Math.toRadians(heading) - Math.PI/2);

        //if it is on an edge and is moving away, let it go
        return (xPos + (size / 2.0) >= 600 && xVel > 0) ||
                (xPos - (size / 2.0) <= 0 && xVel < 0) ||
                (yPos + (size / 2.0) >= 600 && yVel > 0) ||
                (yPos - (size / 2.0) <= 0 && yVel < 0);
        //if it is going into an edge, stop it
    }

    /**
     * accessor for target's x position
     * @return target's x position
     */
    public double getxPos(){ return this.xPos; }

    /**
     * accessor for target's y position
     * @return target's y position
     */
    public double getyPos(){ return this.yPos; }

    /**
     * accessor for target's heading
     * @return targets heading
     */
    public double getHeading(){ return this.heading; }

    /**
     * mutator for target's velocity
     * @param v desired velocity
     */
    public void setVelocity(double v){ this.velocity = v; }

    /**
     * mutator for target's acceleration
     * @param a desired acceleration
     */
    public void setAcceleration(double a){ this.acceleration = a; }

    /**
     * mutator for target's angular velocity
     * @param w desired angular velocity
     */
    public void setAngularVelocity(double w){ this.angularVelocity = w; }

    /**
     * accessor method for target's velocity arrow
     * @return target's velocity arrow
     */
    public Arrow getVel(){ return this.vel; }

}
