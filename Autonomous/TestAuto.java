package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Sensors.GyroSensor;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;

import java.io.BufferedReader;

@Autonomous(name = "Test Auto")
public class TestAuto extends OpMode {
    ElapsedTime time = new ElapsedTime();
    MecanumDrivetrain mecDrive = new MecanumDrivetrain();
    GyroSensor Gyro = new GyroSensor();

    @Override
    public void init() {
        mecDrive.initMecanum(hardwareMap);
        time.reset();
        Gyro.GyroInit(hardwareMap);

    }


    @Override
    public void start() {
        time.startTime();
    }


    @Override
    public void loop() {
        Gyro.readGyro();
        telemetry.addData("GyroX: ", Gyro.getX());
        telemetry.addData("GyroY: ", Gyro.getY());
        telemetry.addData("GyroZ: ", Gyro.getZ());



        mecDrive.MecanumRotate((Gyro.getZ()*0.01));


    }
}
