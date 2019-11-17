package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import static org.firstinspires.ftc.teamcode.Constants.*;

public class MecanumDrivetrain {
    private DcMotor FR, FL, BR, BL;
    ElapsedTime time = new ElapsedTime();

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

    public void initEncoders(){
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    public void SetTargetPosition(int target){
        FR.setTargetPosition(FR.getCurrentPosition()+target);

        FL.setTargetPosition(FL.getCurrentPosition()+target);

        BR.setTargetPosition(BR.getCurrentPosition()+target);

        BL.setTargetPosition(BL.getCurrentPosition()+target);

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

    public int InchToTick(Double Inches){
        return ((int)(Inches*kTickPerInch));
    }








}
