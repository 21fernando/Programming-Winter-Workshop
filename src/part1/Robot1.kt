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
     */
    override fun move() {
        if(canMove(acceleration)){
            netAcceleration = acceleration + calculateFriction()
            velocity += netAcceleration
            if(velocity>terminalVelocity){
                velocity = terminalVelocity
            }
            if(velocity<(-1*terminalVelocity)){
                velocity = -1*terminalVelocity
            }
            absoluteVelocity[0] = velocity* cos(Math.toRadians(heading) - PI /2)
            absoluteVelocity[1] = velocity* sin(Math.toRadians(heading) - PI /2)
            xPos -= absoluteVelocity[0]
            yPos -= absoluteVelocity[1]

        }
        else{
            velocity = 0.0
        }
    }

}