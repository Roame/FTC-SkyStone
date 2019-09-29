package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Const;

@TeleOp(name = "Main TeleOp", group = "Competition")
public class MainTeleOp extends OpMode {
    Constants constants = new Constants()
    MecanumDrive MecD = new MecanumDrive();

    @Override
    public void init() {
        MecD.InitDrive(hardwareMap);
    }
  
    @Override
    public void loop() {
        MecD.Drive(gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_stick_y);
    }
}
