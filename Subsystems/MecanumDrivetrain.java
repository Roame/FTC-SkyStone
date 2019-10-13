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

    public void MecanumDrive(float drive, float rotation, float strafe){
        float FRPower = Range.clip(drive-strafe-rotation, (float) -1.0, (float) 1.0);
        float FLPower = Range.clip(drive+strafe+rotation, (float) -1.0, (float) 1.0);
        float BRPower = Range.clip(drive+strafe-rotation, (float) -1.0, (float) 1.0);
        float BLPower = Range.clip(drive-strafe+rotation, (float) -1.0, (float) 1.0);

        FR.setPower(FRPower);
        FL.setPower(FLPower);
        BR.setPower(BRPower);
        BL.setPower(BLPower);
    }
}
