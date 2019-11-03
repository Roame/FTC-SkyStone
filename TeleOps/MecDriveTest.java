package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.Utility.MecanumPower;
import org.firstinspires.ftc.teamcode.Utility.Vector2;


@TeleOp(name="Mecanum Drive Test")
public class MecDriveTest extends OpMode {
    MecanumDrivetrain drivetrain = new MecanumDrivetrain(telemetry);
    MecanumPower mecPower = new MecanumPower();
    Vector2 input;
    double rotation;

    @Override
    public void init() {
        drivetrain.initMecanum(hardwareMap);
        telemetry.setAutoClear(true);
        input = new Vector2();
    }

    @Override
    public void loop() {

        telemetry.addData("Stick X", gamepad1.right_stick_x);
        telemetry.addData("Stick Y", -gamepad1.right_stick_y);
        input.setCoordinates(gamepad1.right_stick_x, -gamepad1.right_stick_y);
        rotation = gamepad1.left_stick_x;

        mecPower = drivetrain.calcTranslation(input);

        mecPower = drivetrain.calcRotation(drivetrain.calcTranslation(input), rotation);

        drivetrain.mecanumMappedDrive(input, rotation);


    }
}
