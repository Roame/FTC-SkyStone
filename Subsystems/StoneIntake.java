package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;

public class StoneIntake {
    private Constants c = new Constants();
    private DcMotor rightMotor, leftMotor;
    HardwareMap hwmap;

    public StoneIntake(HardwareMap hw){
        this.hwmap = hw;
    }

    public void initStoneIntake(){
        leftMotor = hwmap.get(DcMotor.class, c.kIntakeLeftMotor);
        rightMotor = hwmap.get(DcMotor.class, c.kIntakeRightMotor);

        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);

        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }

    public void intakePower(double power){
        leftMotor.setPower(power);
        rightMotor.setPower(power);
    }
}
