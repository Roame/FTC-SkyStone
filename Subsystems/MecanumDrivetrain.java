package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import static org.firstinspires.ftc.teamcode.Constants.*;

public class MecanumDrivetrain {
    private DcMotor FR, FL, BR, BL;
    private HardwareMap hw;

    public MecanumDrivetrain(HardwareMap hw){
        this.hw = hw;
    }
  
    public void initMecanum(){
        FR = hw.get(DcMotor.class, kMecanumFRMotor);
        FL = hw.get(DcMotor.class, kMecanumFLMotor);
        BR = hw.get(DcMotor.class, kMecanumBRMotor);
        BL = hw.get(DcMotor.class, kMecanumBLMotor);

        FR.setDirection(DcMotor.Direction.REVERSE);
        FL.setDirection(DcMotor.Direction.FORWARD);
        BR.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.FORWARD);
    }

    public void MecanumDrive(float LeftJoyStickX, float RightJoyStickX, float RightJoyStickY){
        FR.setPower(Range.clip(RightJoyStickY+RightJoyStickX-LeftJoyStickX, -1.0, 1.0));
        FL.setPower(Range.clip(RightJoyStickY+RightJoyStickX+LeftJoyStickX, -1.0, 1.0));
        BR.setPower(Range.clip(RightJoyStickY-RightJoyStickX-LeftJoyStickX, -1.0, 1.0));
        BL.setPower(Range.clip(RightJoyStickY-RightJoyStickX+LeftJoyStickX, -1.0, 1.0));
    }
}
