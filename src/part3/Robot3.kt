package part3
import utilities.PID
import utilities.RobotBase
import utilities.Target
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class Robot3(spawn: DoubleArray, sz: Int, t:Target) : RobotBase(spawn,sz){
    private val target = t
    var kP_drive = 2
    val pid = PID(0.1, 0.001, 0.5)
    var reached = false
    override val friction = 0.0


    init {
        pid.setpoint = target.y
        pid.reversed = false
        pid.setOutputLimits(maxLinearAcceleration)
    }

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
        if(!reachedTarget(target)) {
            var distance = sqrt((target.x - xPos).pow(2) + (target.y - yPos).pow(2))
            if (yPos < target.y) distance *= -1
            val acc = kP_drive * distance
            accelerateForward(acc)
        }
    }

}