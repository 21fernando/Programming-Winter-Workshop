package utilities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class Avatar extends Rectangle {

    private final RobotBase r;
    private final int size;
    private final Arrow vel;

    /**
     * Class for the graphics related to the robot graphic
     * @param r robot object associated with this graphics object
     * @param size size of the robot to be drawn
     * @param c color of the robot
     */
    public Avatar(RobotBase r, int size, Color c){
        super(size, size, c);
        this.r = r;
        setX(r.getSpawnX()-(size/2.0));
        setY(r.getSpawnY()-(size/2.0));
        this.size = size;
        vel = new Arrow();
        vel.setStartX(this.r.getSpawnX());
        vel.setStartY(this.r.getSpawnY());
        vel.setEndX(this.r.getSpawnX());
        vel.setEndY(this.r.getSpawnY());
    }

    /**
     * moves the robot and velocity vector
     */
    public void move(){
        r.rotate();
        r.move();
        Rotate rotate = new Rotate(r.getAngularVelocity(), getX()+size/2.0, getY()+size/2.0);
        rotate.setAngle(r.getAngularVelocity());
        getTransforms().addAll(rotate);
        setY(getY() + r.getVelocity());
        vel.setStartX(r.getXPos());
        vel.setStartY(r.getYPos());
        vel.setEndX(r.getXPos() + -10*r.getVelocity()*Math.cos(Math.toRadians(r.getHeading())-(Math.PI/2)));
        vel.setEndY(r.getYPos() + -10*r.getVelocity()*Math.sin(Math.toRadians(r.getHeading())-(Math.PI/2)));
    }

    /**
     * Accessor method for robot's velocity vector
     * @return Robot's velocity vector Line object
     */
    public Arrow getVel(){
        return this.vel;
    }

}
