package part2
import utilities.PID
import utilities.RobotBase
import utilities.Target
import kotlin.math.*

class Robot2(spawn: DoubleArray, sz: Int, t:Target) : RobotBase(spawn,sz){
    val target = t
    override val friction = 0.0

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
        val acc = if (target.y > yPos) -1.0 else 1.0
        accelerateForward(acc)
    }
}