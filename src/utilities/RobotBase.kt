package utilities

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

/**
 * Contains the information for a robot and calculate the physics behind its motion
 */
abstract class RobotBase(spawn: DoubleArray, sz: Int) {

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

    //Calculates acceleration due to friction that the robot experiences
    open fun calculateFriction (): Double{
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

    //checks if the robot is able to move without colliding into a boundary
    open fun canMove(acc:Double): Boolean{
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
        val xVel = vel* cos(Math.toRadians(heading) - PI/2)
        val yVel = vel* sin(Math.toRadians(heading) - PI/2)

        //if it is on an edge and is moving away, let it go
        if((xPos+(size/2) >= 600 && xVel>0)||
                (xPos-(size/2)<=0 && xVel<0)||
                (yPos+(size/2) >= 600 && yVel>0)||
                (yPos-(size/2)<=0 && yVel<0)){
            return true
        }
        //if it is going into an edge, stop it
        return false
    }
    //moves the robot forwards and backwards
    abstract fun move()
    //rotates the robot
    abstract fun rotate()

}