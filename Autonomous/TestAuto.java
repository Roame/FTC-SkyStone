package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Sensors.GyroSensor;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;

import java.io.BufferedReader;
import java.nio.channels.DatagramChannel;

@Autonomous(name = "Test Auto")
public class TestAuto extends OpMode {
    public MecanumDrivetrain MecDrive = new MecanumDrivetrain();
    Telemetry.Item position = telemetry.addData("Position", 0);
    public ElapsedTime time = new ElapsedTime();
//    public GyroSensor gyro = new GyroSensor();
    public enum States {
        STEP1, STEP2, STEP3, STEP4
    }
    States state = States.STEP1;
    @Override
    public void init() {
        MecDrive.initMecanum(hardwareMap);
//        gyro.GyroInit(hardwareMap);
        MecDrive.initEncoders();


        telemetry.addData("InitMode: ", MecDrive.FR.getMode());

    }

    @Override
    public void init_loop() {
        telemetry.addData("InitFR :", MecDrive.FR.getCurrentPosition());
        telemetry.addData("InitBR :", MecDrive.BR.getCurrentPosition());
        telemetry.addData("InitFL :", MecDrive.FL.getCurrentPosition());
        telemetry.addData("InitBL :", MecDrive.BL.getCurrentPosition());




        super.init_loop();
    }

    @Override
    public void start() {
        time.reset();
        time.startTime();
        telemetry.addData("Mode: ", MecDrive.FR.getMode());
        MecDrive.MecanumSetMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER );
        MecDrive.SetTargetPosition(100);
        MecDrive.MecanumSetMode(DcMotor.RunMode.RUN_TO_POSITION);
  //      MecDrive.setPower(1);

    }


    @Override
    public void loop() {
        //gyro.readGyro();





        switch (state){
            case STEP1:

//                MecDrive.MecanumSetMode(DcMotor.RunMode.RUN_USING_ENCODER);
//                MecDrive.setPower(1);
                //MecDrive.MecanumStraight(0.5);

                break;

            case STEP2:

                break;

            case STEP3:

                break;

        }







        telemetry.addData("State: ", state);
        telemetry.addData("FR Busy: ", MecDrive.FR.getPower()!=0);
        telemetry.addData("FL Busy: ", MecDrive.FL.getPower()!=0);
        telemetry.addData("BR Busy: ", MecDrive.BR.getPower()!=0);
        telemetry.addData("BL Busy: ", MecDrive.BL.getPower()!=0);
        telemetry.addData("FRMode: ", MecDrive.FR.getMode());
        telemetry.addData("FLMode: ", MecDrive.FL.getMode());
        telemetry.addData("BRMode: ", MecDrive.BR.getMode());
        telemetry.addData("BLMode: ", MecDrive.BL.getMode());
        telemetry.addData("FRTarget Pos: ", MecDrive.FR.getTargetPosition());
        telemetry.addData("FLTarget Pos: ", MecDrive.FL.getTargetPosition());
        telemetry.addData("BRTarget Pos: ", MecDrive.BR.getTargetPosition());
        telemetry.addData("BLTarget Pos: ", MecDrive.BL.getTargetPosition());
        telemetry.addData("FR POS", MecDrive.FR.getCurrentPosition());
        telemetry.addData("FL POS", MecDrive.FL.getCurrentPosition());
        telemetry.addData("BR POS", MecDrive.BR.getCurrentPosition());
        telemetry.addData("BL POS", MecDrive.BL.getCurrentPosition());
    }
}
