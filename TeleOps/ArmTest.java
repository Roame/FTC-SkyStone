package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.StoneArmSystem;

@TeleOp(name = "Arm test")
public class ArmTest extends OpMode {
    StoneArmSystem arm;
    ElapsedTime time;
    Telemetry.Item input;

    @Override
    public void init() {
        Telemetry.Item item = telemetry.addData("Ouput", "waiting");
        input = telemetry.addData("Ticks", 0);

        arm = new StoneArmSystem(item);
        arm.init(hardwareMap);

        time = new ElapsedTime();
        time.reset();

        telemetry.setAutoClear(false);
        telemetry.update();
    }

    @Override
    public void loop() {
        arm.scrollPosition(-gamepad1.left_stick_y,time.seconds());
        input.setValue(arm.armMotor.getCurrentPosition());


    }
}
