package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.Utility.ControlSystems.EncMotor;

import static org.firstinspires.ftc.teamcode.Constants.*;

public class StoneArmSystem {
    public EncMotor armMotor = new EncMotor(kArmMotor, 3892);

    public StoneArmSystem(){
    }

    public void init(HardwareMap hardwareMap){
        armMotor.init(hardwareMap);
        armMotor.configVelocityPID(kArmP, kArmI, kArmD);
        armMotor.setAcceleration(kArmAcceleration);
        armMotor.setEncoderLimits(kArmMinEncoder, kArmMaxEncoder);
        armMotor.setVelocityRamp(0.0);
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
        armMotor.update();
    }
}
