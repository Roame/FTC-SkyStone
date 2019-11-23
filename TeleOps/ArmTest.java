package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.StoneArmSystem;

@TeleOp(name = "Arm test")
public class ArmTest extends OpMode {
    StoneArmSystem arm = new StoneArmSystem();

    Telemetry.Item yInput = telemetry.addData("Input", gamepad1.left_stick_y);

    @Override
    public void init() {
        arm.init(hardwareMap);
        telemetry.setAutoClear(false);

        //yInput = telemetry.addData("Input", gamepad1.left_stick_y);
        yInput.setValue(gamepad1.left_stick_y);
        telemetry.update();
    }

    @Override
    public void loop() {
        arm.setTargetPower(gamepad1.left_stick_y);

        yInput.setValue(gamepad1.left_stick_y);
    }
}
