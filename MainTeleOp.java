package org.firstinspires.ftc.teamcode;

import android.transition.Slide;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.SlideDrivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.StoneIntake;

@TeleOp(name = "Main TeleOp", group = "Competition")
public class MainTeleOp extends OpMode {
    SlideDrivetrain slideDrive = new SlideDrivetrain();
    StoneIntake stoneIntake = new StoneIntake();

    @Override
    public void init() {
        slideDrive.initSlide(hardwareMap);
        stoneIntake.initStoneIntake(hardwareMap);
    }
  
    @Override
    public void loop() {
        slideDrive.SlideDrive(gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_stick_y);
    }
}
