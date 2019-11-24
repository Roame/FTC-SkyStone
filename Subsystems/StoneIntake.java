package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import static org.firstinspires.ftc.teamcode.Constants.*;


public class StoneIntake {
    private DcMotor rightMotor, leftMotor;

    public StoneIntake(){}

    public void init(HardwareMap hw){
        leftMotor = hw.get(DcMotor.class, kIntakeLeftMotor);
        rightMotor = hw.get(DcMotor.class, kIntakeRightMotor);

        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);

        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        stop();
    }

    public void collect(){
        intakePower(kIntakePower);
    }

    public void reverse(){
        intakePower(-kIntakePower);
    }

    public void stop(){
        intakePower(0);
    }

    public void intakePower(double power){
        leftMotor.setPower(power);
        rightMotor.setPower(power);
    }
}
