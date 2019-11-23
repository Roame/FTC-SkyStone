package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import static org.firstinspires.ftc.teamcode.Constants.*;

public class StoneArmSystem {
    ElapsedTime time;
    double previousTime = 0;

    DcMotor armMotor;

    double cPower = 0;

    public StoneArmSystem(){
    }

    public void init(HardwareMap hardwareMap){
        armMotor = hardwareMap.get(DcMotor.class, kArmMotor);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
        } else if (targetPower < cPower) {
            cPower -= rampRate * elapsedTime;
        }
    }

    private boolean approachingBounds(double targetPower){
        int cPos = armMotor.getCurrentPosition();

        boolean inUpperCutoff = Math.abs(kArmMaxEncoder-cPos)<=kArmCutOffVal;
        boolean inLowerCutoff = Math.abs(kArmMinEncoder-cPos)<=kArmCutOffVal;

        if(inUpperCutoff && targetPower < 0 || inLowerCutoff && targetPower >0){
            return true;
        } else {
            return false;
        }
    }


}
