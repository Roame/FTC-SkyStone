package org.firstinspires.ftc.teamcode.Utility.ControlSystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class EncMotor {

    DcMotor motor;
    String name;
    PIDController positionPID, velocityPID;
    int upperBound, lowerBound;
    double cruiseVelocity, cRampVelocity, acceleration = 0, accelStartTime = 0, initVelocity=0;
    boolean accelerationStarted = false, accelerating = false;
    double pTime, cVelocity;
    int pPos;
    int motorTicksPerRev;
    private enum States {
        CRUISING, APPROACHING_UPPERBOUND, APPROACHING_LOWERBOUND
    }

    public EncMotor(String name, int ticksPerRev){
        this.name = name;
        motorTicksPerRev = ticksPerRev;
    }

    private enum Mode {
        POWER, DEF_POS, PID_POS, PID_VEL, RAMP_VEL
    }

    Mode mode;

    public void init(HardwareMap hw){
        motor = hw.get(DcMotor.class, name);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setDirection(DcMotor.Direction.FORWARD);
    }

    public void configPositionPID(double P, double I, double D){
        if(positionPID == null) {
            positionPID = new PIDController();
        }
        positionPID.setPID(P,I,D);
    }

    public void setPositionPID(double radians){
        positionPID.setTarget(radsToEncoderTicks(radians));
        mode = Mode.PID_POS;
    }

    public void configVelocityPID(double P, double I, double D){
        if(velocityPID == null) {
            velocityPID = new PIDController();
        }
        velocityPID.setPID(P,I,D);
    }

    public void setVelocityPID(double radiansPerSec){
        velocityPID.setTarget(radsPerSecToTicksPerFrame(radiansPerSec));
        mode = Mode.PID_VEL;
    }

    public void setVelocityRamp(double radiansPerSec){
        double convertedSpeed = radsPerSecToTicksPerFrame(radiansPerSec);
        velocityPID.setReset(false);
        States cState = getState();
        if(cState == States.CRUISING || (cState == States.APPROACHING_LOWERBOUND && radiansPerSec>=0) || (cState==States.APPROACHING_UPPERBOUND && radiansPerSec<=0)) {
            //Only run what is in here when the input won't cause the motor to pass the set bounds.
            if(cruiseVelocity != convertedSpeed) {
                cruiseVelocity = convertedSpeed;
                initVelocity = cRampVelocity; //This is done in order to make transition from one acceleration to another smoother
                acceleration = Math.copySign(acceleration, cruiseVelocity-initVelocity); //Set directionality of the acceleration
                accelerationStarted = false;
            }
        }
        mode = Mode.RAMP_VEL;
    }

    public void update(){
        switch (mode){
            case PID_POS:
                positionPID.update(motor.getCurrentPosition());
                motor.setPower(positionPID.getOutput());
                break;

            case PID_VEL:
                velocityPID.update(getVelocity());
                motor.setPower(velocityPID.getOutput());
                break;

            case RAMP_VEL:
                States cState = getState();
                if(cState != States.CRUISING && ((cruiseVelocity > 0 && cState == States.APPROACHING_UPPERBOUND)||(cruiseVelocity < 0 && cState == States.APPROACHING_LOWERBOUND))){
                    setVelocityRamp(0.0); //Try to accelerate to zero before passing bound

                }

                if(!accelerationStarted){ //removed && !accelerating as this doesn't apply
                    accelerationStarted = true;
                    accelStartTime = System.currentTimeMillis();
                    accelerating = true;
                }

                if(accelerating){
                    double runningTime = (System.currentTimeMillis()-accelStartTime)/velocityPID.getFrameLength(); //Calculate running time and convert to frames
                    cRampVelocity = initVelocity+(acceleration*runningTime);
                    if((cRampVelocity>cruiseVelocity && acceleration >0) || (cRampVelocity<cruiseVelocity && acceleration < 0)){
                         cRampVelocity = cruiseVelocity;
                         accelerating = false;
                    }
                    velocityPID.setTarget(cRampVelocity);
                }

                velocityPID.update(getVelocity());
                motor.setPower(velocityPID.getOutput());
                break;



            default:
                //Do nothing
                break;
        }
    }




    public void setPower(double power){
        motor.setPower(power);
        mode = Mode.POWER;
    }

    public void setDirection(DcMotor.Direction direction){
        motor.setDirection(direction);
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior){
        motor.setZeroPowerBehavior(behavior);
    }

    public void setMode(DcMotor.RunMode runMode){
        motor.setMode(runMode);
    }

    public void setTargetPos(int target){
        motor.setTargetPosition(target);
        mode = Mode.DEF_POS;
    }


    public int getCurrentPosition(){
        return motor.getCurrentPosition();
    }

    public double getVelocity(){
        double cTime = System.currentTimeMillis();
        if(pTime == 0){
            pTime = cTime;
        }
        double deltaT = cTime-pTime;
        if(deltaT >= velocityPID.getFrameLength()){
            deltaT /= velocityPID.getFrameLength(); //convert to ms
            int cPos = motor.getCurrentPosition();
            cVelocity = (((double)(cPos-pPos))/deltaT);
            pPos = cPos;
            pTime = cTime;
        }
        return cVelocity;
    }

    public void setAcceleration(double radsPerSecPerSec){
        acceleration = radsPerSecPerSecToTicksPerFramePerFrame(radsPerSecPerSec);
    }

    public void setEncoderLimits(int minimumEncoder, int maximumEncoder){
        this.lowerBound = minimumEncoder;
        this.upperBound = maximumEncoder;
    }


    private States getState(){
        if(upperBound-motor.getCurrentPosition() < 0){
            return States.APPROACHING_UPPERBOUND;
        } else if (motor.getCurrentPosition()-lowerBound < 0){
            return  States.APPROACHING_LOWERBOUND;
        } else {
            double velocity = cRampVelocity;
            double deceleration;
            if (velocity == 0) {
                deceleration = acceleration; //Just in case velocity equals 0, could cause confusion otherwise
            } else {
                deceleration = Math.copySign(acceleration, -velocity);
            }
            double distance;

            States concernedBound = States.CRUISING;
            int bound;
            if (velocity > 0) {
                bound = upperBound;
                concernedBound = States.APPROACHING_UPPERBOUND;
            } else if (velocity < 0) {
                bound = lowerBound;
                concernedBound = States.APPROACHING_LOWERBOUND;
            }
            distance = Math.abs((-velocity * velocity) / (2.0 * deceleration));

            double distanceLeft = getDistToBound() - distance;
            if (distanceLeft <= 0) {
                return concernedBound;
            } else {
                return States.CRUISING;
            }
        }
    }

    private int getDistToBound(){
        double velocity = getVelocity();
        if(velocity>0){
            return upperBound-motor.getCurrentPosition();
        } else if (velocity < 0){
            return motor.getCurrentPosition()-lowerBound;
        } else {
            return 10000; //Placeholder value
        }
    }


    private int radsToEncoderTicks(double radians){
        return (int)(radians*(motorTicksPerRev/(2.0*Math.PI)));
    }

    private int radsPerSecToTicksPerFrame(double radsPS){
        return (int)(radsPS*(motorTicksPerRev/(2.0*Math.PI))*(velocityPID.getFrameLength()/1000.0));
    }

    private double radsPerSecPerSecToTicksPerFramePerFrame(double radsPSPS){
        return (radsToEncoderTicks(radsPSPS)*Math.pow((velocityPID.getFrameLength()/1000.0),2.0));
    }
}
