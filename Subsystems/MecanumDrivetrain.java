package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

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

    public void correctedDrive(float drive, float strafe, float rotation, double maxPower){
        double totalTranslation = drive+strafe;
        double percentDrive = drive/totalTranslation;
        double percentStrafe = strafe/totalTranslation;

        //First determining translation effect
        double FLPower = percentDrive + percentStrafe;
        double FRPower = percentDrive - percentStrafe;
        double BLPower = percentDrive - percentStrafe;
        double BRPower = percentDrive + percentStrafe;

        //Updating Internal Rotation
        if(pTime == 0){
            pTime = System.currentTimeMillis();
        }
        double deltaT = (System.currentTimeMillis()-pTime)/1000.0; //In seconds
        double scaledRotation = kTurnSpeed*rotation;
        targetHeading += deltaT*scaledRotation;

        //Determining rotation error and applying to drivetrain
        GyroSensor.getInstance().readGyro();
        double cHeading = GyroSensor.getInstance().getZ();
        double error = GyroSensor.getDifference(cHeading, targetHeading); //Positive output indicates the robot must turn CCW
        double percentRotAmp = kAllottedRotationPercent/2.0;
        double correctionPower = Range.clip(error*kAngleErrorCorrection, -percentRotAmp, percentRotAmp);

        FLPower = FLPower*(1-correctionPower) - correctionPower;
        FRPower = FRPower*(1-correctionPower) + correctionPower;
        BLPower = BLPower*(1-correctionPower) - correctionPower;
        BRPower = BRPower*(1-correctionPower) + correctionPower;

        FL.setPower(FLPower*maxPower);
        BL.setPower(BLPower*maxPower);
        BR.setPower(BRPower*maxPower);
        FR.setPower(FRPower*maxPower);
    }
}