import kotlin.math.*

/**
 * Contains the information for a robot and calculate the physics behind its motion
 */
class Robot(spawn: DoubleArray) {

    val spawnX: Double = spawn[0]
    val spawnY: Double = spawn[1]
    var velocity = 10.0
    var acceleration = 0.0
    var netAcceleration = 0.0
    val terminalVelocity = 10.0
    var angularVelocity = 0.0
    var angularAcceleration = 0.0
    val terminalAngularVelocity = 10.0
    var xPos: Double = spawn[0]
    var yPos: Double = spawn[1]
    var heading = 0.0
    var friction = 0.5

    /**
     * Rotates the robot
     * @param a the desired angular velocity
     */
    fun rotate(a:Double){
        angularAcceleration = a
        angularVelocity = a
        if(angularVelocity>terminalAngularVelocity){
            angularVelocity = terminalAngularVelocity
        }
        if(angularVelocity<(-1*terminalAngularVelocity)){
            angularVelocity = -1*terminalAngularVelocity
        }
        heading += angularVelocity
        if(heading>=360){
            heading-=360
        }else if(heading<0){
            heading+=360
        }
    }

    /**
     * Moves the robot in a linear fashion
     * @param v the velocity of the robot
     */
    fun move(v: Double){
        acceleration = v
        netAcceleration = acceleration + calculateFriction()
        velocity += netAcceleration
        if(velocity>terminalVelocity){
            velocity = terminalVelocity
        }
        if(velocity<(-1*terminalVelocity)){
            velocity = -1*terminalVelocity
        }
        xPos -= velocity* cos(Math.toRadians(heading) - PI/2)
        yPos -= velocity* sin(Math.toRadians(heading) - PI/2)
    }

    private fun calculateFriction (): Double{
        return when {
            velocity > 0.1 -> {
                -friction
            }
            velocity <-0.1 -> {
                friction
            }
            else -> {
                -velocity
            }
        }
    }

}