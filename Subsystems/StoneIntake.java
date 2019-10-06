package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Hardware;

import static org.firstinspires.ftc.teamcode.Constants.*;


public class StoneIntake {
    private DcMotor rightMotor, leftMotor;

    public StoneIntake(){}

    public void initStoneIntake(HardwareMap hw){
        leftMotor = hw.get(DcMotor.class, kIntakeLeftMotor);
        rightMotor = hw.get(DcMotor.class, kIntakeRightMotor);

        leftMotor.setDirection(DcMotor.Direction.REVERSE);
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
