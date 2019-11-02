package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="StillNotImportant",group="yes")
public class NotImportant extends OpMode {
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    private float maxSpeed = 0.5f;

    @Override
    public void init() {
        frontLeft = hardwareMap.get(DcMotor.class, "FL");
        frontRight = hardwareMap.get(DcMotor.class, "FR");
        backLeft = hardwareMap.get(DcMotor.class, "BL");
        backRight = hardwareMap.get(DcMotor.class, "BR");

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
    }
    @Override
    public void loop() {
        if (gamepad1.left_bumper){
            maxSpeed=0.25f;
        }else if (gamepad1.right_bumper){
            maxSpeed=1f;
        }else{
            maxSpeed=0.5f;
        }

        drive(-gamepad1.left_stick_y,gamepad1.right_stick_x,maxSpeed);



    }


    void drive(float forwardVal,float turnVal,float speed) {
        float rightVal = (forwardVal-turnVal)/(Math.abs(turnVal)+Math.abs(forwardVal));
        float leftVal = (forwardVal+turnVal)/(Math.abs(turnVal)+Math.abs(forwardVal));

        rightVal *= speed;
        leftVal *= speed;

        frontLeft.setPower(leftVal);
        backLeft.setPower(leftVal);
        frontRight.setPower(rightVal);
        backRight.setPower(rightVal);
>>>>>>> 16203f9d6065faccfaa32d150ca9caa61e1429e6
    }
}
