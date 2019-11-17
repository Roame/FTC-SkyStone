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
    @Override
    public void init() {
        MecDrive.initMecanum(hardwareMap);
        MecDrive.initEncoders();
        gyro.GyroInit(hardwareMap);
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
        if(time.seconds()<4) {
            MecDrive.SetTargetPosition(MecDrive.InchToTick(25.0));
            MecDrive.MecanumStraight(0.25);
        }
        if(time.seconds()>4 && time.seconds() < 5){
            MecDrive.MecanumGyroRotate(gyro.getZ(), 90);
        }
        if(time.seconds()>5 && time.seconds()<9) {
            MecDrive.SetTargetPosition(MecDrive.InchToTick(25.0));
            MecDrive.MecanumStraight(0.25);
        }
        if(time.seconds()>9 && time.seconds() < 10){
            MecDrive.MecanumGyroRotate(gyro.getZ(), 90);
        }
        if(time.seconds()>10 && time.seconds() < 14) {
            MecDrive.SetTargetPosition(MecDrive.InchToTick(25.0));
            MecDrive.MecanumStraight(0.25);
        }
        if(time.seconds()>14 && time.seconds() < 15){
            MecDrive.MecanumGyroRotate(gyro.getZ(), 90);
        }
        if(time.seconds()>15 && time.seconds()<19) {
            MecDrive.SetTargetPosition(MecDrive.InchToTick(25.0));
            MecDrive.MecanumStraight(0.25);
        }
        if(time.seconds()>19 && time.seconds() < 20){
            MecDrive.MecanumGyroRotate(gyro.getZ(), 90);
        }
        telemetry.addData("GyroZ :", gyro.getZ());
    }
}
