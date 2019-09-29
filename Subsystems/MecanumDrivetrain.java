package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.teamcode.Constants;

public class MecanumDrivetrain {
    private DcMotor FR, FL, BR, BL;
    private Constants c = new Constants();
    private HardwareMap hw;

    public MecanumDrivetrain(HardwareMap hw){
        this.hw = hw;
    }
  
    public void initMecanum(){
        FR = hw.get(DcMotor.class, c.kMecanumFRMotor);
        FL = hw.get(DcMotor.class, c.kMecanumFLMotor);
        BR = hw.get(DcMotor.class, c.kMecanumBRMotor);
        BL = hw.get(DcMotor.class, c.kMecanumBLMotor);

        FR.setDirection(DcMotor.Direction.REVERSE);
        FL.setDirection(DcMotor.Direction.FORWARD);
        BR.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.FORWARD);

    }

    public void MecanumDrive(float LeftJoyStickX, float RightJoyStickX, float RightJoyStickY){

        FR.setPower(Clip(-1, 1, RightJoyStickY+RightJoyStickX-LeftJoyStickX));
        FL.setPower(Clip(-1, 1, RightJoyStickY+RightJoyStickX+LeftJoyStickX));
        BR.setPower(Clip(-1, 1, RightJoyStickY-RightJoyStickX-LeftJoyStickX));
        BL.setPower(Clip(-1, 1, RightJoyStickY-RightJoyStickX+LeftJoyStickX));



    }
    private double Clip(double Min, double Max, double Value){
        double Clipped;
        if (Value>Max) {
            Clipped=Max;
        } else if (Value<Min){
            Clipped=Min;
        } else {
            Clipped=Value;
        }
        return Clipped;





    }


}
