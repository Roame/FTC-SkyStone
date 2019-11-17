package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Utility.MecanumPower;
import org.firstinspires.ftc.teamcode.Utility.Vector2;

import static java.lang.Math.*;
import static org.firstinspires.ftc.teamcode.Constants.*;

public class MecanumDrivetrain {
    private DcMotor FR, FL, BR, BL;
    Telemetry telemetry;

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

    public void mecanumDrive(float drive, float strafe, float rotation){
        float FRPower = Range.clip(drive-strafe-rotation, (float) -1.0, (float) 1.0);
        float FLPower = Range.clip(drive+strafe+rotation, (float) -1.0, (float) 1.0);
        float BRPower = Range.clip(drive+strafe-rotation, (float) -1.0, (float) 1.0);
        float BLPower = Range.clip(drive-strafe+rotation, (float) -1.0, (float) 1.0);

        FR.setPower(FRPower);
        FL.setPower(FLPower);
        BR.setPower(BRPower);
        BL.setPower(BLPower);
    }

    public void mecanumDrive(MecanumPower power){
        FL.setPower(power.FLPower);
        FR.setPower(power.FRPower);
        BL.setPower(power.BLPower);
        BR.setPower(power.BRPower);
    }

    public void mecanumMappedDrive(Vector2 translation, double rotation){
        MecanumPower output;
        output = calcTranslation(translation);
        output = calcRotation(output, rotation);

        mecanumDrive(output);
    }



    public MecanumPower calcTranslation(Vector2 input) {
        Vector2 mappedVector = new Vector2(), wheelPowerVector = new Vector2();
        MecanumPower output = new MecanumPower();

        //Finding the distance between the center of an ellipse and its curve:
        //Ellipse is designed to round out lateral and longitudinal movements
        mappedVector.magnitude =
                (kLongitudinalSpeed*kLateralSpeed)/
                pow((pow(kLongitudinalSpeed*cos(toRadians(input.direction)), 2) + pow(kLateralSpeed*sin(toRadians(input.direction)), 2)), 0.5);

        mappedVector.magnitude *= input.magnitude; //This is done to scale the vector back to the original x and y proportions
        mappedVector.direction = input.direction; //Assignment of directionality

        //Magnitude is scaled by root 2 in order to maintain x and y components of the power relative to the robot:
        wheelPowerVector.magnitude = mappedVector.magnitude*pow(2,0.5);
        wheelPowerVector.direction = mappedVector.direction - 45; //Rotating vector by 45 degrees, CW.
        // This is done to mimic the direction in which mecanum wheels exert force

        //Assigning power to wheels:
        output.FLPower = output.BRPower = wheelPowerVector.getCoordinates().x;
        output.BLPower = output.FRPower = wheelPowerVector.getCoordinates().y;

        return output;
    }

    public MecanumPower calcRotation(MecanumPower power, double rotation){
        double rotationValue = kRotationSpeed*rotation;

        //Applying rotation
        power.FLPower += rotationValue;
        power.FRPower -= rotationValue;
        power.BLPower += rotationValue;
        power.BRPower -= rotationValue;

        //Check if any power exceeds 1 and if so correct the power sent to each wheel
        double maxPower = max(max(abs(power.FLPower), abs(power.FRPower)),max(abs(power.BLPower), abs(power.BRPower)));
        if(maxPower > 1){
            power.FLPower = copySign(abs(power.FLPower)-rotationValue, power.FLPower);
            power.FRPower = copySign(abs(power.FRPower)-rotationValue, power.FRPower);
            power.BLPower = copySign(abs(power.BLPower)-rotationValue, power.BLPower);
            power.BRPower = copySign(abs(power.BRPower)-rotationValue, power.BRPower);
        }

        return power;
    }
}