package part2
import utilities.RobotBase
import utilities.Target
import kotlin.math.*

class Robot2(spawn: DoubleArray, sz: Int, t:Target) : RobotBase(spawn,sz){
    val target = t
    var reached = false
    val kP_drive = 10

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
        if(!reached) {
            val distance = sqrt((target.x - xPos).pow(2) + (target.y - yPos).pow(2))
            var acc = kP_drive * distance
            if (yPos < target.y) acc *= -1
//            println("Distance: $distance, acc: $acc, current speed: $linearVelocity")
            accelerateForward(acc)
            if(linearVelocity<0.001 && linearVelocity >-0.001 && distance < 0.1){
                reached = true
            }
        }
    }
}