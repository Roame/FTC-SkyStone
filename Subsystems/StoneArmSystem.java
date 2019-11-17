package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import static org.firstinspires.ftc.teamcode.Constants.*;

public class StoneArmSystem {
    HardwareMap hardwareMap;

    DcMotor armMotor;

    public StoneArmSystem(){
    }

    public void init(HardwareMap hardwareMap){
        armMotor = hardwareMap.get(DcMotor.class, kArmMotor);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void moveArm(int updateTicks){
        int currentPos = armMotor.getCurrentPosition();
    }


}
