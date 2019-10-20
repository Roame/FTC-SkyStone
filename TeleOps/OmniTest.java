package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.SlideDrivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.StoneIntake;

@TeleOp(name = "Omnitest", group = "Competition")
public class OmniTest extends OpMode {
    SlideDrivetrain slideDrive = new SlideDrivetrain();
    //StoneIntake stoneIntake = new StoneIntake();

    @Override
    public void init() {
        slideDrive.initSlide(hardwareMap);
        //stoneIntake.initStoneIntake(hardwareMap);
    }
  
    @Override
    public void loop() {
        telemetry.addData("Left X: ", gamepad1.left_stick_x);
        telemetry.addData("Right X: ", gamepad1.right_stick_x);

        slideDrive.SlideDrive(gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_stick_y);
    }
}
