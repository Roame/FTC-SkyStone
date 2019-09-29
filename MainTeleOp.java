package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;

@TeleOp(name = "Main TeleOp", group = "Competition")
public class MainTeleOp extends OpMode {
    Constants constants = new Constants();
    MecanumDrivetrain mecDrive = new MecanumDrivetrain();

    @Override
    public void init() {
        mecDrive.initDrive(hardwareMap);
    }
  
    @Override
    public void loop() {
    }
}
