package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static org.firstinspires.ftc.teamcode.Constants.*;

public class StoneArmSystem {
    ElapsedTime time;
    Telemetry.Item item;

    double previousTime = 0;

    public DcMotor armMotor;

    double cPower = 0;

    public StoneArmSystem(Telemetry.Item item){
        this.item = item;
        item.setValue("Instanced");
    }

    public void init(HardwareMap hardwareMap){
        armMotor = hardwareMap.get(DcMotor.class, kArmMotor);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setTargetPower(double targetPower){
        if(time == null){
            time = new ElapsedTime();
            time.reset();
        }

        targetPower *= kArmMaxSpeed;

        double elapsedTime = time.seconds()-previousTime;
        previousTime = time.seconds();

        if(!approachingBounds(targetPower)) {
            rampPowerTo(targetPower, elapsedTime);
        } else {
            rampPowerTo(0, elapsedTime);
        }
    }

    private void rampPowerTo(double targetPower, double elapsedTime){
        if (targetPower > cPower) {
            cPower += rampRate * elapsedTime;
            if(cPower > targetPower){
                cPower = targetPower;
            }
        } else if (targetPower < cPower) {
            cPower -= rampRate * elapsedTime;
            if(cPower < targetPower){
                cPower = targetPower;
            }
        }
        item.setValue(cPower);
        armMotor.setPower(cPower);
    }

    private boolean approachingBounds(double direction){
        int cPos = armMotor.getCurrentPosition();

        boolean inUpperCutoff = Math.abs(kArmMaxEncoder-cPos)<=kArmCutOffVal;
        boolean inLowerCutoff = Math.abs(kArmMinEncoder-cPos)<=kArmCutOffVal;

        if(inUpperCutoff && direction > 0 || inLowerCutoff && direction < 0){
            return true;
        } else {
            return false;
        }
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
            item.setValue(currentRate);

            double power = error * kP + runningError * kI;
            armMotor.setPower(power);
            item.setValue(power);

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
        double maxPower = 0.2;
        double minPower = 0.05;
        int errorTolerance = 10;
        double kP = 0.01;

        int error = targetPos-armMotor.getCurrentPosition();

        double power = (((double) error)*kP);
        power = Range.clip(power, -1, 1)*maxPower;

        if(Math.abs(error)>errorTolerance){
            power = Math.copySign((Math.abs(error-errorTolerance)*kP), error)+minPower;
            power = Range.clip(power, -1, 1)*maxPower;
        } else {
            power = 0;
        }

        item.setValue(error);
        armMotor.setPower(power);
    }


}
