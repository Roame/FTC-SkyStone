package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.Utility.MecInput;


@TeleOp(name="Mecanum Drive Test")
public class MecDriveTest extends OpMode {
    MecanumDrivetrain drivetrain = new MecanumDrivetrain();
    MecInput mecPower = new MecInput();

    @Override
    public void init() {
        drivetrain.initMecanum(hardwareMap);
    }

    @Override
    public void loop() {

        mecPower.TY = -gamepad1.right_stick_y;
        mecPower.TX = gamepad1.right_stick_x;
        mecPower.R = gamepad1.left_stick_x;

        telemetry.addData("Rotation: ", mecPower.R);
        telemetry.addData("Trans x: ", mecPower.TX);
        telemetry.addData("Trans y: ", mecPower.TY);

        drivetrain.MecanumDrive(drivetrain.calcMecanumPower(mecPower));

    }
}
