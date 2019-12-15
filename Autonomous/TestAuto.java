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
    public GyroSensor gyro = new GyroSensor();
    public enum States {
        STEP1, STEP2, STEP3, STEP4
    }
    States state = States.STEP1;
    @Override
    public void init() {
        MecDrive.initMecanum(hardwareMap);
        gyro.GyroInit(hardwareMap);
        MecDrive.initEncoders();
    }

    @Override
    public void init_loop() {
        super.init_loop();
    }

    @Override
    public void start() {
        time.reset();
        time.startTime();
    }


    @Override
    public void loop() {
        gyro.readGyro();





        switch (state){
            case STEP1:
                boolean targetSet = true;
                if(targetSet) {
                    MecDrive.SetTargetPos ition(MecDrive.InchToTick(5.0));
                    targetSet = false;
                }
                MecDrive.MecanumSetMode(DcMotor.RunMode.RUN_USING_ENCODER);
                MecDrive.MecanumStraight(0.2);
                if(MecDrive.FR.getPower()==0){
                    state = States.STEP2;
                    targetSet = true;
                }

                break;

            case STEP2:
//
//                MecDrive.SetTargetPosition(MecDrive.InchToTick(-500.0));
//                MecDrive.MecanumSetMode(DcMotor.RunMode.RUN_USING_ENCODER);
//                MecDrive.MecanumStraight(-0.2);
                MecDrive.MecanumSetMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                MecDrive.MecanumStraight(0.0);
//                if(!MecDrive.FR.isBusy()){
//                    state = States.STEP3;
//                }
                break;

            case STEP3:
            MecDrive.MecanumStraight(0);
                break;

        }
        telemetry.addData("State: ", state);
        telemetry.addData("FR Busy: ", MecDrive.FR.getPower()!=0);
        telemetry.addData("GyroZ: ", gyro.getZ());
        telemetry.addData("Mode: ", MecDrive.FR.getMode());
        telemetry.addData("Target Pos: ", MecDrive.FR.getCurrentPosition());
        telemetry.addData("5-5: ", 0);
    }
}
