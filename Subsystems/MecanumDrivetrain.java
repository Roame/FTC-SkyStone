package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Utility.MecInput;
import org.firstinspires.ftc.teamcode.Utility.MecanumPower;
import org.firstinspires.ftc.teamcode.Utility.Vector2;

import static org.firstinspires.ftc.teamcode.Constants.*;

public class MecanumDrivetrain {
    private DcMotor FR, FL, BR, BL;

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

    public void MecanumDrive(MecanumPower power){
        FL.setPower(power.FLPower);
        FR.setPower(power.FRPower);
        BL.setPower(power.BLPower);
        BR.setPower(power.BRPower);
    }

    public MecanumPower calcMecanumPower(MecInput input){
        MecanumPower output;
        output = calcTranslation(input);
        output = calcRotation(input, output);

        return output;
    }

    private MecanumPower calcTranslation(MecInput input) {
        MecanumPower output = new MecanumPower();
        //Converting input to a unit vector the first quadrant
        Vector2 vInput = new Vector2(Math.abs(input.TX), Math.abs(input.TY));

        //Mapping vInput to an ellipse
        Vector2 vOutput = new Vector2();
        vOutput.x = (float) (1 / (Math.pow(kStrafeSpeed, -2) + Math.pow(vInput.y / (vInput.x * kDriveSpeed), 2)));

        if (vInput.x == 0) {
            vOutput.y = 1;
        } else {
            vOutput.y = (vInput.y / vInput.x) * vOutput.x;
        }

        //Reflecting point back to the correct quadrant
        vOutput.x = Math.copySign(vOutput.x, vInput.x);
        vOutput.y = Math.copySign(vOutput.y, vInput.y);

        //Rotating point 45 degress CW
        vOutput.x = (float) (vOutput.x*Math.cos(Math.PI/4) + vOutput.y*Math.sin(Math.PI/4));
        vOutput.y = (float) (vOutput.x*-Math.sin(Math.PI/4) + vOutput.y*Math.cos(Math.PI/4));

        //Take the x and y components and move them to mecanum power for each wheel
        output.FLPower = output.BRPower = vOutput.x;
        output.FRPower = output.BLPower = vOutput.y;

        return output;
    }

    private MecanumPower calcRotation(MecInput input, MecanumPower power){
        //Applying rotation
        power.FLPower += kRotationSpeed;
        power.FRPower -= kRotationSpeed;
        power.BLPower += kRotationSpeed;
        power.BRPower -= kRotationSpeed;

        //Trimming power back into the expected range
        power.FLPower = Range.clip(power.FLPower, -1.0f, 1.0f);
        power.FRPower = Range.clip(power.FRPower, -1.0f, 1.0f);
        power.BLPower = Range.clip(power.BLPower, -1.0f, 1.0f);
        power.BRPower = Range.clip(power.BRPower, -1.0f, 1.0f);

        return power;
    }
}