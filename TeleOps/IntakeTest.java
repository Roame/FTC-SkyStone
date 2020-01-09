package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.StoneIntake;
import org.firstinspires.ftc.teamcode.Utility.MecInput;
import org.firstinspires.ftc.teamcode.Utility.MecanumPower;

@TeleOp(name = "Intake Test", group = "Competition")
public class IntakeTest extends OpMode {
    MecanumDrivetrain mecDrive = new MecanumDrivetrain();
    StoneIntake stoneIntake = new StoneIntake();
    MecInput input = new MecInput();

    @Override
    public void init() {
        mecDrive.initMecanum(hardwareMap);
        stoneIntake.initStoneIntake(hardwareMap);
    }
  
    @Override
    public void loop() {
        input.TY = -gamepad1.right_stick_y;
        input.TX = gamepad1.right_stick_x;
        input.R = gamepad1.left_stick_x;

        mecDrive.MecanumDrive(mecDrive.calcMecanumPower(input));

        if(gamepad1.right_bumper) {
            stoneIntake.intakePower(0.5);
        } else if (gamepad1.left_bumper){
            stoneIntake.intakePower(-0.5);
        } else {
            stoneIntake.intakePower(0);
        }
    }
}
