package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Main TeleOp", group = "Competition")
public class MainTeleOp extends OpMode {
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
