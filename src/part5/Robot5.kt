package part5
import utilities.RobotBase
import utilities.Target

class Robot5(spawn: DoubleArray, sz: Int, t:Target) : RobotBase(spawn,sz){
    private val target = t
    /**
     * Rotates the robot
     */
    override fun rotate() {
        TODO("Use proportional control rotate the robot to line up with the target. Have it stop once aligned")
    }

    /**
     * Moves the robot in a linear fashion
     */
    override fun move() {
        TODO("Use the proportional derivative controller from last time to move to the target once aligned")
    }

}