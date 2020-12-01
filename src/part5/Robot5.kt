package part5
import utilities.RobotBase
import utilities.Target

class Robot5(spawn: DoubleArray, sz: Int, t:Target) : RobotBase(spawn,sz){

    /**
     * Rotates the robot
     */
    override fun rotate() {
        TODO("Use proportional control, the heading, and the angular velocity of the robot to line it up with the taget")
    }

    /**
     * Moves the robot in a linear fashion
     */
    override fun move() {
        //TODO("Use the proportional derivative controller from last time")
    }

}