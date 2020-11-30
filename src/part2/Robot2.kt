package part2
import utilities.RobotBase

class Robot2(spawn: DoubleArray, sz: Int) : RobotBase(spawn,sz){

    /**
     * Rotates the robot
     */
    override fun rotate() {
        TODO("Rotate the robot to line up with the target")
    }

    /**
     * Moves the robot in a linear fashion
     */
    override fun move() {
        TODO("Use proportional control to move the robot on top of the target")
    }
}