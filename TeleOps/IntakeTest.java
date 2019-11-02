package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.StoneIntake;

@TeleOp(name = "Intake Test", group = "Competition")
public class IntakeTest extends OpMode {
    StoneIntake stoneIntake = new StoneIntake();

    @Override
    public void init() {
        stoneIntake.initStoneIntake(hardwareMap);
    }

    @Override
    public void loop() {

        if(gamepad1.right_bumper) {
            stoneIntake.intakePower(0.5);
        } else if (gamepad1.left_bumper){
            stoneIntake.intakePower(-0.5);
        } else {
            stoneIntake.intakePower(0);
        }
    }
}