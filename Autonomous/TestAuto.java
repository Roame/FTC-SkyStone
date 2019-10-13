package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.io.BufferedReader;

@Autonomous(name = "Test Auto")
public class TestAuto extends OpMode {
    DcMotor FR;
    DcMotor FL;
    DcMotor BR;
    DcMotor BL;
    ElapsedTime time = new ElapsedTime();





    @Override
    public void init() {
        FR = hardwareMap.get(DcMotor.class, "FR");
        FL = hardwareMap.get(DcMotor.class, "FL");
        BR = hardwareMap.get(DcMotor.class, "BR");
        BL = hardwareMap.get(DcMotor.class, "BL");
        time.reset();




    }


    @Override
    public void start(){
        time.startTime();
    }



    @Override
    public void loop() {
    if(time.seconds()<5){
        FR.setPower(0.5);
        FL.setPower(0.5);
        BR.setPower(0.5);
        BL.setPower(0.5);
    }
    if(time.seconds()>5 && time.seconds()<10){
        FR.setPower(0);
        FL.setPower(0);
        BR.setPower(0);
        BL.setPower(0);
    }
    if(time.seconds()<15 && time.seconds()>10){
        FR.setPower(0.5);
        FL.setPower(-0.5);
        BR.setPower(0.5);
        BL.setPower(-0.5);
    }
    if(time.seconds()>15){
        FR.setPower(0);
        FL.setPower(0);
        BR.setPower(0);
        BL.setPower(0);

    }




    }
}
