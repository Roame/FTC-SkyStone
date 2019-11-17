package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.teamcode.Constants.*;

public class StoneGripper {
    private Servo gripServo;

    public StoneGripper(){}

    public void init (HardwareMap hardwareMap){
        gripServo = hardwareMap.get(Servo.class, kStoneGripperServo);
        gripServo.setPosition(kGripperOpenVal);
    }

    public void closeGripper(){
        gripServo.setPosition(kGripperClosedVal);
    }

    public void openGripper(){
        gripServo.setPosition(kGripperOpenVal);
    }
}
