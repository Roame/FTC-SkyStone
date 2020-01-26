package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Sensors.GyroSensor;

import static org.firstinspires.ftc.teamcode.Constants.*;

public class MecanumDrivetrain {
    private DcMotor FR, FL, BR, BL;

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