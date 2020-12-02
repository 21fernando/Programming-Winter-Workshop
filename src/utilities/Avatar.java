package utilities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.util.Arrays;

public class Avatar extends Rectangle {

    private final RobotBase r;
    private final int size;
    private final Arrow vel;
    private final Arrow dir;
    private final int dirLen=20;

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
        dir = new Arrow();
        dir.setStartX(this.r.getSpawnX());
        dir.setStartY(this.r.getSpawnY());
        dir.setEndX(this.r.getSpawnX());
        dir.setEndY(this.r.getSpawnY()+dirLen);
    }

    /**
     * moves the robot and velocity vector
     */
    public void move(){
        r.rotate();
        r.move();
        r.simulate();
        setRotate(r.getHeading());
        setY(r.getYPos() - size/2.0);
        setX(r.getXPos() - size/2.0);
        vel.setStartX(r.getXPos());
        vel.setStartY(r.getYPos());
        vel.setEndX(r.getXPos() + r.getXVel()*10);
        vel.setEndY(r.getYPos() + r.getYVel()*10);
        dir.setStartX(r.getXPos());
        dir.setStartY(r.getYPos());
        dir.setEndX(r.getXPos() + dirLen*Math.cos(Math.toRadians(r.getHeading())-(Math.PI/2)));
        dir.setEndY(r.getYPos() + dirLen*Math.sin(Math.toRadians(r.getHeading())-(Math.PI/2)));
    }

    /**
     * Accessor method for robot's velocity vector
     * @return Robot's velocity vector Line object
     */
    public Arrow getVel(){
        return this.vel;
    }

    /**
     * Accessor method for robot's velocity vector
     * @return Robot's velocity vector Line object
     */
    public Arrow getDir(){
        return this.dir;
    }


}
