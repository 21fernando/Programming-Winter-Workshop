package part3
import utilities.RobotBase
import utilities.Target

class Robot3(spawn: DoubleArray, sz: Int, t:Target) : RobotBase(spawn,sz){
    private val target = t

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
        TODO("Implement proportional control")
    }

}