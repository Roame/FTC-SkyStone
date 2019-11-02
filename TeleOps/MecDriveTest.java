package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.Utility.MecanumPower;
import org.firstinspires.ftc.teamcode.Utility.Vector2;


@TeleOp(name="Mecanum Drive Test")
public class MecDriveTest extends OpMode {
    MecanumDrivetrain drivetrain = new MecanumDrivetrain();
    MecanumPower mecPower = new MecanumPower();
    Vector2 input;
    double rotation;

    @Override
    public void init() {
        //drivetrain.initMecanum(hardwareMap);
        input = new Vector2();
    }

    @Override
    public void loop() {

        //input.setCoordinates(gamepad1.right_stick_x, -gamepad1.right_stick_y);
        //rotation = gamepad1.left_stick_x;

        input.setCoordinates(1, 0);
        rotation = 1;

        mecPower = drivetrain.calcTranslation(input);
        telemetry.addData("Trans X: ", mecPower.FLPower);
        telemetry.addData("Trans Y: ", mecPower.FRPower);

        mecPower = drivetrain.calcRotation(drivetrain.calcTranslation(input), rotation);
        telemetry.addData("With Rot Front Left: ", mecPower.FLPower);
        telemetry.addData("With Rot Back Left: ", mecPower.BLPower);
        telemetry.addData("With Rot Front Right: ", mecPower.FRPower);
        telemetry.addData("With Rot Back Right: ", mecPower.BRPower);

        /*
        telemetry.addData("Rotation: ", gamepad1.left_stick_x);
        telemetry.addData("Trans x: ", gamepad1.right_stick_x);
        telemetry.addData("Trans y: ", -gamepad1.right_stick_y);

        drivetrain.mecanumMappedDrive(input, rotation);
        */

    }
}
