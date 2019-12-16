package org.firstinspires.ftc.teamcode.Utility.ControlSystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class EncMotor {

    DcMotor motor;
    String name;
    PIDController positionPID, velocityPID;
    double cruiseVelocity, acceleration;
    double pTime, cVelocity;
    int pPos;
    int motorTicksPerRev;

    public EncMotor(String name, int ticksPerRev){
        this.name = name;
        motorTicksPerRev = ticksPerRev;
    }

    private enum Mode {
        POWER, DEF_POS, PID_POS, PID_VEL
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
        velocityPID.setTarget(radsPerSecToTicksPer100ms(radiansPerSec));
        mode = Mode.PID_VEL;
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
        if(deltaT >= 100){
            deltaT /= 100.0; //convert to ms
            cVelocity = (((double)(motor.getCurrentPosition()-pPos))/deltaT);
        }
        return cVelocity;
    }


    private int radsToEncoderTicks(double radians){
        return (int)(radians*(motorTicksPerRev/(2.0*Math.PI)));
    }

    private int radsPerSecToTicksPer100ms(double radsPS){
        return (int)(radsPS*(motorTicksPerRev/(2.0*Math.PI))*(0.1));
    }
}
