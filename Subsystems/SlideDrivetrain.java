package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;


import static org.firstinspires.ftc.teamcode.Constants.*;

public class SlideDrivetrain {
    private DcMotor LM, RM, CM;

    public SlideDrivetrain(){}

    public void initSlide(HardwareMap HW){
        LM = HW.get(DcMotor.class, kSlideLeftMotor);
        RM = HW.get(DcMotor.class, kSlideRightMotor);
        CM = HW.get(DcMotor.class, kSlideCenterMotor);

        LM.setDirection(DcMotor.Direction.FORWARD);
        RM.setDirection(DcMotor.Direction.FORWARD);
        CM.setDirection(DcMotor.Direction.FORWARD);
    }

    public void SlideDrive(double LeftJoystickX, double RightJoystickX, double RightJoystickY){
        LM.setPower(Range.clip(RightJoystickY+LeftJoystickX, -1.0, 1.0));
        RM.setPower(Range.clip(RightJoystickY-LeftJoystickX, -1.0, 1.0));
        CM.setPower(Range.clip(RightJoystickX, -1.0, 1.0));
    }
}
