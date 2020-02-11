package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;

@Autonomous(name = "Motor Test")
public class WheelMotorTest extends OpMode {

    MecanumDrivetrain drivetrain = new MecanumDrivetrain();
    Telemetry.Item FRO, FLO, BRO, BLO;

    @Override
    public void init() {
        drivetrain.initMecanum(hardwareMap);
        drivetrain.initEncoders();

        FRO = telemetry.addData("FR", 0);
        FLO = telemetry.addData("FL", 0);
        BRO = telemetry.addData("BR", 0);
        BLO = telemetry.addData("BL", 0);
    }

    @Override
    public void loop() {
        FRO.setValue(drivetrain.FR.getCurrentPosition());
        FLO.setValue(drivetrain.FL.getCurrentPosition());
        BRO.setValue(drivetrain.BR.getCurrentPosition());
        BLO.setValue(drivetrain.BL.getCurrentPosition());
    }
}
