import kotlin.math.*

/**
 * Contains the information for a robot and calculate the physics behind its motion
 */
class Robot(spawn: DoubleArray, sz: Int, kp: Int, ki: Int, kd: Int) {

    val spawnX: Double = spawn[0]
    val spawnY: Double = spawn[1]
    var velocity = 0.0
    var absoluteVelocity = doubleArrayOf(0.0,0.0)
    var acceleration = 0.0
    var netAcceleration = 0.0
    val terminalVelocity = 10.0
    var angularVelocity = 0.0
    val terminalAngularVelocity = 10.0
    var xPos: Double = spawn[0]
    var yPos: Double = spawn[1]
    var heading = 0.0
    var friction = 0.3
    val size = sz
    val kp = kp
    val kd = kd
    val ki = ki

    /**
     * Rotates the robot
     * @param a the desired angular velocity
     */
    fun rotate(a:Double){
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
     * @param a the velocity of the robot
     */
    fun move(a: Double){
        if(canMove(a)){
            acceleration = a
            netAcceleration = acceleration + calculateFriction()
            velocity += netAcceleration
            if(velocity>terminalVelocity){
                velocity = terminalVelocity
            }
            if(velocity<(-1*terminalVelocity)){
                velocity = -1*terminalVelocity
            }
            absoluteVelocity[0] = velocity* cos(Math.toRadians(heading) - PI/2)
            absoluteVelocity[1] = velocity* sin(Math.toRadians(heading) - PI/2)
            xPos -= absoluteVelocity[0]
            yPos -= absoluteVelocity[1]

        }
        else{
            velocity = 0.0
            acceleration = 0.0
        }
    }

    private fun calculateFriction (): Double{
        return when {
            abs(velocity)-friction < 0 ->{
                -velocity
            }
            velocity > 0.1 -> {
                -friction
            }
            velocity <-0.1 -> {
                friction
            }
            else -> -velocity
        }
    }

    private fun canMove(acc:Double): Boolean{
        //If it is in bounds, go ahead and move
        if((xPos+(size/2) < 600) && (xPos-(size/2)>0) && (yPos+(size/2) < 600) && (yPos-(size/2)>0)){
            return true
        }

        //calculate the absolute x and y velocities
        var vel = velocity + acc + calculateFriction()
        if(vel>terminalVelocity){
            vel = terminalVelocity
        }
        if(vel<(-1*terminalVelocity)){
            vel = -1*terminalVelocity
        }
        var XVel = vel* cos(Math.toRadians(heading) - PI/2)
        var YVel = vel* sin(Math.toRadians(heading) - PI/2)

        //if it is on an edge and is moving away, let it go
        if((xPos+(size/2) >= 600 && XVel>0)||
                (xPos-(size/2)<=0 && XVel<0)||
                (yPos+(size/2) >= 600 && YVel>0)||
                (yPos-(size/2)<=0 && YVel<0)){
            return true
        }
        //if it is going into an edge, stop it
        return false
    }


}