package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.StoneIntake;

@TeleOp(name = "Main TeleOp", group = "Competition")
public class MainTeleOp extends OpMode {
    MecanumDrivetrain mecDrive = new MecanumDrivetrain();
    StoneIntake stoneIntake = new StoneIntake();

    @Override
    public void init() {
        mecDrive.initMecanum(hardwareMap);
        stoneIntake.initStoneIntake(hardwareMap);
    }
  
    @Override
    public void loop() {
        telemetry.addData("Right X: ", gamepad1.right_stick_x);
        telemetry.addData("Left Stick x: ", gamepad1.left_stick_x);
        telemetry.addData("Right Y:", gamepad1.right_stick_y);


        mecDrive.MecanumDrive(gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_stick_y);

        if(gamepad1.right_bumper) {
            stoneIntake.intakePower(0.5);
        } else if (gamepad1.left_bumper){
            stoneIntake.intakePower(-0.5);
        } else {
            stoneIntake.intakePower(0);
        }
    }
}
