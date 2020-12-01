package part1
import utilities.RobotBase
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class Robot1(spawn: DoubleArray, sz: Int) : RobotBase(spawn,sz){

    /**
     * Rotates the robot
     */
    override fun rotate() {
        if (leftButtonDown) accelerateTurn(-10.0)
        else if (rightButtonDown) accelerateTurn(10.0)
    }

    /**
     * Moves the robot in a linear fashion
     */
    override fun move() {
        if (upButtonDown) accelerateForward(1.0)
        else if (downButtonDown) accelerateForward(-1.0)
    }

}