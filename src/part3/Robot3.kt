package part3
import utilities.RobotBase
import utilities.Target

class Robot3(spawn: DoubleArray, sz: Int, t:Target) : RobotBase(spawn,sz){

    /**
     * Rotates the robot
     */
    override fun rotate() {
        //Robot is lined up with target, so this method can be left blank
    }

    /**
     * Moves the robot in a linear fashion
     */
    override fun move() {
        //TODO("Use proportional control to move the robot on top of the target")
    }

}