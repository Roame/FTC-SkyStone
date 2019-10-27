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
        telemetry.addData("Dist: ", Gyro.getZ()-(-90));
        telemetry.addData("Closest distance", mecDrive.FindRotationAngle(Gyro.getZ(), -90));
        mecDrive.MecanumGyroStraight(.2, Gyro.getZ(), -90);




    }
}
