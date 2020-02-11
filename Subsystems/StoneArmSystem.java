package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Utility.ControlSystems.EncMotor;

import static org.firstinspires.ftc.teamcode.Constants.*;

public class StoneArmSystem {
    public EncMotor armMotor = new EncMotor(kArmMotor, 3892);
    public Servo headServo;

    public enum HeadState {
        STRAIGHT, ROTATED
    }

    public HeadState cState = HeadState.STRAIGHT;
    public HeadState tState = HeadState.STRAIGHT;

    private double adjustmentPos = 0.0;

    public StoneArmSystem(){
    }

    public void init(HardwareMap hardwareMap){
        armMotor.init(hardwareMap);
        armMotor.configVelocityPID(kArmP, kArmI, kArmD);
        armMotor.setAcceleration(kArmAcceleration);
        armMotor.setEncoderLimits(kArmMinEncoder, kArmMaxEncoder);
        armMotor.setVelocityRamp(0.0);

        headServo = hardwareMap.get(Servo.class, kArmServo);
        straightenHead();
    }

    public void moveUp(){
        setVelocity(kArmMaxVelocity);
    }

    public void moveDown(){
        setVelocity(-kArmMaxVelocity);
    }

    public void holdPosition(){
        setVelocity(0.0);
    }

    public void setVelocity(double radPerSec){
        armMotor.setVelocityRamp(radPerSec);
    }

    public void update(){
        //General function that must be looped through to ensure proper control of the arm

        armMotor.update();

        //Designed to protect the servo from colliding with the arm
        if(armMotor.getCurrentPosition() >= kArmActivatedPosition) {
            if (tState == HeadState.ROTATED) {
                headServo.setPosition(kArmServoRotated + adjustmentPos);
            } else {
                headServo.setPosition(kArmServoStraight + adjustmentPos);
            }
        } else {
            straightenHead();
            adjustmentPos = 0; // Reset the adjustment
        }
    }



    public void rotateHead(){
        headServo.setPosition(kArmServoRotated);
        cState = HeadState.ROTATED;
    }

    public void straightenHead(){
        headServo.setPosition(kArmServoStraight);
        cState = HeadState.STRAIGHT;
    }

    public void toggleHead(){
        if(cState == HeadState.STRAIGHT){
            rotateHead();
        } else if (cState == HeadState.ROTATED){
            straightenHead();
        }
    }

    public void toggleHeadTarget(){
        if(tState == HeadState.STRAIGHT){
            tState = HeadState.ROTATED;
        } else if (tState == HeadState.ROTATED){
            tState = HeadState.STRAIGHT;
        }
    }

    public void adjustHeadDeg(double degree){
        adjustmentPos += degree*kArmServoDecPerDeg;
    }
}
