package utilities

import kotlin.math.*

/**
 * Contains the information for a robot and calculate the physics behind its motion
 */
abstract class RobotBase(spawn: DoubleArray, sz: Int) {

    val spawnX: Double = spawn[0]
    val spawnY: Double = spawn[1]

    var linearVelocity = 0.0
        private set
    val absoluteVelocity: Array<Double>
        get() = arrayOf(xVel, yVel)
    val xVel
        get() = linearVelocity * cos(Math.toRadians(heading) - PI / 2)
    val yVel
        get() = linearVelocity * sin(Math.toRadians(heading) - PI / 2)
    val terminalVelocity = 15.0

    var angularVelocity = 0.0
        private set
    val terminalAngularVelocity = 10.0

    var xPos: Double = spawn[0]
        private set
    var yPos: Double = spawn[1]
        private set

    var heading = 0.0
        private set

    open val friction = 0.3
    val size = sz
    
    val maxLinearAcceleration = 4.0
    val maxAngularAcceleration = 5.0
    
    private var linearAcceleration = 0.0
    private var angularAcceleration = 0.0

    var leftButtonDown = false
    var rightButtonDown = false
    var upButtonDown = false
    var downButtonDown = false

    // Calculates acceleration due to friction that the robot experiences
    open fun calculateFriction (): Double{
        return when {
            abs(linearVelocity)-friction < 0 ->{
                -linearVelocity
            }
            linearVelocity > 0.1 -> {
                -friction
            }
            linearVelocity <-0.1 -> {
                friction
            }
            else -> -linearVelocity
        }
    }

    //checks if the robot is able to move without colliding into a boundary
    open fun canMove(acc:Double): Boolean{
        val accel = applyAbsoluteMax(acc, maxLinearAcceleration) + calculateFriction()
        val vel = applyAbsoluteMax(accel+linearVelocity, terminalVelocity)
        val vx = vel* cos(Math.toRadians(heading) - PI /2)
        val vy = vel* sin(Math.toRadians(heading) - PI /2)
        return !inBounds(xPos, yPos) || inBounds(xPos+vx, yPos+vy)
    }

    protected fun accelerateForward(acc: Double) {
        linearAcceleration += acc
    }

    protected fun accelerateTurn(acc: Double){
        angularAcceleration += acc
    }
    
    open fun simulate(){
//        angularVelocity = 0.0  // dont want spin momentum right now

        // make sure valid accelerations
        linearAcceleration = applyAbsoluteMax(linearAcceleration, maxLinearAcceleration)
        angularAcceleration = applyAbsoluteMax(angularAcceleration, maxAngularAcceleration)

        // apply friction
        linearAcceleration += calculateFriction()

        // apply acceleration
        linearVelocity = applyAbsoluteMax(linearVelocity+linearAcceleration, terminalVelocity)
        angularVelocity = applyAbsoluteMax(angularVelocity+angularAcceleration, terminalAngularVelocity)

        // move
        val newX = xPos + xVel
        val newY = yPos + yVel
//        println("Heading: $heading, Vel:($xVel, $yVel), new: ($newX, $newY), old: ($xPos, $yPos)")
        if (inBounds(newX, newY)){
            xPos = newX
            yPos = newY
        }
        // rotate
        heading += angularVelocity

        // reset variables
        linearAcceleration = 0.0
        angularAcceleration = 0.0
        angularVelocity = 0.0  // dont want spin momentum right now
//        println("pos: ($xPos, $yPos), vel: $linearVelocity, inBounds: ${inBounds(xPos, yPos)}")

    }

    private fun applyAbsoluteMax(num:Double, max: Double): Double {
        return when {
            num > max -> max
            num < -max -> -max
            else -> num
        }
    }

    private fun inBounds(x: Double, y:Double): Boolean {
        return x+(size/2) < 600 && (x-(size/2)>0) && (y+(size/2) < 600) && y-(size/2) > 0
    }

    //moves the robot forwards and backwards
    abstract fun move()
    //rotates the robot
    abstract fun rotate()

    protected fun reachedTarget(target: Target): Boolean {
        val stopped = linearVelocity<0.001 && linearVelocity >-0.001
        val distance = sqrt((target.x - xPos).pow(2) + (target.y - yPos).pow(2))
        return stopped && distance < 0.1
    }


}