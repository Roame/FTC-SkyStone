package org.firstinspires.ftc.teamcode.Utility.ControlSystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SmartMotor {
    public DcMotor motor;
    private PIDController2 positionPID;
    private PIDController2 velocityPID;
    private double encoderTicksPerRev;

    //maxAcceleration and cruiseVelocity
    private double maxAcceleration = 0;
    private double cruiseVelocity = 0;

    //Target values:
    private double targetPosition = 0;
    private double targetLimPosition = 0;
    private double targetVelocity = 0;
    private double targetLimVelocity = 0;

    //Soft limits:
    private double maxEncoderPos = 0;
    private double minEncoderPos = 0;

    //Variables for keeping track of previous states
    private int loopCount = 0; //Used to take track the first few loops
    private int targetLoops = 3; //How many loops should be ran before starting operation
    private boolean firstRunReadings = true;
    private double lastPositionRad = 0;
    private double lastTimeStamp = 0;
    private double loopElapsedSeconds = 0;

    //Variables for position/velocity curves
    private double cTPosition = 0;
    private double cTVelocity = 0;

    //Variables for smoothing out readings
    private double ESPosVal = 0;
    private double ESVelVal = 0;

    private double alpha = (1.0/3.0); //Roughly smoothing out over the last 1/x readings

    private enum MotorState {
        POWER, POSITION, POSITION_LIMITED, VELOCITY, VELOCITY_LIMITED
    }
    private MotorState cState;

    private Telemetry telemetry;

    public SmartMotor(HardwareMap hw, String name, double encodersPerRev, Telemetry telemetry){
        this.telemetry = telemetry;
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

    public void setEncoderLimits(double min, double max){
        minEncoderPos = min;
        maxEncoderPos = max;
    }


    public void setPower(double power){
        motor.setPower(power);
        cState = MotorState.POWER;
    }

    public void setPosition(double position){
        targetPosition = position;
        positionPID.setTarget(position);
        cState = MotorState.POSITION;
    }

    public void setPositionWithLimits(double position){
        //Not resetting the PID in order to attempt make transitions more fluid
        //Restricting position to bounds
        if(!(maxEncoderPos == 0 && minEncoderPos == 0)) {
            if (position > maxEncoderPos) {
                position = maxEncoderPos;
            } else if (position < minEncoderPos) {
                position = minEncoderPos;
            }
        }
        targetLimPosition = position;
        //Setting up the PID is done continuously in the update method
        cState = MotorState.POSITION_LIMITED;
    }


    public void setVelocity(double velocity) {
        //Not resetting the PID in order to attempt make transitions more fluid
        targetVelocity = velocity;
        //PID is set up here as there are no additional functions influencing it
        velocityPID.setTarget(velocity);
        cState = MotorState.VELOCITY;
    }

    public void setVelocityWithLimits(double velocity){
        //Restricting velocity:
        if(Math.abs(velocity) > cruiseVelocity){
            velocity = Math.copySign(cruiseVelocity, velocity);
        }
        targetLimVelocity = velocity;
        cState = MotorState.VELOCITY_LIMITED;
    }



    public void update(){
        //Main function of this class. Monitors/regulates motor behavior

        //This function always runs:
        updateReadings(); //Updating values for runtime

        //First few loops are reserved to just tracking the motor's current behavior
        if(loopCount < targetLoops){
            loopCount++;
            updateInternalValues(); //Preparing for limited positional or velocity controls
            return; //Exit motor class and allow code to proceed
        }


        //Depending on the current target state, motor behavior will change:
        switch (cState){
            case POWER:
                //Do nothing, DcMotor class handles this for us.
                updateInternalValues(); //Keeping variables up to date
                break;

            case POSITION:
                motor.setPower(positionPID.getOutput(getMotorPosRad()));

                updateInternalValues(); //Keeping variables up to date
                break;

            case POSITION_LIMITED:
                //Calculate the next position and update internal variables:
                double newPosition = getNextPosition();
                //Feed new position to PID and motor
                motor.setPower(positionPID.getOutput(getMotorPosRad(), newPosition));
                break;

            case VELOCITY:
                //Currently just directly feeding the PID and motor
                motor.setPower(velocityPID.getOutput(getMotorVelRad()));

                updateInternalValues(); //Keeping variables up to date
                break;

            case VELOCITY_LIMITED:
                //Get next velocity:
                telemetry.addData("Current Target", targetLimVelocity);
                double newVelocity = getNextVelocity();
                cTPosition = getMotorPosRad(); //Updating variables
                //Update PID and motor
                motor.setPower(velocityPID.getOutput(getMotorVelRad(), newVelocity));

                break;

            default:
                //Do nothing, this case should not be called
                break;
        }
    }



    public double getNextPosition(){
        double remainingDist = targetLimPosition - cTPosition;
        double decelerationDist = getDecelerationDist();

        double elapsedSeconds = loopElapsedSeconds;

        double effectiveAcceleration;
        //If there is still time to accelerate/cruise to the target:
        if(Math.abs(decelerationDist) < Math.abs(remainingDist)){
            //If the current velocity has yet to achieve cruise velocity:
            if(Math.abs(cTVelocity) < cruiseVelocity || cTVelocity != Math.copySign(cTPosition, remainingDist)){
                effectiveAcceleration = Math.copySign(maxAcceleration, remainingDist); //Acting with the motion
                cTPosition += cTVelocity *elapsedSeconds + 0.5*effectiveAcceleration*Math.pow(elapsedSeconds,2); //Determine new position
                cTVelocity += effectiveAcceleration*elapsedSeconds; //Determining new velocity for next loop
                return cTPosition;
            }

            //If the current velocity has met or exceeded the cruise velocity:
            if(Math.abs(cTVelocity) >= cruiseVelocity) {
                //No acceleration necessary
                cTVelocity = Math.copySign(cruiseVelocity, cTVelocity);
                cTPosition += cTVelocity *elapsedSeconds;
                return cTPosition;
            }
        }

        //If the the motor needs to begin decelerating:
        if(Math.abs(decelerationDist) >= Math.abs(remainingDist)){
            effectiveAcceleration = 0;
            //Determining acceleration based on directionality of the target and the current motion:
            if((cTVelocity >0 && remainingDist >0) || (cTVelocity <0 &&remainingDist <0)) {
                effectiveAcceleration = Math.copySign(maxAcceleration, -remainingDist); //Acting away from target
            }
            if((cTVelocity <0 && remainingDist>0) || (cTVelocity >0 && remainingDist<0)){
                effectiveAcceleration = Math.copySign(maxAcceleration, remainingDist); //Acting towards target
            }
            //Calculating new position
            cTPosition += cTVelocity * elapsedSeconds + 0.5 * effectiveAcceleration * Math.pow(elapsedSeconds, 2); //Determine new position
            cTVelocity += effectiveAcceleration * elapsedSeconds; //Determining new velocity for next loop
            return cTPosition;
        }

        //In all other cases, default to the current internal target:
        return cTPosition;
    }



    private double getNextVelocity(){
        double elapsedSeconds = loopElapsedSeconds;

        double distToLim = 0;
        if(targetLimVelocity > 0){
            distToLim = encoderUnitsToRads(maxEncoderPos)-getMotorPosRad();
        } else if(targetLimVelocity < 0){
            distToLim = encoderUnitsToRads(minEncoderPos)-getMotorPosRad();
        }

        if(maxEncoderPos == 0 && minEncoderPos == 0){
            distToLim = Math.copySign(1000, targetLimVelocity); //Placeholder val
        }

        double decelDist = getDecelerationDist();

        if(Math.abs(distToLim)>Math.abs(decelDist)){
            if(Math.abs(cTVelocity) < Math.abs(targetLimVelocity)){
                double effectiveAcceleration = Math.copySign(maxAcceleration, distToLim); //Motion is directed towards concerned limit
                cTVelocity += effectiveAcceleration*elapsedSeconds;
                cTVelocity = Math.abs(cTVelocity) > cruiseVelocity ? Math.copySign(cruiseVelocity, targetLimVelocity) : cTVelocity;
                return cTVelocity;
            }
            if(Math.abs(cTVelocity) >= Math.abs(targetLimVelocity)){
                cTVelocity = targetLimVelocity;
                return  cTVelocity;
            }
        } else if(Math.abs(distToLim)<=Math.abs(decelDist)){
            double effectiveAcceleration = Math.copySign(maxAcceleration, -cTVelocity); //Acting against the motion to bring velocity to zero
            cTVelocity += effectiveAcceleration*elapsedSeconds;
            if((effectiveAcceleration<0 && cTVelocity <0) || (effectiveAcceleration>0 && cTVelocity >0)){
                cTVelocity = 0;
            }
            return cTVelocity;
        }
        return cTVelocity;
    }


    public double getDecelerationDist(){
        //Returns the distance required to decelerate to 0, allowing for proper regulation of the velocity curve
        return -(-Math.pow(cTVelocity, 2)/(2*maxAcceleration));
    }









    private void updateInternalValues(){
        //This function is used to track the motor's motion while certain control modes are not being used
        //This ensures that when we switch to these methods, they will be aware of the current motion and be able to handle it accordingly
        cTPosition = getMotorPosRad();
        cTVelocity = getMotorVelRad();
    }



    public double getMotorPosRad(){
        return ESPosVal; //Return smoothed output
    }

    public double getMotorVelRad(){
        return ESVelVal; //Return smoothed output
    }

    private void updateReadings(){
        //Used to update readings critical to operation
        updateLoopElapsedSeconds(); //Tracking loop time
        updatePosRadReadings(); //Getting position
        updateVelRadReadings(); //Getting velocity
        firstRunReadings = false; //First run is completed
    }

    private void updatePosRadReadings(){
        double cVal = encoderUnitsToRads(motor.getCurrentPosition());
        ESPosVal = expSmoothing(cVal, ESPosVal, alpha); //Used for smoothing the output to avoid instability
    }

    private void updateVelRadReadings(){
        if(firstRunReadings){
            lastPositionRad = encoderUnitsToRads(motor.getCurrentPosition());
            return; //Exit function to avoid evaluating distance over zero time
        }
        double cPos = encoderUnitsToRads(motor.getCurrentPosition());
        double cTime = System.currentTimeMillis();
        double elapsedTime = loopElapsedSeconds;
        double cVal = (cPos-lastPositionRad)/elapsedTime;

        ESVelVal = expSmoothing(cVal, ESVelVal, alpha); //Update smoothing

        lastPositionRad = cPos;
        lastTimeStamp = cTime;
    }

    private double expSmoothing(double currentValue, double runningValue, double alphaVal){
        //The output of this function should be used to update the runningValue
        return (alphaVal*currentValue) + ((1-alphaVal)*runningValue);
    }



    private void updateLoopElapsedSeconds(){
        //This function measures the time between runs
        if(firstRunReadings){
            lastTimeStamp = System.currentTimeMillis();
            return ;
        }
        loopElapsedSeconds = (System.currentTimeMillis()-lastTimeStamp)/1000.0;
        return;
    }



    public double encoderUnitsToRads(double _encUnits){
        return (_encUnits/encoderTicksPerRev)*2*Math.PI;
    }
}
