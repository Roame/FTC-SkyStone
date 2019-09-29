package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecanumDrive {
    private DcMotor FR, FL, BR, BL;




    public MecanumDrive(){

    }
    public void InitDrive(HardwareMap HW){
        FR = HW.get(DcMotor.class, "FR");
        FL = HW.get(DcMotor.class, "FL");
        BR = HW.get(DcMotor.class, "BR");
        BL = HW.get(DcMotor.class, "BL");
        FR.setDirection(DcMotor.Direction.REVERSE);
        FL.setDirection(DcMotor.Direction.FORWARD);
        BR.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.FORWARD);

    }

    public void Drive(float LeftJoyStickX, float RightJoyStickX, float RightJoyStickY){

        FR.setPower(Clip(-1, 1, RightJoyStickY+RightJoyStickX-LeftJoyStickX));
        FL.setPower(Clip(-1, 1, RightJoyStickY+RightJoyStickX+LeftJoyStickX));
        BR.setPower(Clip(-1, 1, RightJoyStickY-RightJoyStickX-LeftJoyStickX));
        BL.setPower(Clip(-1, 1, RightJoyStickY-RightJoyStickX+LeftJoyStickX));



    }
    private double Clip(double Min, double Max, double Value){
        double Cliped;
        if (Value>Max) {
            Cliped=Max;
        } else if (Value<Min){
            Cliped=Min;
        } else {
            Cliped=Value;
        }
        return Cliped;





    }


}
