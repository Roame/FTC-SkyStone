package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Sensors.GyroSensor;
import org.firstinspires.ftc.teamcode.Subsystems.FoundationGrabber;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;

import static java.lang.Thread.sleep;


@Autonomous(name = "FoundationMover_ParkOutside_Blue")
public class FoundationMover_ParkOutside_Blue extends OpMode {
    public MecanumDrivetrain MecDrive = new MecanumDrivetrain();
    Telemetry.Item position = telemetry.addData("Position", 0);
    public ElapsedTime time = new ElapsedTime();
    public GyroSensor gyro = new GyroSensor();
    public FoundationGrabber foundationGrabber = new FoundationGrabber();
    public enum States {
        STEP1, STEP2, STEP3, STEP4, STEP5, STEP6, STEP7, STEP8, STEP9, STEP10, STEP11, STEP12, STEP13, STEP14, STEP15, STEP16, STEP17, STEP18, STEP19, STEP20
    }
    States state = States.STEP1;
    @Override
    public void init() {
        MecDrive.initMecanum(hardwareMap);
        gyro.GyroInit(hardwareMap);
        MecDrive.initEncoders();
        foundationGrabber.init(hardwareMap);

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
        MecDrive.MecanumSetMode(DcMotor.RunMode.RUN_TO_POSITION);
        MecDrive.SetTargetPosition(-3100);
        MecDrive.setPower(.4);

    }


    @Override
    public void loop() {
        gyro.readGyro();





        switch (state){
            case STEP1:


                if(MecDrive.EncoderEqualsTarget(5, -3100)){
                    MecDrive.setPower(0);
                    state=States.STEP2;
                    foundationGrabber.grab();
                    time.reset();
                    time.startTime();
                }

                break;

            case STEP2:
                foundationGrabber.grab();
                if(foundationGrabber.rightServo.getPosition()== Constants.kFGrabRightClosed && foundationGrabber.leftServo.getPosition()== Constants.kFGrabLeftClosed){





                    state = States.STEP3;
                    MecDrive.SetTargetPosition(-3200);
                    MecDrive.setPower(.8);
                    MecDrive.MecanumSetMode(DcMotor.RunMode.RUN_TO_POSITION);


                }


                break;

            case STEP3:

                if(MecDrive.EncoderEqualsTarget(5, -3200)){
                    state=States.STEP4;
                    MecDrive.SetTargetPosition(-1000);
                }



                break;

            case STEP4:

                if(MecDrive.EncoderEqualsTarget(5, -1000)){
                    MecDrive.setPower(0);
                    MecDrive.MecanumSetMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    state=States.STEP5;


                }



                break;

            case STEP5:
                MecDrive.MecanumGyroRotate(gyro.getZ(), 90);
                if(gyro.getZ()>88 && gyro.getZ()< 92){


                    MecDrive.initEncoders();
                    MecDrive.initEncoders();
                    MecDrive.SetTargetPosition(-700);
                    MecDrive.MecanumSetMode(DcMotor.RunMode.RUN_TO_POSITION);
                    MecDrive.setPower(.8);
                    state = States.STEP6;
                }



                break;

            case STEP6:
                if(MecDrive.EncoderEqualsTarget(5, -700)){
                    state=States.STEP7;
                    MecDrive.setPower(0);
                }




                break;

            case STEP7:
                foundationGrabber.open();
                if(foundationGrabber.getState()== FoundationGrabber.States.OPEN){
                    state=States.STEP8;
                    time.reset();
                    time.startTime();
                }






                break;

            case STEP8:
                if(time.milliseconds()>1000){
                    MecDrive.SetTargetPosition(1000);
                    MecDrive.setPower(.8);
                }
                if(MecDrive.EncoderEqualsTarget(5, 1000)){
                    MecDrive.setPower(0);
                    MecDrive.MecanumSetMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    state=States.STEP9;
                }





                break;
            case STEP9:
                MecDrive.MecanumGyroRotate(gyro.getZ(), 0);
                if(gyro.getZ()>-2 && gyro.getZ()<2){
                    MecDrive.initEncoders();
                    MecDrive.SetTargetPosition(550);
                    MecDrive.setPower(.8);
                    state=States.STEP10;
                }



                break;

            case STEP10:

                if(MecDrive.EncoderEqualsTarget(5, 550)){
                    MecDrive.setPower(0);
                    MecDrive.MecanumSetMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    state=States.STEP11;
                }






                break;

            case STEP11:

                MecDrive.MecanumGyroRotate(gyro.getZ(), 90);
                if(gyro.getZ()>89 && gyro.getZ()<91){
                    MecDrive.initEncoders();
                    MecDrive.SetTargetPosition(2200);
                    MecDrive.setPower(.8);
                    state=States.STEP12;
                }





                break;

            case STEP12:







                break;

        }







        telemetry.addData("State: ", state);
        telemetry.addData("gyro Z: ", gyro.getZ());
        telemetry.addData("Time: ", time.milliseconds());
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
        telemetry.addData("GrabberState: ", foundationGrabber.getState());
    }
}
