package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Test Program")
public class Testing extends OpMode {
    DcMotor FrontL;
    DcMotor FrontR;
    DcMotor BackL;
    DcMotor BackR



    @Override
    public void init() {
        FrontL = hardwareMap.get(DcMotor.class, "FrontL");

    }

    @Override
    public void loop() {
        FrontL.setPower(gamepad1.right_trigger);
        FrontR.setPower(gamepad1.right_trigger);
        BackL.setPower(gamepad1.right_trigger);
        BackR.setPower(gamepad1.right_trigger);}
}
