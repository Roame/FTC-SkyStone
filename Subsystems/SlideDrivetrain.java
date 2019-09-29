package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;

public class SlideDrivetrain {
    private DcMotor LM, RM, CM;
    private Constants c = new Constants();

    public SlideDrivetrain(){

    }

    public void initSlide(HardwareMap HW){
        LM = HW.get(DcMotor.class, c.kSlideCenterMotor);
        RM = HW.get(DcMotor.class, c.kSlideCenterMotor);
        CM = HW.get(DcMotor.class, c.kSlideCenterMotor);

        LM.setDirection(DcMotor.Direction.FORWARD);
        RM.setDirection(DcMotor.Direction.FORWARD);
        CM.setDirection(DcMotor.Direction.FORWARD);

    }

    public void SlideDrive(double LeftJoystickX, double RightJoystickX, double RightJoystickY){

        LM.setPower(Clip(-1, 1, RightJoystickY+LeftJoystickX));
        RM.setPower(Clip(-1, 1, RightJoystickY-LeftJoystickX));
        CM.setPower(Clip(-1, 1, RightJoystickX));

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
