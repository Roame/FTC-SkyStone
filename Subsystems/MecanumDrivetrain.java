package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Utility.MecInput;
import org.firstinspires.ftc.teamcode.Utility.MecanumPower;
import org.firstinspires.ftc.teamcode.Utility.Vector2;

import static org.firstinspires.ftc.teamcode.Constants.*;

public class MecanumDrivetrain {
    public DcMotor FR, FL, BR, BL;
    ElapsedTime time = new ElapsedTime();

    public MecanumDrivetrain(){}

    public void initMecanum(HardwareMap hw){
        FR = hw.get(DcMotor.class, kMecanumFRMotor);
        FL = hw.get(DcMotor.class, kMecanumFLMotor);
        BR = hw.get(DcMotor.class, kMecanumBRMotor);
        BL = hw.get(DcMotor.class, kMecanumBLMotor);

        FR.setDirection(DcMotor.Direction.REVERSE);
        FL.setDirection(DcMotor.Direction.FORWARD);
        BR.setDirection(DcMotor.Direction.FORWARD);
        BL.setDirection(DcMotor.Direction.REVERSE);
    }

    public void reverseWheels(){
        FR.setDirection(DcMotor.Direction.FORWARD);
        FL.setDirection(DcMotor.Direction.REVERSE);
        BR.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.FORWARD);
    }

    public void initEncoders(){
        FR.setTargetPosition(0);
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FL.setTargetPosition(0);
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        BR.setTargetPosition(0);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        BL.setTargetPosition(0);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION );

    }

    public void setPower(double power){
        FR.setPower(power);
        FL.setPower(power);
        BR.setPower(power);
        BL.setPower(power);


    }

    public void SetTargetPosition(int target){
        FR.setTargetPosition(target);

        FL.setTargetPosition(target);

        BR.setTargetPosition(target);

        BL.setTargetPosition(target);
    }

    public void mecanumDrive(float drive, float strafe, float rotation, double maxPower){
        float FRPower = Range.clip(drive-strafe-rotation, (float) -1.0, (float) 1.0)*((float) maxPower);
        float FLPower = Range.clip(drive+strafe+rotation, (float) -1.0, (float) 1.0)*((float) maxPower);
        float BRPower = Range.clip(drive+strafe-rotation, (float) -1.0, (float) 1.0)*((float) maxPower);
        float BLPower = Range.clip(drive-strafe+rotation, (float) -1.0, (float) 1.0)*((float) maxPower);

        FR.setPower(FRPower);
        FL.setPower(FLPower);
        BR.setPower(BRPower);
        BL.setPower(BLPower);
    }

    public void MecanumStraight(double Speed){
        FR.setPower(Speed);
        FL.setPower(Speed);
        BR.setPower(Speed);
        BL.setPower(Speed);
    }

    public void MecanumStrafe(double Speed){
        FR.setPower(-Speed);
        FL.setPower(Speed);
        BR.setPower(Speed);
        BL.setPower(-Speed);
    }
    public void MecanumRotate(double Speed){
        FR.setPower(-Speed);
        FL.setPower(+Speed);
        BR.setPower(-Speed);
        BL.setPower(+Speed);
    }
    public void MecanumGyroStraight(double Speed, double gyroAngle, double targetGyroAngle){
        FR.setPower(Speed+(FindRotationAngle(gyroAngle, targetGyroAngle)*kGyroSensitivity));
        FL.setPower(Speed-(FindRotationAngle(gyroAngle, targetGyroAngle)*kGyroSensitivity));
        BR.setPower(Speed+(FindRotationAngle(gyroAngle, targetGyroAngle)*kGyroSensitivity));
        BL.setPower(Speed-(FindRotationAngle(gyroAngle, targetGyroAngle))*kGyroSensitivity);
    }
    public void MecanumGyroStrafe(double Speed, double gyroAngle, double targetGyroAngle){
            FR.setPower(-Speed + (FindRotationAngle(gyroAngle, targetGyroAngle) * kGyroSensitivity));
            FL.setPower(Speed - (FindRotationAngle(gyroAngle, targetGyroAngle) * kGyroSensitivity));
            BR.setPower(Speed + (FindRotationAngle(gyroAngle, targetGyroAngle) * kGyroSensitivity));
            BL.setPower(-Speed - (FindRotationAngle(gyroAngle, targetGyroAngle) * kGyroSensitivity));
    }
    public double FindRotationAngle(double current, double target){
        double absDifference = Math.abs(current-target);
        if(absDifference<180) {
            return target-current;
        } else {
            return 360-(current+target);
        }
    }
    public void MecanumGyroRotate(double gyroAngle, double targetGyroAngle){
        if(Math.abs(0.5*(FindRotationAngle(gyroAngle, targetGyroAngle)) * kGyroSensitivity)>kGyroDeadzone) {
            FR.setPower(0.5*(FindRotationAngle(gyroAngle, targetGyroAngle) * kGyroSensitivity));
            FL.setPower(-0.5*(FindRotationAngle(gyroAngle, targetGyroAngle) * kGyroSensitivity));
            BR.setPower(0.5*(FindRotationAngle(gyroAngle, targetGyroAngle) * kGyroSensitivity));
            BL.setPower(-0.5*(FindRotationAngle(gyroAngle, targetGyroAngle) * kGyroSensitivity));
        } else {
            FR.setPower(0);
            FL.setPower(0);
            BR.setPower(0);
            BL.setPower(0);
        }
    }
    public void MecanumSetMode(DcMotor.RunMode mode){
        FR.setMode(mode);
        FL.setMode(mode);
        BR.setMode(mode);
        BL.setMode(mode);

    }

    public int InchToTick(Double Inches){
        return ((int)(Inches*kTickPerInch));
    }

    public boolean EncoderEqualsTarget(double tolerance, double target) {
        double sumOfPos = FR.getCurrentPosition() + FL.getCurrentPosition() + BR.getCurrentPosition() + BL.getCurrentPosition();
        return sumOfPos > (target - tolerance) * 4 && sumOfPos < (target + tolerance) * 4;
    }
}
