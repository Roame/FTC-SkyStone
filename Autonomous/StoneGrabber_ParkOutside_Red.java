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
import org.firstinspires.ftc.teamcode.Subsystems.StoneIntake;

import static java.lang.Thread.sleep;


@Autonomous(name = "StoneGrabber_ParkOutside_Red")
public class StoneGrabber_ParkOutside_Red extends OpMode {
    public MecanumDrivetrain MecDrive = new MecanumDrivetrain();
    Telemetry.Item position = telemetry.addData("Position", 0);
    public ElapsedTime time = new ElapsedTime();
    public GyroSensor gyro = new GyroSensor();
    public StoneIntake stoneIntake = new StoneIntake();
    public enum States {
        STEP1, STEP2, STEP3, STEP4, STEP5, STEP6, STEP7, STEP8, STEP9, STEP10, STEP11, STEP12, STEP13, STEP14, STEP15, STEP16, STEP17, STEP18, STEP19, STEP20
    }
    States state = States.STEP1;
    @Override
    public void init() {
        MecDrive.initMecanum(hardwareMap);
        gyro.GyroInit(hardwareMap);
        MecDrive.initEncoders();
        stoneIntake.init(hardwareMap);
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
        MecDrive.MecanumSetMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        MecDrive.initEncoders();
        MecDrive.SetTargetPosition(2300);
        MecDrive.setPower(.75);
        stoneIntake.collect();
    }


    @Override
    public void loop() {
        gyro.readGyro();





        switch (state) {
            case STEP1:

                if(MecDrive.EncoderEqualsTarget(5, 2300)){
                    MecDrive.setPower(0);
                    MecDrive.MecanumSetMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    state=States.STEP2;
                }

                break;

            case STEP2:
            MecDrive.MecanumGyroRotate(gyro.getZ(), 32);
            if(gyro.getZ()>30 && gyro.getZ()<34){

                MecDrive.initEncoders();
                MecDrive.SetTargetPosition(2000);
                MecDrive.setPower(.5);
                state = States.STEP3;
                time.reset();
                time.startTime();
            }

                break;

            case STEP3:
                if(MecDrive.EncoderEqualsTarget(5, 1000)){
                    stoneIntake.stop();
                }





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
        telemetry.addData("BL POS", MecDrive.BL.getCurrentPosition()); }
}
