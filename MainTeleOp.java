package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.SlideDrivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.StoneIntake;

@TeleOp(name = "Main TeleOp", group = "Competition")
public class MainTeleOp extends OpMode {
    Constants constants = new Constants();
    MecanumDrivetrain mecDrive = new MecanumDrivetrain(hardwareMap);
    StoneIntake stoneIntake = new StoneIntake(hardwareMap);

    @Override
    public void init() {
        mecDrive.initMecanum();
        stoneIntake.initStoneIntake();
    }
  
    @Override
    public void loop() {
    }
}
