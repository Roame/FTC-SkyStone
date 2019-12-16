package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Utility.ControlSystems.EncMotor;

@TeleOp(name = "PID Test", group = "Test")
public class PIDTest extends OpMode {
    EncMotor motor = new EncMotor("IRM", 560);

    @Override
    public void init() {
        motor.init(hardwareMap);
        motor.configPositionPID(0.003,0.00001,0.002);
        motor.configVelocityPID(0.01, 0.002, 0.002);
    }

    @Override
    public void loop() {
        motor.setVelocityPID(-2*Math.PI);
        System.out.println(motor.getVelocity());
        motor.update();
    }
}
