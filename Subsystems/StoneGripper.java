package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.teamcode.Constants.*;

public class StoneGripper {
    private Servo gripServo;
    public enum States {
        OPENED, CLOSED
    }
    private States cState = States.CLOSED;

    public StoneGripper(){}

    public void init (HardwareMap hardwareMap){
        gripServo = hardwareMap.get(Servo.class, kStoneGripperServo);
        openGripper();
    }

    public void closeGripper(){
        gripServo.setPosition(kGripperClosedVal);
        cState = States.CLOSED;
    }

    public void openGripper(){
        gripServo.setPosition(kGripperOpenVal);
        cState = States.OPENED;
    }

    public void toggleState(){
        if(cState==States.CLOSED){
            openGripper();
        } else if(cState==States.OPENED){
            closeGripper();
        }
    }

    public States getState(){
        return cState;
    }
}
