package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

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

    private boolean approachingBounds(double targetPower){
        int cPos = armMotor.getCurrentPosition();

        boolean inUpperCutoff = Math.abs(kArmMaxEncoder-cPos)<=kArmCutOffVal;
        boolean inLowerCutoff = Math.abs(kArmMinEncoder-cPos)<=kArmCutOffVal;

        if(inUpperCutoff && targetPower < 0 || inLowerCutoff && targetPower > 0){
            return true;
        } else {
            return false;
        }
    }

    int runningError, runningPower, cTargetPos;
    public void setPosition(int targetPos){
        if(cTargetPos!=targetPos){
            runningError = 0;
        }
        cTargetPos = targetPos;

        int error = cTargetPos-armMotor.getCurrentPosition();
        runningError +=error;
        double power = (((double) error)/500.0)+(runningError/10000);


        if(Math.abs(power) < 0.01){
            power = 0.0;
        }

        item.setValue(power*.5);
        armMotor.setPower(power*.5);
    }


}
