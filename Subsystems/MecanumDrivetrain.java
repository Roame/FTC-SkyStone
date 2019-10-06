package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import static org.firstinspires.ftc.teamcode.Constants.*;

public class MecanumDrivetrain {
    private DcMotor FR, FL, BR, BL;

    public MecanumDrivetrain(){}
  
    public void initMecanum(HardwareMap hw){
        FR = hw.get(DcMotor.class, kMecanumFRMotor);
        FL = hw.get(DcMotor.class, kMecanumFLMotor);
        BR = hw.get(DcMotor.class, kMecanumBRMotor);
        BL = hw.get(DcMotor.class, kMecanumBLMotor);

        FR.setDirection(DcMotor.Direction.FORWARD);
        FL.setDirection(DcMotor.Direction.REVERSE);
        BR.setDirection(DcMotor.Direction.FORWARD);
        BL.setDirection(DcMotor.Direction.REVERSE);
    }

    public void MecanumDrive(float LeftJoyStickX, float RightJoyStickX, float RightJoyStickY){
        //float[] inputs = correctJoysticks();

        float rotation = LeftJoyStickX;
        float strafe = RightJoyStickX;
        float drive = -RightJoyStickY;

        float FRPower = RightJoyStickY-RightJoyStickX-LeftJoyStickX;


        FR.setPower(Range.clip(RightJoyStickY-RightJoyStickX-LeftJoyStickX, -1.0, 1.0));
        FL.setPower(Range.clip(RightJoyStickY+RightJoyStickX+LeftJoyStickX, -1.0, 1.0));
        BR.setPower(Range.clip(RightJoyStickY+RightJoyStickX-LeftJoyStickX, -1.0, 1.0));
        BL.setPower(Range.clip(RightJoyStickY-RightJoyStickX+LeftJoyStickX, -1.0, 1.0));
    }

    //public float[] correctJoysticks(float)
}
