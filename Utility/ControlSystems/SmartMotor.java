package org.firstinspires.ftc.teamcode.Utility.ControlSystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class SmartMotor {
    public DcMotor motor = null;
    private PIDController2 positionPID = null;
    private PIDController2 velocityPID = null;
    private double encoderTicksPerRev;

    //maxAcceleration and cruiseVelocity are only used in positional controls
    private double maxAcceleration = 0;
    private double cruiseVelocity = 0;

    //Target values:
    private double targetPosition = 0;
    private double targetVelocity = 0;

    //Variables for keeping track of previous states
    private boolean firstRun = true;
    private double lastPositionRad = 0;
    private double lastTimeStamp = 0;

    //Variables for position curve
    private double posCTPosition = 0;
    private double posCTVelocity = 0;
    private double posLastTimeStamp = 0;

    //Variables for smoothing out readings
    private double ESPosVal = 0;
    private double ESVelVal = 0;

    private double alpha = (1.0/3.0); //Roughly smoothing out over the last 1/x readings

    private enum MotorState {
        POWER, POSITION, VELOCITY
    }
    private MotorState cState = null;



    public SmartMotor(HardwareMap hw, String name, double encodersPerRev){
        encoderTicksPerRev = encodersPerRev;
        motor = hw.get(DcMotor.class, name);

        cState = MotorState.POWER; //Set to power by default
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motor.setDirection(DcMotor.Direction.FORWARD);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motor.setPower(0);

        positionPID = new PIDController2();
        velocityPID = new PIDController2();
    }

    public void configPositionPID(double p, double i, double d){
        positionPID.setPID(p, i ,d, 0);
    }

    public void configVelocityPID(double p, double i, double d, double f){
        velocityPID.setPID(p,i,d,f);
    }



    public void setDirection(DcMotor.Direction direction){
        motor.setDirection(direction);
    }



    public void setMaxAcceleration(double acceleration){
        maxAcceleration = acceleration;
    }

    public void setCruiseVelocity(double velocity){
        cruiseVelocity = velocity;
    }


    public void setPower(double power){
        motor.setPower(power);
        cState = MotorState.POWER;
    }

    public void setPosition(double position){
        //Not resetting the PID in order to attempt make transitions more fluid
        targetPosition = position;
        //Setting up the PID is done continuously in the update method
        cState = MotorState.POSITION;
    }


    public void setVelocity(double velocity) {
        //Not resetting the PID in order to attempt make transitions more fluid
        targetVelocity = velocity;
        //PID is set up here as there are no additional functions influencing it
        velocityPID.setTarget(velocity);
        cState = MotorState.VELOCITY;
    }



    public void update(){
        //Main function of this class. Monitors/regulates motor behavior
        updateReadings(); //Updating values for runtime

        //Depending on the current target state, motor behavior will change:
        switch (cState){
            case POWER:
                //Do nothing, DcMotor class handles this for us.
                break;

            case POSITION:
                //Calculate the next position and update internal variables:
                double newPosition = getNextPosition();
                //Feed new position to PID and motor
                motor.setPower(positionPID.getOutput(getMotorPosRad(), newPosition));
                break;

            case VELOCITY:
                //Currently just directly feeding the PID and motor
                motor.setPower(velocityPID.getOutput(getMotorVelRad()));
                break;

            default:
                //Do nothing, this case should not be called
                break;
        }


    }



    public double getNextPosition(){
        double remainingDist = targetPosition - posCTPosition;
        double decelerationDist = getDecelerationDist();

        double cTime = System.currentTimeMillis();
        double elapsedSeconds = (cTime-posLastTimeStamp)/1000.0;
        posLastTimeStamp = cTime;

        //If there is still time to accelerate/cruise to the target:
        if(Math.abs(decelerationDist) < Math.abs(remainingDist)){
            //If the current velocity has yet to achieve cruise velocity:
            if(Math.abs(posCTVelocity) < cruiseVelocity){
                double effectiveAcceleration = Math.copySign(maxAcceleration, remainingDist); //Acting with the motion
                posCTPosition += posCTVelocity*elapsedSeconds + 0.5*effectiveAcceleration*Math.pow(elapsedSeconds,2); //Determine new position
                posCTVelocity += effectiveAcceleration*elapsedSeconds; //Determining new velocity for next loop
                return posCTPosition;
            }

            //If the current velocity has met or exceeded the cruise velocity:
            if(Math.abs(posCTVelocity) >= cruiseVelocity) {
                //No acceleration necessary
                posCTVelocity = Math.copySign(cruiseVelocity, posCTVelocity);
                posCTPosition += posCTVelocity*elapsedSeconds;
                return posCTPosition;
            }
        }

        //If the the motor needs to begin decelerating:
        if(Math.abs(decelerationDist) >= Math.abs(remainingDist)){
            double effectiveAcceleration = Math.copySign(maxAcceleration, -remainingDist); //Acting against the motion
            posCTPosition += posCTVelocity*elapsedSeconds + 0.5*effectiveAcceleration*Math.pow(elapsedSeconds,2); //Determine new position
            posCTVelocity += effectiveAcceleration*elapsedSeconds; //Determining new velocity for next loop
            return posCTPosition;
        }

        //In all other cases, default to the current internal target:
        return posCTPosition;
    }

    public double getDecelerationDist(){
        //Returns the distance required to decelerate to 0, allowing for proper regulation of the velocity curve
        return -(-Math.pow(posCTVelocity, 2)/(2*maxAcceleration));
    }













    public double getMotorPosRad(){
        return ESPosVal; //Return smoothed output
    }

    public double getMotorVelRad(){
        return ESVelVal; //Return smoothed output
    }

    private void updateReadings(){
        //Used to update motion readings critical to operation
        updatePosRadReadings();
        updateVelRadReadings();
    }

    private void updatePosRadReadings(){
        double cVal = (motor.getCurrentPosition()/encoderTicksPerRev)*2*Math.PI;
        ESPosVal = expSmoothing(cVal, ESPosVal, alpha); //Used for smoothing the output to avoid instability
    }

    private void updateVelRadReadings(){
        if(firstRun){
            lastTimeStamp = System.currentTimeMillis();
            lastPositionRad = motor.getCurrentPosition();
            firstRun = false;
            return; //Exit function to avoid evaluating distance over zero time
        }
        double cPos = (motor.getCurrentPosition()/encoderTicksPerRev)*2*Math.PI;
        double cTime = System.currentTimeMillis();
        double elapsedTime = (cTime - lastTimeStamp)/1000.0; //Converted to seconds
        double cVal = (cPos-lastPositionRad)/elapsedTime;

        ESVelVal = expSmoothing(cVal, ESVelVal, alpha); //Update smoothing

        lastPositionRad = cPos;
        lastTimeStamp = cTime;
    }

    private double expSmoothing(double currentValue, double runningValue, double alphaVal){
        //The output of this function should be used to update the runningValue
        return (alphaVal*currentValue) + ((1-alphaVal)*runningValue);
    }
}
