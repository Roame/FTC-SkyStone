package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.teamcode.Constants.*;

public class FoundationGrabber {
    Servo leftServo, rightServo;
    public enum States{
        OPEN, CLOSED
    }
    States state;

    public FoundationGrabber(){}

    public void init(HardwareMap hardwareMap){
        leftServo = hardwareMap.get(Servo.class, kFGrabberLeftServo);
        rightServo = hardwareMap.get(Servo.class, kFGrabberRightServo);

        open();
    }

    public void grab(){
        leftServo.setPosition(kFGrabLeftClosed);
        rightServo.setPosition(kFGrabRightClosed);
        state = States.CLOSED;
    }

    public void open(){
        leftServo.setPosition(kFGrabLeftOpen);
        rightServo.setPosition(kFGrabRightOpen);
        state = States.OPEN;
    }

    public void toggleState(){
        if(state == States.OPEN){
            grab();
        } else if(state == States.CLOSED){
            open();
        }
    }

    public States getState(){
        return state;
    }

}

