package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.SlideDrivetrain;

@TeleOp(name = "Main TeleOp", group = "Competition")
public class MainTeleOp extends OpMode {
    Constants constants = new Constants();
    MecanumDrivetrain mecDrive = new MecanumDrivetrain();
    SlideDrivetrain slideDrive = new SlideDrivetrain();

    @Override
    public void init() {
        mecDrive.initMecanum(hardwareMap);
        slideDrive.initSlide(hardwareMap);
    }
  
    @Override
    public void loop() {
    }
}
