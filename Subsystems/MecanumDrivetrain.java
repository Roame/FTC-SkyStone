package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Sensors.GyroSensor;

import static org.firstinspires.ftc.teamcode.Constants.*;

public class MecanumDrivetrain {
    public DcMotor FR, FL, BR, BL;
    ElapsedTime time = new ElapsedTime();

    public double targetHeading = 0;
    private double pTime;


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

    public void correctedDrive(float drive, float strafe, float rotation, double maxPower, boolean autoCorrection){
        double totalTranslation = Math.abs(drive)+Math.abs(strafe);
        double percentDrive = (drive/totalTranslation)*Math.abs(drive); //Determining percent contribution and then scaling back based on user input
        double percentStrafe = (strafe/totalTranslation)*Math.abs(strafe);

        //First determining translation effect
        double FLPower = percentDrive + percentStrafe;
        double FRPower = percentDrive - percentStrafe;
        double BLPower = percentDrive - percentStrafe;
        double BRPower = percentDrive + percentStrafe;

        double correctionPower;
        if(autoCorrection) {
            //Updating Internal Rotation
            double cTime = System.currentTimeMillis();
            if (pTime == 0) {
                pTime = System.currentTimeMillis();
            }
            double deltaT = (cTime - pTime) / 1000.0; //In seconds
            pTime = cTime;
            double scaledRotation = -kTurnSpeed * rotation;
            targetHeading += deltaT * scaledRotation;
            targetHeading %= 360; //Reducing back to 360 degrees
            if (Math.abs(targetHeading) > 180) {
                targetHeading -= (Math.copySign(360, targetHeading)); //Making target heading compatible with gyro output
            }
            //Determining rotation error and applying to drivetrain
            GyroSensor.getInstance().readGyro();
            double cHeading = GyroSensor.getInstance().getZ();
            double error = GyroSensor.getDifference(cHeading, targetHeading); //Positive output indicates the robot must turn CCW
            double percentRotAmp = kAllottedRotationPercent/2.0;
            correctionPower = Range.clip(error*kAngleErrorCorrection, -percentRotAmp, percentRotAmp);
        } else {
            GyroSensor.getInstance().readGyro();
            targetHeading = GyroSensor.getInstance().getZ();
            correctionPower = -(kAllottedRotationPercent/2.0)*rotation;
        }

        //Experienced issues when any of the powers were ~zero, hence the odd coding...
        if(Math.abs(FLPower) >0.05 || Math.abs(FRPower) >0.05 || Math.abs(BLPower) >0.05 || Math.abs(BRPower) >0.05) {
            FLPower = (FLPower * (1.0 - Math.abs(correctionPower))) - correctionPower;
            FRPower = (FRPower * (1.0 - Math.abs(correctionPower))) + correctionPower;
            BLPower = (BLPower * (1.0 - Math.abs(correctionPower))) - correctionPower;
            BRPower = (BRPower * (1.0 - Math.abs(correctionPower))) + correctionPower;
        } else {
            FLPower = -correctionPower;
            FRPower = correctionPower;
            BLPower = -correctionPower;
            BRPower = correctionPower;
        }

        FL.setPower(FLPower*maxPower);
        BL.setPower(BLPower*maxPower);
        BR.setPower(BRPower*maxPower);
        FR.setPower(FRPower*maxPower);
    }
}
