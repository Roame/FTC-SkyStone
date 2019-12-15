package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static org.firstinspires.ftc.teamcode.Constants.*;

public class StoneArmSystem {
    public DcMotor armMotor;

    public StoneArmSystem(){
    }

    public void init(HardwareMap hardwareMap){
        armMotor = hardwareMap.get(DcMotor.class, kArmMotor);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    double pTime, pPos, pScale;
    int tPos, runningError;
    boolean pHoldingState = false;
    public void scrollPosition(double rateScale, double timeSeconds){
        if(rateScale != 0 && !approachingBounds(rateScale)) {
            pHoldingState = false;
            int rate = 50; //Encoder ticks per second
            double kP = 0.000001, kI = 0.000005;

            if (pTime == 0) {
                pTime = timeSeconds;
            }

            if (pPos == 0) {
                pPos = armMotor.getCurrentPosition();
            }

            double deltaTime = timeSeconds - pTime;
            double deltaPos = armMotor.getCurrentPosition() - pPos;

            double targetRate = ((double) rate) * rateScale;
            double currentRate = (deltaPos / deltaTime);

            double error = targetRate - currentRate;
            runningError += (int) error;

            double power = error * kP + runningError * kI;
            armMotor.setPower(power);

        } else {
            if(!pHoldingState){
                tPos = armMotor.getCurrentPosition();
                runningError = 0;
                pHoldingState = true;
            }
            setPosition(tPos);
        }
        pTime = timeSeconds;
        pPos = armMotor.getCurrentPosition();
        pScale = rateScale;
    }


    public void setPosition(int targetPos){
        double power;
        double maxPower = 0.2;
        double minPower = 0.05;
        int errorTolerance = 10;
        double kP = 0.01;

        int error = targetPos-armMotor.getCurrentPosition();

        if(Math.abs(error)>errorTolerance){
            power = Math.copySign((Math.abs(error-errorTolerance)*kP), error)+minPower;
            power = Range.clip(power, -1, 1)*maxPower;
        } else {
            power = 0;
        }

        armMotor.setPower(power);
    }

    private boolean approachingBounds(double power){
        int cPos = armMotor.getCurrentPosition();

        boolean inUpperCutoff = Math.abs(kArmMaxEncoder-cPos)<=kArmCutOffVal;
        boolean inLowerCutoff = Math.abs(kArmMinEncoder-cPos)<=kArmCutOffVal;

        if(inUpperCutoff && power > 0 || inLowerCutoff && power < 0){
            return true;
        } else {
            return false;
        }
    }
}
